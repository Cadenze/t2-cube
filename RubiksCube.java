import java.util.Arrays;

/**
 * Emulation of a Rubik's cube in terms of its "cubelets".
 */
public class RubiksCube {
    private Corner[] corners;
    private int[] spin;       /* 0 spin is white/yellow side up/down, count clockwise */
    private Edge[] edges;
    private boolean[] parity; /* true parity is for white/yellow/(red) facing not left/right */
    private String[] centres;

    public static void main(String[] args) {
        RubiksCube gan = new RubiksCube("U' D2 R2 F' D L R2");
        gan.printCube();
    }

    /**
     * Initializes a solved cube.
     */
    public RubiksCube() {
        corners = new Corner[] {
            Corner.WHITE_BLUE_RED, Corner.WHITE_ORANGE_BLUE, Corner.WHITE_GREEN_ORANGE, Corner.WHITE_RED_GREEN,
            Corner.YELLOW_RED_BLUE, Corner.YELLOW_BLUE_ORANGE, Corner.YELLOW_ORANGE_GREEN, Corner.YELLOW_GREEN_RED
        };
        spin = new int[8];
        edges = new Edge[] {
            Edge.WHITE_RED, Edge.WHITE_BLUE, Edge.WHITE_ORANGE, Edge.WHITE_GREEN,
            Edge.RED_BLUE, Edge.ORANGE_BLUE, Edge.ORANGE_GREEN, Edge.RED_GREEN,
            Edge.YELLOW_RED, Edge.YELLOW_BLUE, Edge.YELLOW_ORANGE, Edge.YELLOW_GREEN
        };
        parity = new boolean[12];
        Arrays.fill(parity, true);
        centres = new String[] { "w", "r", "b", "o", "g", "y"};
    }

    /**
     * Initializes a cube, scrambled based on a string of instructions
     * @param instruction The scramble instructions
     */
    public RubiksCube(String instruction) {
        this();
        moves(instruction);
    }

    /**
     * Takes in a move, parses the notation, and then executes it.
     * @param move Singmaster notation for a single move
     */
    public void move(String move) {
        String face = move.substring(0,1);
        int turn = 0;
        if(move.length() == 1) {
            turn = 1;
        } else if(move.substring(1).equals("2")) {
            turn = 2;
        } else {
            turn = 3;
        }

        turnFace(face, turn);
    }

    /**
     * Takes in multiple moves, parses the notation, and then executes the moves
     * @param instruction Singmaster notation for multiple moves
     */
    public void moves(String instruction) {
        String[] instructions = instruction.split(" ");
        for(int i = 0; i < instructions.length; i++) {
            move(instructions[i]);
        }
    }

    /**
     * Turns a face of the cube a certain number of times.
     * @param face Indicates which face we are turning
     * @param turn Number of quarter-turns we are making
     */
    private void turnFace(String face, int turn) {
        switch(face) {
            case "U":
                for(int i = 0; i < turn; i++) {
                    spinCorner(0, 1, 2, 3);
                    spinEdge(0, 1, 2, 3);
                }
                break;

            case "D":
                for(int i = 0; i < turn; i++) {
                    spinCorner(4, 7, 6, 5);
                    spinEdge(8, 11, 10, 9);
                }
                break;

            case "F":
                for(int i = 0; i < turn; i++) {
                    spinCorner(0, 3, 7, 4);
                    spinEdge(0, 7, 8, 4);
                    
                    editSpin(0, +1);
                    editSpin(3, -1);
                    editSpin(7, +1);
                    editSpin(4, -1);

                    editParity(0);
                    editParity(7);
                    editParity(8);
                    editParity(4);
                }
                break;

            case "B":
                for(int i = 0; i < turn; i++) {
                    spinCorner(2, 1, 5, 6);
                    spinEdge(2, 5, 10, 6);

                    editSpin(2, +1);
                    editSpin(1, -1);
                    editSpin(5, +1);
                    editSpin(6, -1);

                    editParity(2);
                    editParity(5);
                    editParity(10);
                    editParity(6);
                }
                break;

            case "L":
                for(int i = 0; i < turn; i++) {
                    spinCorner(3, 2, 6, 7);
                    spinEdge(3, 6, 11, 7);

                    editSpin(3, +1);
                    editSpin(2, -1);
                    editSpin(6, +1);
                    editSpin(7, -1);
                }
                break;

            case "R":
                for(int i = 0; i < turn; i++) {
                    spinCorner(1, 0, 4, 5);
                    spinEdge(1, 4, 9, 5);

                    editSpin(1, +1);
                    editSpin(0, -1);
                    editSpin(4, +1);
                    editSpin(5, -1);
                }
                break;

            case "M":
                for(int i = 0; i < turn; i++) {
                    spinEdge(0, 2, 10, 8);
                    spinCentre(0, 3, 5, 1);
                    
                    editParity(0);
                    editParity(2);
                    editParity(10);
                    editParity(8);
                }
                break;
            
            case "E":
                for(int i = 0; i < turn; i++) {
                    spinEdge(7, 6, 5, 4);
                    spinCentre(4, 3, 2, 1);

                    editParity(7);
                    editParity(6);
                    editParity(5);
                    editParity(4);
                }
                break;

            case "S":
                for(int i = 0; i < turn; i++) {
                    spinEdge(1, 3, 11, 9);
                    spinCentre(0, 4, 5, 2);

                    editParity(1);
                    editParity(3);
                    editParity(11);
                    editParity(9);
                }
                break;

            case "x":
                for(int i = 0; i < turn; i++) {
                    spinCorner(1, 0, 4, 5);
                    spinEdge(1, 4, 9, 5);
                    spinEdge(0, 8, 10, 2);
                    spinCentre(0, 1, 5, 3);
                    spinCorner(3, 7, 6, 2);
                    spinEdge(3, 7, 11, 6);

                    editSpin(1, +1);
                    editSpin(0, -1);
                    editSpin(4, +1);
                    editSpin(5, -1);
                    editSpin(3, +1);
                    editSpin(7, -1);
                    editSpin(6, +1);
                    editSpin(2, -1);

                    editParity(0);
                    editParity(2);
                    editParity(10);
                    editParity(8);
                }
                break;

            case "y":
                for(int i = 0; i < turn; i++) {
                    spinCorner(0, 1, 2, 3);
                    spinEdge(0, 1, 2, 3);
                    spinEdge(7, 4, 5, 6);
                    spinCentre(4, 1, 2, 3);
                    spinCorner(4, 5, 6, 7);
                    spinEdge(8, 9, 10, 11);

                    editParity(7);
                    editParity(6);
                    editParity(5);
                    editParity(4);
                }
                break;

            case "z":
                for(int i = 0; i < turn; i++) {
                    spinCorner(0, 3, 7, 4);
                    spinEdge(0, 7, 8, 4);
                    spinEdge(1, 3, 11, 9);
                    spinCentre(0, 4, 5, 2);
                    spinCorner(2, 6, 5, 1);
                    spinEdge(2, 6, 10, 5);

                    editSpin(2, +1);
                    editSpin(1, -1);
                    editSpin(5, +1);
                    editSpin(6, -1);
                    editSpin(0, +1);
                    editSpin(3, -1);
                    editSpin(7, +1);
                    editSpin(4, -1);

                    editParity(0);
                    editParity(7);
                    editParity(8);
                    editParity(4);
                    editParity(1);
                    editParity(3);
                    editParity(11);
                    editParity(9);
                    editParity(2);
                    editParity(5);
                    editParity(10);
                    editParity(6);
                }
                break;

            default:
                System.out.println("turnFace error.");
                break;
        }
    }

    /**
     * Spins 4 centres 90 degrees clockwise
     * @param twelve o'clock position
     * @param nine o'clock position
     * @param six o'clock position
     * @param three o'clock position
     */
    private void spinCentre(int twelve, int nine, int six, int three) {
        String temp = centres[twelve];
        centres[twelve] = centres[nine];
        centres[nine] = centres[six];
        centres[six] = centres[three];
        centres[three] = temp;
    }

    /**
     * Spins 4 corners 90 degrees clockwise.
     * @param twelve o'clock position
     * @param nine o'clock position
     * @param six o'clock position
     * @param three o'clock position
     */
    private void spinCorner(int twelve, int nine, int six, int three) {
        Corner c = corners[twelve];
        corners[twelve] = corners[nine];
        corners[nine] = corners[six];
        corners[six] = corners[three];
        corners[three] = c;

        transferSpin(twelve, nine, six, three);
    }

    /**
     * Spins 4 edges 90 degrees clockwise.
     * @param twelve o'clock position
     * @param nine o'clock position
     * @param six o'clock position
     * @param three o'clock position
     */
    private void spinEdge(int twelve, int nine, int six, int three) {
        Edge e = edges[twelve];
        edges[twelve] = edges[nine];
        edges[nine] = edges[six];
        edges[six] = edges[three];
        edges[three] = e;

        transferParity(twelve, nine, six, three);
    }

    /**
     * Allows the spin of corners to follow to their new position, not adjusted for faceTurn.
     * @param twelve o'clock position
     * @param nine o'clock position
     * @param six o'clock position
     * @param three o'clock position
     */
    private void transferSpin(int twelve, int nine, int six, int three) {
        int s = spin[twelve];
        spin[twelve] = spin[nine];
        spin[nine] = spin[six];
        spin[six] = spin[three];
        spin[three] = s;
    }

    /**
     * Allows the parity of edges to follow to their new position, not adjusted for faceTurn.
     * @param twelve o'clock position
     * @param nine o'clock position
     * @param six o'clock position
     * @param three o'clock position
     */
    private void transferParity(int twelve, int nine, int six, int three) {
        boolean p = parity[twelve];
        parity[twelve] = parity[nine];
        parity[nine] = parity[six];
        parity[six] = parity[three];
        parity[three] = p;
    }

    /**
     * Increments or decrements the spin of a distinct corner.
     * @param corner Corner number
     * @param delta The change in spin
     */
    private void editSpin(int corner, int delta) {
        spin[corner] += delta;
        while(spin[corner] > 2) {
            spin[corner] -= 3;
        }
        while(spin[corner] < 0) {
            spin[corner] += 3;
        }
    }

    /**
     * Changes the parity of a specific edge.
     * @param edge
     */
    private void editParity(int edge) {
        parity[edge] = !parity[edge];
    }

    /**
     * Returns the corner at a specified position.
     * @param position Position of corner
     * @return The colours of the corner
     */
    public Corner getCorner(int position) {
        return corners[position];
    }

    /**
     * Returns the spin of the corner at a specified position.
     * @param position Position of corner
     * @return The spin of a corner
     */
    public int getSpin(int position) {
        return spin[position];
    }

    /**
     * Returns the edge at a specified position.
     * @param position Position of edge
     * @return The colours of an edge
     */
    public Edge getEdge(int position) {
        return edges[position];
    }

    /**
     * Returns the centre at a specified face.
     * @param face Specified face
     * @return The colour of the centre
     */
    public String getCentre(int face) {
        return centres[face];
    }

    /**
     * Returns the parity of the edge at a specified position.
     * @param position Position of edge
     * @return The parity of an edge
     */
    public boolean getParity(int position) {
        return parity[position];
    }

    /**
     * Returns the position of a specified corner piece.
     * @param cubelet Corner piece
     * @return Position of piece
     */
    public int findCorner(Corner cubelet) {
        return findCorner(cubelet, 0, 8);
    }

    /**
     * Returns the position of a specified corner piece, within parameters.
     * @param cubelet Corner piece
     * @param start Beginning of search position (inclusive)
     * @param stop End of search position (non-inclusive)
     * @return Position of piece
     */
    public int findCorner(Corner cubelet, int start, int stop) {
        for(int i = start; i < stop; i++) {
            if(cubelet == corners[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the position of a specified edge piece.
     * @param cubelet Edge piece
     * @return Position of piece
     */
    public int findEdge(Edge cubelet) {
        return findEdge(cubelet, 0, 12);
    }

    /**
     * Returns the position of a specifed edge piece, within parameters.
     * @param cubelet Edge piece
     * @param start Beginning of search position (inclusive)
     * @param stop End of search position (non-inclusive)
     * @return Position of piece
     */
    public int findEdge(Edge cubelet, int start, int stop) {
        for(int i = start; i < stop; i++) {
            if(cubelet == edges[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the face that the specified colour centre is located.
     * @param colour Colour of the centre piece
     * @return Face of a cube
     */
    public int findCentre(String colour) {
        for(int i = 0; i < 6; i++) {
            if(colour.equals(centres[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the colours of a cube as 6 arrays of 9 cells.
     * @return An array of 6 arrays of 9 elements representing colours
     */
    public String[][] compileCube() {
        String[][] rubik = new String[6][9];
        for(int i = 0; i < 6; i++) {
            rubik[i] = Arrays.copyOf(compileFace(i), 9);
        }
        return rubik;
    }

    /**
     * Returns the 9 colours of a face in 9 cells.
     * @param face
     * @return An array of 9 elements representing colours
     */
    public String[] compileFace(int face) {
        String[] cell = new String[9];
        for(int i = 0; i < 9; i++) {
            cell[i] = compileCell(face, i);
        }
        return cell;
    }

    /**
     * Returns the colour of a cell
     * @param face
     * @param cell
     * @return A string representing colour
     */
    public String compileCell(int face, int cell) {
        final String error = "compileCell error.";
        if(cell == 4) {
            return centres[face];
        }

        switch(face) {
            case 0:
                switch(cell) {
                    case 0: return corners[2].colour(spin[2], 0);
                    case 1: return edges[2].colour(parity[2], 0);
                    case 2: return corners[1].colour(spin[1], 0);
                    case 3: return edges[3].colour(parity[3], 0);
                    case 5: return edges[1].colour(parity[1], 0);
                    case 6: return corners[3].colour(spin[3], 0);
                    case 7: return edges[0].colour(parity[0], 0);
                    case 8: return corners[0].colour(spin[0], 0);
                    default: System.out.println(error);
                }
                break;

            case 1:
                switch(cell) {
                    case 0: return corners[3].colour(spin[3], 1);
                    case 1: return edges[0].colour(parity[0], 1);
                    case 2: return corners[0].colour(spin[0], 2);
                    case 3: return edges[7].colour(parity[7], 0);
                    case 5: return edges[4].colour(parity[4], 0);
                    case 6: return corners[7].colour(spin[7], 2);
                    case 7: return edges[8].colour(parity[8], 1);
                    case 8: return corners[4].colour(spin[4], 1);
                    default: System.out.println(error);
                }
                break;

            case 2:
                switch(cell) {
                    case 0: return corners[0].colour(spin[0], 1);
                    case 1: return edges[1].colour(parity[1], 1);
                    case 2: return corners[1].colour(spin[1], 2);
                    case 3: return edges[4].colour(parity[4], 1);
                    case 5: return edges[5].colour(parity[5], 1);
                    case 6: return corners[4].colour(spin[4], 2);
                    case 7: return edges[9].colour(parity[9], 1);
                    case 8: return corners[5].colour(spin[5], 1);
                    default: System.out.println(error);
                }
                break;

            case 3:
                switch(cell) {
                    case 0: return corners[1].colour(spin[1], 1);
                    case 1: return edges[2].colour(parity[2], 1);
                    case 2: return corners[2].colour(spin[2], 2);
                    case 3: return edges[5].colour(parity[5], 0);
                    case 5: return edges[6].colour(parity[6], 0);
                    case 6: return corners[5].colour(spin[5], 2);
                    case 7: return edges[10].colour(parity[10], 1);
                    case 8: return corners[6].colour(spin[6], 1);
                    default: System.out.println(error);
                }
                break;

            case 4:
                switch(cell) {
                    case 0: return corners[2].colour(spin[2], 1);
                    case 1: return edges[3].colour(parity[3], 1);
                    case 2: return corners[3].colour(spin[3], 2);
                    case 3: return edges[6].colour(parity[6], 1);
                    case 5: return edges[7].colour(parity[7], 1);
                    case 6: return corners[6].colour(spin[6], 2);
                    case 7: return edges[11].colour(parity[11], 1);
                    case 8: return corners[7].colour(spin[7], 1);
                    default: System.out.println(error);
                }
                break;

            case 5:
                switch(cell) {
                    case 0: return corners[7].colour(spin[7], 0);
                    case 1: return edges[8].colour(parity[8], 0);
                    case 2: return corners[4].colour(spin[4], 0);
                    case 3: return edges[11].colour(parity[11], 0);
                    case 5: return edges[9].colour(parity[9], 0);
                    case 6: return corners[6].colour(spin[6], 0);
                    case 7: return edges[10].colour(parity[10], 0);
                    case 8: return corners[5].colour(spin[5], 0);
                    default: System.out.println(error);
                }
                break;

            default:
                System.out.println(error);
        }
        return "";
    }

    /**
     * Compares the colours of two cells.
     * @param face1
     * @param cell1
     * @param face2
     * @param cell2
     * @return true if same, false if different
     */
    public boolean compareCells(int face1, int cell1, int face2, int cell2) {
        return compileCell(face1, cell1).equals(compileCell(face2, cell2));
    }

    /**
     * Compares the colours of two cells on the same face.
     * @param face
     * @param cell1
     * @param cell2
     * @return true if same, false if different
     */
    public boolean compareCells(int face, int cell1, int cell2) {
        return compareCells(face, cell1, face, cell2);
    }

    /**
     * Prints a net of the cube to the console.
     */
    public void printCube() {
        String[][] rubik = compileCube();
        System.out.println("    " + rubik[3][8] + rubik[3][7] + rubik[3][6]);
        System.out.println("    " + rubik[3][5] + rubik[3][4] + rubik[3][3]);
        System.out.println("    " + rubik[3][2] + rubik[3][1] + rubik[3][0]);
        System.out.println(rubik[4][6] + rubik[4][3] + rubik[4][0] + " " + rubik[0][0] + rubik[0][1] + rubik[0][2] + " " + rubik[2][2] + rubik[2][5] + rubik[2][8] + " " + rubik[5][8] + rubik[5][7] + rubik[5][6]);
        System.out.println(rubik[4][7] + rubik[4][4] + rubik[4][1] + " " + rubik[0][3] + rubik[0][4] + rubik[0][5] + " " + rubik[2][1] + rubik[2][4] + rubik[2][7] + " " + rubik[5][5] + rubik[5][4] + rubik[5][3]);
        System.out.println(rubik[4][8] + rubik[4][5] + rubik[4][2] + " " + rubik[0][6] + rubik[0][7] + rubik[0][8] + " " + rubik[2][0] + rubik[2][3] + rubik[2][6] + " " + rubik[5][2] + rubik[5][1] + rubik[5][0]);
        System.out.println("    " + rubik[1][0] + rubik[1][1] + rubik[1][2]);
        System.out.println("    " + rubik[1][3] + rubik[1][4] + rubik[1][5]);
        System.out.println("    " + rubik[1][6] + rubik[1][7] + rubik[1][8]);
    }

    /**
     * Returns the colours of a cube.
     * @return 6 words, 9 letters each
     */
    public String toString() {
        StringBuilder text = new StringBuilder();
        int f = 0;
        while(true) {
            text.append(toString(f));
            f++;
            if(f == 6) {
                break;
            }
            text.append(" ");
        }
        return text.toString();
    }

    /**
     * Returns the 9 colours of a specific face, proceeding left to right, row by row.
     * @param face
     * @return 9 letters representing the 9 colours of a face
     */
    public String toString(int face) {
        StringBuilder colours = new StringBuilder();
        String[] array = compileFace(face);
        for(int i = 0; i < 9; i++) {
            colours.append(array[i]);
        }
        return colours.toString();
    }
}