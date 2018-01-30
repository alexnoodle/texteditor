import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Brendan on 2/24/17.
 */
public class WritersBlockTester {

    public WritersBlockTester() {}

    public static void main(String[] args) throws IOException {
        TextToMap textToMap = new TextToMap();
        MapToText mapToText = new MapToText();

        Scanner userin = new Scanner(System.in);
        System.out.println("\nEnter the names of the text files: ");
        String[] files = userin.next().split(",");
        System.out.println("\nEnter desired depth: ");
        int howDeep = Integer.parseInt(userin.next());
        System.out.println("\nEnter desired length: ");
        int howLong = Integer.parseInt(userin.next());
        System.out.println("\nRetrieving map...");

        Map newMap = null;
        for (int i = 0; i < files.length; i++) {
            newMap = textToMap.generateMap(files[i], howDeep, newMap);
        }

        //unknown why following line is necessary
        userin.nextLine();
        String seed = null;
        while (true) {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nDepth: " + howDeep);
            System.out.println(howLong + " characters generated.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            String finalGen = "";
            for (int i=0; i<howLong; i++) {
                finalGen = mapToText.printChar(newMap, howDeep, seed);
            }
            System.out.println(finalGen);
            System.out.println("\nType 'write -FILENAME-' to write this text to a text file.");
            String request = userin.nextLine();
            String[] parts = request.split(" ");
            String taskLine = parts[0];
            if (taskLine.equals("write")) {
                String outName = parts[1];
                mapToText.writeToTextFile(outName, finalGen);
                System.out.println("\nFile Written!\n");
                userin.nextLine();
            } else if (taskLine.equals("nd")) {
                howDeep = Integer.parseInt(parts[1]);
                System.out.println("\nRetrieving map...");
                newMap = null;
                for (int i = 0; i < files.length; i++) {
                    newMap = textToMap.generateMap(files[i], howDeep, newMap);
                }
            } else if (taskLine.equals("nl")) {
                howLong = Integer.parseInt(parts[1]);
            } else if (taskLine.equals("complete")) {
                seed = ". Sentence seed: 'Can you '\nCan you ";
            }
        }
    }

}
