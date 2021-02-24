import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MultipleSolves {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner kb = new Scanner(System.in);
        System.out.print("Input scramble or text file: ");
        String input = kb.nextLine();
        if (input.contains(".")) {
            System.out.print("Method: ");
            String method = kb.nextLine();
            System.out.print("Output file name: ");
            String output = kb.nextLine();
            stats(input, output, method);
        } else {
            scramble(input);
        }
        kb.close();
    }

    /**
     * Retrieves the statistics on a single scramble.
     * @param sequence Singmaster notation for multiple moves
     */
    public static void scramble(String sequence) {
        Solve cube = new Solve(sequence);
        System.out.println("Solved: " + cube.checkSolved());
        System.out.println("QTM: " + cube.getQTM());
        System.out.println("HTM: " + cube.getHTM());
        System.out.println("STM: " + cube.getSTM());
    }

    /**
     * Retrieves the statistics on multiple scrambles.
     * @param file .txt files containing a scramble per line
     * @param output .csv file containing a solve per line
     * @param type Method to solve with
     */
    public static void stats(String file, String output, String type) throws FileNotFoundException {
        final String ERROR = "An error occured.";
        try {
            File csv = new File(output);
            if (!csv.createNewFile()) {
                System.out.println("File already exists.");
                return;
            }
        } catch (IOException e) {
            System.out.println(ERROR);
            e.printStackTrace();
            return;
        }

        final String header = "Scramble, Solved?, QTM, HTM, STM, AHTM, ASTM";
        switch(type) {
            case "Beginners": case "Beginner": case "beginners": case "beginner":
            try (
                Scanner explorer = new Scanner(new BufferedReader(new FileReader(file)));
                BufferedWriter pen = new BufferedWriter(new FileWriter(output));
            ) {
                pen.write(header);
                pen.newLine();
                while(explorer.hasNextLine()) {
                    String sequence = explorer.nextLine();
                    Solve cube = new Solve(sequence);
                    boolean solved = cube.checkSolved();
                    int qtm = cube.getQTM();
                    int htm = cube.getHTM();
                    int stm = cube.getSTM();
                    int ahtm = cube.getAHTM();
                    int astm = cube.getASTM();
                    pen.write(sequence + ", " + solved + ", " + qtm + ", " + htm + ", " + stm + ", " + ahtm + ", " + astm);
                    pen.newLine();
                }
            } catch(IOException e) {
                System.out.println(ERROR);
                e.printStackTrace();
            } break;

            case "CFOP": case "cfop": case "Fridrich": case "fridrich":
            try (
                Scanner explorer = new Scanner(new BufferedReader(new FileReader(file)));
                BufferedWriter pen = new BufferedWriter(new FileWriter(output));
            ) {
                pen.write(header);
                pen.newLine();
                while(explorer.hasNextLine()) {
                    String sequence = explorer.nextLine();
                    CFOP cube = new CFOP(sequence);
                    boolean solved = cube.checkSolved();
                    int qtm = cube.getQTM();
                    int htm = cube.getHTM();
                    int stm = cube.getSTM();
                    int ahtm = cube.getAHTM();
                    int astm = cube.getASTM();
                    pen.write(sequence + ", " + solved + ", " + qtm + ", " + htm + ", " + stm + ", " + ahtm + ", " + astm);
                    pen.newLine();
                }
            } catch(IOException e) {
                System.out.println(ERROR);
                e.printStackTrace();
            } break;

            case "Roux": case "roux":
            try (
                Scanner explorer = new Scanner(new BufferedReader(new FileReader(file)));
                BufferedWriter pen = new BufferedWriter(new FileWriter(output));
            ) {
                pen.write(header);
                pen.newLine();
                while(explorer.hasNextLine()) {
                    String sequence = explorer.nextLine();
                    AdvRoux cube = new AdvRoux(sequence);
                    boolean solved = cube.checkSolved();
                    int qtm = cube.getQTM();
                    int htm = cube.getHTM();
                    int stm = cube.getSTM();
                    int ahtm = cube.getAHTM();
                    int astm = cube.getASTM();
                    pen.write(sequence + ", " + solved + ", " + qtm + ", " + htm + ", " + stm + ", " + ahtm + ", " + astm);
                    pen.newLine();
                }
            } catch(IOException e) {
                System.out.println(ERROR);
                e.printStackTrace();
            } break;

            default: System.out.println("Method currently does not exist.");
        }
    }
}
