/**
 * Lists the possible colours of the corners of a cube.
 */
public enum Corner {
    WHITE_BLUE_RED       (Colour.WHITE,  Colour.BLUE,   Colour.RED),
    WHITE_ORANGE_BLUE    (Colour.WHITE,  Colour.ORANGE, Colour.BLUE),
    WHITE_GREEN_ORANGE   (Colour.WHITE,  Colour.GREEN,  Colour.ORANGE),
    WHITE_RED_GREEN      (Colour.WHITE,  Colour.RED,    Colour.GREEN),
    YELLOW_RED_BLUE      (Colour.YELLOW, Colour.RED,    Colour.BLUE),
    YELLOW_BLUE_ORANGE   (Colour.YELLOW, Colour.BLUE,   Colour.ORANGE),
    YELLOW_ORANGE_GREEN  (Colour.YELLOW, Colour.ORANGE, Colour.GREEN),
    YELLOW_GREEN_RED     (Colour.YELLOW, Colour.GREEN,  Colour.RED);

    private final Colour colour;
    private final Colour colour1;
    private final Colour colour2;

    /**
     * Initializes the three colours of a corner.
     * @param c Colour at up;
     * @param c1 Colour at right;
     * @param c2 Colour at front;
     */
    Corner(Colour c, Colour c1, Colour c2) {
        colour = c;
        colour1 = c1;
        colour2 = c2;
    }

    /**
     * Retrieves the colour of a direction.
     * @param number Direction
     * @return Colour
     */
    public Colour colour(int number) {
        if(number == 0)      { return colour; }
        else if(number == 1) { return colour1; }
        else                 { return colour2; }
    }

    /**
     * Retrieves the colour, after a specified spin, of a direction.
     * @param spin Spin number
     * @param number Direction
     * @return Colour
     */
    public Colour colour(int spin, int number) {
        switch(spin) {
            case 0:
                return colour(number);
            
            case 1:
                if(number == 0)      { return colour2; }
                else if(number == 1) { return colour; }
                else                 { return colour1; }

            default:
                if(number == 0)      { return colour1; }
                else if(number == 1) { return colour2; }
                else                 { return colour; }
        }
    }
}
