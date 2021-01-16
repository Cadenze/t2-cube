public class Solve extends RubiksCube {
    private int qtm;
    private int htm;

    /**
     * Initializes a solved cube and then solves it. (No work done)
     */
    public Solve() {
        super();
        solve();
    }

    /**
     * Initializes a cube, scrambles it, and then solves it.
     * @param instruction The scramble instructions
     */
    public Solve(String instruction) {
        super(instruction);
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

    }
}
