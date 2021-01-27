import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Scrambler {
    public static void main(String[] args) {
        generate(300);
    }

    public static void generate(int lines) {
        generate(lines, "scrambles.txt");
    }

    public static void generate(int lines, String fileName) {
        try {
            File scrambles = new File(fileName);
            if (!scrambles.createNewFile()) {
                System.out.println("File already exists.");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
        }

        try (
            BufferedWriter pen = new BufferedWriter(new FileWriter(fileName));
        ) {
            for (int i = 0; i < lines; i++) {
                pen.write(scramble());
                pen.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String scramble() {
        Random r = new Random();
        String[] cells = new String[r.nextInt(10) + 18];
        cells[0] = translate(r.nextInt(6), r.nextInt(3));
        do {
            cells[1] = translate(r.nextInt(6), r.nextInt(3));
        } while(identical(cells[0], cells[1]));
        for(int i = 2; i < cells.length; i++) {
            do {
                cells[i] = translate(r.nextInt(6), r.nextInt(3));
            } while(identical(cells[i-1], cells[i]) || opposite(cells[i-2], cells[i-1], cells[i]));
        }
        StringBuilder result = new StringBuilder();
        int counter = 0;
        while(true) {
            result.append(cells[counter]);
            counter++;
            if(counter == cells.length) {
                break;
            }
            result.append(" ");
        }
        return result.toString();
    }

    public static String translate(int face, int move) {
        String sequence = "";
        switch(face) {
            case 0: sequence += "U"; break;
            case 1: sequence += "D"; break;
            case 2: sequence += "F"; break;
            case 3: sequence += "B"; break;
            case 4: sequence += "L"; break;
            case 5: sequence += "R"; break;
            default: return sequence;
        }

        if(move == 0) {
            return sequence;
        } else if(move == 1) {
            return sequence + 2;
        } else {
            return sequence + "'";
        }
    }

    protected static boolean identical(String move1, String move2) {
        return move1.substring(0,1).equals(move2.substring(0,1));
    }

    protected static boolean opposite(String move1, String move2, String move3) {
        return identical(move1, move3) && opposite(move2, move3);
    }

    private static boolean opposite(String move1, String move2) {
        int one = numbering(move1);
        int two = numbering(move2);
        return Math.abs(one - two) == 3;
    }

    private static int numbering(String face) {
        switch(face) {
            case "U": return 0;
            case "D": return 3;
            case "F": return 1;
            case "B": return 4;
            case "L": return 2;
            case "R": return 5;
            default: return 10;
        }
    }
}
