public enum Colour {
    WHITE  ("w", 0),
    RED    ("r", 1),
    BLUE   ("b", 2),
    ORANGE ("o", 3),
    GREEN  ("g", 4),
    YELLOW ("y", 5);

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
     * Returns the number form of the colour.
     * @return
     */
    public int getNumber() {
        return face;
    }
}
