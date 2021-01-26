import java.util.Arrays;

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

    @Override
    /**
     * Solves a cube using the CFOP method.
     */
    public void solve() {
        cross();
        f2l();
        OLL state = new OLL();
        oll(state);
        pll();
    }

    /**
     * Completes the first two layers.
     */
    public void f2l() {
        
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
        private int[] posT;

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
                relpos0 = pos0[0];
                return relposT % 2 - relpos0;
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
            if(posT[0] == 1) {
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
            int from = 0;
            if(posT[1] - posT[0] != 3) {
                from = posT[1];
            }
            int moves = (from + 4 - secondPos) % 4;
            return alignMoves(moves);
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

    public void oll(OLL state) {
        final String ERROR = "oll error.";
        int t0code = 0;

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
                    case 112: ollCase(49, state); break;
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
                    case 1: ollCase(6, state); break;
                    case 2: ollCase(5, state); break;
                    case 11: ollCase(12, state); break;
                    case 12: ollCase(7, state); break;
                    case 21: ollCase(9, state); break;
                    case 22: ollCase(10, state); break;
                    case 31: ollCase(8, state); break;
                    case 32: ollCase(11, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 22:
                int code22;
                if(state.consecutiveZeroes()) {
                    code22 = 100 + state.relativePosition() * 10 + state.nextSpin();
                } else {
                    code22 = 200 + state.relativePosition() * 10 + state.nextSpin();
                }
                switch(code22) {
                    case 101: ollCase(44, state); break;
                    case 102: ollCase(32, state); break;
                    case 111: ollCase(41, state); break;
                    case 112: ollCase(30, state); break;
                    case 121: ollCase(42, state); break;
                    case 122: ollCase(29, state); break;
                    case 131: ollCase(43, state); break;
                    case 132: ollCase(31, state); break;
                    case 201: ollCase(36, state); break;
                    case 202: ollCase(38, state); break;
                    case 211: ollCase(37, state); break;
                    case 212: ollCase(35, state); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 24: ollCase(28, state); break;

            case 30:
                switch(state.edgeSpin()) {
                    case 11: ollCase(55, state); break;
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
                    case 1: ollCase(14, state); break;
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
                    case 101: ollCase(46, state); break;
                    case 102: ollCase(34, state); break;
                    case 110: case 111: case 112: ollCase(45, state); break;
                    case 120: case 121: case 122: ollCase(33, state); break;
                    case 200: case 201: case 202: ollCase(39, state); break;
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
            case 44: break;
            default: System.out.println(ERROR);
        }
    }

    private void ollCase(int number, OLL state) {
        switch(number) {
            case 1: ollExecute(1, 2, 1, "R U2 R2 F R F' U2 R' F R F'", state); break;
            case 2: ollExecute(1, 1, 2, "r U r' U2 r U2 R' U2 R U' r'", state); break;
            case 3: ollExecute(0, 3, "r' R2 U R' U r U2 r' U M'", state); break;
            case 4: ollExecute(0, 0, "M U' r U2 r' U' R U' R' M'", state); break;
            case 5: ollExecute(0, 2, "l' U2 L U L' U l", state); break;
            case 6: ollExecute(0, 1, "r U2 R' U' R U' r'", state); break;
            case 7: ollExecute(0, 3, "r U R' U R U2 r'", state); break;
            case 8: ollExecute(0, 0, "l' U' L U' L' U2 l", state); break;
            case 9: ollExecute(0, 0, "R U R' U' R' F R2 U R' U' F'", state); break;
            case 10: ollExecute(0, 1, "R U R' U R' F R F' R U2 R'", state); break;
            case 11: ollExecute(0, 1, "r U R' U R' F R F' R U2 r'", state); break;
            case 12: ollExecute(0, 2, "M' R' U' R U' R' U2 R U' R r'", state); break;
            case 13: ollExecute(0, 3, "F U R U' R2 F' R U R U' R'", state); break;
            case 14: ollExecute(0, 0, "R' F R U R' F' R F U' F'", state); break;
            case 15: ollExecute(0, 2, "l' U' l L' U' L U l' U l", state); break;
            case 16: ollExecute(0, 1, "r U r' R U R' U' r U' r'", state); break;
            case 17: ollExecute(1, 3, "F R' F' R2 r' U R U' R' U' M'", state); break;
            case 18: ollExecute(2, 0, "r U R' U R U2 r2 U' R U' R' U2 r", state); break;
            case 19: ollExecute(1, 0, "r' R U R U R' U' M' R' F R F'", state); break;
            case 20: updateThird("r U R' U' M2 U R U' R' U' M'"); break;
            case 21: ollExecute(2, 1, 2, "R U2 R' U' R U R' U' R U' R'", state); break;
            case 22: ollExecute(2, 1, 1, "R U2 R2 U' R2 U' R2 U2 R", state); break;
            case 23: ollExecute(1, 1, "R2 D' R U2 R' D R U2 R", state); break;
            case 24: ollExecute(2, 2, "r U R' U' r' F R F'", state); break;
            case 25: ollExecute(2, 0, "F' r U R' U' r' F R", state); break;
            case 26: ollExecute(0, 1, "R U2 R' U' R U' R'", state); break;
            case 27: ollExecute(0, 3, "R U R' U R U2 R'", state); break;
            case 28: ollExecute(true, 3, "r U R' U' r' R U R U' R'", state); break;
            case 29: ollExecute(2, 2, "R U R' U' R U' R' F' U' F R U R'", state); break;
            case 30: ollExecute(1, 2, "F R' F R2 U' R' U' R U R' F2", state); break;
            case 31: ollExecute(2, 2, "R' U' F U R U' R' F' R", state); break;
            case 32: ollExecute(2, 0, "L U F' U' L' U L F L'", state); break;
            case 33: ollExecute(1, 3, "R U R' U' R' F R F'", state); break;
            case 34: ollExecute(2, 1, "R U R2 U' R' F R U R U' F'", state); break;
            case 35: ollExecute(1, 3, "R U2 R2 F R F' R U2 R'", state); break;
            case 36: ollExecute(1, 1, "L' U' L U' L' U L U L F' L' F", state); break;
            case 37: ollExecute(1, 3, "F R' F' R U R U' R'", state); break;
            case 38: ollExecute(1, 0, "R U R' U R U' R' U' R' F R F'", state); break;
            case 39: ollExecute(1, 0, "L F' L' U' L U F U' L'", state); break;
            case 40: ollExecute(1, 1, "R' F R U R' U' F' U R", state); break;
            case 41: ollExecute(1, 1, "R U R' U R U2 R' F R U R' U' F'", state); break;
            case 42: ollExecute(2, 0, "R' U' R U' R' U2 R F R U R' U' F'", state); break;
            case 43: ollExecute(1, 2, "F' U' L' U L F", state); break;
            case 44: ollExecute(1, 0, "F U R U' R' F'", state); break;
            case 45: ollExecute(1, 2, "F R U R' U' F'", state); break;
            case 46: ollExecute(1, 0, "R' U' R' F R F' U R", state); break;
            case 47: ollExecute(1, 2, 1, "R' U' R' F R F' R' F R F' U R", state); break;
            case 48: ollExecute(2, 1, 1, "F R U R' U' R U R' U' F'", state); break;
            case 49: ollExecute(2, 1, 1, "r U' r2 U r2 U r2 U' r", state); break;
            case 50: ollExecute(2, 1, 1, "r' U r2 U' r2 U' r2 U r'", state); break;
            case 51: ollExecute(1, 2, 1, "F U R U' R' U R U' R' F'", state); break;
            case 52: ollExecute(1, 2, 1, "R U R' U R U' B U' B' R'", state); break;
            case 53: ollExecute(2, 1, 2, "l' U2 L U L' U' L U L' U l", state); break;
            case 54: ollExecute(2, 1, 2, "r U2 R' U' R U R' U' R U' r'", state); break;
            case 55: ollExecute(2, 1, 2, "R' F R U R U' R2 F' R2 U' R' U R U R'", state); break;
            case 56: ollExecute(1, 2, 1, "r' U' r U' R' U R U' R' U R r' U r", state); break;
            case 57: ollExecute(true, 3, "R U R' U' M' U R U' r'", state); break; /* cursed pls fix */
            default: System.out.println("oll error.");
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
}
