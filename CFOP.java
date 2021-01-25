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
        oll();
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
            int[] positions = findTrue();
            int position = 3;
            if(positions[1] - positions[0] != 3) {
                position = positions[0];
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
                int position = (findZero()[0] + 1) % 4;
                return spin[position];
            } else if(deltaZero() == 3) {
                return spin[1];
            } else {
                int position = (findZero()[1] + 1) % 4;
                return spin[position];
            }
        }

        /**
         * Finds whether the two spin 0s are consecutive.
         * For 2 spin 0 cases.
         * @return true if consecutive; false if not
         */
        boolean consecutiveZeroes() {
            int[] positions = findZero();
            return positions[1] - positions[0] != 2;
        }

        /**
         * Finds the distance between the two spin 0s.
         * For 2 spin 0 cases.
         * @return Delta
         */
        int deltaZero() {
            int[] positions = findZero();
            return positions[1] - positions[0];
        }

        /**
         * Finds whether the two true parities are in L shaped or in a line.
         * For 2 true parity cases.
         * @return true if L; false if line
         */
        boolean consecutiveTrues() {
            int[] positions = findTrue();
            return positions[1] - positions[0] != 2;
        }

        /**
         * Returns the position of consecutive spin 0s relative to consecutive true parities.
         * For 2 true parity: 2 spin 0 cases.
         * @return Corners ahead of edges by
         */
        int relativePosition() {
            int[] pos0 = findZero();
            int[] posT = findTrue();
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
            int[] pos0 = findZero();
            int[] posT = findTrue();

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
            int[] pos0 = findZero();
            int[] posT = findTrue();
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
            int[] posT = findTrue();
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
            int[] pos0 = findZero();
            int[] posT = findTrue();
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

    public void oll() {
        OLL state = new OLL();
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
                if(state.consecutiveDuplicates()) { ollCase(2); }
                else { ollCase(1); }
                break;
            case 1:
                if(state.nextSpin() == 1) { ollCase(4); }
                else { ollCase(3); }
                break;
            case 2:
                if(!state.consecutiveZeroes()) { ollCase(17); }
                else if(state.nextSpin() == 1) { ollCase(18); }
                else { ollCase(19); }
                break;
            case 4: ollCase(20); break;

            case 20:
                switch(state.consecutiveSpins()) {
                    case 112: ollCase(49); break;
                    case 121: ollCase(53); break;
                    case 122: ollCase(48); break;
                    case 211: ollCase(50); break;
                    case 212: ollCase(54); break;
                    case 221: ollCase(47); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 21:
                int code21 = state.relativePosition() * 10 + state.nextSpin();
                switch(code21) {
                    case 1: ollCase(6); break;
                    case 2: ollCase(5); break;
                    case 11: ollCase(12); break;
                    case 12: ollCase(7); break;
                    case 21: ollCase(9); break;
                    case 22: ollCase(10); break;
                    case 31: ollCase(8); break;
                    case 32: ollCase(11); break;
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
                    case 101: ollCase(44); break;
                    case 102: ollCase(32); break;
                    case 111: ollCase(41); break;
                    case 112: ollCase(30); break;
                    case 121: ollCase(42); break;
                    case 122: ollCase(29); break;
                    case 131: ollCase(43); break;
                    case 132: ollCase(31); break;
                    case 201: ollCase(36); break;
                    case 202: ollCase(38); break;
                    case 211: ollCase(37); break;
                    case 212: ollCase(35); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 24: ollCase(28); break;

            case 30:
                switch(state.edgeSpin()) {
                    case 11: ollCase(55); break;
                    case 22: ollCase(56); break;
                    case 12: case 21:
                        if(state.spinAfter1() == 1) {
                            ollCase(51);
                        } else {
                            ollCase(52);
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
                    case 1: ollCase(14); break;
                    case 2: ollCase(15); break;
                    case 11: ollCase(16); break;
                    case 12: ollCase(13); break;
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
                    case 101: ollCase(46); break;
                    case 102: ollCase(34); break;
                    case 110: case 111: case 112: ollCase(45); break;
                    case 120: case 121: case 122: ollCase(33); break;
                    case 200: case 201: case 202: ollCase(39); break;
                    case 210: case 211: case 212: 
                    case 220: case 221: case 222: ollCase(40); break;
                    default: System.out.println(ERROR);
                }
                break;
            case 34: ollCase(57); break;

            case 40:
                if(state.consecutiveDuplicates()) { ollCase(22); }
                else { ollCase(21); }
                break;
            case 41:
                if(state.nextSpin() == 1) { ollCase(26); }
                else { ollCase(27); }
                break;
            case 42:
                if(!state.consecutiveZeroes()) { ollCase(25); }
                else if(state.nextSpin() == 1) { ollCase(23); }
                else { ollCase(24); }
                break;
            case 44: break;
            default: System.out.println(ERROR);
        }
    }

    private void ollCase(int number) {

    }
}
