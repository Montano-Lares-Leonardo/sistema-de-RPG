package gameData;

public class Equip extends KeyItem {
    public int category; //0 = head, 1 = body, 2 = accessory, 3 = weapon //esto define de que tipo de equipamiento, por ejemplo, un casco seria category = 0;
    public int type; //if type = 0, that means it can be used by everyone //este define que tipo de equipamiento es, pero como, por ejemplo seria todas las espadas serian type = 1;
    public int hp; //y todos estos son los stats que mejoran al ponerse la armadura.
    public int mp;
    public int atk;
    public int def;
    public int spd;

    public Equip(int id) {
        super(id);
        category = 0;
        type = 0;
        hp = 0;
        mp = 0;
        atk = 0;
        def = 0;
        spd = 0;
    }

    public void assign(String name, String desc, int category, int type, int hp, int mp, int atk, int def, int spd) {
        super.assign(name, desc);
        this.category = category;
        this.type = type;
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
    }

    public void printSelf(int armorMenuThing) {
        System.out.print("\n  " + (armorMenuThing + 1) + ". " + name);
        if (id != 0) {
            System.out.print("x" + counter + "  " + desc + "  ");
            if (atk > 0) System.out.print("+" + atk + "ATK  ");
            else if (atk < 0) System.out.print("-" + atk + "ATK  ");
            if (def > 0) System.out.print("+" + def + "DEF  ");
            else if (def < 0) System.out.print("-" + def + "DEF  ");
            if (spd > 0) System.out.print("+" + spd + "SPD  ");
            else if (spd < 0) System.out.print("-" + spd + "SPD  ");
            if (hp > 0) System.out.print("+" + hp + "HP  ");
            else if (hp < 0) System.out.print("-" + hp + "HP  ");
            if (mp > 0) System.out.print("+" + mp + "MP  ");
            else if (mp < 0) System.out.print("-" + mp + "MP  ");
        }
    }

    public gameData.Character takeaway(gameData.Character character) {
        character.atk -= atk;
        character.def -= def;
        character.spd -= spd;
        character.maxHP -= hp;
        character.maxMP -= mp;
        return character;
    }

    public gameData.Character give(Character character) {
        character.atk += atk;
        character.def += def;
        character.spd += spd;
        character.maxHP += hp;
        character.maxMP += mp;
        return character;
    }
}
