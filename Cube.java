public class Cube {
    private String[][][] rubik;

    /**
     * Initializes a solved 3x3x3 Rubik's Cube.
     *      w = white ; r = red   ; b = blue;
     *      y = yellow; o = orange; g = green.
     */
    public Cube(){
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
     * Takes in a move, parses the notation, and then executes it.
     * @param move = Singmaster notation for a single move
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
     * @param face = Indicates which face we are turning
     * @param turn = Number of quarter-turns we are making
     */
    private void faceturn(String face, int turn) {
        if(face.equals("U")) {
            for(int i = 0; i < turn; i++) {
                rubik[0] = clockwise(rubik[0]);

                String[][] unaltered = new String[][] {
                    {rubik[3][0][2], rubik[3][0][1], rubik[3][0][0]},
                    {rubik[2][0][2], rubik[2][0][1], rubik[2][0][0]},
                    {rubik[1][0][2], rubik[1][0][1], rubik[1][0][0]},
                    {rubik[4][0][2], rubik[4][0][1], rubik[4][0][0]}};
                String[][] altered = transfer(unaltered);

                rubik[3][0][2] = altered[0][0]; rubik[3][0][1] = altered[0][1]; rubik[3][0][0] = altered[0][2];
                rubik[2][0][2] = altered[1][0]; rubik[2][0][1] = altered[1][1]; rubik[2][0][0] = altered[1][2];
                rubik[1][0][2] = altered[2][0]; rubik[1][0][1] = altered[2][1]; rubik[1][0][0] = altered[2][2];
                rubik[4][0][2] = altered[3][0]; rubik[4][0][1] = altered[3][1]; rubik[4][0][0] = altered[3][2];
            }
        } else if(face.equals("D")) {
            for(int i = 0; i < turn; i++) {
                rubik[5] = clockwise(rubik[5]);

                String[][] unaltered = new String[][] {rubik[1][2], rubik[2][2], rubik[3][2], rubik[4][2]};
                String[][] altered = transfer(unaltered);

                rubik[1][2] = altered[0];
                rubik[2][2] = altered[1];
                rubik[3][2] = altered[2];
                rubik[4][2] = altered[3];
            }
        } else if(face.equals("F")) {
            for(int i = 0; i < turn; i++) {
                rubik[1] = clockwise(rubik[1]);

            }
        }
    }

    /**
     * Given a 3x3 square, turns it 90 degrees clockwise.
     * @param square = 3x3 matrix of colours
     * @return = new 3x3 matrix of colours
     */
    private String[][] clockwise(String[][] square) {
        return new String[][] {
            {square[2][0], square[1][0], square[0][0]},
            {square[2][1], square[1][1], square[0][1]},
            {square[2][2], square[1][2], square[0][2]}
        };
    }
    
    /**
     * Given the four 3x1 strips surrounding a face, transfer them 90 degrees clockwise.
     * @param strips = 4 strips, starting north of, proceeding clockswise
     * @return = new 4 strips
     */
    private String[][] transfer(String[][] strips) {
        return new String[][] {
            strips[3], strips[0], strips[1], strips[2]
        };
    }

    private void transfer2(int face) {
        if(face == 0) {
            String[] temp = new String[3];
            temp = rubik[3][0];
            rubik[3][0] = rubik[4][0];
            rubik[4][0] = rubik[1][0];
            rubik[1][0] = rubik[2][0];
            rubik[2][0] = temp;
        } else if(face == 1) {
            String[] temp = new String[3];
            temp = rubik[0][2];
            rubik[0][2][0] = rubik[4][2][2]; rubik[0][2][1] = rubik[4][1][2]; rubik[0][2][2] = rubik[4][0][2];
            rubik[4][2][0] = rubik[5][0][0]; rubik[4][2][1] = rubik[5][0][1]; rubik[4][2][2] = rubik[5][0][2];
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
            String[] temp = new String[3];
            temp = rubik[0][0];
            rubik[0][0][0] = rubik[2][0][2]; rubik[0][0][1] = rubik[2][1][2]; rubik[0][0][2] = rubik[2][2][2];
            rubik[2][0][2] = rubik[5][2][2]; rubik[2][1][2] = rubik[5][2][1]; rubik[2][2][2] = rubik[5][2][0];
        } else if(face == 4) {

        } else if(face == 5) {

        } else { /* error statement */
            System.out.println("transfer face error.");
        }
    }
}