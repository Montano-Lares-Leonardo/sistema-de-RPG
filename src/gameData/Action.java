package gameData;

abstract public class Action extends Common {
    protected String text;
    public int heal;
    public int healMP;
    public int cure; //put this as 100 to heal everything but death, to 101 to heal everything AND death
    public int targetType; //0 = yourself, 1 = enemigo, 2 = aliado, 3 = todos enemigos, 4 = todos aliados
    public int dmgAdd;
    public int status;
    protected int statusDuration;
    public int cost;
    protected int costHP;

    public Action(int id){
        super(id);
        text = "";
        heal = 0;
        healMP = 0;
        cure = 0;
        targetType = 0;
        dmgAdd = 0;
        status = 0;
        statusDuration = 0;
        cost = 0;
        costHP = 0;
    }

    public void assign(String name, String desc, String text, int targetType, int heal, int cure, int healMP, int dmgAdd, int status, int statusDuration) {
        super.assign(name, desc);
        this.text = text;
        this.targetType = targetType;
        this.dmgAdd = dmgAdd;
        this.status = status;
        this.statusDuration = statusDuration;
        this.heal = heal;
        this.cure = cure;
        this.healMP = healMP;
    }

    public abstract void assign(int cost, int costHP, String name, String desc, String text, int target, int dmgAdd, int dmgMult, int status, int statusDuration, int heal, int healMP, int cure, boolean pierce, int repeat);

    public Character[] takeEffect(Character[] characters, StatusEffect[] statusEffects, int user, int menu, int[] party){
        int target = 0, whatevah = 0;
        if(menu != 1){
            party = new int[]{0, 1, 2};
        }
        if (characters[user].restrictionCheckConfused(statusEffects)){
            switch (targetType){
                case 0:
                    target = user;
                    System.out.println(characters[user].name + " " + text);
                    characters[target] = dmgTrue(characters, statusEffects, user, target);
                    break;
                case 1:
                    if(characters[user].side){
                        for (int j = 0; j < 10; j++){
                            if((!characters[j].name.isEmpty()) && (characters[j].side != characters[user].side)){
                                whatevah++;
                                System.out.println(whatevah + ". " + characters[j].name);
                            }
                        }
                        target = choice(whatevah) + 2;
                    } else {
                        while (whatevah == 0){
                            target = (int) (Math.random() * 3);
                            if (!characters[target].name.isEmpty() && (characters[target].curHP > 0)) whatevah = 1;
                        }
                    }
                    if (characters[target].side != characters[user].side){
                        characters[user].curMP -= cost;
                        characters[user].curHP -= costHP;
                        System.out.println(characters[user].name + " " + text);
                        characters[target] = dmgTrue(characters, statusEffects, user, target);
                    }
                    break;
                case 2:
                    if(characters[user].side){
                        for (int j = 0; j < 3; j++){
                            if (!characters[party[j]].name.isEmpty()){
                                whatevah++;
                                System.out.println(whatevah + ". " + characters[party[j]].name);
                            }
                        }
                        target = party[choice(whatevah) - 1];
                    } else {
                        while (whatevah == 0) {
                            target = (int) (Math.random() * 7 + 3);
                            if ((!characters[target].name.isEmpty()) && ((characters[target].curHP > 0) || ((cure == 1) || (cure == 101)))) whatevah = 1;
                        }
                    }
                    if (characters[target].side == characters[user].side){
                        characters[user].curMP -= cost;
                        characters[user].curHP -= costHP;
                        System.out.println(characters[target].name + " " + text);
                        characters[target] = dmgTrue(characters, statusEffects, user, target);
                    }
                    break;
                case 3:
                    characters[user].curMP -= cost;
                    characters[user].curHP -= costHP;
                    System.out.println(characters[user].name + " " + text);
                    for (int j = 0; j < 10; j++){
                        if((!characters[j].name.isEmpty()) && (characters[user].side != characters[j].side)){
                            characters[j] = dmgTrue(characters, statusEffects, user, j);
                        }
                    }
                    break;
                case 4:
                    characters[user].curMP -= cost;
                    characters[user].curHP -= costHP;
                    System.out.println(characters[user].name + " " + text);
                    for (int j = 0; j < 10; j++){
                        if((!characters[j].name.isEmpty()) && (characters[user].side == characters[j].side)){
                            characters[j] = dmgTrue(characters, statusEffects, user, j);
                        }
                    }
                    break;
                default:
                    break;

            }
        } else {
            System.out.println(characters[user].name + " " + text);
            switch (targetType){
                case 0:
                    target = user;
                    characters[target] = dmgTrue(characters, statusEffects, user, target);
                    break;
                case 1:
                case 2:
                    target = (int) (Math.random() * 10);
                    characters[target] = dmgTrue(characters, statusEffects, user, target);
                    break;
                case 3:
                case 4:
                    for (int j = 0; j < 10; j++){
                        characters[j] = dmgTrue(characters, statusEffects, user, j);
                    }
                    break;
            }
        }
        return characters;
    }

    public Character dmgTrue(Character[] characters, StatusEffect[] statusEffects, int user, int target){
        int dmg;
        if ((heal > 0) && (characters[target].restrictionCheckHeal(statusEffects))){
            dmg = Math.min(heal, (characters[target].maxHP - characters[target].curHP));
            System.out.println(characters[target].name + " recupero " + dmg + " HP!");
            characters[target].curHP += dmg;
        } else if (!characters[target].restrictionCheckHeal(statusEffects)) System.out.println(characters[target].name + " No se pudo curar!");
        if (healMP > 0){
            dmg = Math.min(healMP, (characters[target].maxMP - characters[target].curMP));
            System.out.println(characters[target].name + " recupero " + dmg + " MP!");
            characters[target].curMP += dmg;
        }
        if (healMP < 0){
            dmg = Math.min(healMP, characters[target].curMP);
            System.out.println(characters[target].name + " perdio " + dmg + " MP!");
            characters[target].curMP += dmg;
        }
        if (cure != -1){
            if (characters[target].status1[cure]){
                System.out.println(characters[target].name + " " + statusEffects[cure].cureText);
                characters[target].status1[cure] = false;
                characters[target].statusCounter1[cure] = 0;
            }
        }
        if ((cure == 101) || (cure == 100)){
            for (int i = 1; i < 50; i++){
                if (characters[target].status1[i]){
                    System.out.println(characters[target].name + " " + statusEffects[i].cureText);
                    characters[target].status1[i] = false;
                    characters[target].statusCounter1[i] = 0;
                }
            }
        }
        if (status != -1){
            if (!characters[target].status1[status]){
                System.out.println(characters[target].name + " " + statusEffects[status].infectText);
                characters[target].status1[status] = true;
                if(!statusEffects[status].infinite) characters[target].statusCounter1[status] = statusDuration;
            }
        }
        return characters[target];
    }
}
