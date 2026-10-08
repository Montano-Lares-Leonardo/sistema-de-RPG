package gameData;

public class Character extends Common {
    public int maxHP;
    public int curHP;
    public int maxMP;
    public int curMP;
    public int atk;
    public int def;
    public int spd;
    public boolean[] status1 = new boolean[50];
    public int[] statusCounter1 = new int[50];
    public int[] moves = new int[20];
    public int moveCount;
    public int[] equip = new int[4]; //0 = helmet, 1 = armor, 2 = accessory, 3 = weapon
    public int[] equipType = new int[4];//0 = helmet type, 1 = armor type, 2 = accessory type(this one always stays 0 since all accesories are worn by all), 3 = weapon type //este decide el tipo de equipamiento que utiliza un personaje, asi que por ejemplo, si un personaje solo usa armas de tipo 1, seria equipType[3] = 1;
    protected boolean side;
    public int exp;
    public int lvl;
    protected int turnCount;
    protected int exh;
    public Character(int id){
        super(id);
        maxHP = 0;
        curHP = 0;
        maxMP = 0;
        curMP = 0;
        atk = 999;
        def = 0;
        spd = 0;
        moveCount = 0;
        for (int i = 0; i < 20; i++){
            moves[i] = 0;
        }
        for (int i = 0; i < 50; i++){
            status1[i] = false;
            statusCounter1[i] = 0;
        }
        for (int i = 0; i < 4; i++) {
            equip[i] = 0;
            equipType[i] = 0;
        }
        side = false;
        lvl = 1;
        exp = 0;
        turnCount = 0;
        exh = 0;
    }
    public void assign(String name, String desc, int maxhp, int maxmp, int atk, int def, int spd, int[] a, int[] b, int[] c){
        super.assign(name, desc);
        this.maxHP = maxhp;
        curHP = maxhp;
        this.maxMP = maxmp;
        curMP = maxmp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        System.arraycopy(a, 0, moves, 0, a.length);
        System.arraycopy(b, 0, equip, 0, b.length);
        System.arraycopy(c, 0, equipType, 0, c.length);
        moveCount = moves.length;
        side = true;
    }

    public void assign(String name, String desc, int maxhp, int maxmp, int atk, int def, int spd, int[] a, int exp){
        super.assign(name, desc);
        this.maxHP = maxhp;
        curHP = maxhp;
        this.maxMP = maxmp;
        curMP = maxmp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        System.arraycopy(a, 0, moves, 0, a.length);
        moveCount = moves.length;
        this.exp = exp;
        side = false;
    }

    public void printNames(StatusEffect[] statusEffects){
        System.out.print(name + "  HP: " + curHP + "/" + maxHP);
        for (int i = 0; i < 50; i++){
            if (status1[i]){
                System.out.print(" (" + statusEffects[i].name);
                if(statusCounter1[i] > 0) System.out.print(": " + statusCounter1[i]);
                System.out.print(")");
            }
        }
        System.out.println();
    }

    public void statCheck(){
        if(maxHP < curHP) curHP = maxHP;
        if(maxMP < curMP) curMP = maxMP;
        if((curHP <= 0) && (!status1[0])){
            if (!name.isEmpty()){
                System.out.println(name + " ya no puede pelear!");
                S.nextLine();
            }
            curHP = 0;
            for (int i = 0; i < 50; i++){
                status1[i] = false;
                statusCounter1[i] = 0;
            }
            status1[0] = true;
        }
        if (curMP < 0) curMP = 0;
    }

    public Character copy(int id){
        Character copy = new Character(id);
        copy.assign(name, desc);
        copy.maxHP = maxHP;
        copy.curHP = curHP;
        copy.maxMP = maxMP;
        copy.curMP = curMP;
        copy.atk = atk;
        copy.def = def;
        copy.spd = spd;
        System.arraycopy(status1, 0, copy.status1, 0, status1.length);
        System.arraycopy(statusCounter1, 0, copy.statusCounter1, 0, statusCounter1.length);
        System.arraycopy(moves, 0, copy.moves, 0, moves.length);
        System.arraycopy(equip, 0, copy.equip, 0, equip.length);
        System.arraycopy(equipType, 0, copy.equipType, 0, equipType.length);
        copy.moveCount = copy.moves.length;
        copy.side = side;
        copy.exp = exp;
        copy.lvl = lvl;
        return copy;
    }

    public boolean[] restrictionCheck(boolean[] restriction, StatusEffect[] statusEffects){
        for (int i = 0; i < 50; i++){
            if (status1[i]){
                switch (statusEffects[i].effect){
                    case 1:
                        restriction[0] = false;
                        break;
                    case 2:
                        restriction[0] = RNG(50);
                        break;
                    case 3:
                        restriction[1] = false;
                        break;
                    case 4:
                        restriction[2] = false;
                        break;
                    case 5:
                        restriction[3] = false;
                        break;
                    case 7:
                        restriction[4] = false;
                        break;
                }
            }
        }
        return restriction;
    }

    public boolean restrictionCheckConfused(StatusEffect[] statusEffects){
        boolean restriction = true;
        for (int i = 0; i < 50; i++){
            if ((status1[i]) && (statusEffects[i].effect == 7)) {
                restriction = false;
                break;
            }
        }
        return restriction;
    }

    public boolean restrictionCheckHeal(StatusEffect[] statusEffects){
        boolean restriction = true;
        for (int i = 0; i < 50; i++){
            if ((status1[i]) && (statusEffects[i].effect == 6)) {
                restriction = false;
                break;
            }
        }
        return restriction;
    }

    public void statusCheck(StatusEffect[] statusEffects){
        for (int i = 0; i < 50; i++){
            if (status1[i]){
                if(!statusEffects[i].desc.isEmpty()){
                    System.out.println(name + " " + statusEffects[i].desc);
                    S.nextLine();
                }
                switch (statusEffects[i].effect){
                    case 8:
                        curHP -= 1;
                        break;
                    case 9:
                        curMP -= 1;
                        break;
                }
                if(!statusEffects[i].infinite) {
                    statusCounter1[i] -= 1;
                    if(statusCounter1[i] <= 0) {
                        status1[i] = false;
                        statusCounter1[i] = 0;
                        System.out.println(name + " " + statusEffects[i].cureText);
                    }
                }
            }
        }
    }

    public int expRequirement(){
        return 8 + (int) ((Math.pow(lvl, 2)) / (Math.pow(lvl, 0.6571)));
    }
}
