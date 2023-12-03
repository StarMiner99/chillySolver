import java.util.*;

public class Main {
    public static final String[] map = {
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

    private static Hashtable<Vec2, Vec2> portals = new Hashtable<>() {{
        put(new Vec2(39, 9), new Vec2(3, 38));
        put(new Vec2(3, 38), new Vec2(11, 4));
        put(new Vec2(11, 4), new Vec2(39, 9));
    }};

    private static Vec2 startPenguin;
    private static Vec2 goal;
    private static FieldData[][] fieldData = new FieldData[40][40];


    public static void main(String[] args) {

        System.out.println(map.length);
        System.out.println(map[0].length());
        map2Moves();
        System.out.println(Arrays.deepToString(fieldData));




    }

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
                    }
                    case ' ', 'O': {
                        addMoveByIndex(new Vec2(i, j), Field.PLAIN);
                    }
                    case '$': {
                        addMoveByIndex(new Vec2(i, j), Field.PRESENT);
                    }
                    case 'X': {
                        goal = new Vec2(i, j);
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
        Present hitPresent = null;
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
                    hitPresent = new Present(new Vec2(nextCordX, nextCordY));
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
        //System.out.println(origin);
        //System.out.println(portals.get(origin.toString()));
        return portals.get(origin);
    }

}