import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends RubiksCube {
    public static void main(String[] args) {
        RubiksCube scramble = new RubiksCube("D' F2 R' D L2 D2 F R' F' D2 B D2 L' B' R2 F2 B R2 F' U");
        Corner[] corners = new Corner[8];
        Arrays.fill(corners, Corner.WILDCARD);
        int[] spin = new int[8];
        Edge[] edges = new Edge[] {
            Edge.WHITE_RED, Edge.WHITE_BLUE, Edge.WHITE_ORANGE, Edge.WHITE_GREEN,
            Edge.WILDCARD, Edge.WILDCARD, Edge.WILDCARD, Edge.WILDCARD,
            Edge.WILDCARD, Edge.WILDCARD, Edge.WILDCARD, Edge.WILDCARD
        };
        boolean[] parity = new boolean[12];
        Arrays.fill(parity, true);
        Colour[] centres = new Colour[] { Colour.WHITE, Colour.RED, Colour.BLUE, Colour.ORANGE, Colour.GREEN, Colour.YELLOW};
        RubiksCube first = new RubiksCube(corners, spin, edges, parity, centres);
        Search cross = new Search(scramble, first);
        String path = cross.forward();
        System.out.println(path);
    }
    
    private final RubiksCube start;
    private final RubiksCube end;
    private RubiksCube[] state;
    private String[] path;

    private static final String[] instructions = { "U", "U2", "U'", "D", "D2", "D'", "L", "L2", "L'", 
                                                   "R", "R2", "R'", "F", "F2", "F'", "B", "B2", "B'"};

    public Search(RubiksCube start, RubiksCube end) {
        this.start = start;
        this.end = end;
    }

    public String forward() {
        while(true) {
            advanceForward();
            for(int i = 0; i < state.length; i++) {
                if(end.compare(state[i])) {
                    return path[i];
                }
            }
        }
    }

    private void advanceForward() {
        if(state.length == 0) {
            state = new RubiksCube[] { start };
            path = new String[] { "" };
        } else {
            List<RubiksCube> updatedStates = new ArrayList<>();
            List<String> updatedPaths = new ArrayList<>();

            if(path.length == 1) {
                for(String i : instructions) {
                    RubiksCube cube = new RubiksCube(start);
                    cube.move(i);
                    updatedStates.add(cube);
                    updatedPaths.add(i);
                }
            } else {
                for(int s = 0; s < state.length; s++) {
                    for(String i : instructions) {
                        String[] elements = path[s].split(" ");
                        String element = elements[elements.length - 1];
                        if(!element.startsWith(i.substring(0,1))) {
                            RubiksCube cube = new RubiksCube(state[s]);
                            cube.move(i);
                            updatedStates.add(cube);
                            updatedPaths.add(path[s] + " " + i);
                        }
                    }
                }
            }

            state = updatedStates.toArray(new RubiksCube[0]);
            path = updatedPaths.toArray(new String[0]);
        }
    }
}
