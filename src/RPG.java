import gameData.Common;

import java.io.*;

public class RPG{
    public static void printSaves(){
        for (int i = 1; i <= 4; i++){
            try {
                String fileName = "saveFile" + i + ".txt";
                FileInputStream fin = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fin);
                Game tempGame = (Game) ois.readObject();
                ois.close();
                if (tempGame.started){
                    System.out.println("  " + i + ". Ubicación: " + tempGame.rooms[tempGame.roomid].name);
                    System.out.print("     Personajes:");
                    for (int j = 0; j < 3; j++){
                        if (!tempGame.protag[tempGame.party[j]].name.isEmpty()) System.out.print("  " + tempGame.protag[tempGame.party[j]].name);
                    }
                    System.out.println();
                } else {
                    System.out.println("  " + i + ". Vacio");
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        Game basicGame = new Game();
        boolean reset = false;
        int playing = 1;
        basicGame.defaultSave();
        if (reset){
            for (int i = 1; i <= 4; i++){
                try {
                    String fileName = "saveFile" + i + ".txt";
                    FileOutputStream fos = new FileOutputStream(fileName);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(basicGame);
                    oos.close();
                    System.out.println(fileName + " successfully full.");
                } catch (IOException e) {
                    System.out.println("Hubo un error catastrofico.");
                }
            }
        }
        while(playing > 0){
            System.out.println("RPG Para Desarrollo II");
            Common.S.nextLine();
            System.out.println("  1. Nuevo Juego\n  2. Continuar\n  3. Salir");
            int choice = Common.choice(3);
            switch (choice){
                case 1:
                    basicGame.play();
                    break;
                case 2:
                    printSaves();
                    int choice2 = Common.choice(4);
                    if (choice2 != 0){
                        try {
                            String fileName = "saveFile" + choice2 + ".txt";
                            FileInputStream fin = new FileInputStream(fileName);
                            ObjectInputStream ois = new ObjectInputStream(fin);
                            Game continuedGame = (Game) ois.readObject();
                            ois.close();
                            continuedGame.play();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                default:
                    System.out.println("Bye~!");
                    playing -= 1;
                    break;
            }
        }
    }
}
