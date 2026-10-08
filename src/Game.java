import gameData.*;
import gameData.Character;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Game implements Serializable {
    public int continyu = 0, roomid = 99, money = 0;
    public int choiceMenu, charaChoice;
    public boolean started = false;
    public int[] itemList = new int[10], party = new int[3];
    public int[][] enemyTroop = new int[100][7];
    public int fleeChance = 80;
    public String[] passwords = new String[50];
    public Room[] rooms = new Room[100];
    public gameData.Character[] enemy = new gameData.Character[100], protag = new gameData.Character[10];
    public StatusEffect[] statusEffects = new StatusEffect[50];
    Action[] items = new Item[100];
    Action[] moves = new Move[100];
    KeyItem[] keyItems = new KeyItem[100];
    Equip[] equips = new Equip[100];

    public Game() {
        for (int i = 0; i < 100; i++) {
            rooms[i] = new Room(i);
            enemy[i] = new gameData.Character(i);
            items[i] = new Item(i);
            moves[i] = new Move(i);
            keyItems[i] = new KeyItem(i);
            for (int j = 0; j < 7; j++) {
                enemyTroop[i][j] = 0;
            }
            equips[i] = new Equip(i);
        }
        for (int i = 0; i < 10; i++) {
            itemList[i] = -1;
            protag[i] = new Character(i);
        }
        for (int i = 0; i < 50; i++) {
            statusEffects[i] = new StatusEffect(i);
            passwords[i] = "";
        }

    }

    public void defaultSave() {
        rooms[0].assignBasic(0, "", "");
        rooms[0].assignOption(0, "", new int[]{0, 0, 0, 0, 0}, new String[]{"", "", "", "", ""}, new int[]{0, 0, 0, 0, 0});
        enemy[0].assign("", "", 0, 0, 0, 0, 0, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0);
        protag[0].assign("", "", 0, 0, 0, 0, 0, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0});
        enemyTroop[0][0] = 0;
        passwords[0] = "";
        party[0] = 1;
        statusEffects[0].assign(1, true, "K.O.", "", "ya no puede pelear.", "puede pelear otra vez.");
        moves[0].assign(0, 0, "Ataque", "", "ataca!", 1, 0, 1, -1, 0, 0, 0, -1, false, 1); //no borren este, es el ataque principal
        items[0].assign("", "", "", 0, 0, -1, 0, 0, -1, 0); //de 0 a 99 son movimientos, de 100 a 199 son objetos
        equips[0].assign("Vacio", "", 0, 0, 0, 0, 0, 0, 0);

        Battle.LEVEL_MAX = 30;
        rooms[99].assignBasic(8, "Cuarto De Prueba", "Estas en el cuarto de prueba");
        rooms[99].assignOption(0, "Agarrar dinero del piso", new int[]{7, 9, 0, 0, 0}, new String[]{"Conseguiste 200$ dinero!", "", "", "", ""}, new int[]{200, 1, 0, 0, 0});
        rooms[99].assignOption(1, "Comprar gota de miel (20$).", new int[]{7, 3, 0, 0, 0}, new String[]{"", "Compraste la gota de miel!", "", "", ""}, new int[]{-20, 99, 0, 0, 0});
        items[99].assign("Gota de miel", "Una gota de miel de abeja. Es muy nutritiva!", "se toma la gota de miel!", 2, 5, 0, 5, 0, -1, 0); //de 0 a 99 son movimientos, de 100 a 199 son objetos
        rooms[99].assignOption(2, "Abrir la puerta con candado", new int[]{6, 9, 1, 0, 0}, new String[]{"Abres el candado...", "", "Entras a la puerta.", "", ""}, new int[]{99, 0, 98, 0, 0});
        keyItems[99].assign("Llave de prueba", "La llave de la puerta con candado del cuarto de prueba.");
        rooms[99].assignOption(3, "Agarrar un perro callejero.", new int[]{8, 9, 0, 0, 0}, new String[]{"Wau Wau! \nTraduccion: Voy a pelear a tu lado", "", "", "", ""}, new int[]{2, 1, 0, 0, 0});
        protag[1].assign("Paladin", "Un paladin paladinico.", 10, 10, 2, 0, 1, new int[]{99, 98, 97, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0}, new int[]{0, 1, 0, 0});
        protag[2].assign("Perro Callejero", "Un perro callejero.", 10, 5, 2, 0, 3, new int[]{94, 95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0});
        rooms[99].assignOption(4, "Agarrar la llave de prueba.", new int[]{2, 4, 9, 0, 0}, new String[]{"Unos enemigos de prueba estan protegiendo la llave de prueba!", "", "", "", ""}, new int[]{99, 99, 1, 0, 0});
        rooms[99].assignOption(5, "Guardar el juego.", new int[]{14, 0, 0, 0, 0}, new String[]{"", "", "", "", ""}, new int[]{0, 0, 0, 0, 0});
        rooms[99].assignOption(6, "Comprar una armadura (100$)", new int[]{7, 5, 0, 0, 0}, new String[]{"", "Compras la armadura", "", "", ""}, new int[]{-100, 99, 0, 0, 0});
        equips[99].assign("Armadura Genial", "Una armadura muy genial!!!", 1, 1, 0, 5, 0, 1, 0);
        enemy[99].assign("Enemigo De Prueba", "Un tipo de maniqui extraño.", 10, 2, 3, 0, 2, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 3);
        enemyTroop[99][0] = 99;
        enemyTroop[99][1] = 99;
        enemyTroop[99][2] = 99;
        moves[99].assign(5, 0, "Curacion", "", "usa su conocimiento medico!", 4, 0, 0, -1, 0, 5, 0, 0, false, 1);
        moves[98].assign(10, 0, "Revivir", "", "reza y es escuchado!", 2, 0, 0, -1, 0, 5, 0, 1, false, 1);
        moves[97].assign(5, 0, "Espiral Infinito De Espadas", "", "mueve su espada de una manera que la mente mortal no puede entender!", 3, 0, 3, -1, 0, 0, 0, 0, false, 1);
        moves[94].assign(2, 0, "Mordida Envenenada", "", "muerde sin lavarse los dientes!", 1, 0, 1, 49, 3, 0, 0, 0, false, 1);
        moves[95].assign(2, 0, "Mordida Triple", "", "muerde tres veces!", 1, 0, 1, -1, 0, 0, 0, 0, false, 3);
        statusEffects[49].assign(8, false, "Infeccion", "sufre de su mordida infectada.", "tiene una mordida infectada", "se mejora.");

        rooms[98].assignBasic(2, "Fin Del Universo", "Estas en el fin del universo...");
        rooms[98].assignSingleOption(0, 0, "Regresar al cuarto de prueba.", 1, "Regresas al cuarto de prueba", 99);
        rooms[98].assignSingleOption(1, 0, "Terminar el juego.", 16, "Creditos: \nTodo: yo", 0);


    }

    public void play() {
        started = true;
        while (continyu == 0) {

            rooms[roomid].optionsCheck();

            System.out.println(rooms[roomid].desc);
            for (int i = 0; i < 10; i++) {
                if (!rooms[roomid].optionName[i].isEmpty()) {
                    System.out.println((i + 1) + ". " + rooms[roomid].optionName[i]);
                }
            }
            int choice = Common.choice(rooms[roomid].options) - 1;
            if ((choice <= 9) && (choice >= 0)) {
                for (int a = 0; a < 5; a++) {
                    if (!rooms[roomid].effectText[a][choice].isEmpty()) {
                        System.out.println(rooms[roomid].effectText[a][choice]);
                    }
                    switch (rooms[roomid].effect[a][choice]) {
                        case 0: //texto vacio
                            break;
                        case 1: //esta opcion te lleva a otro cuarto
                            roomid = rooms[roomid].effectID[a][choice];
                            a = 5;
                            break;
                        case 2: // battle
                            Battle battle = new Battle(enemy, protag, party, enemyTroop[rooms[roomid].effectID[a][choice]], statusEffects, itemList, moves, items, fleeChance);
                            switch (battle.battleEnd) {
                                case 1:
                                    continyu = 1;
                                case 2:
                                    a = 5;
                                    break;
                                case 3:
                                    break;
                            }
                            itemList = battle.itemList;
                            if (!battle.getBattler(0).name.isEmpty()) protag[party[0]] = battle.getBattler(0);
                            if (!battle.getBattler(1).name.isEmpty()) protag[party[1]] = battle.getBattler(1);
                            if (!battle.getBattler(2).name.isEmpty()) protag[party[2]] = battle.getBattler(2);
                            break;
                        case 3: //give item
                            if (Common.actuallyUsedSpace(itemList, -1) <= 9) {
                                itemList[Common.actuallyUsedSpace(itemList, -1)] = (rooms[roomid].effectID[a][choice]);
                            } else System.out.println("Pero tu inventario ya estaba lleno!");
                            break;
                        case 4: //give key item
                            keyItems[rooms[roomid].effectID[a][choice]].counter++;
                            break;
                        case 5: //give armor
                            equips[rooms[roomid].effectID[a][choice]].counter++;
                            break;
                        case 6: //this option to check for key items
                            if (keyItems[rooms[roomid].effectID[a][choice]].counter > 0)
                                keyItems[rooms[roomid].effectID[a][choice]].counter -= 1;
                            else a = 5;
                            break;
                        case 7: //this option to give or take away money
                            if ((money + rooms[roomid].effectID[a][choice]) >= 0) {
                                money += rooms[roomid].effectID[a][choice];
                            } else {
                                System.out.println("Eres demasiado pobre para eso!!");
                                a = 5;
                            }
                            break;
                        case 8: //this option to add party members
                            if (party[1] == 0) {
                                party[1] = rooms[roomid].effectID[a][choice];
                                System.out.println(protag[rooms[roomid].effectID[a][choice]].name + " se unio a la partida!");
                                rooms[roomid].options -= 1;
                            } else if (party[2] == 0) {
                                party[2] = rooms[roomid].effectID[a][choice];
                                System.out.println(protag[rooms[roomid].effectID[a][choice]].name + " se unio a la partida!");
                                rooms[roomid].options -= 1;
                            } else {
                                System.out.println("¿A quien remplazar con " + protag[rooms[roomid].effectID[a][choice]].name + "?\n  1. " + protag[party[0]].name + "\n  2. " + protag[party[1]].name + "\n  3. " + protag[party[2]].name);
                                charaChoice = Common.choice(3) - 1;
                                if ((charaChoice >= 0) && (charaChoice < 3)) {
                                    party[charaChoice] = rooms[roomid].effectID[a][choice];
                                    System.out.println(protag[rooms[roomid].effectID[a][choice]].name + " se unio a la partida!");
                                }
                                //que le pasa a los personajes que han sido reemplazados
                            }
                            break;
                        case 9: //effect id = 0 -> se mantiene la opcion, y solo se quita el efecto, effect id = 1 -> se quita la opcion completamente
                            if (rooms[roomid].effectID[a][choice] == 0) rooms[roomid].assignSingleOption(choice, (a - 1), rooms[roomid].optionName[choice], 0, "", 0);
                            else if (rooms[roomid].effectID[a][choice] == 1) rooms[roomid].assignOption(choice, "", new int[]{0, 0, 0, 0, 0}, new String[]{"", "", "", "", ""}, new int[]{0, 0, 0, 0, 0});
                            break;
                        case 10: //regenerate all health
                            for (int i = 0; i < 3; i++) {
                                protag[party[i]].curHP = protag[party[i]].maxHP;
                                protag[party[i]].curMP = protag[party[i]].maxMP;
                                for (int j = 0; j < 50; j++) {
                                    protag[party[i]].status1[j] = false;
                                    protag[party[i]].statusCounter1[j] = 0;
                                }
                            }
                            break;
                        case 11: //password check
                            String password = Common.S.next();
                            if (!passwords[rooms[roomid].effectID[a][choice]].equals(password)) a = 5;
                            break;
                        case 12: //luck check
                            if (!Common.RNG(rooms[roomid].effectID[a][choice])) a = 5;
                            break;
                        case 14: //save file
                            if(save()){
                                System.out.println("Progreso guardado exitosamente.");
                            } else {
                                System.out.println("No se pudo guardar el juego.");
                            }
                            break;
                        case 15:
                            continyu = 1; //esto te mata
                            break;
                        case 16:
                            continyu = 3; //esto completa el juego
                            break;
                    }
                }
                Common.S.nextLine();
            } else {
                int continyuMenu = 1;
                while (continyuMenu == 1) {
                    System.out.println("Menu De Pausa:\n  1. Estado\n  2. Objetos\n  3. Objetos Clave\n  4. Armadura\n  5. Tecnicas\n  6. Salir del juego");
                    choiceMenu = Common.choice(6);
                    switch (choiceMenu) {
                        case 1:
                            continyuMenu++;
                            while (continyuMenu == 2) {
                                System.out.println("¿Ver el estado de quien?");
                                for (int i = 0; i < 3; i++) {
                                    if (!protag[party[i]].name.isEmpty()) {
                                        System.out.println("  " + (i + 1) + ". " + protag[party[i]].name);
                                    }
                                }
                                choiceMenu = Common.choice(3) - 1;
                                switch (choiceMenu) {
                                    case 0, 1, 2:
                                        choiceMenu = party[choiceMenu];
                                        System.out.println("Nombre: " + protag[choiceMenu].name + "  Nivel: " + protag[choiceMenu].lvl + "  Exp: " + protag[choiceMenu].exp + "/" + protag[choiceMenu].expRequirement() + "  Dinero: " + money + "$" +
                                                "\nHP: " + protag[choiceMenu].curHP + "/" + protag[choiceMenu].maxHP + "    MP: " + protag[choiceMenu].curMP + "/" + protag[choiceMenu].maxMP +
                                                "\nATK: " + protag[choiceMenu].atk + "   DEF: " + protag[choiceMenu].def + "    SPD: " + protag[choiceMenu].spd +
                                                "\nCabeza: " + equips[protag[choiceMenu].equip[0]].name + "   Cuerpo: " + equips[protag[choiceMenu].equip[1]].name +
                                                "\nAccesorio: " + equips[protag[choiceMenu].equip[2]].name + "   Arma: " + equips[protag[choiceMenu].equip[3]].name);
                                        break;
                                    default:
                                        continyuMenu = 1;
                                        break;
                                }
                            }
                            break;
                        case 2:
                            continyuMenu++;
                            while (continyuMenu == 2) {
                                if (Common.actuallyUsedSpace(itemList, -1) > 0) {
                                    System.out.println("¿Cual objeto vas a usar?");
                                    for (int j = 0; j < 10; j++) {
                                        if (itemList[j] != -1) {
                                            System.out.print("  " + (j + 1) + ": " + items[itemList[j]].printSelf());
                                            if (items[itemList[j]].heal > 0)
                                                System.out.print("  HP: " + items[itemList[j]].heal);
                                            if (items[itemList[j]].healMP > 0)
                                                System.out.print("  MP: " + items[itemList[j]].healMP);
                                            if (items[itemList[j]].cure > 0)
                                                System.out.print("  cura: " + statusEffects[items[itemList[j]].cure].name);
                                            else if (items[itemList[j]].cure == 99) System.out.print("  Cura: todo");
                                            else if (items[itemList[j]].cure == 100)
                                                System.out.print("  Cura: todo y revive");
                                            if (items[itemList[j]].dmgAdd > 0)
                                                System.out.print("  Daño: " + items[itemList[j]].dmgAdd);
                                            if (items[itemList[j]].status > 0)
                                                System.out.print("  Inflige: " + statusEffects[items[itemList[j]].status].name);
                                            System.out.println();
                                        }
                                    }
                                    choiceMenu = Common.choice(Common.actuallyUsedSpace(itemList, -1)) - 1;
                                    if ((choiceMenu >= 0) && (continyuMenu < Common.actuallyUsedSpace(itemList, -1))) {
                                        if (itemList[choiceMenu] >= 0) {
                                            if ((choiceMenu < Common.actuallyUsedSpace(itemList, -1)) && (items[itemList[choiceMenu]].targetType != 1) && (items[itemList[choiceMenu]].targetType != 3)) {
                                                protag = items[itemList[choiceMenu]].takeEffect(protag, statusEffects, party[0], 1, party);
                                                itemList[choiceMenu] = -1;
                                                Common.compress(itemList, -1);
                                            } else if ((items[itemList[choiceMenu]].targetType != 1) && (items[itemList[choiceMenu]].targetType != 3))
                                                System.out.println("Ese objeto no se puede usar!");
                                        } else continyuMenu = 1;
                                    } else continyuMenu = 1;
                                } else {
                                    continyuMenu = 1;
                                }
                            }
                            break;
                        case 3:
                            for (int i = 0; i < 100; i++) {
                                if (keyItems[i].counter > 0) {
                                    System.out.println("  " + keyItems[i].name + " x" + keyItems[i].counter + "  " + keyItems[i].desc);
                                }
                            }
                            Common.S.next();
                            break;
                        case 4:
                            continyuMenu++;
                            while (continyuMenu == 2) {
                                System.out.println("¿Cambiar las armaduras de quien?");
                                for (int i = 0; i < 3; i++) {
                                    if (!protag[party[i]].name.isEmpty()) {
                                        System.out.println("  " + (i + 1) + ". " + protag[party[i]].name);
                                    }
                                }
                                choiceMenu = Common.choice(3) - 1;
                                switch (choiceMenu) {
                                    case 0, 1, 2:
                                        continyuMenu = 3;
                                        while (continyuMenu == 3) {
                                            System.out.println("La armadura de " + protag[party[choiceMenu]].name
                                                    + "\n  1. Cabeza: " + equips[protag[choiceMenu].equip[0]].name + "\n  2. Cuerpo: " + equips[protag[choiceMenu].equip[1]].name
                                                    + "\n  3. Accesorio: " + equips[protag[choiceMenu].equip[2]].name + "\n  4. Arma: " + equips[protag[choiceMenu].equip[3]].name
                                                    + "\n    ¿Cual Accesorio quieres cambiar?");
                                            int choiceMenuArmor = Common.choice(4) - 1;
                                            switch (choiceMenuArmor) {
                                                case 0, 1, 2, 3:
                                                    int armorMenuThing = 1;
                                                    int[] armorMenuArray = new int[100];
                                                    for (int i = 0; i < 100; i++) {
                                                        armorMenuArray[i] = -1;
                                                        if ((equips[i].category == choiceMenuArmor) && ((equips[i].type == protag[party[choiceMenu]].equipType[choiceMenuArmor]) || (equips[i].type == 0)) && (equips[i].counter > 0)) {
                                                            equips[i].printSelf(armorMenuThing);
                                                            armorMenuArray[armorMenuThing] = i;
                                                            armorMenuThing++;
                                                        }
                                                    }
                                                    int choiceMenuArmorB = Common.choice(armorMenuThing) - 1;
                                                    if ((choiceMenuArmorB >= 0) && (choiceMenuArmorB <= (armorMenuThing + 1))) {
                                                        protag[choiceMenu] = equips[protag[choiceMenu].equip[choiceMenuArmor]].takeaway(protag[choiceMenu]);
                                                        protag[choiceMenu] = equips[armorMenuArray[choiceMenuArmorB]].give(protag[choiceMenu]);
                                                        protag[choiceMenu].equip[choiceMenuArmor] = equips[armorMenuArray[choiceMenuArmorB]].id;
                                                        protag[choiceMenu].statCheck();
                                                    } else continyuMenu = 2;
                                                    break;
                                                default:
                                                    continyuMenu = 2;
                                                    break;
                                            }
                                        }
                                        break;
                                    default:
                                        continyuMenu = 1;
                                        break;
                                }
                            }
                            break;
                        case 5:
                            continyuMenu++;
                            while (continyuMenu == 2) {
                                System.out.println("¿Ver los movimientos de quien?");
                                for (int i = 0; i < 3; i++) {
                                    if (!protag[party[i]].name.isEmpty()) {
                                        System.out.println("  " + (i + 1) + ". " + protag[party[i]].name);
                                    }
                                }
                                choiceMenu = Common.choice(3) - 1;
                                switch (choiceMenu) {
                                    case 0, 1, 2:
                                        if (protag[party[choiceMenu]].moveCount != 0) {
                                            for (int i = 0; i < protag[party[choiceMenu]].moveCount; i++) {
                                                if (protag[party[choiceMenu]].moves[i] != 0) {
                                                    System.out.println((i + 1) + ". " + moves[protag[party[choiceMenu]].moves[i]].printSelf());
                                                }
                                            }
                                            int move = Common.choice(Common.actuallyUsedSpace(protag[choiceMenu].moves, 0) - 1) - 1;
                                            if (move >= 0) {
                                                if (protag[party[choiceMenu]].curMP >= moves[protag[party[choiceMenu]].moves[move]].cost)
                                                    protag = moves[protag[party[choiceMenu]].moves[move]].takeEffect(protag, statusEffects, party[choiceMenu], 0, party);
                                                else System.out.println("No tienes suficiente MP!");
                                            }
                                        } else {
                                            System.out.println(protag[party[choiceMenu]].name + " No Tiene Movimientos!");
                                        }
                                        break;
                                    default:
                                        continyuMenu = 1;
                                        break;
                                }
                            }
                            break;
                        case 6:
                            continyu = 2;
                        default:
                            continyuMenu = 0;
                            break;
                    }
                }
            }
        }
        switch (continyu) {
            case 1:
                System.out.println("Moriste!!! Game Over!!!");
                break;
            case 3:
                System.out.println("Ganaste El Juego!!!");
                break;
        }
    }

    private boolean save(){
        System.out.println("¿En cual archivo quieres guardar?");
        RPG.printSaves();
        int saveN = Common.choice(4);
        boolean success;
        if (saveN != 0){
            String save = "saveFile" + saveN + ".txt";
            Game temp = new Game();
            temp.continyu = continyu;
            temp.roomid = roomid;
            temp.money = money;
            temp.itemList = itemList; temp.party = party;
            temp.enemyTroop = enemyTroop;
            temp.fleeChance = fleeChance;
            temp.passwords = passwords;
            temp.rooms = rooms;
            temp.enemy = enemy;
            temp.protag = protag;
            temp.statusEffects = statusEffects;
            temp.items = items;
            temp.moves = moves;
            temp.keyItems = keyItems;
            temp.equips = equips;
            temp.started = true;
            try {
                FileOutputStream fos = new FileOutputStream(save);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(temp);
                oos.close();
                success = true;
            } catch (IOException e){
                success = false;
            }
        } else success = false;
        return success;
    }
}
