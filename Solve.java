public class Solve extends RubiksCube {
    private int qtm;
    private int htm;
    private int stm;
    private String movesFirst;

    /**
     * Initializes a solved cube and then solves it. (No work done)
     */
    public Solve() {
        super();
        qtm = 0;
        htm = 0;
        stm = 0;
        movesFirst = "";
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
        movesFirst = "";
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
                    updateFirst("B' R");
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
                    updateFirst("D' F L F'");
                    break;
                case(10):
                    updateFirst("B' L B");
                    break;
                case(11):
                    updateFirst("D F L F'");
                    break;
                default:
                    System.out.println("green cross error.");
            }
        }
    }

    public void corners() {
        corner0();
        corner1();
        corner2();
        corner3();
    }

    private void corner0() {
        int position = findCorner(Corner.WHITE_BLUE_RED);
        int spin = getSpin(position);
        String error = "corner 0 error.";
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
                    updateFirst("R D' R2 D R");
                    break;
                case 6:
                    updateFirst("B D2 B' R' D R");
                    break;
                case 7:
                    updateFirst("F' D F D' R' D' R");
                    break;
                default:
                    System.out.println(error);
            }
        } else if(spin == 1) {

        } else {

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
    private void updateFirst(String moves) {
        moves(moves);
        updateMetrics(moves);
        if(!movesFirst.equals("") && !movesFirst.endsWith(" ")) {
            movesFirst += " ";
        }
        movesFirst += moves;
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
