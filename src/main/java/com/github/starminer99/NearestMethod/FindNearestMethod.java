package com.github.starminer99.NearestMethod;

import com.github.starminer99.Util.*;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/*
#############################
BETTER METHOD -> SEE GetAllDistancesMethod.java
-----------------------------
############################
 */


public class FindNearestMethod {

    public static List<Action> execute() {
        map2Moves();

        List<Action> actionsToFirstPresent = searchNextPresent(startPenguin); // find first present

        List<Action> actions = new LinkedList<>(actionsToFirstPresent);
        for (int i = 0; i < 23; i++) {
            map2Moves();
            List<Action> actionsToNextPresent = searchNextPresent(posNow);

            actions.addAll(actionsToNextPresent);
        }

        map2Moves();
        List<Action> actionsToGoal = searchGoalFrom(posNow);
        actions.addAll(actionsToGoal);

        return actions;
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

    private static Vec2 posNow;
    private static FieldData[][] fieldData = new FieldData[40][40];

    public static void map2Moves() {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length(); i++) {
                char analysedChar = map[j].charAt(i);
                System.out.print(analysedChar);

                switch (analysedChar) {
                    case 'T', '#':
                        break;
                    case 'P': {
                        startPenguin = new Vec2(i, j);
                        addMoveByIndex(new Vec2(i, j), Field.PLAIN);
                        break;
                    }
                    case ' ', 'O': {
                        addMoveByIndex(new Vec2(i, j), Field.PLAIN);
                        break;
                    }
                    case '$': {
                        addMoveByIndex(new Vec2(i, j), Field.PRESENT);
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

    public static void addMoveByIndex(Vec2 startField, Field fieldType) {
        // get left move

        Move goalRight = getEndpoint(new Vec2(1, 0), startField);
        Move goalDown = getEndpoint(new Vec2(0, 1), startField);
        Move goalLeft = getEndpoint(new Vec2(-1, 0), startField);
        Move goalUp = getEndpoint(new Vec2(0, -1), startField);

        fieldData[startField.getX()][startField.getY()] = new FieldData(goalRight, goalDown, goalLeft, goalUp, fieldType);

    }

    public static Move getEndpoint(Vec2 mov, Vec2 start) {
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

    public static List<Action> searchNextPresent(Vec2 origin) {


        fieldData[origin.getX()][origin.getY()].setReachableIn(0);

        List<Action> nextPresentActions;


        boolean nothingFound = false;

        while(!nothingFound) {


            nothingFound = true;

            for (FieldData[] column : fieldData) {
                for (FieldData relevantField : column) {



                    int reachableIn;

                    if (relevantField != null) {
                        reachableIn = relevantField.getReachableIn();
                    } else {
                        reachableIn = -1;
                    }


                    if (reachableIn >= 0) {
                        Move moveRight = relevantField.getDestRight();
                        Move moveDown = relevantField.getDestDown();
                        Move moveLeft = relevantField.getDestLeft();
                        Move moveUp = relevantField.getDestUp();

                        if (moveRight != null) {
                            Vec2 dest = moveRight.goal();

                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveRight.present() != null)) {
                                nothingFound = false;

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.RIGHT);
                            }


                            if (analyzedField != null && moveRight.present() != null) {
                                nextPresentActions = analyzedField.getReachableWith();

                                posNow = dest;

                                // wipe present from map
                                Vec2 presentLocation = moveRight.present();

                                map[presentLocation.getY()] = map[presentLocation.getY()].substring(0, presentLocation.getX()) + ' ' + map[presentLocation.getY()].substring(presentLocation.getX() + 1);

                                return nextPresentActions;
                            }
                        }

                        if (moveDown != null) {
                            Vec2 dest = moveDown.goal();

                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveDown.present() != null)) {
                                nothingFound = false;

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.DOWN);
                            }

                            if (analyzedField != null && moveDown.present() != null) {
                                nextPresentActions = analyzedField.getReachableWith();

                                posNow = dest;

                                // wipe present from map
                                Vec2 presentLocation = moveDown.present();

                                map[presentLocation.getY()] = map[presentLocation.getY()].substring(0, presentLocation.getX()) + ' ' + map[presentLocation.getY()].substring(presentLocation.getX() + 1);

                                return nextPresentActions;
                            }
                        }

                        if (moveLeft != null) {
                            Vec2 dest = moveLeft.goal();

                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveLeft.present() != null)) {
                                nothingFound = false;

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.LEFT);
                            }

                            if (analyzedField != null && moveLeft.present() != null) {
                                nextPresentActions = analyzedField.getReachableWith();

                                posNow = dest;

                                // wipe present from map
                                Vec2 presentLocation = moveLeft.present();

                                map[presentLocation.getY()] = map[presentLocation.getY()].substring(0, presentLocation.getX()) + ' ' + map[presentLocation.getY()].substring(presentLocation.getX() + 1);

                                return nextPresentActions;
                            }
                        }

                        if (moveUp != null) {
                            Vec2 dest = moveUp.goal();
                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveUp.present() != null)) {
                                nothingFound = false;

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.UP);
                            }

                            if (analyzedField != null && moveUp.present() != null) {
                                nextPresentActions = analyzedField.getReachableWith();

                                posNow = dest;

                                // wipe present from map
                                Vec2 presentLocation = moveUp.present();

                                map[presentLocation.getY()] = map[presentLocation.getY()].substring(0, presentLocation.getX()) + ' ' + map[presentLocation.getY()].substring(presentLocation.getX() + 1);

                                return nextPresentActions;
                            }
                        }
                    }
                }

            }
        }

        return null;

    }

    public static List<Action> searchGoalFrom(Vec2 origin) {

        fieldData[origin.getX()][origin.getY()].setReachableIn(0);

        List<Action> goalActions;

        boolean notInGoal = true;

        while (notInGoal) {
            for (FieldData[] fieldDatum : fieldData) {
                for (FieldData relevantField : fieldDatum) {

                    int reachableIn;

                    if (relevantField != null) {
                        reachableIn = relevantField.getReachableIn();
                    } else {
                        reachableIn = -1;
                    }

                    if (reachableIn >= 0) {
                        Move moveRight = relevantField.getDestRight();
                        Move moveDown = relevantField.getDestDown();
                        Move moveLeft = relevantField.getDestLeft();
                        Move moveUp = relevantField.getDestUp();

                        if (moveRight != null) {
                            Vec2 dest = moveRight.goal();

                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveRight.present() != null)) {

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.RIGHT);
                            }


                            if (moveRight.goal().equals(goal)) {
                                relevantField.getReachableWith().add(Action.RIGHT);
                                goalActions = relevantField.getReachableWith();

                                posNow = goal;
                                return goalActions;
                            }
                        }

                        if (moveDown != null) {
                            Vec2 dest = moveDown.goal();

                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveDown.present() != null)) {

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.DOWN);
                            }

                            if (moveDown.goal().equals(goal)) {
                                relevantField.getReachableWith().add(Action.DOWN);
                                goalActions = relevantField.getReachableWith();

                                posNow = goal;
                                return goalActions;
                            }
                        }

                        if (moveLeft != null) {
                            Vec2 dest = moveLeft.goal();

                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveLeft.present() != null)) {

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.LEFT);
                            }

                            if (moveLeft.goal().equals(goal)) {
                                relevantField.getReachableWith().add(Action.LEFT);
                                goalActions = relevantField.getReachableWith();

                                posNow = goal;
                                return goalActions;
                            }
                        }

                        if (moveUp != null) {
                            Vec2 dest = moveUp.goal();
                            FieldData analyzedField = fieldData[dest.getX()][dest.getY()];
                            if (analyzedField != null && (analyzedField.getReachableIn() == -1 || moveUp.present() != null)) {

                                analyzedField.setReachableIn(reachableIn + 1);

                                analyzedField.getReachableWith().clear();
                                analyzedField.getReachableWith().addAll(relevantField.getReachableWith());
                                analyzedField.getReachableWith().add(Action.UP);
                            }

                            if (moveUp.goal().equals(goal)) {
                                relevantField.getReachableWith().add(Action.UP);
                                goalActions = relevantField.getReachableWith();

                                posNow = goal;
                                return goalActions;
                            }
                        }
                    }


                }

            }
        }

        return null;
    }
}
