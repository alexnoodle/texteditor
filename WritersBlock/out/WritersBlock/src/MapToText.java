import java.io.*;
import java.util.*;

/**
 * Created by Brendan on 2/22/17.
 */
public class MapToText {

    public MapToText() {
    }

    // Prints next character with no basis text for generation.
    public String printChar(Map<String, ArrayList<Character>> map, int depth) {
        return printChar(map, depth, "");
    }

    // Prints next character based on basis of previous text written.
    public String printChar(Map<String, ArrayList<Character>> map, int depth, String seed) {
        // A buffer piece is needed in the case of short or empty strings to strengthen generation
        seed = ". " + seed;
        Random rand = new Random();
        String partial = "";
        //ramp up (When the beginning is not long enough for the generation depth, start low and ramp up)
        if (seed.length() < depth) {
            partial = seed;
        } else {
            partial = seed.substring(seed.length() - depth);
        }
        //ramp down (When the latest substring matches no entries of the map, lower the depth until it does match)
        if (map.get(partial) == null) {
            boolean success = false;
            while (success == false) {
                partial = partial.substring(1);
                if (map.get(partial) == null) {
                    success = false;
                } else {
                    success = true;
                }
            }
        }
        ArrayList<Character> candidates = map.get(partial);
        int chosen = rand.nextInt(candidates.size());
        seed = seed + candidates.get(chosen);
        seed = seed.substring(2);
        return seed;
    }

    // Deserializes serialized map file for use. Not implemented in this project.
    /*public Map<String, ArrayList<Character>> deserializeMap(String mapFile) {
        Map<String, ArrayList<Character>> textMap;

        try {
            String prePath = new File(".").getCanonicalPath();
            FileInputStream fis = new FileInputStream(prePath + "/maps/" +
                    mapFile + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            textMap = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return textMap;
    }*/
}
