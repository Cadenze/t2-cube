/**
 * Lists the possible colours of the corners of a cube.
 */
public enum Corner {
    WHITE_BLUE_RED       ("w", "b", "r"),
    WHITE_ORANGE_BLUE    ("w", "o", "b"),
    WHITE_GREEN_ORANGE   ("w", "g", "o"),
    WHITE_RED_GREEN      ("w", "r", "g"),
    YELLOW_RED_BLUE      ("y", "r", "b"),
    YELLOW_BLUE_ORANGE   ("y", "b", "o"),
    YELLOW_ORANGE_GREEN  ("y", "o", "g"),
    YELLOW_GREEN_RED     ("y", "g", "r");

    private final String colour;
    private final String colour1;
    private final String colour2;
    private static final String ERROR = "corner colour error.";

    Corner(String c, String c1, String c2) {
        colour = c;
        colour1 = c1;
        colour2 = c2;
    }

    public String colour(int number) {
        if(number == 0)      { return colour; }
        else if(number == 1) { return colour1; }
        else if(number == 2) { return colour2; }
        else { System.out.println(ERROR); return "E"; }
    }

    public String colour(int spin, int number) {
        switch(spin) {
            case 0:
                return colour(number);
            
            case 1:
                if(number == 0)      { return colour2; }
                else if(number == 1) { return colour; }
                else if(number == 2) { return colour1; }
                else { System.out.println(ERROR); return "E"; }

            case 2:
                if(number == 0)      { return colour1; }
                else if(number == 1) { return colour2; }
                else if(number == 2) { return colour; }
                else { System.out.println(ERROR); return "E"; }

            default:
                System.out.println(ERROR);
                return "E";
        }
    }
}
