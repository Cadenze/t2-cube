public class AdvRoux extends Roux {

    public AdvRoux() {
        super();
    }

    public AdvRoux(String sequence) {
        super(sequence);
    }

    public AdvRoux(String sequence, boolean solve) {
        super(sequence, solve);
    }

    class CMLL {
        private OLL oll;

        CMLL() {
            oll = new OLL();
        }

        int getZeroes() {
            return oll.getZeroes();
        }

        boolean consecutiveZeroes() {
            return oll.consecutiveZeroes();
        }

        int findSkipPair() {
            for(int i = 0; i < 4; i++) {
                if(isSkipPair(i)) {
                    return i;
                }
            }
            return -1;
        }

        boolean isSkipPair(int face) {
            String colour1 = getCorner(face).colour(getSpin(face), 2);
            int prev = (face + 3) % 4;
            String colour2 = getCorner(prev).colour(getSpin(prev), 1);
            return colour1.equals(colour2);
        }

        boolean consecutiveDuplicates() {
            return oll.consecutiveDuplicates();
        }

        boolean isYellowSkipPair(int face) {
            String yellow = getCorner(face).colour(getSpin(face), 2);
            return isSkipPair(face) && yellow.equals("y");
        }

        int findYellowSkipPair() {
            for(int i = 0; i < 4; i++) {
                if(isYellowSkipPair(i)) {
                    return i;
                }
            }
            return -1;
        }

        boolean isTopPair(int face) {
            int position = (face + 3) % 4;
            return getCorner(face).colour(getSpin(face), 0).equals(getCorner(position).colour(getSpin(position), 0));
        }

        int[] findTopPair() {
            int position = 4;
            for(int i = 0; i < 4; i++) {
                if(isTopPair(i)) {
                    position = i;
                    break;
                }
            }
            if(position == 4) {
                return new int[0];
            } else if(isTopPair(position + 2)) {
                return new int[]{ position, position + 2 };
            } else {
                return new int[]{ position };
            }
        }

        int findYellowTopPair() {
            for(int i = 0; i < 4; i++) {
                if(isTopPair(i) && getCorner(i).colour(getSpin(i), 0).equals("y")) {
                    return i;
                }
            }
            return -1;
        }

        int nextSpin() {
            return oll.nextSpin();
        }

        boolean isDiagonal(int corner) {
            int position = (corner + 2) % 4;
            return getCorner(corner).colour(getSpin(corner), 0).equals(getCorner(position).colour(getSpin(position), 0));
        }

        int findZero() {
            return oll.findZero()[0];
        }

        boolean compareCorners(int spin, int side, int relative, int side2) {
            int position1 = findOnlySpin(spin);
            String colour1 = getCorner(position1).colour(spin, side);
            int position2 = (position1 + relative) % 4;
            String colour2 = getCorner(position2).colour(getSpin(position2), side2);
            return colour1.equals(colour2);
        }

        int findOnlySpin(int spin) {
            return oll.findOnlySpin(spin);
        }

        String identify() {
            int code = getZeroes();
            if(code == 2 && !consecutiveZeroes()) {
                code++;
            }
            switch(code) {
                case 0:
                    int[] pair = findTopPair();
                    int yellow = findYellowSkipPair();
                    if(consecutiveDuplicates()) {
                        if(pair.length == 0) {
                            if(isDiagonal(yellow) && isDiagonal((yellow + 3) % 4)) { return "PX"; }
                            else if(isDiagonal(yellow)) { return "PF"; }
                            else { return "PB"; }
                        } else if(pair.length == 2) { return "PC"; }
                        else if(pair[0] == yellow) { return "PL"; }
                        else { return "PR"; }
                    } else {
                        if(pair.length == 2) {
                            if((pair[0] + 4 - yellow) % 2 == 0) { return "HRs"; }
                            else { return "HCs"; }
                        } else {
                            if((pair[0] + 4 - yellow) % 2 == 0) { return "HR"; }
                            else { return "HC"; }
                        }
                    }
                case 1:
                    if(nextSpin() == 1) {
                        if(compareCorners(0, 2, 1, 2)) {
                            if(compareCorners(0, 1, 3, 0)) { return "AL"; }
                            else { return "AF"; }
                        } else if(compareCorners(0, 2, 2, 2)) {
                            if(compareCorners(0, 1, 3, 0)) { return "AC"; }
                            else { return "AB"; }
                        } else {
                            if(compareCorners(0, 1, 1, 0)) { return "AR"; }
                            else { return "AX"; }
                        }
                    } else {
                        if(compareCorners(0, 1, 1, 1)) {
                            if(compareCorners(0, 2, 3, 0)) { return "SL"; }
                            else { return "SX"; }
                        } else if(compareCorners(0, 1, 2, 1)) {
                            if(compareCorners(0, 2, 3, 0)) { return "SF"; }
                            else { return "SC"; }
                        } else {
                            if(compareCorners(0, 2, 1, 0)) { return "SR"; }
                            else { return "SB"; }
                        }
                    }
                case 2:
                    String answer = "";
                    if(nextSpin() == 1) {
                        answer += "U";
                    } else {
                        answer += "T";
                    }
                    int length = findTopPair().length;
                    boolean invertedparallel = isSkipPair(findYellowTopPair());
                    if(length == 2 && invertedparallel) { answer += "Rs"; }
                    else if(length == 2) { answer += "B"; }
                    else if(invertedparallel) { answer += "F"; }
                    if(answer.length() != 1) { return answer; }
                    if(answer.equals("U")) {
                        if(compareCorners(1, 2, 1, 1)) { return "UX"; }
                        else if(compareCorners(1, 0, 2, 1)) { return "UFs"; }
                        else { return "UBs"; }
                    } else {
                        if(compareCorners(2, 1, 1, 2)) { return "TC"; }
                        else if(compareCorners(2, 0, 2, 2)) { return "TL"; }
                        else { return "TR"; } 
                    }
                case 3:
                    if(compareCorners(1, 0, 2, 0)) {
                        if(compareCorners(1, 2, 1, 2)) { return "LF"; }
                        else { return "LB"; } 
                    } else if(compareCorners(1, 2, 2, 1)) {
                        if(compareCorners(1, 0, 1, 1)) { return "LM"; }
                        else { return "LI"; }
                    } else {
                        if(compareCorners(1, 0, 1, 1)) { return "LD"; }
                        else { return "LP"; }
                    }
                case 4:
                    if(findSkipPair() == -1) {
                        return "OD";
                    } else if(isSkipPair(0) && isSkipPair(1)) {
                        return "solved";
                    } else {
                        return "OA";
                    }
                default: System.out.println("cmll identify error."); return "solved";
            }
        }

        String alignment(int face) {
            int from = findSkipPair();
            return oll.alignMoves((from + 4 - face) % 4);
        }

        String alignment(String condition, int face) {
            int from;
            if(condition.equals("yellow")) {
                from = findYellowSkipPair();
            } else {
                from = findTopPair()[0];
            }
            return oll.alignMoves((from + 4 - face) % 4);
        }

        String alignment(int spin1, int spin2, int spin3) {
            return oll.alignment(spin1, spin2, spin3);
        }
    }

    @Override
    public void cmll() {
        CMLL state = new CMLL();
        String code = state.identify();
        final String y = "yellow";
        switch(code) {
            case "solved": break;
            case "OA":
                updateThird(state.alignment(3));
                updateThird("R U R' F' R U R' U' R' F R2 U' R'");
                break;
            case "OD":
                updateThird("F R U' R' U' R U R' F' R U R' U' R' F R F'");
                break;
            case "HCs":
                updateThird(state.alignment(0));
                updateThird("R U2 R' U' R U R' U' R U' R'");
                break;
            case "HRs":
                updateThird(state.alignment(y, 0));
                updateThird("F R U R' U' R U R' U' R U R' U' F'");
                break;
            case "HC":
                updateThird(state.alignment("top", 0));
                updateThird("R U2 R2 F R F' U2 R' F R F'");
                break;
            case "HR":
                updateThird(state.alignment("top", 2));
                updateThird("r U' r2 D' r U' r' D r2 U r'");
                break;
            case "PR":
                updateThird(state.alignment(3));
                updateThird("F R U R' U' R U R' U' F'");
                break;
            case "PB":
                updateThird(state.alignment(2));
                updateThird("F R' F' R U2 R U' R' U R U2 R'");
                break;
            case "PX":
                updateThird(state.alignment(y, 0));
                updateThird("R' F R U F U' R U R' U' F'");
                break;
            case "PF":
                updateThird(state.alignment(3));
                updateThird("R U2 R' U' R U R' U2 R' F R F'");
                break;
            case "PC":
                updateThird(state.alignment(0));
                updateThird("r U' r2 D' r U r' D r2 U r'");
                break;
            case "PL":
                updateThird(state.alignment(y, 0));
                updateThird("R' U' R' F R F' R U' R' U2 R");
                break;
            case "UFs":
                updateThird(state.alignment(0));
                updateThird("R2 D R' U2 R D' R' U2 R'");
                break;
            case "UBs":
                updateThird(state.alignment(2));
                updateThird("R2 D' R U2 R' D R U2 R");
                break;
            case "UF":
                updateThird(state.alignment(0, 1, 2));
                updateThird("R2 F U' F U F2 R2 U' R' F R");
                break;
            case "URs":
                updateThird(state.alignment(y, 3));
                updateThird("F R2 D R' U R D' R2 U' F'");
                break;
            case "UX":
                updateThird(state.alignment(0));
                updateThird("r U' r' U r' D' r U' r' D r");
                break;
            case "UB":
                updateThird(state.alignment(3));
                updateThird("F R U R' U' F'");
                break;
            case "TL":
                updateThird(state.alignment(0, 0, 2));
                updateThird("R U R' U' R' F R F'");
                break;
            case "TR":
                updateThird(state.alignment(2, 1, 0));
                updateThird("L' U' L U L F' L' F");
                break;
            case "TRs":
                updateThird(state.alignment(0, 2, 1));
                updateThird("F R' F R2 U' R' U' R U R' F2");
                break;
            case "TF":
                updateThird(state.alignment(0, 2, 1));
                updateThird("r' U r U2 R2 F R F' R");
                break;
            case "TB":
                updateThird(state.alignment(0, 2, 1));
                updateThird("r' D' r U r' D r U' r U r'");
                break;
            case "TC":
                updateThird(state.alignment(1, 0, 0));
                updateThird("r2 D' r U r' D r2 U' r' U' r");
                break;
            case "SL":
                updateThird(state.alignment(2, 2, 2));
                updateThird("R U R' U R U2 R'");
                break;
            case "SX":
                updateThird(state.alignment(2, 2, 2));
                updateThird("L' U2 L U2 L F' L' F");
                break;
            case "SF":
                updateThird(state.alignment(2, 2, 2));
                updateThird("F R' F' R U2 R U2 R'");
                break;
            case "SC":
                updateThird(state.alignment(2, 2, 2));
                updateThird("R U R' U' R' F R F' R U R' U R U2 R'");
                break;
            case "SR":
                updateThird(state.alignment(2, 0 ,2));
                updateThird("R U R' U R' F R F' R U2 R'");
                break;
            case "SB":
                updateThird(state.alignment(2, 2, 2));
                updateThird("R U' L' U R' U' L");
                break;
            case "AR":
                updateThird(state.alignment(1, 1, 0));
                updateThird("R' U' R U' R' U2 R");
                break;
            case "AC":
                updateThird(state.alignment(0, 1, 1));
                updateThird("R2 D R' U R D' R' U R' U' R U' R'");
                break;
            case "AB":
                updateThird(state.alignment(0, 1, 1));
                updateThird("F' L F L' U2 L' U2 L");
                break;
            case "AX":
                updateThird(state.alignment(0, 1, 1));
                updateThird("R U2 R' U2 R' F R F'");
                break;
            case "AF":
                updateThird(state.alignment(0, 1, 1));
                updateThird("L' U R U' L U R'");
                break;
            case "AL":
                updateThird(state.alignment(1, 1, 1));
                updateThird("R' U' R U' R' U R' F R F' U R");
                break;
            case "LM":
                updateThird(state.alignment(0, 2, 0));
                updateThird("F R U' R' U' R U R' F'");
                break;
            case "LI":
                updateThird(state.alignment(0, 2, 0));
                updateThird("F R' F' R U R U' R'");
                break;
            case "LP":
                updateThird(state.alignment(0, 1, 0));
                updateThird("R U2 R' U' R U R' U' R U R' U' R U' R'");
                break;
            case "LF":
                updateThird(state.alignment(0, 2, 0));
                updateThird("R U2 R D R' U2 R D' R2");
                break;
            case "LD":
                updateThird(state.alignment(0, 2, 0));
                updateThird("R U2 R2 F R F' R U2 R'");
                break;
            case "LB":
                updateThird(state.alignment(1, 0, 2));
                updateThird("R' U2 R' D' R U2 R' D R2");
                break;
            default: System.out.println("cmll execute error.");
        }
    }

    @Override
    public void lse() {
        look += 2;
        eolr();
        mperm();
        alignM();
    }

    public void eolr() {
        new EOLR();
    }

    class EOLR {
        private boolean[] parity;
        private int[] lr;
        private String codeEO;
        private String codeLR;
        private OLL ustate;
        private static final String EXECUTE_CODE_LR_ERROR = "execute code LR error.";

        EOLR() {
            this(true);
        }

        EOLR(boolean solve) {
            ustate = new OLL();
            parity = new boolean[]{ getParity(0), getParity(1), getParity(2), getParity(3), getParity(8), getParity(10) };
            codeEO = codeEO();
            auf();
            lr = new int[]{ findEdge(Edge.YELLOW_ORANGE), findEdge(Edge.YELLOW_RED) };
            codeLR = codeLR();
            if(solve) {
                execute();
                align();
            }
        }

        void align() {
            if(compareCells(1, 0, 4, 4)) {
                updateThird("U");
            } else if(compareCells(2, 0, 4, 4)) {
                updateThird("U2");
            } else if(compareCells(3, 0, 4, 4)) {
                updateThird("U'");
            }

            if(compareCells(0, 0, 1, 4)) {
                updateThird("M'");
            } else if(compareCells(0, 0, 5, 4)) {
                updateThird("M2");
            } else if(compareCells(0, 0, 3, 4)) {
                updateThird("M");
            }
        }

        void auf() {
            if(codeEO.equals("3-1")) {
                if(parity[4]) {
                    updateThird("y2");
                }
                updateThird(ustate.alignMoves((findParity(true) + 2) % 4));
            } else if(codeEO.equals("1-1")) {
                if(parity[5]) {
                    updateThird("y2");
                }
                updateThird(ustate.alignMoves(findParity(false)));
            } else if(codeEO.equals("2a-2") || codeEO.equals("2a-0")) {
                updateThird(ustate.alignment(true, 1));
            } else if(codeEO.equals("2o-2")) {
                updateThird(ustate.alignment(true, 2));
            } else if(codeEO.equals("2o-0")) {
                updateThird(ustate.alignment(true, 3));
            }
        }

        void dock() {
            /* simple insert case */
            lr = new int[]{ findEdge(Edge.YELLOW_ORANGE), findEdge(Edge.YELLOW_RED) };
            if((lr[0] == 2 || lr[0] == 10) && compareCells(0, 1, 1, 0) && compareCells(5, 7, 3, 0) && compileCell(3, 1).equals("y") && compileCell(3, 7).equals("y")) {
                updateThird("M");
                return;
            } else if((lr[0] == 0 || lr[0] == 8) && compareCells(0, 7, 3, 0) && compareCells(5, 1, 1, 0) && compileCell(1, 1).equals("y") && compileCell(1, 7).equals("y")) {
                updateThird("M'");
                return;
            }

            /* dock case */
            if((lr[0] == 2 || lr[0] == 10) && compileCell(3, 1).equals("y") && compileCell(3, 7).equals("y")) {
                updateThird("M'");
            } else if((lr[0] == 0 || lr[0] == 8) && compileCell(1, 1).equals("y") && compileCell(1, 7).equals("y")) {
                updateThird("M");
            }

            docked();
        }

        void docked() {
            String colour = getEdge(10).colour(1);
            if(colour.equals(compileCell(1, 0))) {
                updateThird("M2");
            } else if(colour.equals(compileCell(2, 0))) {
                updateThird("U M2");
            } else if(colour.equals(compileCell(3, 0))) {
                updateThird("U2 M2");
            } else {
                updateThird("U' M2");
            }
        }

        String codeEO() {
            int top = 0;
            int bottom = 0;
            for(int i = 0; i < 4; i++) {
                if(!parity[i]) {
                    top++;
                }
            }
            for(int i = 4; i < 6; i++) {
                if(!parity[i]) {
                    bottom++;
                }
            }
            
            if(top == 2) {
                if(ustate.consecutiveTrues()) {
                    return "2a-" + bottom;
                } else {
                    return "2o-" + bottom;
                }
            }

            return top + "-" + bottom;
        }
        
        /* LR states */
        private static final String UL_UR = "UL UR";
        private static final String UL_UB = "UL UB";
        private static final String UL_UF = "UL UF";
        private static final String UL_DF = "UL DF";
        private static final String UL_DB = "UL DB";
        private static final String UR_UB = "UR UB";
        private static final String UR_UF = "UR UF";
        private static final String UR_DF = "UR DF";
        private static final String UR_DB = "UR DB";
        private static final String UB_UF = "UB UF";
        private static final String UB_DF = "UB DF";
        private static final String UB_DB = "UB DB";
        private static final String UF_DF = "UF DF";
        private static final String UF_DB = "UF DB";
        private static final String DF_DB = "DF DB";

        String codeLR() {
            int code = lr[1] > lr[0] ? lr[1] * 10 + lr[0] : lr[0] * 10 + lr[1];
            switch(code) {
                case 31: return UL_UR;
                case 32: return UL_UB;
                case 30: return UL_UF;
                case 83: return UL_DF;
                case 103: return UL_DB;
                case 21: return UR_UB;
                case 10: return UR_UF;
                case 81: return UR_DF;
                case 101: return UR_DB;
                case 20: return UB_UF;
                case 82: return UB_DF;
                case 102: return UB_DB;
                case 80: return UF_DF;
                case 100: return UF_DB;
                case 108: return DF_DB;
                default: System.out.println("code LR error."); return "";
            }
        }

        void execute() {
            switch(codeEO) {
                case "0-0": lr(); break;
                case "4-0": flip40(); break;
                case "3-1": arrow(); break;
                case "2o-2": flip2lr2(); break;
                case "2a-2": flip2lb2(); break;
                case "2a-0": flip2lb0(); break;
                case "2o-0": flip2fb0(); break;
                case "1-1": flip11(); break;
                case "0-2": flip02(); break;
                case "4-2": flip6(); break;
                default: System.out.println("execute code EO error.");
            }
            dock();
        }

        void flip40() {
            switch(codeLR) {
                case UL_UR: updateThird("U' M' U2 M' U2"); break;
                case UL_UB: updateThird("M' U2 M U M' U2 M U'"); break;
                case UL_UF: updateThird("M' U2 M' U M' U2 M U'"); break;
                case UL_DF: updateThird("M' U2 M' U2 M' U'"); break;
                case UL_DB: updateThird("M' U2 M' U2 M' U"); break;
                case UR_UB: updateThird("M' U2 M U' M' U2 M U"); break;
                case UR_UF: updateThird("M' U2 M' U' M' U2 M U"); break;
                case UR_DF: updateThird("M' U2 M' U2 M' U"); break;
                case UR_DB: updateThird("M' U2 M' U2 M' U'"); break;
                case UB_UF: updateThird("M' U2 M' U2"); break;
                case UB_DF: updateThird("U' M' U2 M' U2 M' U'"); break;
                case UB_DB: updateThird("U' M' U2 M' U2 M' U"); break;
                case UF_DF: updateThird("U' M' U2 M' U2 M' U"); break;
                case UF_DB: updateThird("U' M' U2 M' U2 M' U'"); break;
                case DF_DB: updateThird("M' U2 M' U2 M' U' M' U2 M' U2"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void arrow() {
            switch(codeLR) {
                case UL_UR: updateThird("M' U' M' U2 M' U2"); break;
                case UL_UB: updateThird("M' U"); break;
                case UL_UF: updateThird("U' M' U2 M U'"); break;
                case UL_DF: updateThird("M' U' M U' M' U2"); break;
                case UL_DB: updateThird("M' U'"); break;
                case UR_UB: updateThird("M' U'"); break;
                case UR_UF: updateThird("U M' U2 M U"); break;
                case UR_DF: updateThird("M' U' M' U M' U2"); break;
                case UR_DB: updateThird("M' U"); break;
                case UB_UF: updateThird("M' U' M' U M' U2"); break;
                case UB_DF: updateThird("M' U' M' U' M' U2"); break;
                case UB_DB: updateThird("M' U' M' U2 M' U2"); break;
                case UF_DF: break;
                case UF_DB: updateThird("M' U' M U' M' U2"); break;
                case DF_DB: updateThird("U' M U' M' U'"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip2lr2() {
            switch(codeLR) {
                case UL_UR: updateThird("M2 U' M' U2 M' U2"); break;
                case UL_UB: updateThird("M' U2 M' U2 M U"); break;
                case UL_UF: updateThird("M' U2 M' U2 M U'"); break;
                case UL_DF: updateThird("M' U2 M U' M' U2 M' U'"); break;
                case UL_DB: updateThird("M' U2 M' U' M' U2 M' U'"); break;
                case UR_UB: updateThird("M' U2 M' U2 M U'"); break;
                case UR_UF: updateThird("M' U2 M' U2 M U"); break;
                case UR_DF: updateThird("M' U2 M U M' U2 M' U"); break;
                case UR_DB: updateThird("M' U2 M' U M' U2 M' U"); break;
                case UB_UF: updateThird("M' U2 M' U2 M U' M' U2 M' U2"); break;
                case UB_DF: updateThird("M' U2 M' U' M' U M U"); break;
                case UB_DB: updateThird("M2 U' M' U2 M' U2 M' U"); break;
                case UF_DF: updateThird("M2 U' M' U2 M' U2 M' U"); break;
                case UF_DB: updateThird("M' U2 M U' M' U M U"); break;
                case DF_DB: updateThird("M' U2 M' U2"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip2lb2() {
            switch(codeLR) {
                case UL_UR: updateThird("U' M U' M U'"); break;
                case UL_UB: updateThird("M2 U'"); break;
                case UL_UF: updateThird("U' M' U2 M' U' M U'"); break;
                case UL_DF: updateThird("U2 M' U2 M' U"); break;
                case UL_DB: updateThird("M' U2 M U'"); break;
                case UR_UB: updateThird("M' U2 M U' M U'"); break;
                case UR_UF: updateThird("M' U' M' U2 M' U2 M U"); break;
                case UR_DF: updateThird("M2 U' M' U"); break;
                case UR_DB: updateThird("M2 U' M' U'"); break;
                case UB_UF: updateThird("M' U M' U"); break;
                case UB_DF: updateThird("U' M' U2 M' U'"); break;
                case UB_DB: updateThird("U M' U2 M U"); break;
                case UF_DF: updateThird("M2 U' M' U'"); break;
                case UF_DB: updateThird("M' U' M U"); break;
                case DF_DB: updateThird("M' U M' U M' U"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip2lb0() {
            switch(codeLR) {
                case UL_UR: updateThird("U' M' U M U M' U M U"); break;
                case UL_UB: updateThird("M' U M' U' M U M' U"); break;
                case UL_UF: updateThird("U' M U' M' U2"); break;
                case UL_DF: updateThird("M' U M' U2 M' U"); break;
                case UL_DB: updateThird("U2 M U M' U2 M U'"); break;
                case UR_UB: updateThird("M' U M' U2"); break;
                case UR_UF: updateThird("M' U2 M' U' M' U' M U'"); break;
                case UR_DF: updateThird("M2 U M' U M U"); break;
                case UR_DB: updateThird("M2 U M' U' M U'"); break;
                case UB_UF: updateThird("M' U M U M' U2 M U'"); break;
                case UB_DF: updateThird("U M' U' M' U2 M' U'"); break;
                case UB_DB: updateThird("U' M U' M' U2 M U"); break;
                case UF_DF: updateThird("M2 U M U M U"); break;
                case UF_DB: updateThird("M' U M' U2 M' U"); break;
                case DF_DB: updateThird("M' U M' U2 M' U'"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip2fb0() {
            switch(codeLR) {
                case UL_UR: updateThird("M' U' M' U' M' U2 M U'"); break;
                case UL_UB: updateThird("M U' M' U M U"); break;
                case UL_UF: updateThird("M' U M U' M' U'"); break;
                case UL_DF: updateThird("M' U' M U"); break;
                case UL_DB: updateThird("M U M' U'"); break;
                case UR_UB: updateThird("M U M' U' M U'"); break;
                case UR_UF: updateThird("M' U' M U M' U"); break;
                case UR_DF: updateThird("M' U M U'"); break;
                case UR_DB: updateThird("M U' M' U"); break;
                case UB_UF: updateThird("M' U' M U M' U'"); break;
                case UB_DF: updateThird("U2 M U' M' U M U"); break;
                case UB_DB: updateThird("M' U' M U M' U"); break;
                case UF_DF: updateThird("M U' M' U M U"); break;
                case UF_DB: updateThird("U2 M' U' M U M' U"); break;
                case DF_DB: updateThird("U' M' U2 M' U' M U' M' U"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip11() {
            switch(codeLR) {
                case UL_UR: updateThird("U' M' U M' U2 M U"); break;
                case UL_UB: updateThird("M U M' U"); break;
                case UL_UF: updateThird("M' U' M' U' M U'"); break;
                case UL_DF: updateThird("M' U M' U"); break;
                case UL_DB: updateThird("M U' M' U' M U'"); break;
                case UR_UB: updateThird("M U' M' U'"); break;
                case UR_UF: updateThird("M' U M' U M U"); break;
                case UR_DF: updateThird("M' U' M' U'"); break;
                case UR_DB: updateThird("M U M' U M U"); break;
                case UB_UF: updateThird("M U' M' U2 M' U2 M' U"); break;
                case UB_DF: updateThird("U' M' U2 M U' M U' M' U'"); break;
                case UB_DB: updateThird("U' M' U M' U2"); break;
                case UF_DF: updateThird("M U' M' U' M U'"); break;
                case UF_DB: updateThird("M' U' M' U' M U"); break;
                case DF_DB: updateThird("M' U' M' U2 M' U2 M' U"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip02() {
            switch(codeLR) {
                case UL_UR: updateThird("M' U' M U' M' U2 M' U"); break;
                case UL_UB: updateThird("M U' M U"); break;
                case UL_UF: updateThird("M' U M' U'"); break;
                case UL_DF: updateThird("M' U' M' U M U"); break;
                case UL_DB: updateThird("M U M U' M' U'"); break;
                case UR_UB: updateThird("M U M U'"); break;
                case UR_UF: updateThird("M' U' M' U"); break;
                case UR_DF: updateThird("M' U M' U' M U'"); break;
                case UR_DB: updateThird("M U' M U M' U"); break;
                case UB_UF: updateThird("U' M' U' M U' M' U2 M' U"); break;
                case UB_DF: updateThird("U2 M U' M U M' U"); break;
                case UB_DB: updateThird("M' U' M' U M U"); break;
                case UF_DF: updateThird("M U' M U M' U"); break;
                case UF_DB: updateThird("U2 M' U' M' U M U"); break;
                case DF_DB: updateThird("M' U' M' U M U'"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }

        void flip6() {
            switch(codeLR) {
                case UL_UR: updateThird("U' M U' M' U' M U' M U2 M' U"); break;
                case UL_UB: updateThird("U M' U M' U' M U' M' U2"); break;
                case UL_UF: updateThird("U2 M' U M' U' M U' M' U2"); break;
                case UL_DF: updateThird("U2 M U M' U' M U' M' U2"); break;
                case UL_DB: updateThird("y2 M U M' U' M U' M' U2"); break;
                case UR_UB: updateThird("M' U M' U' M U' M' U2"); break;
                case UR_UF: updateThird("U' M' U M' U' M U' M' U2"); break;
                case UR_DF: updateThird("M U M' U' M U' M' U2"); break;
                case UR_DB: updateThird("d2 M U M' U' M U' M' U2"); break;
                case UB_UF: updateThird("M U' M' U' M U' M U2 M' U"); break;
                case UB_DF: updateThird("U M U M' U' M U' M' U2"); break;
                case UB_DB: updateThird("y2 U' M U M' U' M U' M' U2"); break;
                case UF_DF: updateThird("U' M U M' U' M U' M' U2"); break;
                case UF_DB: updateThird("y2 U M U M' U' M U' M' U2"); break;
                case DF_DB: updateThird("M' U' M' U' M U' M U2 M' U"); break;
                default: System.out.println(EXECUTE_CODE_LR_ERROR);
            }
        }
    }
}
