package gameData;

import java.util.Arrays;
import java.util.Comparator;

public class Battle extends Common {
    private Character[] battler = new Character[10];
    public int battleEnd;
    public int[] itemList = new int[10];
    public static int LEVEL_MAX;

    private int[][] straightUpReversingIt(int[][] X) {
        int A = X.length;
        int[][] Y = new int[A][2];
        for (int i = 0; i < A; i++) {
            Y[i][0] = X[A - (i + 1)][0];
            Y[i][1] = X[A - (i + 1)][1];
        }
        return Y;
    }

    public Battle(Character[] enemy, Character[] protag, int[] party, int[] enemyTroop, StatusEffect[] statusEffects, int[] itemList, Action[] moves, Action[] items, int fleeChance){
        super(0);
        battleEnd = 0;
        int[][] speedCircuit = new int[10][2];
        for(int i = 0; i < 3; i++){
            battler[i] = new Character(i);
            if (party[i] != -1){
                battler[i] = new Character(i);
                battler[i] = protag[party[i]].copy(i);
            }
        }
        for(int i = 3; i < 10; i++){
            battler[i] = new Character(i);
            battler[i] = enemy[enemyTroop[(i - 3)]].copy(i);
        }
        //battle start
        S.nextLine();S.nextLine();
        while(battleEnd == 0){
            //print out names
            System.out.println("\nEnemigos:");
            for (int i = 3; i < 10; i++){
                if (!(battler[i].name.isEmpty())){
                    System.out.print("  ");
                    battler[i].printNames(statusEffects);
                }
            }
            System.out.println("\nAliados:");
            for (int i = 0; i < 3; i++){
                if (!(battler[i].name.isEmpty())){
                    System.out.print("  ");
                    battler[i].printNames(statusEffects);
                }
            }
            S.nextLine();
            for (int i = 0; i < 10; i++){
                speedCircuit[i][0] = battler[i].spd;
                speedCircuit[i][1] = battler[i].id;
            }
            Arrays.sort(speedCircuit, Comparator.comparingInt(a -> a[0]));
            speedCircuit = straightUpReversingIt(speedCircuit);
            for (int i = 0; (i < 10) && (battleEnd == 0); i++){
                int player = speedCircuit[i][1];
                if (!battler[player].name.isEmpty()){
                    boolean[] restriction = new boolean[]{true, true, true, true, true}; //0 = nothing, 1 = normal attack, 2, = abilities, 3 = items, 4 = is confused
                    restriction = battler[player].restrictionCheck(restriction, statusEffects);
                    if (restriction[0]){
                        battler[player].turnCount ++;
                        battler[player].exh = 0;
                        while (battler[player].turnCount > 0){
                            //choosing phase
                            int choiceBattle;
                            int move;
                            if((battler[player].side) && (restriction[4])){
                                System.out.println("¿Que hara " + battler[player].name + "?\n  1. Atacar\n  2. Habilidades\n  3. Objetos\n  4. Estrategia");
                                choiceBattle = choice(4);
                                switch (choiceBattle){
                                    case 1:
                                        if (restriction[1]) battler = moves[0].takeEffect(battler, statusEffects, player, 0, party);
                                        else System.out.println(battler[player].name + " no pudo hacer eso!");
                                        break;
                                    case 2:
                                        if (restriction[2]){
                                            System.out.println("¿Cual movimiento usara " + battler[player].name + "?");
                                            for(int j = 0; j < 20; j++){
                                                if((battler[player].moves[j] > 0) && (!moves[battler[player].moves[j]].name.isEmpty())){
                                                    System.out.print((j + 1) + ": " + moves[battler[player].moves[j]].name);
                                                    if(moves[battler[player].moves[j]].cost > 0) System.out.print("  costo: " + moves[battler[player].moves[j]].cost + " MP");
                                                    if(moves[battler[player].moves[j]].costHP > 0) System.out.print("  costo: " + moves[battler[player].moves[j]].costHP + " HP");
                                                    System.out.println();
                                                }
                                            }
                                            move = choice(actuallyUsedSpace(battler[player].moves, 0)) - 1;
                                            move = battler[player].moves[move];
                                            if (battler[player].curMP >= moves[move].cost) battler = moves[move].takeEffect(battler, statusEffects, player, 0, party);
                                            else System.out.println("No tienes suficiente MP!");
                                        } else System.out.println(battler[player].name + " no pudo hacer eso!");
                                        break;
                                    case 3:
                                        if ((Common.actuallyUsedSpace(itemList, -1) > 0) && (restriction[3])){
                                            System.out.println("¿Cual objeto va a usar " + battler[player].name + "?");
                                            for(int j = 0; j < 10; j++){
                                                if(itemList[j] >= 0){
                                                    System.out.println((j + 1) + ": " + items[itemList[j]].name);
                                                }
                                            }
                                            move = choice(actuallyUsedSpace(itemList, -1)) - 1;
                                            if (move >= 0){
                                                battler = items[itemList[move]].takeEffect(battler, statusEffects, player, 0, party);
                                                itemList[move] = -1;
                                                compress(itemList, -1);
                                            }
                                        } else if (!restriction[3]) System.out.println("No puedes hacer eso!");
                                        else System.out.println("No tienes ningun objeto!");
                                        break;
                                    case 4:
                                        System.out.println("\n  1. Descripción\n  2. No Hacer Nada\n  3. Huir\n  4. Pasar Turno");
                                        int strategyChoice = choice(4);
                                        switch (strategyChoice){
                                            case 1:
                                                for (int j = 0; j < 10; j++){
                                                    if((!battler[j].name.isEmpty()) && (battler[j].side != battler[player].side)){
                                                        System.out.println((j - 2) + ". " + battler[j].name);
                                                    }
                                                }
                                                move = choice(4) + 2;
                                                System.out.println(battler[move].name + ": " + battler[move].desc);
                                                System.out.println("su HP es " + battler[move].maxHP + ", MP es " + battler[move].maxHP + ", ataque es " + battler[move].atk + " y su defensa es " + battler[move].def);
                                                break;
                                            case 2:
                                                break;
                                            case 3:
                                                if(RNG(fleeChance)) {
                                                    battleEnd = 2;
                                                }
                                                else System.out.println("No se pudo Huir!");
                                                fleeChance -= 10;
                                                break;
                                            case 4:
                                                for (int j = 0; j < 10; j++){
                                                    if((!battler[j].name.isEmpty()) && (battler[j].side == battler[player].side)){
                                                        System.out.println((j + 1) + ". " + battler[j].name);
                                                    }
                                                }
                                                move = choice(3) - 1;
                                                battler[move].turnCount++;
                                                System.out.println(battler[player].name + " le paso su turno a " + battler[move].name);
                                                break;
                                        }
                                        break;
                                }
                            } else {
                                move = (int) (Math.random() * battler[player].moveCount);
                                battler = moves[battler[player].moves[move]].takeEffect(battler, statusEffects, player, 0, party);
                            }
                            S.nextLine();
                            battler[player].exh++;
                            battler[player].turnCount -= 1;
                        }
                    }
                }
                for (int j = 0; j < 10; j++){
                    battler[j].statCheck();
                }
                if ((battler[0].curHP <= 0) && (battler[1].curHP <= 0) && (battler[2].curHP <= 0)){
                    battleEnd = 1;
                } else if ((battler[3].curHP <= 0) && (battler[4].curHP <= 0) && (battler[5].curHP <= 0) && (battler[6].curHP <= 0) && (battler[7].curHP <= 0) && (battler[8].curHP <= 0) && (battler[9].curHP <= 0)){
                    battleEnd = 3;
                }
            }
            for (int i = 0; i < 10; i++){
                battler[i].statusCheck(statusEffects);
                battler[i].statCheck();
            }
            if ((battler[0].curHP <= 0) && (battler[1].curHP <= 0) && (battler[2].curHP <= 0)){
                battleEnd = 1;
            } else if ((battler[3].curHP <= 0) && (battler[4].curHP <= 0) && (battler[5].curHP <= 0) && (battler[6].curHP <= 0) && (battler[7].curHP <= 0) && (battler[8].curHP <= 0) && (battler[9].curHP <= 0)){
                battleEnd = 3;
            }
        }
        for(int i = 0; i < 3; i++){
            battler[i].exh = 0;
            battler[i].turnCount = 0;
            for (int j = 0; j < 50; j++){
                if(!statusEffects[j].infinite){
                    battler[i].status1[j] = false;
                    battler[i].statusCounter1[j] = 0;
                }
            }
        }
        switch (battleEnd){
            case 2:
                System.out.println("Escapaste...");
                S.nextLine();
                break;
            case 3:
                System.out.println("Ganaste!!!");
                fleeChance += 5;
                int exp = 0;
                for (int i = 3; i < 10; i++){
                    if (!battler[i].name.isEmpty()){
                        exp += battler[i].exp;
                    }
                }
                S.nextLine();
                battler = levelUp(battler, exp);
                break;
        }
        System.arraycopy(itemList,0,this.itemList,0,itemList.length);
    }

    public Character getBattler(int num) {
        return battler[num];
    }

    private Character[] levelUp(Character[] battler, int exp){
        for (int i = 0; i < 3; i++){
            if ((!battler[i].name.isEmpty()) && (battler[i].lvl <= LEVEL_MAX)){
                battler[i].exp += exp;
                System.out.println(battler[i].name + " obtuvo " + exp + " puntos de experiencia!");
                if (battler[i].exp >= battler[i].expRequirement()){
                    battler[i].exp -= battler[i].expRequirement();
                    battler[i].lvl++;
                    System.out.println(battler[i].name + " subio de nivel!");
                    int lvlupContinyu = 1;
                    while (lvlupContinyu > 0){
                        System.out.println("¿Cual stat mejorar?\n  1. HP\n  2. MP\n  3. ATK\n  4. DEF\n  5. SPD");
                        switch (choice(5)){
                            case 1:
                                battler[i].maxHP += 10;
                                break;
                            case 2:
                                battler[i].maxMP += 10;
                                break;
                            case 3:
                                battler[i].atk += 1;
                                break;
                            case 4:
                                battler[i].def += 1;
                                break;
                            case 5:
                                battler[i].spd += 1;
                                break;
                            default:
                                System.out.println("¿Realmente quieres abandonar la mejora?\n  1. Si\n  2. No");
                                if (choice(2) != 1){
                                    lvlupContinyu++;
                                }
                                break;
                        }
                        lvlupContinyu -= 1;
                    }
                    battler[i].curHP = battler[i].maxHP;
                    battler[i].curMP = battler[i].maxMP;
                    for (int j = 0; j < 50; j++){
                        battler[i].status1[j] = false;
                        battler[i].statusCounter1[j] = 0;
                    }
                }
            }
        }
        return battler;
    }
}
