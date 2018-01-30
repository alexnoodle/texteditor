import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Brendan on 3/2/17.
 */
public class TextGenerator {
    private Map<String, ArrayList<Character>> textMap;
    private TextToMap textToMap;
    private MapToText mapToText;
    private int depth;
    private String file;

    // Creates a new generator with a given generation depth and from a given text file.
    public TextGenerator(String textFile, int inDepth) throws IOException {
        textToMap = new TextToMap();
        mapToText = new MapToText();
        depth = inDepth;
        file = textFile;
        textMap = textToMap.generateMap(textFile, depth);
    }

    public void setDepth(int newDepth) throws IOException {
        if (newDepth<16) {
            depth = newDepth;
            textMap = textToMap.generateMap(file,depth);
        }
        depth = newDepth;
    }

    // Complete a sentence based on the text typed so far.
    public String completeSentence(String input) {
        String output = input;
        while ((!(output.substring(output.length() - 1).equals("."))) &&
                (!(output.substring(output.length() - 1).equals("?"))) &&
                (!(output.substring(output.length() - 1).equals("!")))) {
            output = mapToText.printChar(textMap, depth, output);
        }
        return output;
    }

    //Print a word to follow the text typed so far.
    public String printWord(String input) {
        String output = input;
        boolean writingWord = true;
        while (writingWord) {
            output = mapToText.printChar(textMap, depth, output);
            if (output.substring(output.length()-1).equals(" ")) {
                writingWord = false;
            }
        }
        return output;
    }

    public String printWord() {
        return printWord("");
    }

    // Print a character to follow the text typed so far.
    public String printChar(String input) {
        String output = input;
        output = mapToText.printChar(textMap, depth, output);
        return output;
    }

    // Fix the last character typed.
    public String fixLastChar(String input) {
        String output = input.substring(0, input.length() - 1);
        output = printChar(output);
        return output;
    }
}
