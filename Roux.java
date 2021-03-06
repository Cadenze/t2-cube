public class Roux extends CFOP {
    
    public Roux() {
        super();
    }

    public Roux(String sequence) {
        super(sequence);
    }

    public Roux(String sequence, boolean solve) {
        super(sequence, solve);
    }

    @Override
    public void solve() {
        f2b();
        cmll(); 
        lse();
    }

    public void f2b() {
        look += 2;
        crossRed();
        crossOrange(); 
        updateFirst("z2"); /* yellow on top, red in front */
        f2l(); /* yellow on top, blue in front */
    }

    public void cmll() {
        oll2();
        pll1();
    }

    public void lse() {
        look += 3;
        eo();
        lr();
        mperm();
        alignM();
    }

    public void eo() {
        OLL ustate = new OLL();
        boolean[] parity = { getParity(0), getParity(1), getParity(2), getParity(3), getParity(8), getParity(10) };
        int code = 0;
        for(int i = 0; i < 4; i++) {
            if(!parity[i]) {
                code += 10;
            }
        }
        for(int i = 4; i < 6; i++) {
            if(!parity[i]) {
                code++;
            }
        }

        switch(code) {
            case 42: updateThird("R U' r' U' M' U r U r'"); break;
            case 22:
                if(ustate.consecutiveTrues()) {
                    updateThird(ustate.alignment(true, 1));
                    updateThird("M2 U' M' U' M'");
                } else {
                    updateThird(ustate.alignment(true, 2));
                    updateThird("M' U2 M' U2 M U' M'");
                } break;
            case 2: updateThird("M' U M' U' M U' M'"); break;
            case 31:
                if(getParity(8)) {
                    updateThird(ustate.alignMoves(findParity(true)));
                    updateThird("M U' M'");
                } else {
                    updateThird(ustate.alignMoves((findParity(true) + 2) % 4));
                    updateThird("M' U' M'");
                } break;
            case 40: updateThird("M' U2 M' U2 M' U' M'"); break;
            case 20:
                if(ustate.consecutiveTrues()) {
                    updateThird(ustate.alignment(true, 1));
                    updateThird("M' U M' U2 M' U' M'");
                } else {
                    updateThird(ustate.alignment(true, 3));
                    updateThird("M' U M U' M' U' M'");
                } break;
            case 11:
                if(getParity(8)) {
                    updateThird(ustate.alignMoves(findParity(false)));
                    updateThird("M' U' M' U' M U' M'");
                } else {
                    updateThird(ustate.alignMoves((findParity(false) + 2) % 4));
                    updateThird("M' U' M U' M' U' M'");
                } break;
            case 0: break;
            default: System.out.println("eo code error.");
        }
    }

    public void lr() {
        rightInsert();
        leftInsert();
    }

    private void rightInsert() {
        int face = findRightSlot();
        int edge = findRightEdge();

        if(face == edge) {
            return;
        } else if(edge == 8) {
            updateThird(alignMoves((face + 2) % 4));
            updateThird("M2");
            return;
        } else if(edge == 10) {
            updateThird(alignMoves(face));
            updateThird("M2");
            return;
        } else if(edge == 1 || edge == 3) {
            updateThird("U' M2");
        } else {
            updateThird("M2");
        }

        rightInsert();
    }

    private void leftInsert() {
        int face = findLeftSlot();
        int edge = findLeftEdge();

        if(face == edge) {
            updateThird(alignMoves((face + 1) % 4));
            return;
        } else if(edge == 8) {
            updateThird(alignMoves(face));
            updateThird("M' U2 M' U2 M2");
        } else if(edge == 10) {
            updateThird(alignMoves((face + 2) % 4));
            updateThird("M U2 M U2 M2");
        } else if(edge == 0 || edge == 2) {
            updateThird("M2");
        } else {
            updateThird("U M2");
        }

        leftInsert();
    }

    private int findRightSlot() {
        for(int i = 1; i < 5; i++) {
            if(compareCells(i, 0, 2, 4)) {
                return i - 1;
            }
        }
        return -1;
    }

    private int findLeftSlot() {
        for(int i = 1; i < 5; i++) {
            if(compareCells(i, 0, 4, 4)) {
                return i - 1;
            }
        }
        return -1;
    }

    private int findRightEdge() {
        for(int i = 1; i < 5; i++) {
            if(compareCells(i, 1, 2, 4)) {
                return i - 1;
            }
        }
        if(compareCells(1, 7, 2, 4)) {
            return 8;
        } else {
            return 10;
        }
    }

    private int findLeftEdge() {
        for(int i = 1; i < 5; i++) {
            if(compareCells(i, 1, 4, 4)) {
                return i - 1;
            }
        }
        if(compareCells(1, 7, 4, 4)) {
            return 8;
        } else {
            return 10;
        }
    }

    protected int findParity(boolean parity) {
        for(int i = 0; i < 4; i++) {
            if(getParity(i) == parity) {
                return i;
            }
        }
        return -1;
    }

    protected void mperm() {
        boolean[] correct = {
            getEdge(0).colour(0)  == getCentre(0) && getEdge(0).colour(1)  == getCentre(1),
            getEdge(2).colour(0)  == getCentre(0) && getEdge(2).colour(1)  == getCentre(3),
            getEdge(10).colour(0) == getCentre(5) && getEdge(10).colour(1) == getCentre(3),
            getEdge(8).colour(0)  == getCentre(5) && getEdge(8).colour(1)  == getCentre(1)
        };
        int corrects = 0;
        for(int i = 0; i < 4; i++) {
            if(correct[i]) {
                corrects++;
            }
        }
        
        if(corrects == 0) {
            if(getEdge(0).colour(0) == getCentre(0)) {
                updateThird("U2 M2 U2 M2");
            } else if(getEdge(0).colour(1) == getCentre(1)) {
                updateThird("x' U2 M2 U2 M2");
            } else {
                updateThird("E2 M E2 M");
            }
        } else if(corrects != 4) {
            if(correct[0]) {
                updateThird("M2");
            } else if(correct[1]) {
                updateThird("M'");
            } else if(correct[3]) {
                updateThird("M");
            }

            if((getEdge(0).colour(0) == getCentre(5) && getEdge(0).colour(1) == getCentre(1)) ||
               (getEdge(0).colour(0) == getCentre(1) && getEdge(0).colour(1) == getCentre(5))) {
                updateThird("M' U2 M U2");
            } else {
                updateThird("U2 M' U2 M");
            }
        }
    }

    private String alignMoves(int moves) {
        switch(moves) {
            case 0: return "";
            case 1: return "U";
            case 2: return "U2";
            case 3: return "U'";
            default: return "alignment error";
        }
    }

    protected void alignM() {
        look++;
        if(compareCells(0, 0, 5, 4)) {
            updateThird("M2");
        } else if(compareCells(0, 0, 1, 4)) {
            updateThird("M'");
        } else if(compareCells(0, 0, 3, 4)) {
            updateThird("M");
        } else {
            look--;
        }
    }
}
