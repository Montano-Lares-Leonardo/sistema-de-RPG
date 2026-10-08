package gameData;

public class Room extends Common {
    public int options;
    public String[] optionName = new String[10];
    public int[][] effect = new int[5][10];
    public String[][] effectText = new String[5][10];
    public int[][] effectID = new int[5][10]; //la id del cuarto al que quieres ir, la id del objeto que quieres dar, etc etc
    public Room(int id) {
        super(id);
        options = 0;
        for (int i = 0; i < 10; i++) {
            optionName[i] = "";
            for (int j = 0; j < 5; j++) {
                effect[j][i] = 0;
                effectText[j][i] = "";
                effectID[j][i] = 0;
            }
        }
    }
    public void assignBasic(int options, String name, String desc){
        super.assign(name, desc);
        this.options = options;
    }
    public void assignOption(int optionNum, String optionName, int[] effect, String[] effectText, int[] effectID){
        this.optionName[optionNum] = optionName;
        for (int i = 0; i < 5; i++){
            this.effect[i][optionNum] = effect[i];
            this.effectText[i][optionNum] = effectText[i];
            this.effectID[i][optionNum] = effectID[i];
        }
    }
    public void assignSingleOption(int optionNum, int rowNum, String optionName, int effect, String effectText, int effectID){
        if(rowNum == 0) this.optionName[optionNum] = optionName;
        this.effect[rowNum][optionNum] = effect;
        this.effectText[rowNum][optionNum] = effectText;
        this.effectID[rowNum][optionNum] = effectID;
    }
    public void optionsCheck(){
        for (int i = 0; i < 9; i++){
            if ((optionName[i].isEmpty()) && (!optionName[(i + 1)].isEmpty())){
                for (int j = i; j < 9; j++) {
                    optionName[j] = optionName[(j + 1)];
                    for (int k = 0; k < 5; k++) {
                        effect[k][j] = effect[k][(j + 1)];
                        effectText[k][j] = effectText[k][(j + 1)];
                        effectID[k][j] = effectID[k][(j + 1)];
                    }
                }
                options -= 1;
                break;
            }
        }
    }
}
