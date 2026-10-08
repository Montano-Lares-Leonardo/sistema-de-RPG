package gameData;

public class Item extends Action {
    public int costMoney;

    public Item(int id) {
        super(id);
        costMoney = 0;
    }

    @Override
    public void assign(int cost, int costHP, String name, String desc, String text, int target, int dmgAdd, int dmgMult, int status, int statusDuration, int heal, int healMP, int cure, boolean pierce, int repeat) {
        //ignore this lmao
    }

    @Override
    public gameData.Character dmgTrue(Character[] characters, StatusEffect[] statusEffects, int user, int target){
        int dmg;
        if (((cure == 1) || (cure == 101)) && (characters[target].status1[0])){
            characters[target].status1[0] = false;
            System.out.println(characters[target].name + " revivio!");
        }
        if (!characters[target].status1[0]){
            if (dmgAdd > 0){
                dmg = Math.min(dmgAdd, characters[target].curHP);
                System.out.println(characters[target].name + " sufrio " + dmg + " de daño!");
                characters[target].curHP -= dmg;
            }
            characters[target] = super.dmgTrue(characters, statusEffects, user, target);
        }
        return characters[target];
    }
}