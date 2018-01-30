import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by moorer2 on 2/28/17.
 */
public class TextObject {

    private List<Span> spanList;
    private String text = "";
    private String HTML;


    public TextObject(){
        this.spanList = new ArrayList<Span>();
        this.spanList.add(new Span(0, 0, "Times New Roman"));
    }

    public List<Span> useSpans(){

        String htmlSubText = "";
        Collections.sort(this.spanList);
        /*int prevStart = 0;
        int prevEnd = 0;
        for (Span span : spanList) {
            if (span.getStartPos() > 0){
                String one = new String(this.text.substring(prevEnd, span.getStartPos() - 1));
                String two = new String(this.text.substring(span.getStartPos(), span.getEndPos() - 1));
                String three = new String(this.text.substring(span.getEndPos()));

            }
            String one = new String(this.text.substring(0, span.getStartPos() - 1));
            String two = new String(this.text.substring(span.getStartPos(), span.getEndPos() - 1));
            String three = new String(this.text.substring(span.getEndPos()));
    }*/
        return spanList;

    }



    public void update(String newText){
        System.out.println("");
        System.out.print("old text: ");
        System.out.println(this.text);
        System.out.print("new text: ");
        System.out.println(newText);
        //System.out.println(newText.length());
        char[] newString = newText.toCharArray(); //
        char[] oldString = this.text.toCharArray();
        if (newText.length() - text.length() == 1) {
            for (int i = 0; i < newText.length(); i++) {

                if (this.text.length() == 0) {
                    updateSpansPositiveChange(0);
                    break;
                } else if (i == newText.length() - 1) {
                    updateSpansPositiveChange(newText.length() - 1);

                } else {

                    if (newString[i] != oldString[i]) {
                        updateSpansPositiveChange(i);
                        break;
                    }
                }

            }

        } else if (text.length() - newText.length() == 1) {

            for (int i = 0; i < text.length(); i++) {

                if (newText.length() == 0) {
                    updateSpansMinusOne(0);
                    break;
                } else if (i == text.length() - 1) {
                    updateSpansMinusOne(text.length() - 1);

                } else {

                    if (newString[i] != oldString[i]) {
                        updateSpansMinusOne(i);
                        break;
                    }
                }

            }

        } else if (text.length() - newText.length() > 1){


            deleteText(newText);
        } else if (newText.length() - text.length() > 1){
            insertText(newText);
        }
        this.text = newText;
        System.out.println(newText);
        //System.out.println("New text after update:");
        //System.out.println(this.text);
        //System.out.println(newText);
    }

    public void deleteText(String newText){
        //System.out.println("delete text");
        int lengthChange = text.length() - newText.length();
        char[] newString = newText.toCharArray(); //
        char[] oldString = this.text.toCharArray();
        for (int i = 0; i < text.length(); i++) {

            if (newText.length() == 0) {
                updateSpansDeletion(0, 0);
                break;
            } else if (i == newText.length()) {
                updateSpansDeletion(i, text.length());
                break;

            } else {

                if (newString[i] != oldString[i]) {
                    updateSpansDeletion(i, i + lengthChange);
                    break;
                }
            }

        }
        System.out.println("finished deleteTExt");

    }


    public void insertText(String newText){
        //System.out.println("insert text");
        int lengthChange = newText.length() - text.length();
        char[] newString = newText.toCharArray(); //
        char[] oldString = this.text.toCharArray();
        for (int i = 0; i < newText.length(); i++) {

            if (text.length() == 0) {
                updateSpansLargeInsertion(0, lengthChange);
                break;
            } else if (i == text.length()) {
                updateSpansLargeInsertion(i, newText.length());
                break;

            } else {

                if (newString[i] != oldString[i]) {
                    updateSpansLargeInsertion(i, i + lengthChange);
                    break;
                }
            }

        }
        System.out.println("finished isertText");

    }



    public void updateSpansDeletion(int start, int end){
        int change = end - start;
        System.out.println("update spans neg");
        List<Span> removeSpans = new ArrayList<>();
        for (Span span : spanList){
            if (span.getStartPos()< start && span.getEndPos() > end){
                span.setEndPos(span.getEndPos() - change);
            }
            if (span.getStartPos() >= start && span.getEndPos() <= end){
                removeSpans.add(span);
            }
            if (span.getStartPos() >= start && end >= span.getStartPos() && end <= span.getEndPos()){
                int newEnd = span.getEndPos() - change;
                span.setStartPos(start);
                span.setEndPos(newEnd);
            }
            if (span.getStartPos() < start && span.getEndPos() >= start && span.getEndPos() <= end){
                span.setEndPos(start);
            }
            if (span.getStartPos() > end && span.getEndPos() < end){
                span.setStartPos(span.getStartPos() - change);
                span.setEndPos(span.getEndPos() - change);
            }



            System.out.print("Span: ");
            System.out.print(span.getStartPos());
            System.out.print(span.getEndPos());

        }
        for (Span removedSpan : removeSpans){
            spanList.remove(removedSpan);
        }
    }



    public void updateSpansLargeInsertion(int start, int end){
        int change = end - start;
        System.out.println("update spans pos large");
        System.out.println(start);
        System.out.println(end);

        for (Span span : spanList){

            if (span.getStartPos() > start){
                span.setStartPos(span.getStartPos() + change);
                span.setEndPos(span.getEndPos() + change);
            }
            if (span.getStartPos() == start){
                span.setEndPos(span.getEndPos() + change);
            }
            if (span.getStartPos() < start && span.getEndPos() > start){
                span.setEndPos(span.getEndPos() + change);
            }
            if (span.getEndPos() == start){
                span.setEndPos(span.getEndPos() + change);
            }

            System.out.print("Span: ");
            System.out.print(span.getStartPos());
            System.out.print(span.getEndPos());

        }

    }







    public void newFont(int start, int end, String name){
        System.out.println("");
        System.out.println("");
        System.out.println(start);
        System.out.println(end);
        Span newSpan = new Span(start, end, name);
        List<Span> addedSpans = new ArrayList<>();
        List<Span> removedSpans = new ArrayList<>();
        List<Span> newSpanList = new ArrayList<>();
        System.out.println("Spans we visit");
        for (Span existingSpan : spanList){
            System.out.print(existingSpan.getStartPos());
            System.out.println(existingSpan.getEndPos());
            if (newSpan.getStartPos()<=existingSpan.getStartPos() && newSpan.getEndPos()>=existingSpan.getEndPos()){
                removedSpans.add(existingSpan);
                System.out.println("Case 1");
                System.out.print(existingSpan.getStartPos());
                System.out.println(existingSpan.getEndPos());
                //spanList.add(newSpan);

            }
            else if (existingSpan.getStartPos()<newSpan.getStartPos() &&
                    existingSpan.getEndPos() > newSpan.getEndPos()){
                System.out.println("Case 2");
                System.out.print(existingSpan.getStartPos());
                System.out.println(existingSpan.getEndPos());
                Span firstHalf = new Span(existingSpan.getStartPos(), newSpan.getStartPos(), existingSpan.getFontName());
                Span secondHalf = new Span(newSpan.getEndPos(), existingSpan.getEndPos(), existingSpan.getFontName());
                addedSpans.add(firstHalf);
                addedSpans.add(secondHalf);
                newSpanList.add(firstHalf);
                newSpanList.add(secondHalf);
                removedSpans.add(existingSpan);
            }

            else if (newSpan.getEndPos() < existingSpan.getEndPos() &&
                    newSpan.getStartPos() <= existingSpan.getStartPos() &&
                    newSpan.getEndPos() > existingSpan.getStartPos()){

                System.out.println("Case 3");
                System.out.print(existingSpan.getStartPos());
                System.out.println(existingSpan.getEndPos());
                existingSpan.setStartPos(newSpan.getEndPos());
                newSpanList.add(new Span(newSpan.getEndPos(), existingSpan.getEndPos(), existingSpan.getFontName()));
            }

            else if (newSpan.getStartPos() > existingSpan.getStartPos() &&
                    newSpan.getEndPos() >= existingSpan.getEndPos() &&
                    newSpan.getStartPos() < existingSpan.getEndPos()){

                System.out.println("Case 4");
                System.out.print(existingSpan.getStartPos());
                System.out.println(existingSpan.getEndPos());
                existingSpan.setEndPos(newSpan.getStartPos());
                newSpanList.add(new Span(existingSpan.getStartPos(), newSpan.getStartPos(), existingSpan.getFontName()));

            } else {

                System.out.println("Case 5");
                System.out.print(existingSpan.getStartPos());
                System.out.println(existingSpan.getEndPos());
                newSpanList.add(existingSpan);
            }



        }
        newSpanList.add(newSpan);
        spanList.add(newSpan);
        for (Span addSpan : addedSpans){
            spanList.add(addSpan);
        }
        for (Span removeSpan : removedSpans){
            spanList.remove(removeSpan);
            System.out.println("Removed span:");
            System.out.print(removeSpan.getStartPos());
            System.out.println(removeSpan.getEndPos());
        }

        //spanList = newSpanList;
        //System.out.println(this.text);
    }


    public void updateSpansPositiveChange(int change){
        System.out.println("update spans");
        for (Span span : spanList){
            if (span.getStartPos()> change){
                span.setStartPos(span.getStartPos() + 1);
            }
            if (span.getEndPos()>= change){
                span.setEndPos(span.getEndPos() + 1);
            }
            System.out.print("Span: ");
            System.out.print(span.getStartPos());
            System.out.print(span.getEndPos());

        }
    }

    public void updateSpansMinusOne(int change){
        System.out.println("update spans neg");
        for (Span span : spanList){
            if (span.getStartPos()> change){
                span.setStartPos(span.getStartPos() - 1);
            }
            if (span.getEndPos()>= change){
                span.setEndPos(span.getEndPos() - 1);
            }
            System.out.print("Span: ");
            System.out.print(span.getStartPos());
            System.out.print(span.getEndPos());

        }
    }

    public List<Span> getSpanList() {
        return spanList;
    }

    public void setSpanList(List<Span> spanList) {
        this.spanList = spanList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHTML() {
        return HTML;
    }

    public void setHTML(String HTML) {
        this.HTML = HTML;
    }
}
