import javafx.scene.text.Font;

import java.util.Comparator;

/**
 * Created by Rachel on 3/3/2017.
 */
public class Span implements Comparable<Span>{
    private int startPos;
    private int endPos;
    private Font font;
    private String fontName;



    public Span(int start, int end, String name){
        super();
        this.startPos = start;
        this.endPos = end;
        this.fontName = name;


    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }


    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public Font getFont() {

        return font;
    }

    public int getEndPos() {
        return endPos;
    }



    public int getStartPos(){
        return this.startPos;
    }

    @Override
    public int compareTo(Span o2) {
        return this.getStartPos() - o2.getStartPos();
    }
}
