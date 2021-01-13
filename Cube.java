import java.util.Arrays;

public class Cube {
    private String[][][] rubik;

    /**
     * Initializes a solved 3x3x3 Rubik's Cube.
     *      w = white ; r = red   ; b = blue;
     *      y = yellow; o = orange; g = green.
     */
    public Cube() {
        rubik = new String[][][] {
            {{"w","w","w"},{"w","w","w"},{"w","w","w"}},
            {{"r","r","r"},{"r","r","r"},{"r","r","r"}},
            {{"b","b","b"},{"b","b","b"},{"b","b","b"}},
            {{"o","o","o"},{"o","o","o"},{"o","o","o"}},
            {{"g","g","g"},{"g","g","g"},{"g","g","g"}},
            {{"y","y","y"},{"y","y","y"},{"y","y","y"}}
        };
    }

    /**
     * Initializes a cube, scrambled based on a string of instructions
     * @param instruction The scramble instructions
     */
    public Cube(String instruction) {
        rubik = new String[][][] {
            {{"w","w","w"},{"w","w","w"},{"w","w","w"}},
            {{"r","r","r"},{"r","r","r"},{"r","r","r"}},
            {{"b","b","b"},{"b","b","b"},{"b","b","b"}},
            {{"o","o","o"},{"o","o","o"},{"o","o","o"}},
            {{"g","g","g"},{"g","g","g"},{"g","g","g"}},
            {{"y","y","y"},{"y","y","y"},{"y","y","y"}}
        };

        String[] instructions = instruction.split(" ");
        for(int i = 0; i < instructions.length; i++) {
            move(instructions[i]);
        }
    }

    /**
     * Takes in a move, parses the notation, and then executes it.
     * @param move Singmaster notation for a single move
     */
    public void move(String move) {
        String face = "";
        int turn = 0;
        face = move.substring(0,1);
        if(move.length() == 1) {
            turn = 1;
        } else if(move.substring(1).equals("2")) {
            turn = 2;
        } else {
            turn = 3;
        }

        faceturn(face, turn);
    }

    /**
     * Turns a face of the cube a certain number of times.
     * @param face Indicates which face we are turning
     * @param turn Number of quarter-turns we are making
     */
    private void faceturn(String face, int turn) {
        if(face.equals("U")) {
            faceturn2(0, turn);
        } else if(face.equals("D")) {
            faceturn2(5, turn);
        } else if(face.equals("F")) {
            faceturn2(1, turn);
        } else if(face.equals("B")) {
            faceturn2(3, turn);
        } else if(face.equals("L")) {
            faceturn2(4, turn);
        } else if(face.equals("R")) {
            faceturn2(2, turn);
        } else { /* error statement */
            System.out.println("faceturn error.");
        }
    }

    /**
     * Faceturn support method.
     * @param face
     * @param turn
     */
    private void faceturn2(int face, int turn) {
        for(int i = 0; i < turn; i++) {
            rubik[face] = clockwise(rubik[face]);
            transfer(face);
        }
    }

    /**
     * Given a 3x3 square, turns it 90 degrees clockwise.
     * @param square 3x3 matrix of colours
     * @return new 3x3 matrix of colours
     */
    private String[][] clockwise(String[][] square) {
        return new String[][] {
            {square[2][0], square[1][0], square[0][0]},
            {square[2][1], square[1][1], square[0][1]},
            {square[2][2], square[1][2], square[0][2]}
        };
    }

    /**
     * Given the face, transfer the 4 surrounding strips 90 degrees clockwise.
     * @param face Integer face number that is rotating
     */
    private void transfer(int face) {
        if(face == 0) {
            String[] temp = Arrays.copyOf(rubik[3][0], 3);

            rubik[3][0] = Arrays.copyOf(rubik[4][0], 3);
            rubik[4][0] = Arrays.copyOf(rubik[1][0], 3);
            rubik[1][0] = Arrays.copyOf(rubik[2][0], 3);
            rubik[2][0] = Arrays.copyOf(temp, 3);
        } else if(face == 1) {
            String[] temp = Arrays.copyOf(rubik[0][2], 3);

            rubik[0][2][0] = rubik[4][2][2]; rubik[0][2][1] = rubik[4][1][2]; rubik[0][2][2] = rubik[4][0][2];
            rubik[4][0][2] = rubik[5][0][0]; rubik[4][1][2] = rubik[5][0][1]; rubik[4][2][2] = rubik[5][0][2];
            rubik[5][0][0] = rubik[2][2][0]; rubik[5][0][1] = rubik[2][1][0]; rubik[5][0][2] = rubik[2][0][0];
            rubik[2][0][0] = temp[0]; rubik[2][1][0] = temp[1]; rubik[2][2][0] = temp[2];
        } else if(face == 2) {
            String[] temp = new String[3];
            temp[0] = rubik[0][0][2]; temp[1] = rubik[0][1][2]; temp[2] = rubik[0][2][2];

            rubik[0][0][2] = rubik[1][0][2]; rubik[0][1][2] = rubik[1][1][2]; rubik[0][2][2] = rubik[1][2][2];
            rubik[1][0][2] = rubik[5][0][2]; rubik[1][1][2] = rubik[5][1][2]; rubik[1][2][2] = rubik[5][2][2];
            rubik[5][0][2] = rubik[3][2][0]; rubik[5][1][2] = rubik[3][1][0]; rubik[5][2][2] = rubik[3][0][0];
            rubik[3][2][0] = temp[0]; rubik[3][1][0] = temp[1]; rubik[3][0][0] = temp[2];
        } else if(face == 3) {
            String[] temp = Arrays.copyOf(rubik[0][0], 3);

            rubik[0][0][0] = rubik[2][0][2]; rubik[0][0][1] = rubik[2][1][2]; rubik[0][0][2] = rubik[2][2][2];
            rubik[2][0][2] = rubik[5][2][2]; rubik[2][1][2] = rubik[5][2][1]; rubik[2][2][2] = rubik[5][2][0];
            rubik[5][2][2] = rubik[4][2][0]; rubik[5][2][1] = rubik[4][1][0]; rubik[5][2][0] = rubik[4][0][0];
            rubik[4][2][0] = temp[0]; rubik[4][1][0] = temp[1]; rubik[4][0][0] = temp[2];
        } else if(face == 4) {
            String[] temp = new String[3];
            temp[0] = rubik[0][0][0]; temp[1] = rubik[0][1][0]; temp[2] = rubik[0][2][0];

            rubik[0][0][0] = rubik[3][2][2]; rubik[0][1][0] = rubik[3][1][2]; rubik[0][2][0] = rubik[3][0][2];
            rubik[3][2][2] = rubik[5][0][0]; rubik[3][1][2] = rubik[5][1][0]; rubik[3][0][2] = rubik[5][2][0];
            rubik[5][0][0] = rubik[1][0][0]; rubik[5][1][0] = rubik[1][1][0]; rubik[5][2][0] = rubik[1][2][0];
            rubik[1][0][0] = temp[0]; rubik[1][1][0] = temp[1]; rubik[1][2][0] = temp[2];
        } else if(face == 5) {
            String[] temp = Arrays.copyOf(rubik[1][2], 3);

            rubik[1][2] = Arrays.copyOf(rubik[4][2], 3);
            rubik[4][2] = Arrays.copyOf(rubik[3][2], 3);
            rubik[3][2] = Arrays.copyOf(rubik[2][2], 3);
            rubik[2][2] = Arrays.copyOf(temp, 3);
        } else { /* error statement */
            System.out.println("transfer face error.");
        }
    }

    /**
     * Prints a net of the cube to the console.
     */
    public void printCube() {
        System.out.println("    " + rubik[3][2][2] + rubik[3][2][1] + rubik[3][2][0]);
        System.out.println("    " + rubik[3][1][2] + rubik[3][1][1] + rubik[3][1][0]);
        System.out.println("    " + rubik[3][0][2] + rubik[3][0][1] + rubik[3][0][0]);
        System.out.println(rubik[4][2][0] + rubik[4][1][0] + rubik[4][0][0] + " " + rubik[0][0][0] + rubik[0][0][1] + rubik[0][0][2] + " " + rubik[2][0][2] + rubik[2][1][2] + rubik[2][2][2] + " " + rubik[5][2][2] + rubik[5][2][1] + rubik[5][2][0]);
        System.out.println(rubik[4][2][1] + rubik[4][1][1] + rubik[4][0][1] + " " + rubik[0][1][0] + rubik[0][1][1] + rubik[0][1][2] + " " + rubik[2][0][1] + rubik[2][1][1] + rubik[2][2][1] + " " + rubik[5][1][2] + rubik[5][1][1] + rubik[5][1][0]);
        System.out.println(rubik[4][2][2] + rubik[4][1][2] + rubik[4][0][2] + " " + rubik[0][2][0] + rubik[0][2][1] + rubik[0][2][2] + " " + rubik[2][0][0] + rubik[2][1][0] + rubik[2][2][0] + " " + rubik[5][0][2] + rubik[5][0][1] + rubik[5][0][0]);
        System.out.println("    " + rubik[1][0][0] + rubik[1][0][1] + rubik[1][0][2]);
        System.out.println("    " + rubik[1][1][0] + rubik[1][1][1] + rubik[1][1][2]);
        System.out.println("    " + rubik[1][2][0] + rubik[1][2][1] + rubik[1][2][2]);
    }

    /**
     * Returns the colours of a cube.
     * @return 6 words, 9 letters each
     */
    public String toString() {
        String text = "";
        int f = 0;
        while(true) {
            text = text.concat(toString(f));
            f++;
            if(f == 6) {
                break;
            }
            text = text.concat(" ");
        }
        return text;
    }

    /**
     * Returns the 9 colours of a specific face, proceeding left to right, row by row.
     * @param face
     * @return 9 letters representing the 9 colours of a face
     */
    public String toString(int face) {
        String text = "";
        for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                text = text.concat(toString(face, r, c));
            }
        }
        return text;
    }

    /**
     * Returns the colour of a specific cell.
     * @param face
     * @param row
     * @param cell
     * @return Single letter representing colour
     */
    public String toString(int face, int row, int cell) {
        return rubik[face][row][cell];
    }
}