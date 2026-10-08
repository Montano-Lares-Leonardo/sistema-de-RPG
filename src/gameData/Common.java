package gameData;

import java.io.Serializable;
import java.util.Scanner;

abstract public class Common implements Serializable {
    public static Scanner S = new Scanner(System.in);
    public int id;
    public String name;
    public String desc;
    public Common(int id){
        this.id = id;
        name = "";
        desc = "";
    }

    public void assign(String name, String desc){
        this.name = name;
        this.desc = desc;
    }
    public String printSelf(){
        return (name + "  " + desc);
    }

    public static boolean RNG(int chance){
        return chance >= (int) (Math.random() * 100 + 1);
    }

    public static int choice(int limit){
        int choice;
        try {
            choice = S.nextInt();
        } catch (Exception e){
            choice = 0;
            S.next();
        }
        if ((choice > limit) || (choice < 0)){
            choice = 0;
        }
        return choice;
    }

    public static void compress(int[] array, int empty){
        for (int i = 0; i < array.length; i++) {
            if ((array[i] == empty) && (i < (array.length - 1))) {
                if (array[i + 1] != empty) {
                    for (int j = i; j < (array.length - 1); j++) {
                        array[j] = array[j + 1];
                    }
                    array[array.length - 1] = empty;
                }
            }
        }
    }

    public static int actuallyUsedSpace(int[] array, int empty){
        int arrayLength = 0;
        boolean over = true;
        while ((arrayLength < array.length) && (over)){
            if (array[arrayLength] == empty){
                over = false;
            } else {
                arrayLength++;
            }
        }
        return arrayLength;
    }
}
