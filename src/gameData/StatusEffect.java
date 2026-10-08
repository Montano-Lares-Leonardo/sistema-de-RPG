package gameData;

public class StatusEffect extends Common {
    public int effect; //1 = can't move, 2 = can sometimes move (paralysis pokemon), 3 = can't use abilities, 4 = can't use items, 5 = can't use the normal move, 6 = can't eat / can't be healed, 7 = attacks randomly, 8 = dmg over time, 9 = MP dmg over time
    public boolean infinite;
    public String infectText;
    public String cureText;
    public StatusEffect(int id){
        super(id);
        effect = 0;
        infinite = false;
        infectText = "";
        cureText = "";
    }
    public void assign(int effect, boolean infinite, String name, String text, String infectText, String cureText){
        super.assign(name, text);
        this.effect = effect;
        this.infinite = infinite;
        this.infectText = infectText;
        this.cureText = cureText;
    }
}
