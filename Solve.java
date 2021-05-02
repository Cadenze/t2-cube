import java.util.Arrays;

public class Solve extends RubiksCube {

    /* Variables for storing conventional metrics */
    private int qtm;
    private int htm;
    private int stm;

    /* Variables for storing moves in the first and second layers. */
    private String movesFirst;
    private String movesSecond;

    /* Variables for Adjustment Factor */
    private int df; /* deduction factor from triggers */
    private int regrip; /* number of regrips in the middle of algorithm */
    protected int look; /* number of look and find algorithms */
    private int rotation; /* number of cube rotations in the middle of algorithm */

    /**
     * Initializes a solved cube. (No work done)
     */
    public Solve() {
        super();
        initialize();
    }

    /**
     * Initializes a cube, scrambles it, and then solves it.
     * @param instruction The scramble instructions
     */
    public Solve(String instruction) {
        this(instruction, true);
    }

    /**
     * Initializes a cube, scrambles it, and then allows the user the option to solve it.
     * @param instruction The scramble instructions
     * @param solve true to solve; false to leave scrambled
     */
    public Solve(String instruction, boolean solve) {
        super(instruction);
        initialize();
        if(solve) {
            solve();
        }
    }

    /**
     * Initializes metrics and move saves.
     */
    private void initialize() {
        qtm = 0;
        htm = 0;
        stm = 0;
        movesFirst = "";
        movesSecond = "";
        df = 0;
        regrip = 0;
        look = 0;
        rotation = 0;
    }

    /**
     * Solves a cube using the beginner's method.
     */
    public void solve() {
        cross();
        corners();
        secondLayer();
        oll1();
        oll2();
        pll1();
        pll2();
        alignment();
    }

    /**
     * Creates a white cross.
     */
    public void cross() {
        look++;
        crossRed();
        crossBlue();
        crossOrange();
        crossGreen();
    }

    protected void crossRed() {
        crossRed(Edge.WHITE_RED);
    }

    /**
     * Inserts the White-Red edge.
     */
    protected void crossRed(Edge e) {
        int position = findEdge(e);
        if(getParity(position)) {
            switch(position) {
                case(0):
                    break;
                case(1):
                    updateFirst("U");
                    break;
                case(2):
                    updateFirst("U2");
                    break;
                case(3):
                    updateFirst("U'");
                    break;
                case(4):
                    updateFirst("R U");
                    break;
                case(5):
                    updateFirst("R' U");
                    break;
                case(6):
                    updateFirst("L U'");
                    break;
                case(7):
                    updateFirst("L' U'");
                    break;
                case(8):
                    updateFirst("F2");
                    break;
                case(9):
                    updateFirst("D' F2");
                    break;
                case(10):
                    updateFirst("D2 F2");
                    break;
                case(11):
                    updateFirst("D F2");
                    break;
                default:
                    System.out.println("red cross error.");
            }
        } else {
            switch(position) {
                case(0):
                    updateFirst("F R U");
                    break;
                case(1):
                    updateFirst("R' F'");
                    break;
                case(2):
                    updateFirst("B L U'");
                    break;
                case(3):
                    updateFirst("L F");
                    break;
                case(4):
                    updateFirst("F'");
                    break;
                case(5):
                    updateFirst("R2 F'");
                    break;
                case(6):
                    updateFirst("L2 F");
                    break;
                case(7):
                    updateFirst("F");
                    break;
                case(8):
                    updateFirst("D R F'");
                    break;
                case(9):
                    updateFirst("R F'");
                    break;
                case(10):
                    updateFirst("D' R F'");
                    break;
                case(11):
                    updateFirst("L' F");
                    break;
                default:
                    System.out.println("red cross error.");
            }
        }
    }
    
    protected void crossBlue() {
        crossBlue(Edge.WHITE_BLUE);
    }

    /**
     * Inserts the White-Blue edge.
     */
    void crossBlue(Edge e) {
        int position = findEdge(e);
        if(getParity(position)) {
            switch(position) {                
                case(1):
                    break;                
                case(2):
                    updateFirst("B2 D' R2");
                    break;
                case(3):
                    updateFirst("L2 D2 R2");
                    break;               
                case(4):
                    updateFirst("R");
                    break;
                case(5):
                    updateFirst("R'");
                    break;
                case(6):
                    updateFirst("B2 R'");
                    break;
                case(7):
                    updateFirst("L D2 R2");
                    break;               
                case(8):
                    updateFirst("D R2");
                    break;
                case(9):
                    updateFirst("R2");
                    break;
                case(10):
                    updateFirst("D' R2");
                    break;
                case(11):
                    updateFirst("D2 R2");
                    break; 
                default:
                    System.out.println("blue cross error.");
            }
        } else {
            switch(position) {
                case(1):
                    updateFirst("R U' B U");
                    break;
                case(2):
                    updateFirst("B' R'");
                    break;
                case(3):
                    updateFirst("L U F U'");
                    break;
                case(4):
                    updateFirst("U F' U'");
                    break;
                case(5):
                    updateFirst("U' B U");
                    break;
                case(6):
                    updateFirst("U' B' U");
                    break;
                case(7):
                    updateFirst("U F U'");
                    break;
                case(8):
                    updateFirst("F' R F");
                    break;
                case(9):
                    updateFirst("D B R'");
                    break;
                case(10):
                    updateFirst("B R'");
                    break;
                case(11):
                    updateFirst("D' B R'");
                    break;
                default:
                    System.out.println("blue cross error.");
            }
        }
    }

    protected void crossOrange() {
        crossOrange(Edge.WHITE_ORANGE);
    }
    /**
     * Inserts the White-Orange edge.
     */
    protected void crossOrange(Edge e) {
        int position = findEdge(e);
        if(getParity(position)) {
            switch(position) {
                case(1):
                    updateFirst("R2 D B2");
                    break;
                case(2):
                    break;
                case(3):
                    updateFirst("L2 D' B2");
                    break;               
                case(4):
                    updateFirst("R' D R B2");
                    break;
                case(5):
                    updateFirst("U R' U'");
                    break;
                case(6):
                    updateFirst("U' L U");
                    break;
                case(7):
                    updateFirst("U' L' U");
                    break;               
                case(8):
                    updateFirst("D2 B2");
                    break;
                case(9):
                    updateFirst("D B2");
                    break;
                case(10):
                    updateFirst("B2");
                    break;
                case(11):
                    updateFirst("D2' B2");
                    break; 
                default:
                    System.out.println("orange cross error.");
            }
        } else {
            switch(position) {
                case(1):
                    updateFirst("R B");
                    break;
                case(2):
                    updateFirst("B U' L U");
                    break;
                case(3):
                    updateFirst("L' B'");
                    break;
                case(4):
                    updateFirst("F D2 F' B2");
                    break;
                case(5):
                    updateFirst("B");
                    break;
                case(6):
                    updateFirst("B'");
                    break;
                case(7):
                    updateFirst("L2 B'");
                    break;
                case(8):
                    updateFirst("D' L B'");
                    break;
                case(9):
                    updateFirst("D2 L B'");
                    break;
                case(10):
                    updateFirst("D L B'");
                    break;
                case(11):
                    updateFirst("L B'");
                    break;
                default:
                    System.out.println("orange cross error.");
            }
        }
    }

    protected void crossGreen() {
        crossGreen(Edge.WHITE_GREEN);
    }

    /**
     * Inserts the White-Green edge.
     */
    void crossGreen(Edge e) {
        int position = findEdge(e);
        if(getParity(position)) {
            switch(position) {               
                case(3):
                    break;               
                case(4):
                    updateFirst("R' D2 R L2");
                    break;
                case(5):
                    updateFirst("R D2 R' L2");
                    break;
                case(6):
                    updateFirst("L");
                    break;
                case(7):
                    updateFirst("L'");
                    break;               
                case(8):
                    updateFirst("D' L2");
                    break;
                case(9):
                    updateFirst("D2 L2");
                    break;
                case(10):
                    updateFirst("D L2");
                    break;
                case(11):
                    updateFirst("L2");
                    break; 
                default:
                    System.out.println("green cross error.");
            }
        } else {
            switch(position) {
                case(3):
                    updateFirst("L U' F U");
                    break;
                case(4):
                    updateFirst("F D' F' L2");
                    break;
                case(5):
                    updateFirst("B' D B L2");
                    break;
                case(6):
                    updateFirst("U B' U'");
                    break;
                case(7):
                    updateFirst("U' F U");
                    break;
                case(8):
                    updateFirst("F L' F'");
                    break;
                case(9):
                    updateFirst("D' F L' F'");
                    break;
                case(10):
                    updateFirst("B' L B");
                    break;
                case(11):
                    updateFirst("D F L' F'");
                    break;
                default:
                    System.out.println("green cross error.");
            }
        }
    }

    /**
     * Finishes the first layer.
     */
    public void corners() {
        cornerInsert(Corner.WHITE_BLUE_RED, 0);
        updateFirst("y");
        cornerInsert(Corner.WHITE_ORANGE_BLUE, 1);
        updateFirst("y");
        cornerInsert(Corner.WHITE_GREEN_ORANGE, 2);
        updateFirst("y");
        cornerInsert(Corner.WHITE_RED_GREEN, 3);
        updateFirst("z2"); /* yellow on top, green in front */
    }

    /**
     * Inserts a corner into the first layer.
     * @param cubelet Corner piece
     * @param iteration Number of corners inserted before this
     */
    private void cornerInsert(Corner cubelet, int iteration) {
        look++;
        int position = findCorner(cubelet);
        int spin = getSpin(position);
        final String error = "corner " + iteration + " error.";
        if(spin == 0) {
            switch(position) {
                case 0:
                    break;
                case 1:
                    updateFirst("B' D2 B R' D R");
                    break;
                case 2:
                    updateFirst("L' D2 L D' R' D R");
                    break;
                case 3:
                    updateFirst("F' D' F R' D2 R");
                    break;
                case 4:
                    updateFirst("R' D2 R D R' D' R");
                    break;
                case 5:
                    updateFirst("D' R' D2 R D R' D' R");
                    break;
                case 6:
                    updateFirst("D2 R' D2 R D R' D' R");
                    break;
                case 7:
                    updateFirst("D R' D2 R D R' D' R");
                    break;
                default:
                    System.out.println(error);
            }
        } else if(spin == 1) {
            switch(position) {
                case 0:
                    updateFirst("F D' F' R' D' R");
                    break;
                case 1:
                    updateFirst("R D' R' D' R' D' R");
                    break;
                case 2:
                    updateFirst("L' D2 L R' D' R");
                    break;
                case 3:
                    updateFirst("L D' L' D R' D' R");
                    break;
                case 4:
                    updateFirst("D' R' D R");
                    break;
                case 5:
                    updateFirst("D2 R' D R");
                    break;
                case 6:
                    updateFirst("R' D2 R");
                    break;
                case 7:
                    updateFirst("R' D R");
                    break;
                default:
                    System.out.println(error);
            }
        } else {
            switch(position) {
                case 0:
                    updateFirst("R' D R D' R' D R");
                    break;
                case 1:
                    updateFirst("R D2 R2 D R");
                    break;
                case 2:
                    updateFirst("L' D L R' D2 R");
                    break;
                case 3:
                    updateFirst("L R' D L' R");
                    break;
                case 4:
                    updateFirst("R' D' R");
                    break;
                case 5:
                    updateFirst("D' R' D' R");
                    break;
                case 6:
                    updateFirst("D2 R' D' R");
                    break;
                case 7:
                    updateFirst("D R' D' R");
                    break;
                default:
                    System.out.println(error);
            }
        }
    }

    /**
     * Completes the second layer.
     */
    public void secondLayer() {
        edgeInsert(Edge.ORANGE_GREEN, false);
        updateSecond("y");
        edgeInsert(Edge.ORANGE_BLUE, true);
        updateSecond("y");
        edgeInsert(Edge.RED_BLUE, false);
        updateSecond("y");
        edgeInsert(Edge.RED_GREEN, true); /* yellow on top, red in front */
    }

    /**
     * Inserts an edge into the second layer.
     * @param cubelet Edge piece
     * @param natural Natural parity of face; true for red/orange, false for blue/green
     */
    private void edgeInsert(Edge cubelet, boolean natural) {
        look++;
        final String error = "second layer error.";
        int position = findEdge(cubelet);
        if(getParity(position) == natural) {
            switch(position) {
                case 0:
                    updateSecond("y U2 L' U L U F U' F' y'");
                    break;
                case 1:
                    updateSecond("y U' L' U L U F U' F' y'");
                    break;
                case 2:
                    updateSecond("y L' U L U F U' F' y'");
                    break;
                case 3:
                    updateSecond("y U L' U L U F U' F' y'");
                    break;
                case 4:
                    break;
                case 5:
                    updateSecond("y R U' R' U' F' U F y' R U' R' U' F' U F");
                    break;
                case 6:
                    updateSecond("y' L' U L U F U' F' y U2 R U' R' U' F' U F");
                    break;
                case 7:
                    updateSecond("L' U L U F U' F' y L' U L U F U' F' y'");
                    break;
                default:
                    System.out.println(error);
            }
        } else {
            switch(position) {
                case 0:
                    updateSecond("U R U' R' U' F' U F");
                    break;
                case 1:
                    updateSecond("U2 R U' R' U' F' U F");
                    break;
                case 2:
                    updateSecond("U' R U' R' U' F' U F");
                    break;
                case 3:
                    updateSecond("R U' R' U' F' U F");
                    break;
                case 4:
                    updateSecond("R U' R' U' F' U F U' R U' R' U' F' U F");
                    break;
                case 5:
                    updateSecond("y R U' R' U' F' U F U L' U L U F U' F' y'");
                    break;
                case 6:
                    updateSecond("y' L' U L U F U' F' y2 U' L' U L U F U' F' y'");
                    break;
                case 7:
                    updateSecond("L' U L U F U' F' U' R U' R' U' F' U F");
                    break;
                default:
                    System.out.println(error);
            }
        }
    }

    /**
     * Creates a yellow cross.
     */
    public void oll1() {
        boolean[] parity = {getParity(0), getParity(1), getParity(2), getParity(3)};
        if(parity[0] && parity[1] && parity[2]) {
            return;
        }
        look++;
        if(parity[0]) {
            if(parity[1]) { updateThird("U2 F U R U' R' F'"); }
            else if(parity[2]) { updateThird("U F R U R' U' F'"); }
            else { updateThird("U F U R U' R' F'"); }
        } else if(parity[1]) {
            if(parity[2]) { updateThird("U' F U R U' R' F'"); }
            else { updateThird("F R U R' U' F'"); }
        } else if(parity[3]) { updateThird("F U R U' R' F'"); }
        else {
            updateThird("F U R U' R' F' U F R U R' U' F'");
        }
    }

    /**
     * Creates a yellow face on top.
     */
    public void oll2() {
        int[] spin = new int[4];
        int zeroes = 0;
        for(int i = 0; i < 4; i++) {
            spin[i] = getSpin(i);
            if(spin[i] == 0) { zeroes++; }
        }

        /* Trust the natural recursion. */
        final String sune = "R U R' U R U2 R'";
        int spinToFind;
        if(zeroes == 0) { spinToFind = 2; }
        else if(zeroes == 1) { spinToFind = 0; }
        else if(zeroes == 2) { spinToFind = 1; }
        else { return; }

        look++;

        if(spin[3] == spinToFind) { updateThird(sune); }
        else if(spin[0] == spinToFind) { updateThird("U " + sune); }
        else if(spin[2] == spinToFind) { updateThird("U' " + sune); }
        else { updateThird("U2 " + sune); }

        oll2();
    }

    /**
     * Permutates the corners of the last layer.
     */
    public void pll1() {
        String alg = "R' F R' B2 R F' R' B2 R2";
        int same = -1;
        for(int i = 1; i < 5; i++) {
            if(compareCells(i, 0, 2)) {
                same = i - 1;
                break;
            }
        }
        look++;
        switch(same) {
            case 0:
                if(compareCells(2, 0, 2)) {
                    look--;
                    return;
                } else {
                    updateThird("U2 " + alg);
                    break;
                }
            case 1:
                updateThird("U' " + alg);
                break;
            case 2:
                updateThird(alg);
                break;
            case 3:
                updateThird("U " + alg);
                break;
            default:
                updateThird(alg + " U " + alg);
        }
    }

    /**
     * Permutates the edges of the last layer.
     */
    public void pll2() {
        int same = -1;
        for(int i = 1; i < 5; i++) {
            if(compareCells(i, 0, 1)) {
                same = i - 1;
                break;
            }
        }
        look++;
        switch(same) {
            case 0:
                if(compareCells(2, 0, 1)) {
                    look--;
                    return;
                } else {
                    updateThird("U2");
                    break;
                }
            case 1:
                updateThird("U'");
                break;
            case 2:
                break;
            case 3:
                updateThird("U");
                break;
            default:
                if(compareCells(1, 1, 2, 0)) {
                    updateThird("U M2 U' M2 U' M U2 M2 U2 M");
                } else if(compareCells(1, 1, 3, 0)) {
                    updateThird("M2 U M2 U2 M2 U M2");
                } else {
                    updateThird("M2 U' M2 U' M U2 M2 U2 M");
                }
                alignment();
                return;
        }

        if(compareCells(1, 1, 2, 0)) {
            updateThird("F2 U' L R' F2 L' R U' F2");
        } else {
            updateThird("F2 U L R' F2 L' R U F2");
        }
    }

    /**
     * Aligns the top layer.
     */
    public void alignment() {
        if(compareCells(1, 1, 2, 4)) {
            updateThird("U'");
        } else if(compareCells(1, 1, 3, 4)) {
            updateThird("U2");
        } else if(compareCells(1, 1, 4, 4)) {
            updateThird("U");
        }
    }

    /**
     * Updates the metrics given a set of moves.
     * @param moves Singmaster notation for multiple moves
     */
    public void updateMetrics(String moves) {
        qtm += countQTM(moves);
        htm += countHTM(moves);
        stm += countSTM(moves);
        AF moveset = new AF(moves);
        df += moveset.calculateDF();
        regrip += moveset.calculateRegrip();
        rotation += moveset.calculateRotation();
    }

    /**
     * Updates metrics, moves, and stores the moves for first layer.
     * @param moves Singmaster notation for multiple moves
     */
    protected void updateFirst(String moves) {
        moves(moves);
        updateMetrics(moves);
        if(!movesFirst.equals("") && !movesFirst.endsWith(" ")) {
            movesFirst += " ";
        }
        movesFirst += moves;
    }

    /**
     * Updates metrics, moves, and stores the moves for second layer.
     * @param moves Singmaster notation for multiple moves
     */
    protected void updateSecond(String moves) {
        if(!moves.isEmpty()) {
            moves(moves);
            updateMetrics(moves);
            if(!movesSecond.equals("") && !movesSecond.endsWith(" ")) {
                movesSecond += " ";
            }
            movesSecond += moves;
        }
    }

    /**
     * Updates metrics and moves for third layer.
     * @param moves Singmaster notation for multiple moves
     */
    protected void updateThird(String moves) {
        if(!moves.isEmpty()) {
            moves(moves);
            updateMetrics(moves);
        }
    }

    /**
     * Checks whether the cube is in solved state
     * @return true if solved
     */
    public boolean checkSolved() {
        for(int i = 0; i < 6; i++) {
            for(int j = 1; j < 9; j++) {
                if(!compareCells(i, 0, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * An object to analyze the Adjustment Factor.
     */
    class AF {
        private String[] move;
        private int[] flicks;
        private int[] left;
        private int[] right;

        AF(String sequence) {
            this(sequence.split(" "));
        }

        AF(String[] cells) {
            move = new String[cells.length];
            flicks = new int[cells.length];
            for(int i = 0; i < cells.length; i++) {
                if(cells[i].length() == 1) {
                    move[i] = cells[i];
                    flicks[i] = 1;
                } else if(cells[i].substring(1).equals("2")) {
                    move[i] = cells[i].substring(0, 1);
                    flicks[i] = 2;
                } else {
                    move[i] = cells[i].substring(0, 1);
                    flicks[i] = -1;
                }
            }
            left = new int[0];
            right = new int[0];
        }

        int calculateDF() {
            if(move.length < 4) {
                return 0;
            }
            int factor = 0;
            int counter = 3;
            while(counter < move.length) {
                String prev2 = move[counter-3] + move[counter-2];
                String current = move[counter-1] + move[counter];
                if(current.equals(prev2)) {
                    counter += 2;
                    factor++;
                } else {
                    counter++;
                }
            }
            return factor;
        }

        int calculateRotation() {
            if(move.length < 4) {
                return 0;
            }
            int rota = 0;
            for(int i = 1; i < move.length - 1; i++) {
                if(move[i].equals("x") || move[i].equals("y") || move[i].equals("z")) {
                    rota++;
                }
            }
            return rota;
        }

        int calculateRegrip() {
            int rg = 0;
            boolean entanglement = false;
            for(int i = 0; i < move.length; i++) {
                switch(move[i] + flicks[i]) {
                    case "L1": case "L2": case "L-1": case "l1": case "l2": case "l-1":
                        if(moveLeft(flicks[i]) == 1) {
                            if(entanglement) { entanglement = false; }
                            else { rg++; }
                        } break;
                    case "R1": case "R2": case "R-1": case "r1": case "r2": case "r-1":
                        if(moveRight(flicks[i]) == 1) {
                            if(entanglement) { entanglement = false; }
                            else { rg++; }
                        } break;
                    case "F1": case "B-1": case "S1": case "f1": case "b-1":
                        if(checkRight(-1)) {
                            right = new int[]{ -1 };
                        } else if(checkLeft(1)) {
                            left = new int[]{ 1 };
                        } else {
                            right = new int[]{ -1 };
                            rg++;
                        } break;
                    case "F-1": case "B1": case "S-1": case "f-1": case "b1":
                        if(checkLeft(-1)) {
                            left = new int[]{ -1 };
                        } else if(checkRight(1)) {
                            right = new int[]{ 1 };
                        } else {
                            left = new int[]{ -1 };
                            rg++;
                        } break;
                    case "F2": case "B2": case "S2": case "f2": case "b2":
                        if(left.length == 0 && right.length == 0) {
                            entanglement = true;
                            left = new int[]{ -1 };
                            right = new int[]{ -1 };
                        } else if(checkLeft(-1) && !checkLeft(1)) {
                            left = new int[]{ -1 };
                        } else if(checkRight(-1) && !checkRight(1)) {
                            right = new int[]{ -1 };
                        } else if(checkLeft(1) && !checkLeft(-1)) {
                            left = new int[]{ 1 }; 
                        } else if(checkRight(1) && !checkRight(-1)) {
                            right = new int[]{ 1 };
                        } else if(!checkRight(1) && !checkRight(-1) && !checkLeft(1) && !checkLeft(-1)) {
                            entanglement = true;
                            left = new int[]{ -1 };
                            right = new int[]{ -1 };
                            rg++;
                        } break;
                    case "U1":
                        if(checkRight(0) && checkRight(1) && right.length == 0) {
                            right = new int[]{ 0 };
                        } else if(checkRight(0) && !checkRight(1)) {
                            right = new int[]{ 0 };
                        } else if(checkRight(1) && !checkRight(0)) {
                            right = new int[]{ 1 };
                        } else if(!checkRight(0) && !checkRight(1)) {
                            right = new int[]{ 0 };
                            rg++;
                        } break;
                    case "U-1":
                        if(checkLeft(0) && checkLeft(1) && left.length == 0) {
                            left = new int[]{ 0 };
                        } else if(checkLeft(0) && !checkLeft(1)) {
                            left = new int[]{ 0 };
                        } else if(checkLeft(1) && !checkLeft(0)) {
                            left = new int[]{ 1 };
                        } else if(!checkLeft(0) && !checkLeft(1)) {
                            left = new int[]{ 0 };
                            rg++;
                        } break;
                    case "D-1": case "E-1": case "u1": case "d-1":
                        if(!checkRight(0)) {
                            rg++;
                        }
                        right = new int[]{ 0 };
                        break;
                    case "D1": case "E1": case "u-1": case "d1":
                        if(!checkLeft(0)) {
                            rg++;
                        }
                        left = new int[]{ 0 };
                        break;
                    case "U2": case "D2": case "E2": case "u2": case "d2":
                        if(checkLeft(0)) {
                            left = new int[]{ 0 };
                        } else if(checkRight(0)) {
                            right = new int[]{ 0 };
                        } else {
                            entanglement = true;
                            left = new int[]{ 0 };
                            right = new int[]{ 0 };
                            rg++;
                        } break;
                    case "M1": case "M2": case "M-1":
                        if(!checkLeft(8) && !checkRight(8)) {
                            entanglement = true;
                            left = new int[]{ 8 };
                            right = new int[]{ 8 };
                            rg++;
                        } break;
                    case "x1": case "x2": case "x-1":
                    case "y1": case "y2": case "y-1":
                    case "z1": case "z2": case "z-1":
                        left = new int[0];
                        right = new int[0];
                        break;
                    default: System.out.println("regrip calc error.");
                }
            }
            return rg;
        }

        boolean checkLeft(int position) {
            if(left.length == 0) {
                return true;
            } else if(left.length == 1) {
                return left[0] == position;
            } else {
                return left[0] == position || left[1] == position;
            }
        }

        boolean checkRight(int position) {
            if(right.length == 0) {
                return true;
            } else if(right.length == 1) {
                return right[0] == position;
            } else {
                return right[0] == position || right[1] == position;
            }
        }

        /**
         * Returns the regrips needed to perform the next move.
         * @param move +1 for elevation, -1 for depression, and 2 for sublimation
         * @return 1 if regrip; 0 if no
         */
        int moveLeft(int move) {
            if(left.length == 0) {
                left = assign(move);
            } else if(left.length == 1) {
                if(left[0] == 8) {
                    left = assign(move);
                    return 1;
                }
                left = change(left[0], move);
                if(left.length == 2) {
                    return 1;
                }
            } else {
                left = superposition(left, move);
            }
            return 0;
        }

        /**
         * Returns the regrips needed to perform the next move.
         * @param move +1 for elevation, -1 for depression, and 2 for sublimation
         * @return 1 if regrip; 0 if no
         */
        int moveRight(int move) {
            if(right.length == 0) {
                right = assign(move);
            } else if(right.length == 1) {
                if(right[0] == 8) {
                    right = assign(move);
                    return 1;
                }
                right = change(right[0], move);
                if(right.length == 2) {
                    return 1;
                }
            } else {
                right = superposition(right, move);
            }
            return 0;
        }

        int[] assign(int move) {
            switch(move) {
                case 1: return new int[]{ 0, 1 };
                case -1: return new int[]{ -1, 0 };
                case 2: return new int[]{ -1, 1 };
                default: System.out.println("assign error.");
            }
            return new int[0];
        }

        int[] change(int initial, int move) {
            if(move == 2) {
                if(initial == 0) {
                    return assign(2);
                } else {
                    return new int[]{ -initial };
                }
            } else {
                int answer = initial + move;
                if(answer == -2 || answer == 2) {
                    return assign(move);
                }
                return new int[]{ answer };
            }
        }

        int[] superposition(int[] initial, int move) {
            if(move == 2) {
                if(initial[0] == -1 && initial[1] == 1) {
                    return initial;
                } else if(initial[0] == -1) {
                    return change(initial[0], move);
                } else if(initial[1] == 1) {
                    return change(initial[1], move);
                } else {
                    return assign(2);
                }
            } else {
                initial[0] += move;
                initial[1] += move;
                if(initial[1] == 2) {
                    return new int[]{ initial[0] };
                } else if(initial[0] == -2) {
                    return new int[]{ initial[1] };
                } else {
                    return Arrays.copyOf(initial, 2);
                }
            }
        }
    }

    /**
     * Counts the Quarter Turn Metric from a set of moves.
     * @param moves Singmaster notation for multiple moves
     * @return QTM
     */
    public int countQTM(String moves) {
        String[] instructions = moves.split(" ");
        int count = 0;
        for(String i : instructions) {
            count += countEachQTM(i);
        }
        return count;
    }

    /**
     * Counts the Quarter Turn Metric for a single move.
     * @param move Singmaster notation for a single move
     * @return QTM
     */
    private int countEachQTM(String move) {
        String face = move.substring(0, 1);
        switch(face) {
            case("x"): case("y"): case("z"):
                return 0;

            case("M"): case("E"): case("S"):
                if(move.length() == 1) {
                    return 2;
                } else if(move.substring(1).equals("2")) {
                    return 4;
                } else {
                    return 2;
                }
            
            default:
                if(move.length() == 1) {
                    return 1;
                } else if(move.substring(1).equals("2")) {
                    return 2;
                } else {
                    return 1;
                }
        }
    }

    /**
     * Counts the Half Turn Metric from a set of moves.
     * @param moves Singmaster notation for multiple moves
     * @return HTM
     */
    public int countHTM(String moves) {
        String[] instructions = moves.split(" ");
        int count = 0;
        for(String i : instructions) {
            String face = i.substring(0, 1);
            switch(face) {
                case("x"): case("y"): case("z"):
                    break;

                case("M"): case("E"): case("S"):
                    count += 2;
                    break;
                
                default:
                    count++;
                    break;
            }
        }
        return count;
    }

    /**
     * Counts the Slice Turn Metric from a set of moves.
     * @param moves Singmaster notation for multiple moves
     * @return STM
     */
    public int countSTM(String moves) {
        String[] instructions = moves.split(" ");
        int count = 0;
        for(String i : instructions) {
            String face = i.substring(0, 1);
            if(!face.equals("x") && !face.equals("y") && !face.equals("z")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the Quarter Turn Metric.
     * @return QTM
     */
    public int getQTM() {
        return qtm;
    }

    /**
     * Returns the Half Turn Metric.
     * @return HTM
     */
    public int getHTM() {
        return htm;
    }

    /**
     * Returns the Slice Turn Metric
     * @return STM
     */
    public int getSTM() {
        return stm;
    }

    public int getAF() {
        return look * 2 + rotation * 2 + regrip - df;
    }

    public int getAQTM() {
        return qtm + getAF();
    }

    public int getAHTM() {
        return htm + getAF();
    }

    public int getASTM() {
        return stm + getAF();
    }
    
    public int getNR() {
        return look + rotation * 2 - df;
    }

    public int getNRQTM() {
        return qtm + getNR();
    }

    public int getNRHTM() {
        return htm + getNR();
    }

    public int getNRSTM() {
        return stm + getNR();
    }
}
