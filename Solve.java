public class Solve extends RubiksCube {
    private int qtm;
    private int htm;
    private int stm;
    private String movesCross;

    /**
     * Initializes a solved cube and then solves it. (No work done)
     */
    public Solve() {
        super();
        qtm = 0;
        htm = 0;
        stm = 0;
        movesCross = "";
        solve();
    }

    /**
     * Initializes a cube, scrambles it, and then solves it.
     * @param instruction The scramble instructions
     */
    public Solve(String instruction) {
        super(instruction);
        qtm = 0;
        htm = 0;
        stm = 0;
        movesCross = "";
        solve();
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
    }

    /**
     * Creates a white cross.
     */
    public void cross() {
        crossRed();
        crossBlue();
        crossOrange();
        crossGreen();
        move("x2"); /* yellow on top for ease of every step after */
    }

    /**
     * Inserts the White-Red edge.
     */
    private void crossRed() {
        int position = findEdge(Edge.WHITE_RED);
        if(getParity(position)) {
            switch(position) {
                case(0):
                    break;
                case(1):
                    updateCross("U");
                    break;
                case(2):
                    updateCross("U2");
                    break;
                case(3):
                    updateCross("U'");
                    break;
                case(4):
                    updateCross("R U");
                    break;
                case(5):
                    updateCross("R' U");
                    break;
                case(6):
                    updateCross("L U'");
                    break;
                case(7):
                    updateCross("L' U'");
                    break;
                case(8):
                    updateCross("F2");
                    break;
                case(9):
                    updateCross("D' F2");
                    break;
                case(10):
                    updateCross("D2 F2");
                    break;
                case(11):
                    updateCross("D F2");
                    break;
                default:
                    System.out.println("red cross error.");
            }
        } else {
            switch(position) {
                case(0):
                    updateCross("F R U");
                    break;
                case(1):
                    updateCross("R' F'");
                    break;
                case(2):
                    updateCross("B L U'");
                    break;
                case(3):
                    updateCross("L F");
                    break;
                case(4):
                    updateCross("F'");
                    break;
                case(5):
                    updateCross("R2 F'");
                    break;
                case(6):
                    updateCross("L2 F");
                    break;
                case(7):
                    updateCross("F");
                    break;
                case(8):
                    updateCross("D R F'");
                    break;
                case(9):
                    updateCross("R F'");
                    break;
                case(10):
                    updateCross("D' R F'");
                    break;
                case(11):
                    updateCross("L' F");
                    break;
                default:
                    System.out.println("red cross error.");
            }
        }
    }
    
    /**
     * Inserts the White-Blue edge.
     */
    private void crossBlue() {
        int position = findEdge(Edge.WHITE_BLUE);
        if(getParity(position)) {
            switch(position) {                
                case(1):
                    break;                
                case(2):
                    updateCross("B2 D' R2");
                    break;
                case(3):
                    updateCross("L2 D2 R2");
                    break;               
                case(4):
                    updateCross("R");
                    break;
                case(5):
                    updateCross("R'");
                    break;
                case(6):
                    updateCross("B2 R'");
                    break;
                case(7):
                    updateCross("L D2 R2");
                    break;               
                case(8):
                    updateCross("D R2");
                    break;
                case(9):
                    updateCross("R2");
                    break;
                case(10):
                    updateCross("D' R2");
                    break;
                case(11):
                    updateCross("D2 R2");
                    break; 
                default:
                    System.out.println("blue cross error.");
            }
        } else {
            switch(position) {
                case(1):
                    updateCross("R U' B U");
                    break;
                case(2):
                    updateCross("B' R");
                    break;
                case(3):
                    updateCross("L U F U'");
                    break;
                case(4):
                    updateCross("U F' U'");
                    break;
                case(5):
                    updateCross("U' B U");
                    break;
                case(6):
                    updateCross("U' B' U");
                    break;
                case(7):
                    updateCross("U F U'");
                    break;
                case(8):
                    updateCross("F' R F");
                    break;
                case(9):
                    updateCross("D B R'");
                    break;
                case(10):
                    updateCross("B R'");
                    break;
                case(11):
                    updateCross("D' B R'");
                    break;
                default:
                    System.out.println("blue cross error.");
            }
        }
    }

    /**
     * Inserts the White-Orange edge.
     */
    private void crossOrange() {
        int position = findEdge(Edge.WHITE_ORANGE);
        if(getParity(position)) {
            switch(position) {               
                case(2):
                    break;
                case(3):
                    updateCross("L2 D' B2");
                    break;               
                case(4):
                    updateCross("R' D R B2");
                    break;
                case(5):
                    updateCross("U R' U'");
                    break;
                case(6):
                    updateCross("U' L U");
                    break;
                case(7):
                    updateCross("U' L' U");
                    break;               
                case(8):
                    updateCross("D2 B2");
                    break;
                case(9):
                    updateCross("D B2");
                    break;
                case(10):
                    updateCross("B2");
                    break;
                case(11):
                    updateCross("D2' B2");
                    break; 
                default:
                    System.out.println("orange cross error.");
            }
        } else {
            switch(position) {
                case(2):
                    updateCross("B U' L U");
                    break;
                case(3):
                    updateCross("L' B'");
                    break;
                case(4):
                    updateCross("F D2 F' B2");
                    break;
                case(5):
                    updateCross("B");
                    break;
                case(6):
                    updateCross("B'");
                    break;
                case(7):
                    updateCross("L2 B'");
                    break;
                case(8):
                    updateCross("D' L B'");
                    break;
                case(9):
                    updateCross("D2 L B'");
                    break;
                case(10):
                    updateCross("D L B'");
                    break;
                case(11):
                    updateCross("L B'");
                    break;
                default:
                    System.out.println("orange cross error.");
            }
        }
    }

    /**
     * Inserts the White-Green edge.
     */
    private void crossGreen() {
        int position = findEdge(Edge.WHITE_GREEN);
        if(getParity(position)) {
            switch(position) {               
                case(3):
                    break;               
                case(4):
                    updateCross("R' D2 R L2");
                    break;
                case(5):
                    updateCross("R D2 R' L2");
                    break;
                case(6):
                    updateCross("L");
                    break;
                case(7):
                    updateCross("L'");
                    break;               
                case(8):
                    updateCross("D' L2");
                    break;
                case(9):
                    updateCross("D2 L2");
                    break;
                case(10):
                    updateCross("D L2");
                    break;
                case(11):
                    updateCross("L2");
                    break; 
                default:
                    System.out.println("green cross error.");
            }
        } else {
            switch(position) {
                case(3):
                    updateCross("L U' F U");
                    break;
                case(4):
                    updateCross("F D' F' L2");
                    break;
                case(5):
                    updateCross("B' D B L2");
                    break;
                case(6):
                    updateCross("U B' U'");
                    break;
                case(7):
                    updateCross("U' F U");
                    break;
                case(8):
                    updateCross("F L' F'");
                    break;
                case(9):
                    updateCross("D' F L F'");
                    break;
                case(10):
                    updateCross("B' L B");
                    break;
                case(11):
                    updateCross("D F L F'");
                    break;
                default:
                    System.out.println("green cross error.");
            }
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
    }

    /**
     * Updates metrics, moves, and stores the moves for cross.
     * @param moves Singmaster notation for multiple moves
     */
    private void updateCross(String moves) {
        moves(moves);
        updateMetrics(moves);
        if(!movesCross.equals("") && !movesCross.endsWith(" ")) {
            movesCross += " ";
        }
        movesCross += moves;
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
}
