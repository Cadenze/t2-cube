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
            System.out.print("Output file name: ");
            String output = kb.nextLine();
            stats(input, output);
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
     */
    public static void stats(String file, String output) throws FileNotFoundException {
        try {
            File csv = new File(output);
            if (!csv.createNewFile()) {
                System.out.println("File already exists.");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
        }

        try (
            Scanner explorer = new Scanner(new BufferedReader(new FileReader(file)));
            BufferedWriter pen = new BufferedWriter(new FileWriter(output));
        ) {
            while(explorer.hasNextLine()) {
                String sequence = explorer.nextLine();
                CFOP cube = new CFOP(sequence);
                boolean solved = cube.checkSolved();
                int qtm = cube.getQTM();
                int htm = cube.getHTM();
                int stm = cube.getSTM();
                pen.write(sequence + ", " + solved + ", " + qtm + ", " + htm + ", " + stm);
                pen.newLine();
            }
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
