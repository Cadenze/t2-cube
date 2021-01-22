import java.io.BufferedReader;
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
        kb.close();
        if (input.contains(".")) {
            stats(input);
        } else {
            scramble(input);
        }
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
    public static void stats(String file) throws FileNotFoundException {
        try {
            File csv = new File("statistics.csv");
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
            FileWriter pen = new FileWriter("statistics.csv");
        ) {
            while(explorer.hasNextLine()) {
                String sequence = explorer.nextLine();
                Solve cube = new Solve(sequence);
                boolean solved = cube.checkSolved();
                int qtm = cube.getQTM();
                int htm = cube.getHTM();
                int stm = cube.getSTM();
                pen.write(sequence + ", " + solved + ", " + qtm + ", " + htm + ", " + stm);
            }
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
