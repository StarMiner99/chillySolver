package getAllDistances;

import Util.*;

import java.util.*;

public class GetAllDistancesMethod {

    public static List<Action> execute() {
        map2Moves(fieldData, map);
        needToBeScanned.add(startPenguin);

        scanUntilDone();

        System.out.println("scan done size: ");

        System.out.println(finalData.size());

        fetchNearestNeighbor();

        return null;
    }

    public static String[] map = {
            "TT T#T   #T#T#T#T #   T #T#  #TT  #T$  #",
            "T     #     $                          T",
            "T       $  T TT  TT##T#TT   T  T  T  T  ",
            "   T     T   T  T         T$            ",
            "#T     T   O   T$TT##TTTT   TT T        ",
            "  $ T                           T   T   ",
            "#                TT#TTTTT$             T",
            "     T  T    TT$                  T$    ",
            " T          T$   T#TT#T#T  T#          #",
            "        T    T T         #      T      O",
            "# T     #    T   T##TTTT# T T      T#T $",
            "    T T              P         # $     #",
            "#          TT    T##T#TTT T   T    T T #",
            "     TTT   T   T       $     #   T   T T",
            "T  #$        T  #T###TT#T T TT          ",
            "   ## T   T     T        T T        T T#",
            "  #T        #  T#T##TT#T#  T T    T     ",
            "T T  T T T  #                # TT T    #",
            "     T   #   T   TT#TTTTT T# T       T  ",
            "T  T       #  #     $           T   TTT ",
            "   T  T      T  #T##TTT## T  T#     T   ",
            "#   T    T    T T        TTT  T TT     T",
            "$T  #T    T    T TT#TT### T  T        T ",
            "# T T#    T #T            TT   T T T    ",
            "#        T  $    T###T#TT            T  ",
            "   T T        T        #  T     T    TT ",
            "T T T  ##   T T  T  T  T$T    T  T T  $ ",
            "   TT  T      T T $# T T   TT        T T",
            "      #TT  T                T    #     T",
            "  T        TTT T T T  T#T           T  T",
            "T  T    T      T             T T      TT",
            "#T   T        T  T TT # TT  TT  #      #",
            "#   T    ##    $   TT    #  #  T# T     ",
            " T     T     T TT TT#T      T   T       ",
            " # T              T T   T  #T T #  TT T#",
            "T  T      T T #    T  T   $            T",
            "T      TT       #        # #  T       TT",
            "# T         T      T          # T  T   #",
            "T$ O           T     T     T    T      X",
            " T #   # T    #T# #  ##   T  T  $ # #TTT"};

    private static final Hashtable<Vec2, Vec2> portals = new Hashtable<>() {{
        put(new Vec2(39, 9), new Vec2(3, 38));
        put(new Vec2(3, 38), new Vec2(11, 4));
        put(new Vec2(11, 4), new Vec2(39, 9));
    }};

    private static Vec2 startPenguin;
    private static Vec2 goal;


    private static FieldData[][] fieldData = new FieldData[40][40];

    public static void map2Moves(FieldData[][] fieldData, String[] map) {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length(); i++) {
                char analysedChar = map[j].charAt(i);
                System.out.print(analysedChar);

                switch (analysedChar) {
                    case 'T', '#':
                        break;
                    case 'P': {
                        startPenguin = new Vec2(i, j);
                        addMoveByIndex(new Vec2(i, j), Field.PLAIN, fieldData, map);
                        break;
                    }
                    case ' ', 'O': {
                        addMoveByIndex(new Vec2(i, j), Field.PLAIN, fieldData, map);
                        break;
                    }
                    case '$': {
                        addMoveByIndex(new Vec2(i, j), Field.PRESENT, fieldData, map);
                        break;
                    }
                    case 'X': {
                        goal = new Vec2(i, j);
                        break;
                    }
                }
            }
            System.out.println();
        }
    }

    public static void addMoveByIndex(Vec2 startField, Field fieldType, FieldData[][] fieldData, String[] map) {
        // get left move

        Move goalRight = getEndpoint(new Vec2(1, 0), startField, map);
        Move goalDown = getEndpoint(new Vec2(0, 1), startField, map);
        Move goalLeft = getEndpoint(new Vec2(-1, 0), startField, map);
        Move goalUp = getEndpoint(new Vec2(0, -1), startField, map);

        fieldData[startField.getX()][startField.getY()] = new FieldData(goalRight, goalDown, goalLeft, goalUp, fieldType);

    }

    public static Move getEndpoint(Vec2 mov, Vec2 start, String[] map) {
        // get left move
        Vec2 hitPresent = null;
        boolean obstacle = false;
        int xNow;
        int yNow;
        int nextCordX = start.getX();
        int nextCordY = start.getY();
        do {
            xNow = nextCordX;
            yNow = nextCordY;
            nextCordX = xNow + mov.getX();
            nextCordY = yNow + mov.getY();

            if (nextCordX >= map[yNow].length()) {
                nextCordX = 0;
            } else if (nextCordX < 0) {
                nextCordX = map[yNow].length() -1;
            }

            if (nextCordY >= map.length) {
                nextCordY = 0;
            } else if (nextCordY < 0) {
                nextCordY = map.length -1;
            }

            char nextChar = map[nextCordY].charAt(nextCordX);

            switch (nextChar) {
                case 'T', '#':
                    obstacle = true;
                    break;
                case '$':
                    hitPresent = new Vec2(nextCordX, nextCordY);
                    break;
                case 'O':
                    Vec2 destCord = portalSolver(new Vec2(nextCordX, nextCordY));

                    xNow = destCord.getX();
                    yNow = destCord.getY();
                    obstacle = true;
                    break;
            }
        } while (!obstacle);

        Move goal = new Move(new Vec2(xNow, yNow), hitPresent);
        return goal.goal().equals(start) ? null : goal;
    }

    public static Vec2 portalSolver(Vec2 origin) {
        return portals.get(origin);
    }

    private static final List<Vec2> hasBeenScanned = new ArrayList<>();
    private static final List<Vec2> needToBeScanned = new LinkedList<>();

    private static HashMap<Vec2, List<Pair<List<Action>, Pair<Vec2, Vec2>>>> finalData = new HashMap<>(); // first Vec2 start in list 24 options to go to presents, action list for action array, first vec2 for present, second for destination

    private static void scanUntilDone() {

        while (!needToBeScanned.isEmpty()) {
            Vec2 scannedVector = needToBeScanned.getFirst();
            needToBeScanned.removeFirst();
            if (!hasBeenScanned.contains(scannedVector)) {


                hasBeenScanned.add(scannedVector);


                List<Pair<List<Action>, Pair<Vec2, Vec2>>> actionList = new ArrayList<>();

                Pair<List<Action>, Pair<Vec2, Vec2>> nextPresent = null;

                String[] mapCopy = new String[40];
                System.arraycopy(map, 0, mapCopy, 0, map.length);

                FieldData[][] modField = new FieldData[40][40];


                do {
                    map2Moves(modField, mapCopy);
                    nextPresent = getNearestPresentOrGoal(scannedVector, modField);
                    if (nextPresent != null) {
                        mapCopy[nextPresent.b.a.getY()] = mapCopy[nextPresent.b.a.getY()].substring(0, nextPresent.b.a.getX()) + ' ' + mapCopy[nextPresent.b.a.getY()].substring(nextPresent.b.a.getX() + 1);

                        actionList.add(nextPresent);
                    }

                } while (nextPresent != null);

                finalData.put(scannedVector, actionList);
            }

        }
    }

    private static Pair<List<Action>, Pair<Vec2, Vec2>> getNearestPresentOrGoal(Vec2 origin, FieldData[][] field) {

        field[origin.getX()][origin.getY()].setReachableIn(0);

        List<Action> returnList;
        Vec2 returnVector;

        boolean nothingLeft = false;

        while (!nothingLeft) {
            nothingLeft = true;

            for (FieldData[] column:
                 field) {
                for (FieldData singleField:
                     column) {

                    int reachableIn;

                    if(singleField != null) {
                        reachableIn = singleField.getReachableIn();

                        if (singleField.getFieldType() == Field.PRESENT) {
                            nothingLeft = false;
                        }

                    } else {
                        reachableIn = -1;
                    }

                    if (reachableIn >= 0) {
                        Pair<List<Action>, Pair<Vec2, Vec2>> movesToPresent;
                        movesToPresent = analyzeMove(singleField.getDestRight(), field, singleField, Action.RIGHT);
                        if (movesToPresent != null) {
                            return movesToPresent;
                        }

                        movesToPresent = analyzeMove(singleField.getDestDown(), field, singleField, Action.DOWN);
                        if (movesToPresent != null) {
                            return movesToPresent;
                        }

                        movesToPresent = analyzeMove(singleField.getDestLeft(), field, singleField, Action.LEFT);
                        if (movesToPresent != null) {
                            return movesToPresent;
                        }

                        movesToPresent = analyzeMove(singleField.getDestUp(), field, singleField, Action.UP);
                        if (movesToPresent != null) {
                            return movesToPresent;
                        }
                    }

                }
            }
        }

        boolean goalExists = field[goal.getX()][goal.getY()] == null;

        while(goalExists) {


            for (FieldData[] column:
                    field) {
                for (FieldData singleField :
                        column) {

                    int reachableIn;

                    if(singleField != null) {
                        reachableIn = singleField.getReachableIn();

                    } else {
                        reachableIn = -1;
                    }

                    if (reachableIn >= 0) {
                        Pair<List<Action>, Pair<Vec2, Vec2>> movesToGoal;
                        movesToGoal = analyzeMoveOnGoal(singleField.getDestRight(), field, singleField, Action.RIGHT);
                        if (movesToGoal != null) {
                            return movesToGoal;
                        }

                        movesToGoal = analyzeMoveOnGoal(singleField.getDestDown(), field, singleField, Action.DOWN);
                        if (movesToGoal != null) {
                            return movesToGoal;
                        }

                        movesToGoal = analyzeMoveOnGoal(singleField.getDestLeft(), field, singleField, Action.LEFT);
                        if (movesToGoal != null) {
                            return movesToGoal;
                        }

                        movesToGoal = analyzeMoveOnGoal(singleField.getDestUp(), field, singleField, Action.UP);
                        if (movesToGoal != null) {
                            return movesToGoal;
                        }
                    }

                }
            }


        }

        return null;

    }

    private static Pair<List<Action>, Pair<Vec2, Vec2>> analyzeMove(Move move, FieldData[][] field, FieldData moveOrigin, Action direction) {
        if (move == null) {
            return null;
        }

        Vec2 dest = move.goal();
        FieldData analyzedField = field[dest.getX()][dest.getY()];

        if(analyzedField != null && analyzedField.getReachableIn() == -1) {
            analyzedField.setReachableIn(moveOrigin.getReachableIn() + 1);

            analyzedField.getReachableWith().clear();
            analyzedField.getReachableWith().addAll(moveOrigin.getReachableWith());
            analyzedField.getReachableWith().add(direction);
        }

        if(analyzedField != null && move.present() != null) {
            List<Action> actionList = analyzedField.getReachableWith();
            Vec2 presentVec = move.present();
            needToBeScanned.add(dest);

            return new Pair<>(actionList, new Pair<>(presentVec, dest));

        }

        return null;
    }

    private static Pair<List<Action>, Pair<Vec2, Vec2>> analyzeMoveOnGoal(Move move, FieldData[][] field, FieldData moveOrigin, Action direction) {
        if (move == null) {
            return null;
        }

        Vec2 dest = move.goal();
        FieldData analyzedField = field[dest.getX()][dest.getY()];

        if(analyzedField != null && analyzedField.getReachableIn() == -1) {
            analyzedField.setReachableIn(moveOrigin.getReachableIn() + 1);

            analyzedField.getReachableWith().clear();
            analyzedField.getReachableWith().addAll(moveOrigin.getReachableWith());
            analyzedField.getReachableWith().add(direction);
        }

        if (dest.equals(goal)) {
            moveOrigin.getReachableWith().add(direction);
            return new Pair<>(moveOrigin.getReachableWith(), new Pair<>(dest, dest));
        }

        return null;
    }

    private static void fetchNearestNeighbor() {

        List<Action> actionList = new LinkedList<>();

        List<Vec2> pickedUp = new LinkedList<>();
        Vec2 originVector = startPenguin;

        for (int j = 0; j < 24; j++) {

            List<Pair<List<Action>, Pair<Vec2, Vec2>>> analyzedData = finalData.get(originVector);

            if (analyzedData != null) { // if originVector is the goal analyzed Data will be null because there is no way to move to anywhere from the goal


                int smallestIndex = -1;
                for (int i = 0; i < analyzedData.size(); i++) {
                    try {
                        if (analyzedData.get(i).a.size() < analyzedData.get(smallestIndex).a.size() && !pickedUp.contains(analyzedData.get(i).b.a) && !analyzedData.get(i).b.a.equals(goal)) {
                            smallestIndex = i;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        if (!pickedUp.contains(analyzedData.get(i).b.a) && !analyzedData.get(i).b.a.equals(goal)) {
                            smallestIndex = i;
                        }
                    }

                }

                originVector = analyzedData.get(smallestIndex).b.b;
                pickedUp.add(analyzedData.get(smallestIndex).b.a);
                actionList.addAll(analyzedData.get(smallestIndex).a);
            }



        }
        // one last time until we are in goal

        List<Pair<List<Action>, Pair<Vec2, Vec2>>> analyzedData = finalData.get(originVector);

        for (Pair<List<Action>, Pair<Vec2, Vec2>> dataPair:
             analyzedData) {
            if (dataPair.b.a.equals(goal)) {
                actionList.addAll(dataPair.a);
            }

        }


        System.out.println(actionList);
        System.out.println(actionList.size());

    }



}
