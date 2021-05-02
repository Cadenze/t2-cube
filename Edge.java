/**
 * Lists the possible colours of the edges of a cube.
 */
public enum Edge {
    WHITE_RED     (Colour.WHITE,  Colour.RED),
    WHITE_BLUE    (Colour.WHITE,  Colour.BLUE),
    WHITE_ORANGE  (Colour.WHITE,  Colour.ORANGE),
    WHITE_GREEN   (Colour.WHITE,  Colour.GREEN),
    RED_BLUE      (Colour.RED,    Colour.BLUE),
    ORANGE_BLUE   (Colour.ORANGE, Colour.BLUE),
    RED_GREEN     (Colour.RED,    Colour.GREEN),
    ORANGE_GREEN  (Colour.ORANGE, Colour.GREEN),
    YELLOW_RED    (Colour.YELLOW, Colour.RED),
    YELLOW_BLUE   (Colour.YELLOW, Colour.BLUE),
    YELLOW_ORANGE (Colour.YELLOW, Colour.ORANGE),
    YELLOW_GREEN  (Colour.YELLOW, Colour.GREEN);

    private final Colour colour;
    private final Colour colour1;

    /**
     * Initializes the two colours of an edge.
     * @param c Main colour
     * @param d Secondary colour
     */
    Edge(Colour c, Colour d) {
        colour = c;
        colour1 = d;
    }

    /**
     * Retrieves the colour of a direction.
     * @param number Direction
     * @return Colour
     */
    public Colour colour(int number) {
        return colour(true, number);
    }

    /**
     * Retrieves the colour, at a specified parity, of a direction.
     * @param parity True/false parity
     * @param number Direction
     * @return Colour
     */
    public Colour colour(boolean parity, int number) {
        return parity == (number == 0) ? colour : colour1;
    }
}
