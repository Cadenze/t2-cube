public enum Colour {
    WHITE    ("w", 0),
    RED      ("r", 1),
    BLUE     ("b", 2),
    ORANGE   ("o", 3),
    GREEN    ("g", 4),
    YELLOW   ("y", 5),
    WILDCARD ("E", 8);

    private final String letter;
    private final int face;
    
    /**
     * Initializes the information of a colour.
     * @param c Letter representing colour
     * @param n Number representing the fundamnetal face of that colour
     */
    Colour(String c, int n) {
        letter = c;
        face = n;
    }

    /**
     * Returns the letter form of the colour.
     * @return
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Returns the letter form of the colour.
     * @return
     */
    public String getString() {
        return getLetter();
    }

    /**
     * Returns the number form of the colour.
     * @return
     */
    public int getNumber() {
        return face;
    }

    /**
     * Compares the two colours, taking into account that one of the colours might be a WILDCARD.
     * @param colour other colour
     * @return true if same/wildcard; false if not
     */
    public boolean compare(Colour colour) {
        if(colour == Colour.WILDCARD || this == Colour.WILDCARD) {
            return true;
        } else {
            return this == colour;
        }
    }
}
