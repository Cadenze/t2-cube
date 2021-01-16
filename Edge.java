/**
 * Lists the possible colours of the edges of a cube.
 */
public enum Edge {
    WHITE_RED     ("w", "r"),
    WHITE_BLUE    ("w", "b"),
    WHITE_ORANGE  ("w", "o"),
    WHITE_GREEN   ("w", "g"),
    RED_BLUE      ("r", "b"),
    ORANGE_BLUE   ("o", "b"),
    RED_GREEN     ("r", "g"),
    ORANGE_GREEN  ("o", "g"),
    YELLOW_RED    ("y", "r"),
    YELLOW_BLUE   ("y", "b"),
    YELLOW_ORANGE ("y", "o"),
    YELLOW_GREEN  ("y", "g");

    private final String colour;
    private final String colour1;
    private static final String ERROR = "edge colour error.";

    /**
     * Initializes the two colours of an edge.
     * @param c Main colour
     * @param d Secondary colour
     */
    Edge(String c, String d) {
        colour = c;
        colour1 = d;
    }

    /**
     * Retrieves the colour of a direction.
     * @param number Direction
     * @return Colour
     */
    public String colour(int number) {
        return colour(true, number);
    }

    /**
     * Retrieves the colour, at a specified parity, of a direction.
     * @param parity True/false parity
     * @param number Direction
     * @return Colour
     */
    public String colour(boolean parity, int number) {
        if(parity) {
            if(number == 0) { return colour; }
            else if (number == 1) { return colour1; }
            else { System.out.println(ERROR); return "E"; }
        } else {
            if(number == 0) { return colour1; }
            else if (number == 1) { return colour; }
            else { System.out.println(ERROR); return "E"; }
        }
    }
}
