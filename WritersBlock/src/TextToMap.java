import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brendan on 2/22/17.
 */

public class TextToMap {

    public TextToMap() {
    }

    // Based on file path, gets string which is a compound of all lines in that text file.
    public String readText(String file) {
        String line;
        String compoundText = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Reads each line of the file.
            while ((line = bufferedReader.readLine()) != null) {
                compoundText = compoundText + " " + line;
            }

            // Closes file.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + file + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + file + "'");
        }

        if ((compoundText.length()!=0)&&(!(compoundText.substring(compoundText.length()-1).equals(".")))) {
            compoundText = compoundText+".";
        }
        return compoundText;
    }

    // Generates map based on string of text (intended to be a compound of a text file).
    public Map<String, ArrayList<Character>> generateMap(String file, int depth) throws IOException {
        return generateMap(file,depth,null);
    }

    public Map<String, ArrayList<Character>> generateMap(String file, int depth, Map<String,
            ArrayList<Character>> textMap) throws IOException {

        if (textMap == null) {
            textMap = new HashMap<String, ArrayList<Character>>();
        }

        String text = readText(file);

        int curDepth = depth;

        //Creates entries at each depth of prediction. Each will create different length keys.
        while (curDepth > 0) {
            int position = 0;
            //Runs through the entire text, creating keys.
            while ((position + curDepth) < text.length()) {
                String key = text.substring(position, position + curDepth);
                char val = text.charAt(position + curDepth);

                //if the key is not already there.
                if (textMap.get(key) == null) {
                    //create the key and value
                    ArrayList<Character> newPredictionArray = new ArrayList<Character>();
                    newPredictionArray.add(val);
                    textMap.put(key, newPredictionArray);
                } else {
                    //the key was already there
                    ArrayList<Character> predictionArray = textMap.get(key);
                    predictionArray.add(val);
                    textMap.put(key, predictionArray);
                }
                position++;
            }
            curDepth--;
        }
        return textMap;
    }
}