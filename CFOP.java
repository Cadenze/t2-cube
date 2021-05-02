import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CFOP extends Solve {

    /**
     * Initializes a solved cube. (No work done)
     */
    public CFOP() {
        super();
    }

    /**
    * Initializes a cube, scrambles it, and then solves it.
    * @param sequence The scramble instructions
    */
    public CFOP(String sequence) {
        super(sequence);
    }

    /**
     * Initializes a cube, scrambles it, and then allows the user the option to solve it.
     * @param sequence The scramble instructions
     * @param solve true to solve; false to leave scrambled
     */
    public CFOP(String sequence, boolean solve) {
        super(sequence, solve);
    }

    
    /**
     * Solves a cube using the CFOP method.
     */
    @Override
    public void solve() {
        cross();
        f2l();
        oll();
        pll();
        look += 7;
        alignment();
    }

    @Override
    public void cross() {
        new Cross();
    }

    class Cross {
        private int[] condition;
        private Edge anchor;
        private final Edge[] sequence = { Edge.WHITE_RED, Edge.WHITE_BLUE, Edge.WHITE_ORANGE, Edge.WHITE_GREEN };

        Cross() {
            condition = new int[4];
            anchor();
            int counter = searchSequence(anchor);
            counter++;
            counter %= 4;
            crossBlue(sequence[counter]);
            counter++;
            counter %= 4;
            crossOrange(sequence[counter]);
            counter++;
            counter %= 4;
            crossGreen(sequence[counter]);

            int position = findEdge(Edge.WHITE_RED);
            alignU(position);
            updateFirst("z2");
        }

        void anchor() {
            List<Edge> potential = new ArrayList<>(); /* number of potential anchors */
            for(Edge edge : sequence) {
                int position = findEdge(edge);
                if(position < 4 && getParity(position)) { /* in bottom layer & true parity */
                    potential.add(edge);
                }
            }

            if(potential.isEmpty()) {
                crossRed();
                anchor = Edge.WHITE_RED;
            } else if(potential.size() == 1) {
                anchor = potential.get(0);
            } else {
                anchor = potential.get(0);
            }

            int position = findEdge(anchor);
            alignU(position);
        }

        void identify() {
            int lowest = 4;
            Edge target = null;
            for(int i = 0; i < 4; i++) {
                condition[i] = getCondition(sequence[i]);
                if(condition[i] < lowest && condition[i] != 0) {
                    lowest = condition[i];
                    target = sequence[i];
                }
            }

            if(lowest == 5) {
                return;
            } else if(lowest == 1) {
                insertGood(target);
            } else if(lowest == 2) {
                insertStillGood(target);
            } else {
                removeBad(target);
            }
            identify();
        }

        void alignCross() {
            int current = findCentre(Colour.RED);
            switch(current) {
                case 1: break;
                case 2: updateFirst("y"); break;
                case 3: updateFirst("y2"); break;
                case 4: updateFirst("y'"); break;
                default: System.out.println("align cross error.");
            }
            current = findEdge(Edge.WHITE_RED, 8, 12);
            switch(current) {
                case 8: break;
                case 9: updateFirst("D'"); break;
                case 10: updateFirst("D2"); break;
                case 11: updateFirst("D"); break;
                default: System.out.println("align cross error.");
            }
        }

        void insertGood(Edge e) {
            int position = findEdge(e, 4, 8);
            alignE(position - 5);
            int carryDes = getParity(5) ? 1 : 0;

            int carryFrom = -1;
            for(int i = 0; i < 4; i++) {
                if(inSequence(getEdge(i)) && !getParity(i)) {
                    carryFrom = i;
                    break;
                }
            }

            if(carryFrom == -1) {
                for(int i = 0; i < 4; i++) {
                    if(inSequence(getEdge(i))) {
                        carryFrom = i;
                        break;
                    }
                }
            }

            if(carryFrom != -1) {
                alignU((carryFrom + 4 - carryDes) % 4);
            }

            int diffFromAnchor = (searchSequence(e) + 4 - searchSequence(anchor)) % 4;
            int finalAnchorPos = 8 + carryDes + 4 - diffFromAnchor;
            alignD((findEdge(anchor, 8 , 12) + 8 - finalAnchorPos) % 4);

            if(carryDes == 1) {
                updateFirst("R'");
            } else {
                updateFirst("F");
            }
        }

        void insertStillGood(Edge e) {
            int position = findEdge(e, 0, 4);
            int diffFromAnchor = (searchSequence(e) + 4 - searchSequence(anchor)) % 4;
            int finalPos = (findEdge(anchor, 8, 12) - 8 + diffFromAnchor) % 4;
            alignU((position - finalPos + 4) % 4);
            position = findEdge(e, 0, 4);
            switch(position) {
                case 0: updateFirst("F2"); break;
                case 1: updateFirst("R2"); break;
                case 2: updateFirst("B2"); break;
                case 3: updateFirst("L2"); break;
                default: System.out.println("cross insert still good error.");
            }
        }

        void removeBad(Edge e) {
            int position = findEdge(e);
            if(position < 4 && inSequence(getEdge(position + 8)) && getParity(position + 8)) {
                /* you fucked up */
                /* somehow U-slice moves */
                int positionDes = -1;
                for(int i = 0; i < 4; i++) {
                    if(!(inSequence(getEdge(i + 8)) && getParity(i + 8))) {
                        positionDes = i;
                        break;
                    }
                }
                alignU((position + 4 - positionDes) % 4);
                position = positionDes;
            }

            switch(position) {
                case 0: case 8: updateFirst("F"); break;
                case 1: case 9: updateFirst("R"); break;
                case 2: case 10: updateFirst("B"); break;
                case 3: case 11: updateFirst("L"); break;
                default: System.out.println("cross remove bad error.");
            }
        }

        boolean inSequence(Edge e) {
            for(int i = 0; i < 4; i++) {
                if(e == sequence[i]) {
                    return true;
                }
            }
            return false;
        }

        void alignE(int diff) {
            switch(diff) {
                case 0: break;
                case 1: updateFirst("u"); break;
                case 2: updateFirst("u2"); break;
                case 3: updateFirst("u'"); break;
                default: System.out.println("cross alignE error.");
            }
        }

        void alignU(int diff) {
            switch(diff) {
                case 0: break;
                case 1: updateFirst("U"); break;
                case 2: updateFirst("U2"); break;
                case 3: updateFirst("U'"); break;
                default: System.out.println("cross alignU error.");
            }
        }

        void alignD(int diff) {
            switch(diff) {
                case 0: break;
                case 1: updateFirst("D'"); break;
                case 2: updateFirst("D2"); break;
                case 3: updateFirst("D"); break;
                default: System.out.println("cross alignD error.");
            }
        }

        int getCondition(Edge e) {
            int position = findEdge(e);
            if(position < 4) {
                if(getParity(position)) {
                    return 2;
                } else {
                    return 3;
                }
            } else if(position < 8) {
                return 1;
            } else if(getParity(position)) {
                int actualDiff = (position + 4 - findEdge(anchor)) % 4;
                int theoretical = (searchSequence(e) + 4 - searchSequence(anchor)) % 4;
                if(actualDiff == theoretical) {
                    return 0;
                }
            }
            return 4;
        }

        int searchSequence(Edge e) {
            for(int i = 0; i < 4; i++) {
                if(sequence[i] == e) {
                    return i;
                }
            }
            return -1;
        }

        int countZero(Edge e) {
            int counter = 0;
            final int base = findEdge(e, 8, 12);
            int positionInSequence = searchSequence(e);
            
            for(int i = 1; i < 4; i++) {
                int position = base + i;
                if(position > 11) {
                    position -= 4;
                }
                
                if(getEdge(position) == sequence[(positionInSequence + i) % 4] && getParity(position)) {
                    counter++;
                }
            }

            return counter;
        }
    }

    /**
     * An object to evaluate an F2L scenario.
     * Is recursively called up to 3 times.
     */
    class F2L {
        private int edge;
        private int corner;
        private boolean parity;
        private int spin;
        private int iteration;

        /**
         * Stores the variables that identifies the F2L case.
         * @param corner cubelet
         * @param edge cubelet
         * @param natural parity
         */
        F2L(Corner corner, Edge edge, boolean natural, int iteration) {
            this.edge = findEdge(edge);
            this.corner = findCorner(corner);
            parity = getParity(this.edge) == natural;
            spin = getSpin(this.corner);
            this.iteration = iteration;
        }

        /**
         * Identifies whether the pair needs to be extracted before insertion.
         * @return Extraction code
         */
        int extractionCode() {
            int code = 0;
            if(edge < 4) { code += 10; }
            else if(edge > 7) { code += 10 * edge; }
            else if(edge > 4) { code += 20; }
            if(corner < 4) { code++; }
            else if(corner > 4) { code += 2; }
            return code;
        }

        /**
         * Returns the parity and spin of a pair.
         * For edge and/or spin in place.
         * @return Parity-spin code
         */
        int paritySpinCode() {
            int code = 0;
            if(parity) { code += 10; }
            code += spin;
            return code;
        }

        /**
         * Returns the delta on top layer, parity, and spin of a pair.
         * For when both are on U layer.
         * @return Delta-parity-spin code
         */
        int deltaParitySpinCode() {
            int delta = (edge + 4 - corner) % 4;
            return delta * 100 + paritySpinCode();
        }

        /**
         * Retrieves the position of the corner.
         * @return position
         */
        int getCornerPosition() {
            return corner;
        }

        /**
         * Retrieves the position of the edge.
         * @return position
         */
        int getEdgePosition() {
            return edge;
        }

        /**
         * Returns how many F2l pairs are inserted before this.
         * @return iteration
         */
        int getIteration() {
            return iteration;
        }

        /**
         * Return the moves needed to align a corner into a specific position.
         * @param position Destination
         * @return Singmaster move
         */
        String alignCorner(int position) {
            int diff = (corner + 4 - position) % 4;
            switch(diff) {
                case 0: return "";
                case 1: return "U";
                case 2: return "U2";
                case 3: return "U'";
                default: System.out.println("align error."); return "";
            }
        }

        /**
         * Return the moves needed to align an edge into a specific position.
         * @param position Destination
         * @return Singmaster move
         */
        String alignEdge(int position) {
            int diff = (edge + 4 - position) % 4;
            switch(diff) {
                case 0: return "";
                case 1: return "U";
                case 2: return "U2";
                case 3: return "U'";
                default: System.out.println("align error."); return "";
            }
        }

        /**
         * Returns the extraction sequence for a corner-edge pair from position.
         * @param position
         */
        String extraction(int position) {
            switch(position) {
                case 5: return "R' U R";
                case 6: return "L U L'";
                case 7: return "L' U' L";
                default: System.out.println("Extraction error.");
            }
            return "";
        }
    }

    /**
     * Completes the first two layers.
     */
    public void f2l() {
        f2lIdentify(Corner.WHITE_RED_GREEN, Edge.RED_GREEN, true, 0);
        updateFirst("y"); 
        f2lIdentify(Corner.WHITE_GREEN_ORANGE, Edge.ORANGE_GREEN, false, 1);
        updateFirst("y");
        f2lIdentify(Corner.WHITE_ORANGE_BLUE, Edge.ORANGE_BLUE, true, 2);
        updateFirst("y");
        f2lIdentify(Corner.WHITE_BLUE_RED, Edge.RED_BLUE, false, 3);
    }

    /**
     * Identifies the F2L case.
     * @param corner position
     * @param edge position
     * @param natural parity
     * @param iteration How many corners inserted before?
     */
    public void f2lIdentify(Corner corner, Edge edge, boolean natural, int iteration) {
        F2L pair = new F2L(corner, edge, natural, iteration);
        final String ERROR = "f2l identify error.";
        switch(pair.extractionCode()) {
            case 0: switch(pair.paritySpinCode()) {
                case 0: f2lCase(38, pair); break;
                case 1: f2lCase(41, pair); break;
                case 2: f2lCase(42, pair); break;
                case 10: f2lCase(37, pair); break;
                case 11: f2lCase(39, pair); break;
                case 12: f2lCase(40, pair); break;
                default: System.out.println(ERROR);
            } return;
            case 1: switch(pair.paritySpinCode()) {
                case 0: f2lCase(31, pair); break;
                case 1: f2lCase(36, pair); break;
                case 2: f2lCase(35, pair); break;
                case 10: f2lCase(32, pair); break;
                case 11: f2lCase(34, pair); break;
                case 12: f2lCase(33, pair); break;
                default: System.out.println(ERROR);
            } return;
            case 10: switch(pair.paritySpinCode()) {
                case 0: f2lCase(26, pair); break;
                case 1: f2lCase(29, pair); break;
                case 2: f2lCase(28, pair); break;
                case 10: f2lCase(25, pair); break;
                case 11: f2lCase(27, pair); break;
                case 12: f2lCase(30, pair); break;
                default: System.out.println(ERROR);
            } return;
            case 11: switch(pair.deltaParitySpinCode()) {
                case 0: f2lCase(18, pair); break;
                case 1: f2lCase(2, pair); break;
                case 2: f2lCase(13, pair); break;
                case 10: f2lCase(23, pair); break;
                case 11: f2lCase(12, pair); break;
                case 12: f2lCase(15, pair); break;
                case 100: f2lCase(24, pair); break;
                case 101: f2lCase(16, pair); break;
                case 102: f2lCase(11, pair); break;
                case 110: f2lCase(17, pair); break;
                case 111: f2lCase(14, pair); break;
                case 112: f2lCase(1, pair); break;
                case 200: f2lCase(22, pair); break;
                case 201: f2lCase(8, pair); break;
                case 202: f2lCase(9, pair); break;
                case 210: f2lCase(19, pair); break;
                case 211: f2lCase(4, pair); break;
                case 212: f2lCase(5, pair); break;
                case 300: f2lCase(20, pair); break;
                case 301: f2lCase(6, pair); break;
                case 302: f2lCase(3, pair); break;
                case 310: f2lCase(21, pair); break;
                case 311: f2lCase(10, pair); break;
                case 312: f2lCase(7, pair); break;
                default: System.out.println(ERROR);
            } return;
            case 2:
                updateSecond(pair.extraction(pair.getCornerPosition()));
                break;
            case 20: case 22:
                updateSecond(pair.extraction(pair.getEdgePosition()));
                break;
            case 12:
                updateSecond(pair.alignEdge(1));
                updateSecond(pair.extraction(pair.getCornerPosition()));
                break;
            case 21:
                updateSecond(pair.alignCorner(1));
                updateSecond(pair.extraction(pair.getEdgePosition()));
                break;
            case 80: case 81: case 82: case 100: case 101: case 102:
                updateSecond("M2");
                break;
            case 90: case 91: case 92: case 110: case 111: case 112:
                updateSecond("S2");
                break;
            default: System.out.println(ERROR); return;
        }

        f2lIdentify(corner, edge, natural, iteration);
    }

    /**
     * Given a case number, matches it with the F2L algorithm
     * @param number F2L case
     * @param pair the F2L pair
     */
    private void f2lCase(int number, F2L pair) {
        switch(number) {
            case 1: f2lExecuteEdge(0, "R U' R'", pair); break;
            case 2: f2lExecuteEdge(1, "F' U F", pair); break;
            case 3: f2lExecuteEdge(3, "F' U' F", pair); break;
            case 4: f2lExecuteEdge(2, "R U R'", pair); break;
            case 5: if(pair.getIteration() == 3) { f2lExecuteEdge(3, "R U R' U2 R U' R'", pair); }
                else { f2lExecuteEdge(1, "R F R' F' R'", pair); } break;
            case 6: if(pair.getIteration() == 3) { f2lExecuteEdge(3, "R2 B U B' U' R2", pair); }
                else { f2lExecuteEdge(0, "R F R F' R'", pair); } break;
            case 7: f2lExecuteEdge(0, "R U2 R' U2 R U' R'", pair); break;
            case 8: f2lExecuteEdge(2, "d R' U2 R U2 R' U R y", pair); break;
            case 9: if(pair.getIteration() == 3) { f2lExecuteEdge(3, "R U' R' d R' U' R y", pair); }
                else { f2lExecuteEdge(2, "R' U' R F' U' F", pair); } break;
            case 10: if(pair.getIteration() == 3) { f2lExecuteEdge(0, "R U R' U R U R'", pair); }
                else { f2lExecuteEdge(3, "R' U R2 U R'", pair); } break;
            case 11: f2lExecuteEdge(2, "R U2 R' d R' U' R y", pair); break;
            case 12: if(pair.getIteration() == 3) { f2lExecuteEdge(0, "R U' R' U R U' R' U2 R U' R'", pair); }
                else { f2lExecuteEdge(0, "R' U2 R2 U R'", pair); } break;
            case 13: if(pair.getIteration() == 3) { f2lExecuteEdge(0, "d R' U R U' R' U' R y", pair); }
                else { f2lExecuteEdge(0, "R' U R F' U' F", pair); } break;
            case 14: f2lExecuteEdge(2, "R U' R' U R U R'", pair); break;
            case 15: if(pair.getIteration() == 3) { f2lExecuteEdge(0, "M U L F' L' U' M'", pair); }
                else { f2lExecuteEdge(1, "R' U R U' R U R'", pair); } break;
            case 16: if(pair.getIteration() == 3) { f2lExecuteEdge(1, "R U' R' U d R' U' R y", pair); }
                else { f2lExecuteEdge(1, "F' R' U' R U F", pair); } break;
            case 17: f2lExecuteEdge(1, "R U2 R' U' R U R'", pair); break;
            case 18: f2lExecuteEdge(0, "y' R' U2 R U R' U' R y", pair); break;
            case 19: f2lExecuteEdge(1, "R U2 R' U R U' R'", pair); break;
            case 20: f2lExecuteEdge(0, "F' U2 F2 R' F' R", pair); break;
            case 21: f2lExecuteEdge(1, "R U R' U R U' R'", pair); break;
            case 22: f2lExecuteEdge(2, "F' L' U2 L F", pair); break;
            case 23: f2lExecuteEdge(2, "R2 U2 R' U' R U' R2", pair); break;
            case 24: f2lExecuteEdge(0, "F' L' U L F R U R'", pair); break;
            case 25: if(pair.getIteration() == 3) { f2lExecuteEdge(1, "R' U' R' U' R' U R U R", pair); }
                else { f2lExecuteEdge(1, "R2 U' R' U R2", pair); } break;
            case 26: f2lExecuteEdge(0, "y' R U R U R U' R' U' R' y", pair); break;
            case 27: f2lExecuteEdge(1, "R U' R' U R U' R'", pair); break;
            case 28: f2lExecuteEdge(0, "R U R' U' F R' F' R", pair); break;
            case 29: f2lExecuteEdge(0, "y' R' U' R U R' U' R y", pair); break;
            case 30: f2lExecuteEdge(1, "R U R' U' R U R'", pair); break;
            case 31: f2lExecuteCorner(0, "R U' R' d R' U R y", pair); break;
            case 32: f2lExecuteCorner(0, "R U R' U' R U R' U' R U R'", pair); break;
            case 33: f2lExecuteCorner(1, "R U' R' U2 R U' R'", pair); break;
            case 34: if(pair.getIteration() == 3) { f2lExecuteCorner(3, "F' U F U2 F' U F", pair); }
                else { f2lExecuteCorner(0, "E' R U R' E", pair); } break;
            case 35: f2lExecuteCorner(1, "R U R' d R' U' R y", pair); break;
            case 36: f2lExecuteCorner(0, "d R' U' R d' R U R'", pair); break;
            case 37: break;
            case 38: if(pair.getIteration() == 3) { updateSecond("R' F R F' R U' R' U R U' R' U2 R U' R'"); }
                else { updateSecond("R U' R2 U2 R U' F' U F"); } break;
            case 39: updateSecond("R U' R' U' R U R' U2 R U' R'"); break;
            case 40: updateSecond("R U' R' U R U2 R' U R U' R'"); break;
            case 41: if(pair.getIteration() == 3) { updateSecond("R U' R' d R' U' R U' R' U' R y"); }
                else { updateSecond("R U' R2 U' R y' R' U' R y"); } break;
            case 42: if(pair.getIteration() == 0) { updateSecond("y' R' U R2 U R' y R U R'"); }
                else { updateSecond("R U' R' U d R' U' R U' R' U R y"); } break;
            default: System.out.println("f2l case error.");
        }
    }

    /**
     * Executes an F2L algorithm by anchoring a corner.
     * @param position Corner position
     * @param alg Algorithm to perform
     * @param pair The F2L pair
     */
    private void f2lExecuteCorner(int position, String alg, F2L pair) {
        updateSecond(pair.alignCorner(position));
        updateSecond(alg);
    }

    /**
     * Executes an F2L algorithm by anchoring an edge.
     * @param position Edge position
     * @param alg Algorithm to perform
     * @param pair The F2L pair
     */
    private void f2lExecuteEdge(int position, String alg, F2L pair) {
        updateSecond(pair.alignEdge(position));
        updateSecond(alg);
    }

    /**
     * An object to store and evaluate the state of the cube pre-OLL.
     */
    class OLL {
        private final boolean[] parity = {getParity(0), getParity(1), getParity(2), getParity(3)};
        private final int[] spin = {getSpin(0), getSpin(1), getSpin(2), getSpin(3)};
        private int trues;
        private int zeroes;
        private int[] pos0;
        int[] posT;

        /**
         * Stores the variables for identifying the OLL case.
         */
        OLL() {
            trues = 0;
            zeroes = 0;
            for(int i = 0; i < 4; i++) {
                if(parity[i]) { trues++; }
                if(spin[i] == 0) { zeroes++; }
            }
            pos0 = findZero();
            posT = findTrue();
        }

        /**
         * Checks whether there are duplicates in the next 3 consecutive corners.
         * For 0 spin 0 cases.
         * @return true if duplicate; false if not
         */
        boolean consecutiveDuplicates() {
            return spin[0] == spin[1] || spin[1] == spin[2];
        }

        /**
         * Returns the three consecutive spin numbers.
         * For L cases only.
         * @return Code for 3 spins
         */
        int consecutiveSpins() {
            int position = 3;
            if(posT[1] - posT[0] != 3) {
                position = posT[0];
            }
            return spin[position] * 100 + spin[(position + 1) % 4] * 10 + spin[(position + 2) % 4];
        }

        /**
         * Finds the spin of the corner after the corner(s) with spin 0.
         * For 1 & 2 spin 0 cases.
         * @return Spin of corner
         */
        int nextSpin() {
            if(zeroes == 1) {
                int position = (pos0[0] + 1) % 4;
                return spin[position];
            } else if(deltaZero() == 3) {
                return spin[1];
            } else {
                int position = (pos0[1] + 1) % 4;
                return spin[position];
            }
        }

        /**
         * Finds whether the two spin 0s are consecutive.
         * For 2 spin 0 cases.
         * @return true if consecutive; false if not
         */
        boolean consecutiveZeroes() {
            return pos0[1] - pos0[0] != 2;
        }

        /**
         * Finds the distance between the two spin 0s.
         * For 2 spin 0 cases.
         * @return Delta
         */
        int deltaZero() {
            return pos0[1] - pos0[0];
        }

        /**
         * Finds whether the two true parities are in L shaped or in a line.
         * For 2 true parity cases.
         * @return true if L; false if line
         */
        boolean consecutiveTrues() {
            return posT[1] - posT[0] != 2;
        }

        /**
         * Returns the position of consecutive spin 0s relative to consecutive true parities.
         * For 2 true parity: 2 spin 0 cases.
         * @return Corners ahead of edges by
         */
        int relativePosition() {
            int relpos0 = 0;
            int relposT = 0;

            if(posT[1] - posT[0] != 3) { relposT = posT[1]; }

            if(zeroes == 1) {
                relpos0 = pos0[0] + 1;
                return (relpos0 + 4 - relposT) % 4;
            } else if(consecutiveZeroes()) {
                if(pos0[1] - pos0[0] != 3) { relpos0 = pos0[1]; }
                return (relpos0 + 4 - relposT) % 4;
            } else {
                relpos0 = pos0[1];
                return (relpos0 + 4 - relposT) % 2;
            }
        }

        /**
         * Return the spin of the position of the true parity edge that is "unaccompanied " by a spin 0 corner.
         * For 2 true parity, 2 spin 0, non-consecutive only.
         * @return Spin of corner
         */
        int unaccompaniedSpin() {
            if(pos0[0] == posT[0] || pos0[1] == posT[0]) {
                return spin[posT[1]];
            } else {
                return spin[posT[0]];
            }
        }

        /**
         * Checks the two spins after the position with both true parity and spin 0.
         * For 2 true parity, 2 spin 0, consecutive only.
         * @return Spins of corners
         */
        int spinsAfterDouble() {
            int position;
            if(pos0[0] == posT[0] || pos0[1] == posT[0]) {
                position = posT[0];
            } else {
                position = posT[1];
            }
            return spin[(position + 1) % 4] * 10 + spin[(position + 2) % 4];
        }

        /**
         * Checks the spins of the edge positions.
         * For 2 true parity, 0 & 2 spin 0 non-consecutive.
         * @return Spin of corner
         */
        int edgeSpin() {
            return spin[findTrue()[0]] * 10 + spin[findTrue()[1]];
        }
        
        /**
         * Checks the spin of a corner after spin 1.
         * For 2 true parity, 0 spin 0 cases.
         * @return Spin of Corner
         */
        int spinAfter1() {
            if(spin[posT[0]] == 1) {
                return spin[(posT[0] + 1) % 4];
            } else {
                return spin[(posT[1] + 1) % 4];
            }
        }

        /**
         * Checks the existence of a double.
         * For 1 spin 0 cases.
         * @return true if double; false if not
         */
        boolean doubleExistence() {
            if(zeroes == 1) {
                return pos0[0] == posT[0] || pos0[0] == posT[1];
            }
            return pos0[0] == posT[0] || pos0[1] == posT[0] || pos0[0] == posT[1] || pos0[1] == posT[1];
        }

        /**
         * Finds the positions of corners that have spin 0.
         * @return Array of positions
         */
        int[] findZero() {
            int[] positions = new int[zeroes];
            int counter = 0;
            for(int i = 0; i < spin.length; i++) {
                if(spin[i] == 0) {
                    positions[counter] = i;
                    counter++;
                }
            }
            return positions;
        }

        /**
         * Finds the positions of edges that are true parity.
         * @return Array of positions
         */
        int[] findTrue() {
            int[] positions = new int[trues];
            int counter = 0;
            for(int i = 0; i < parity.length; i++) {
                if(parity[i]) {
                    positions[counter] = i;
                    counter++;
                }
            }
            return positions;
        }

        /**
         * Finds the only position with given spin.
         * @param spin Spin of corner
         * @return Position of corner
         */
        int findOnlySpin(int spin) {
            for(int i = 0; i < 4; i++) {
                if(this.spin[i] == spin) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Translates number of moves into actual moves
         * @param moves Number of U turns
         * @return Moves
         */
        String alignMoves(int moves) {
            switch(moves) {
                case 0: return "";
                case 1: return "U";
                case 2: return "U2";
                case 3: return "U'";
                default: return "alignment error";
            }
        }

        /**
         * Returns the moves needed to align for OLL procedure.
         * @param spin Spin of corner
         * @param position Final position of corner
         * @return Moves needed to get to final position
         */
        String alignment(int spin, int position) {
            int from = findOnlySpin(spin);
            int moves = (from + 4 - position) % 4;
            return alignMoves(moves);
        }

        /**
         * Returns the moves needed to align for OLL procedure
         * @param spin0 Spin of corner 0
         * @param spin1 Spin of corner 1
         * @param spin2 Spin of corner 2
         * @return Moves
         */
        String alignment(int spin0, int spin1, int spin2) {
            int[] current = Arrays.copyOf(spin, 4);
            int counter = 0;
            while(current[0] != spin0 || current[1] != spin1 || current[2] != spin2) {
                int temp = current[0];
                for(int i = 0; i < 3; i++) {
                    current[i] = current[i+1];
                }
                current[3] = temp;
                counter++;
            }
            return alignMoves(counter);
        }

        /**
         * Returns the moves needed to align for OLL procedure.
         * @param parity Parity of edges to search
         * @param secondPos Final position of the latter corner.
         * @return Moves
         */
        String alignment(boolean parity, int secondPos) {
            if(parity) {
                int from = 0;
                if(posT[1] - posT[0] != 3) {
                    from = posT[1];
                }
                int moves = (from + 4 - secondPos) % 4;
                return alignMoves(moves);
            } else {
                int from = 0;
                for(int i = 0; i < 4; i++) {
                    if(!this.parity[i]) {
                        from = i;
                    }
                }
                int moves = (from + 4 - secondPos) % 4;
                return alignMoves(moves);
            }
        }

        /**
         * Returns the number of correctly oriented edges.
         * @return Number of true parities
         */
        int getTrues() {
            return trues;
        }

        /**
         * Returns the number of correctly oriented corners.
         * @return Number of spin 0s
         */
        int getZeroes() {
            return zeroes;
        }
    }

    /**
     * Matches the cube to an OLL state.
     */
    public void oll() {
        final String ERROR = "oll error.";
        int t0code = 0;
        OLL state = new OLL();

        if(state.getTrues() == 2 && !state.consecutiveTrues()) {
            t0code += 30;
        } else {
            t0code += state.getTrues() * 10;
        }

        t0code += state.getZeroes();

        switch(t0code) {
            case 0:
                if(state.consecutiveDuplicates()) { ollCase(2, state); }
                else { ollCase(1, state); }
                break;
            case 1:
                if(state.nextSpin() == 1) { ollCase(4, state); }
                else { ollCase(3, state); }
                break;
            case 2:
                if(!state.consecutiveZeroes()) { ollCase(17, state); }
                else if(state.nextSpin() == 1) { ollCase(18, state); }
                else { ollCase(19, state); }
                break;
            case 4: ollCase(20, state); break;

            case 20:
                switch(state.consecutiveSpins()) {
                    case 112: ollCase(49, state); break; /* L */
                    case 121: ollCase(53, state); break;
                    case 122: ollCase(48, state); break;
                    case 211: ollCase(50, state); break;
                    case 212: ollCase(54, state); break;
                    case 221: ollCase(47, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 21:
                int code21 = state.relativePosition() * 10 + state.nextSpin();
                switch(code21) {
                    case 1: ollCase(6, state); break; /* Square */
                    case 2: ollCase(5, state); break;
                    case 11: ollCase(12, state); break; /* Small lightning */
                    case 12: ollCase(7, state); break;
                    case 21: ollCase(9, state); break; /* Fish */
                    case 22: ollCase(10, state); break;
                    case 31: ollCase(8, state); break; /* Small Lightning */
                    case 32: ollCase(11, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 22:
                int code22;
                if(state.consecutiveZeroes()) {
                    code22 = 100 + state.relativePosition() * 10 + state.nextSpin();
                } else {
                    code22 = 200 + state.relativePosition() * 10 + state.unaccompaniedSpin();
                }
                switch(code22) {
                    case 101: ollCase(44, state); break; /* P */
                    case 102: ollCase(32, state); break;
                    case 111: ollCase(41, state); break; /* Awkward */
                    case 112: ollCase(30, state); break;
                    case 121: ollCase(42, state); break;
                    case 122: ollCase(29, state); break;
                    case 131: ollCase(43, state); break; /* P */
                    case 132: ollCase(31, state); break;
                    case 201: ollCase(36, state); break; /* W */
                    case 202: ollCase(38, state); break;
                    case 211: ollCase(37, state); break; /* Fish */
                    case 212: ollCase(35, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 24: ollCase(28, state); break;

            case 30:
                switch(state.edgeSpin()) {
                    case 11: ollCase(55, state); break; /* I */
                    case 22: ollCase(56, state); break;
                    case 12: case 21:
                        if(state.spinAfter1() == 1) {
                            ollCase(51, state);
                        } else {
                            ollCase(52, state);
                        }
                        break;
                    default: System.out.println(ERROR);
                }
                break;
            case 31:
                int code31 = 0;
                if(state.doubleExistence()) {
                    code31 += 10;
                }
                code31 += state.nextSpin();
                switch(code31) {
                    case 1: ollCase(14, state); break; /* Knight */
                    case 2: ollCase(15, state); break;
                    case 11: ollCase(16, state); break;
                    case 12: ollCase(13, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 32:
                int code32;
                if(state.consecutiveZeroes()) {
                    code32 = 100 + state.spinsAfterDouble();
                } else {
                    code32 = 200 + state.edgeSpin();
                }
                switch(code32) {
                    case 101: ollCase(46, state); break; /* C */
                    case 102: ollCase(34, state); break;
                    case 110: case 111: case 112: ollCase(45, state); break; /* T */
                    case 120: case 121: case 122: ollCase(33, state); break;
                    case 200: case 201: case 202: ollCase(39, state); break; /* Big lightning */
                    case 210: case 211: case 212: 
                    case 220: case 221: case 222: ollCase(40, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 34: ollCase(57, state); break;

            case 40:
                if(state.consecutiveDuplicates()) { ollCase(22, state); }
                else { ollCase(21, state); }
                break;
            case 41:
                if(state.nextSpin() == 1) { ollCase(26, state); }
                else { ollCase(27, state); }
                break;
            case 42:
                if(!state.consecutiveZeroes()) { ollCase(25, state); }
                else if(state.nextSpin() == 1) { ollCase(23, state); }
                else { ollCase(24, state); }
                break;
            case 44: break; /* OLL skip */
            default: System.out.println(ERROR);
        }
    }

    /**
     * Given a case, performs the moves needed to finish OLL/
     * @param number OLL case
     * @param state OLL state
     */
    private void ollCase(int number, OLL state) {
        switch(number) {
            case 1: ollExecute(1, 2, 1, "R U2 R2 F R F' U2 R' F R F'", state); break; /* Runway */
            case 2: ollExecute(1, 1, 2, "r U r' U2 r U2 R' U2 R U' r'", state); break; /* Zamboni */
            case 3: ollExecute(0, 3, "r' R2 U R' U r U2 r' U M'", state); break; /* Anti-nazi */
            case 4: ollExecute(0, 0, "M U' r U2 r' U' R U' R' M'", state); break; /* Nazi */
            case 5: ollExecute(0, 2, "l' U2 L U L' U l", state); break; /* Right back wide anti-sune */
            case 6: ollExecute(0, 1, "r U2 R' U' R U' r'", state); break; /* Right front wide anti-sune */
            case 7: ollExecute(0, 3, "r U R' U R U2 r'", state); break; /* Wide sune */
            case 8: ollExecute(0, 0, "l' U' L U' L' U2 l", state); break; /* Left wide sune */
            case 9: ollExecute(0, 0, "R U R' U' R' F R2 U R' U' F'", state); break; /* Kite */
            case 10: ollExecute(0, 1, "R U R' U R' F R F' R U2 R'", state); break; /* Anti-kite */
            case 11: ollExecute(0, 1, "r U R' U R' F R F' R U2 r'", state); break; /* Downstairs */
            case 12: ollExecute(0, 2, "M' R' U' R U' R' U2 R U' R r'", state); break; /* Upstairs */
            case 13: ollExecute(0, 3, "F U R U' R2 F' R U R U' R'", state); break; /* Gun */
            case 14: ollExecute(0, 0, "R' F R U R' F' R F U' F'", state); break; /* Anti-gun */
            case 15: ollExecute(0, 2, "l' U' l L' U' L U l' U l", state); break; /* Squeegee */
            case 16: ollExecute(0, 1, "r U r' R U R' U' r U' r'", state); break; /* Anti-squeegee */
            case 17: ollExecute(1, 3, "F R' F' R2 r' U R U' R' U' M'", state); break; /* Slash */
            case 18: ollExecute(2, 0, "r U R' U R U2 r2 U' R U' R' U2 r", state); break; /* Crown */
            case 19: ollExecute(1, 0, "r' R U R U R' U' M' R' F R F'", state); break; /* Bunny */
            case 20: updateThird("r U R' U' M2 U R U' R' U' M'"); break; /* X */
            case 21: ollExecute(2, 1, 2, "R U2 R' U' R U R' U' R U' R'", state); break; /* Double sune */
            case 22: ollExecute(2, 1, 1, "R U2 R2 U' R2 U' R2 U2 R", state); break; /* Pi */
            case 23: ollExecute(1, 1, "R2 D' R U2 R' D R U2 R", state); break; /* Headlights */
            case 24: ollExecute(2, 2, "r U R' U' r' F R F'", state); break; /* Chameleon */
            case 25: ollExecute(2, 0, "F' r U R' U' r' F R", state); break; /* Bowtie */
            case 26: ollExecute(0, 1, "R U2 R' U' R U' R'", state); break; /* Anti-sune */
            case 27: ollExecute(0, 3, "R U R' U R U2 R'", state); break; /* Sune */
            case 28: ollExecute(true, 3, "r U R' U' r' R U R U' R'", state); break; /* Arrow */
            case 29: ollExecute(2, 2, "R U R' U' R U' R' F' U' F R U R'", state); break; /* Spotted chameleon */
            case 30: ollExecute(1, 2, "F R' F R2 U' R' U' R U R' F2", state); break; /* Anti-spotted chameleon */
            case 31: ollExecute(2, 2, "R' U' F U R U' R' F' R", state); break; /* Couch */
            case 32: ollExecute(2, 0, "L U F' U' L' U L F L'", state); break; /* Anti-couch */
            case 33: ollExecute(1, 3, "R U R' U' R' F R F'", state); break; /* Key */
            case 34: ollExecute(2, 1, "R U R2 U' R' F R U R U' F'", state); break; /* City */
            case 35: ollExecute(1, 3, "R U2 R2 F R F' R U2 R'", state); break; /* Fish salad */
            case 36: ollExecute(1, 1, "L' U' L U' L' U L U L F' L' F", state); break; /* Wario */
            case 37: ollExecute(1, 3, "F R' F' R U R U' R'", state); break; /* Mounted fish */
            case 38: ollExecute(1, 0, "R U R' U R U' R' U' R' F R F'", state); break; /* Mario */
            case 39: ollExecute(1, 0, "L F' L' U' L U F U' L'", state); break; /* Fung */
            case 40: ollExecute(1, 1, "R' F R U R' U' F' U R", state); break; /* Anti-fung */
            case 41: ollExecute(1, 1, "R U R' U R U2 R' F R U R' U' F'", state); break; /* Awkward fish */
            case 42: ollExecute(2, 0, "R' U' R U' R' U2 R F R U R' U' F'", state); break; /* Lefty awkward fish */
            case 43: ollExecute(1, 2, "F' U' L' U L F", state); break; /* Anti-P */
            case 44: ollExecute(1, 0, "F U R U' R' F'", state); break; /* P */
            case 45: ollExecute(1, 2, "F R U R' U' F'", state); break; /* T */
            case 46: ollExecute(1, 0, "R' U' R' F R F' U R", state); break; /* C & Headlights */
            case 47: ollExecute(1, 2, 2, "R' U' R' F R F' R' F R F' U R", state); break; /* Anti-breakneck */
            case 48: ollExecute(2, 1, 1, "F R U R' U' R U R' U' F'", state); break; /* Breakneck */
            case 49: ollExecute(2, 1, 1, "r U' r2 U r2 U r2 U' r", state); break; /* Right back squeezy */
            case 50: ollExecute(2, 1, 1, "r' U r2 U' r2 U' r2 U r'", state); break; /* Right front squeezy */
            case 51: ollExecute(1, 2, 2, "F U R U' R' U R U' R' F'", state); break; /* Ant */
            case 52: ollExecute(1, 2, 2, "R U R' U R U' B U' B' R'", state); break; /* Rice cooker */
            case 53: ollExecute(true, 2, "l' U2 L U L' U' L U L' U l", state); break; /* Frying pan */
            case 54: ollExecute(true, 3, "r U2 R' U' R U R' U' R U' r'", state); break; /* Anti-frying pan */
            case 55: ollExecute(2, 1, 2, "R' F R U R U' R2 F' R2 U' R' U R U R'", state); break; /* Highway */
            case 56: ollExecute(1, 2, 1, "r' U' r U' R' U R U' R' U R r' U r", state); break; /* Streetlights */
            case 57: ollExecute(true, 3, "R U R' U' M' U R U' r'", state); break; /* H (cursed pls fix) */
            default: System.out.println("oll execute error.");
        }
    }

    /**
     * Anchors and performs the OLL algorithm based on a single spin position.
     * @param spin Spin of corner
     * @param position Destined position of corner
     * @param alg Algorithm to perform
     * @param state The OLL state
     */
    private void ollExecute(int spin, int position, String alg, OLL state) {
        updateThird(state.alignment(spin, position));
        updateThird(alg);
    }

    /**
     * Anchors and performs the OLL algorithm based on 3 spins.
     * @param spin0 Spin at position 0
     * @param spin1 Spin at position 1
     * @param spin2 Spin at position 2
     * @param alg Algorithm to perform
     * @param state The OLL state
     */
    private void ollExecute(int spin0, int spin1, int spin2, String alg, OLL state) {
        updateThird(state.alignment(spin0, spin1, spin2));
        updateThird(alg);
    }

    /**
     * Anchors and performs the OLL algorithm based on 2 true parity consecutive.
     * @param parity Parity of edges (default true)
     * @param secondPos Destined position of latter corner
     * @param alg Algorithm to perform
     * @param state The OLL state
     */
    private void ollExecute(boolean parity, int secondPos, String alg, OLL state) {
        updateThird(state.alignment(parity, secondPos));
        updateThird(alg);
    }

    /**
     * An object to store the state of the cube pre-PLL.
     */
    class PLL {
        private int[][] colour;

        PLL() {
            Corner[] corners = { getCorner(0), getCorner(1), getCorner(2), getCorner(3), getCorner(4) };
            Edge[] edges = { getEdge(0), getEdge(1), getEdge(2), getEdge(3) };
            colour = new int[4][3];
            for(int i = 0; i < 4; i++) {
                colour[i][0] = corners[(i + 3) % 4].colour(1).getNumber();
                colour[i][1] = edges[i].colour(1).getNumber();
                colour[i][2] = corners[i].colour(2).getNumber();
            }
        }

        /**
         * Checks whether it's a 0-1-2 triple.
         * @param face
         * @return true if triple
         */
        boolean isTriple(int face) {
            return isLeftPair(face) && isRightPair(face); 
        }

        /**
         * Checks whether it's a 0-1 pair.
         * @param face
         * @return true if pair
         */
        boolean isLeftPair(int face) {
            return colour[face][0] == colour[face][1];
        }

        /**
         * Checks whether it's a 1-2 pair.
         * @param face
         * @return true if pair
         */
        boolean isRightPair(int face) {
            return colour[face][1] == colour[face][2];
        }

        /**
         * Checks whether it's a 0-2 pair.
         * @param face
         * @return true if pair
         */
        boolean isSkipPair(int face) {
            return colour[face][0] == colour[face][2];
        }

        /**
         * Finds and returns the position of a 0-1-2 triple.
         * Returns -1 if no triple found.
         * @return Position if found; -1 if not.
         */
        int findTriple() {
            for(int i = 0; i < 4; i++) {
                if(isTriple(i)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Finds and returns the position of a 0-1 pair.
         * Returns -1 if no pair found.
         * @return Position if found; -1 if not
         */
        int findLeftPair() {
            for(int i = 0; i < 4; i++) {
                if(isLeftPair(i)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Finds and returns the position of a 1-2 pair.
         * Returns -1 if no pair found.
         * @return Position if found; -1 if not
         */
        int findRightPair() {
            for(int i = 0; i < 4; i++) {
                if(isRightPair(i)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Finds and returns the position of a 0-2 pair.
         * Returns -1 if no pair found.
         * @return Position if found; -1 if not
         */
        int findSkipPair() {
            for(int i = 0; i < 4; i++) {
                if(isSkipPair(i)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Encodes pairs and triples as a number.
         * @param face
         * @return Digit code
         */
        int patternEncode(int face) {
            if(isTriple(face)) { return 1; }
            else if(isSkipPair(face)) { return 2; }
            else if(isLeftPair(face)) { return 3; }
            else if(isRightPair(face)) { return 4; }
            else { return 0; }
        }
        
        /**
         * Returns the pairs and triples of the other three sides.
         * @param face
         * @return 3 digit code
         */
        int codeSides(int face) {
            int answer = 0;
            answer += patternEncode((face + 1) % 4) * 100;
            answer += patternEncode((face + 2) % 4) * 10;
            answer += patternEncode((face + 3) % 4);
            return answer;
        }

        /**
         * Returns the pairs and triple situation.
         * @return 4 digit code
         */
        int code() {
            int face = findTriple();
            if(face != -1) {
                return 1000 + codeSides(face);
            } else { face = findSkipPair(); }
            if(face != -1) {
                return 2000 + codeSides(face); 
            } else { face = findRightPair(); }
            if(face != -1) {
                return 4000 + codeSides(face);
            } else { face = findLeftPair(); }
            if(face != -1) {
                return 3000 + codeSides(face);
            } else {
                return 0;
            }
        }

        /**
         * Given the code case, returns the PLL case.
         * @param code 4 digit code
         * @return PLL case
         */
        String perm(int code) {
            switch(code) {
                case 0: return "E";
                case 1000: return "F";
                case 1111: return "skip";
                case 1222: return "U";
                case 1333: return "Ja";
                case 1444: return "Jb";
                case 2003: return "Gc";
                case 2004: return "Rb";
                case 2030: return "Gd";
                case 2040: return "Gb";
                case 2043: return "Ab";
                case 2222: return "HZ";
                case 2300: return "Ra";
                case 2304: return "T";
                case 2400: return "Ga";
                case 2430: return "Aa";
                case 3333: return "Nb";
                case 4003: return "Y";
                case 4300: return "V";
                case 4444: return "Na";
                default: System.out.println("perm error.");
            }
            return "skip";
        }

        /**
         * Moves needed to align a pattern to a side.
         * @param identifier Pattern code
         * @param position Final desired position
         * @return Singmaster moves.
         */
        String alignment(int identifier, int position) {
            int pos = -1;
            switch(identifier) {
                case 1: pos = findTriple(); break;
                case 2: pos = findSkipPair(); break;
                case 3: pos = findLeftPair(); break;
                case 4: pos = findRightPair(); break;
                default: System.out.println("pll alignment error.");
            }
            int diff = (pos + 4 - position) % 4;
            switch(diff) {
                case 0: return "";
                case 1: return "U";
                case 2: return "U2";
                case 3: return "U'";
                default: return "pll alignment error.";
            }
        }
    }

    /**
     * Executes the PLL case.
     */
    public void pll() {
        PLL state = new PLL();
        String perm = state.perm(state.code());
        switch(perm) {
            case "Aa":
                updateThird(state.alignment(2, 2));
                updateThird("x R' U R' D2 R U' R' D2 R2");
                break;
            case "Ab":
                updateThird(state.alignment(2, 0));
                updateThird("x' R U' R D2 R' U R D2 R2");
                break;
            case "F":
                updateThird(state.alignment(1, 3));
                updateThird("R' U' F' R U R' U' R' F R2 U' R' U' R U R' U R");
                break;
            case "Ga":
                updateThird(state.alignment(2, 3));
                updateThird("R2 U R' U R' U' R U' R2 U' D R' U R D'");
                break;
            case "Gb":
                updateThird(state.alignment(2, 3));
                updateThird("R' U' R U D' R2 U R' U R U' R U' R2 D");
                break;
            case "Gc":
                updateThird(state.alignment(2, 3));
                updateThird("R2 U' R U' R U R' U R2 U D' R U' R' D");
                break;
            case "Gd":
                updateThird(state.alignment(2, 3));
                updateThird("R U R' U' D R2 U' R U' R' U R' U R2 D'");
                break;
            case "Ja":
                updateThird(state.alignment(1, 3));
                updateThird("x R2 F R F' R U2 r' U r U2");
                break;
            case "Jb":
                updateThird(state.alignment(1, 3));
                updateThird("R U R' F' R U R' U' R' F R2 U' R'");
                break;
            case "Ra":
                updateThird(state.alignment(2, 3));
                updateThird("R U' R' U' R U R D R' U' R D' R' U2 R'");
                break;
            case "Rb":
                updateThird(state.alignment(2, 3));
                updateThird("R2 F R U R U' R' F' R U2 R' U2 R");
                break;
            case "T":
                updateThird(state.alignment(2, 3));
                updateThird("R U R' U' R' F R2 U' R' U' R U R' F'");
                break;
            case "E":
                if(!compareCells(4, 1, 3, 2)) {
                    updateThird("U");
                }
                updateThird("x' R U' R' D R U R' D' R U R' D R U' R' D'");
                break;
            case "Na":
                updateThird("R U R' U R U R' F' R U R' U' R' F R2 U' R' U2 R U' R'");
                break;
            case "Nb":
                updateThird("R' U R U' R' F' U' F R U R' F R' F' R U' R");
                break;
            case "V":
                updateThird(state.alignment(3, 0));
                updateThird("R' U R' U' y R' F' R2 U' R' U R' F R F");
                break;
            case "Y":
                updateThird(state.alignment(3, 0));
                updateThird("F R U' R' U' R U R' F' R U R' U' R' F R F'");
                break;
            case "HZ":
                if(compareCells(1, 1, 3, 0)) {
                    updateThird("M2 U M2 U2 M2 U M2");
                } else if(compareCells(1, 1, 4, 0)) {
                    updateThird("M2 U' M2 U' M' U2 M2 U2 M'");
                } else {
                    updateThird("U' M2 U' M2 U' M' U2 M2 U2 M'");
                } break;
            case "U":
                updateThird(state.alignment(1, 2));
                if(compareCells(1, 1, 2, 0)) {
                    updateThird("M2 U M U2 M' U M2");
                } else {
                    updateThird("M2 U' M U2 M' U' M2");
                } break;
            case "skip": break;
            default: System.out.println("pll error.");
        }
    }

    
    /**
     * Aligns the last face.
     */
    @Override
    public void alignment() {
        if(compareCells(1, 1, 2, 4)) {
            updateThird("U'");
        } else if(compareCells(1, 1, 3, 4)) {
            updateThird("U2");
        } else if(compareCells(1, 1, 4, 4)) {
            updateThird("U");
        } else if(compareCells(0, 1, 2, 4)) {
            updateThird("B'");
        } else if(compareCells(0, 1, 4, 4)) {
            updateThird("B");
        } else if(compareCells(0, 1, 5, 4)) {
            updateThird("B2");
        } else if(compareCells(0, 7, 2, 4)) {
            updateThird("F");
        } else if(compareCells(0, 7, 4, 4)) {
            updateThird("F'");
        } else if(compareCells(0, 7, 5, 4)) {
            updateThird("F2");
        }
    }
}
