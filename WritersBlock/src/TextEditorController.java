import javafx.stage.FileChooser;

import java.io.*;
import java.util.Scanner;

public class TextEditorController {
    private static TextGenerator james;
    private static TextGenerator tolkien;
    private static TextGenerator rowling;
    private static TextGenerator trump;
    private static TextGenerator ownGenerator;
    static TextGenerator activeGenerator;
    private static TextGenerator namesGenerator;
    private static TextGenerator sciwordsGenerator;

    static int mode;
    static final int WORDDUET = 1;
    private static final int FREE = 0;
    static String sps = File.separator;

    // I'm sure we'll get around to actually using this design pattern...
    public static void makeMap(String Textfile){

    }

    public static String open(){
        FileChooser fc = new FileChooser();
        File fileToOpen = fc.showOpenDialog(null);
        String openFileName = fileToOpen.getAbsolutePath();
        String newString = "";
        try (Scanner scanner = new Scanner(new File(openFileName))) {

            while (scanner.hasNextLine())
                newString += scanner.nextLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newString;
    }
    public static void save(String text) {
        FileChooser fc = new FileChooser();
        File saveFile = fc.showSaveDialog(null);
        String saveFileName = saveFile.getAbsolutePath();
        try (FileOutputStream fos = new FileOutputStream(saveFileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(text.getBytes());
            bos.flush();

        } catch (Exception ignored) {

        }
    }

    public static String generateName(){
        return namesGenerator.printWord();
    }

    public static String generateSciWord(){
        return sciwordsGenerator.printWord();
    }

    public static void useOwnText(){
        FileChooser fc = new FileChooser();
        File fileToOpen = fc.showOpenDialog(null);
        String openFileName = fileToOpen.getAbsolutePath();
        try {
            ownGenerator = new TextGenerator(openFileName, 15);
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeGenerator = ownGenerator;
    }


    public static String autoComplete(String existingSentence){
        return activeGenerator.completeSentence(existingSentence);
    }

    public static void jrrTolkein(){
        if (tolkien == null) {
            try {
                String prePath = new File(".").getCanonicalPath();
                tolkien = new TextGenerator(prePath + sps+"text"+sps + "LOTR.txt", 15);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activeGenerator = tolkien;
    }

    public static void elJames(){
        if (james == null) {
            try {
                String prePath = new File(".").getCanonicalPath();
                james = new TextGenerator(prePath + sps+"text"+sps + "fifty.txt", 15);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activeGenerator = james;
    }

    public static void jkRowling(){
        if (rowling == null) {
            try {
                String prePath = new File(".").getCanonicalPath();
                rowling = new TextGenerator(prePath + sps+"text"+sps + "potter.txt", 15);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activeGenerator = rowling;
    }

    public static void dTrump(){
        if (trump == null) {
            try {
                String prePath = new File(".").getCanonicalPath();
                trump = new TextGenerator(prePath + sps+"text"+sps + "trump.txt", 15);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activeGenerator = trump;
    }

    public static void wordDuet(){
        if (TextEditorController.mode == WORDDUET) {
            mode = FREE;
        } else {
            mode = WORDDUET;
        }
    }

    public static void main(String args[]) throws IOException {
        String prePath = new File(".").getCanonicalPath();
        namesGenerator = new TextGenerator(prePath + sps+"text"+sps + "names.txt", 2);
        sciwordsGenerator = new TextGenerator(prePath + sps+"text"+sps + "sciwords.txt", 2);
        mode = FREE;
        EditorView.main(args);
    }

}