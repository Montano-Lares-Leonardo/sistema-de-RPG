package gameData;

public class Move extends Action {
    private boolean pierce; //0 = affected by defense, 1 = ignores defense
    private int repeat;
    private int dmgMult;

    public Move(int id){
        super(id);
        pierce = false;
        repeat = 0;
        cost = 0;
        costHP = 0;
        dmgMult = 0;
    }
    @Override
    public void assign(int cost, int costHP, String name, String desc, String text, int targetType, int dmgAdd, int dmgMult, int status, int statusDuration, int heal, int healMP, int cure, boolean pierce, int repeat){
        super.assign(name, desc, text, targetType, heal, cure, healMP, dmgAdd, status, statusDuration);
        this.dmgMult = dmgMult;
        this.repeat = repeat;
        this.pierce = pierce;
    }

    @Override
    public gameData.Character dmgTrue(Character[] characters, StatusEffect[] statusEffects, int user, int target) {
        int dmg;
        for (int i = 0; i < repeat; i++){
            if (((cure == 1) || (cure == 101)) && (characters[target].status1[0])){
                characters[target].status1[0] = false;
                System.out.println(characters[target].name + " revivio!");
            }
            if (!characters[target].status1[0]){
                if ((dmgAdd > 0) || (dmgMult > 0)) {
                    dmg = (characters[user].atk * dmgMult) + dmgAdd - characters[user].exh;
                    if (!pierce) dmg -= characters[target].def;
                    dmg = Math.min(dmg, characters[target].curHP);
                    if (dmg < 0) dmg = 0;
                    System.out.println(characters[target].name + " sufrio " + dmg + " de daño!");
                    characters[target].curHP -= dmg;
                }
                characters[target] = super.dmgTrue(characters, statusEffects, user, target);
            }
        }
        return characters[target];
    }
}
