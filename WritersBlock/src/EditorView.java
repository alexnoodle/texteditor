/**
 * Created by moorer2 on 2/28/17.
 */

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.color.CMMException;
import java.util.Scanner;


public class EditorView extends Application{
    private static final double SCENE_WIDTH = 900;
    private static final double SCENE_HEIGHT = 500;
    private static FlowPane pane;
    private int i = 0;
    private TextObject textObject;
    private WebEngine engine;
    private String currentFont = "Times New Roman";
    private TextArea status = new TextArea("");
    private TextFlow textFlow;
    private BorderPane root;
    private boolean authorIsChosen = false;

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();
        TabPane tabPane = addTabs();
        //HBox menuPane = addMenus();
        Node statusPane = addStatus();
        final HTMLEditor htmlEditor = new HTMLEditor();
        textObject = new TextObject();
        Text text1 = new Text("Big italic red text");
        text1.setFill(Color.RED);
        text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
        Text text2 = new Text(" little bold blue text");
        text2.setFill(Color.BLUE);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
        textFlow = new TextFlow(text1, text2);

        //"  <link rel=\"stylesheet\" href=\"textStyle.css\">\n"
        WebView textView = new WebView();
        engine = textView.getEngine();

        engine.setJavaScriptEnabled(true);
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            JSObject jso = (JSObject) engine.executeScript("window");
                            jso.setMember("java", new JavaApp());
                        }

                    }
                });

        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            JSObject jso = (JSObject) engine.executeScript("window");
                            jso.setMember("javaMethod", new JavaApp2());
                        }

                    }
                });



        engine.loadContent("<html><head><script>\n" +
                "    function onInput() {\n" +
                "     textArea = document.getElementById(\"mainText\");\n" +
                "        return textArea.value;\n" +
                "    }\n" +
                "    </script><style media=\"screen\" type=\"text/css\">\n" +
                "textarea {\n" +
                "    height: 200px;\n" +
                "    width: 200px;\n" +
                "}\n" +
                "</style>\n" +
                "    <script>\n" +
                "            function getSelStart(){\n" +
                "                textArea = document.getElementById(\"mainText\");\n" +
                "                return textArea.selectionStart;\n" +
                "            }\n" +
                "        </script>\n" +
                "        <script>\n" +
                "            function getSelEnd(){\n" +
                "                textArea = document.getElementById(\"mainText\");\n" +
                "                return textArea.selectionEnd;\n" +
                "            }\n" +
                "        </script>\n" +
                "        </head>\n" +
                "<body>\n" +
                "<div id=\"left\" style=\"float: left;\"></div>\n" +
                "<form>\n" +
                "        <textarea id=\"mainText\" style=\"width: 375px; height: 405\" oninput=\"java.exit(onInput());\"></textarea>\n" +
                "    </form></div>\n" +
                "</body>\n" +
                "</html>");


        // root.setTop(menuPane);
        //root.setLeft(tabPane);
        root.setTop(tabPane);
        //root.setCenter(statusPane);
        //root.setCenter(htmlEditor);
        root.setCenter(textFlow);
        textView.setPrefWidth(400);
        root.setLeft(textView);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("SPEW");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                status.setPrefWidth((double) newSceneWidth);


            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {

                status.setPrefHeight((double) newSceneHeight);

            }
        });

    }
    public class JavaApp {

        public void exit(String newString) {
            //System.out.print("got here");
            Boolean displayText = false;
            //String test = (String) engine.executeScript("onInput();");
            String newText = newString;
            System.out.println("last char");
            //System.out.println(newString.substring(newString.length()-2, newString.length() - 1));
            //if (!(newString.substring(newString.length() - 2).equals("  ")))
            if (newString.length() > 1) {
                if (!(newString.substring(newString.length() - 2).equals("  "))) {
                    if ((TextEditorController.activeGenerator != null) &&
                            (TextEditorController.mode == TextEditorController.WORDDUET) &&
                            (newString.substring(newString.length() - 1) == " ")) {

                        System.out.println("Detected space");
                        newText = TextEditorController.activeGenerator.printWord(newString);
                        displayText = true;
                        //status.setText(newText);
                        //status.positionCaret(newText.length());
                    }
                }
            }
            //}*/


            textObject.update(newText);
            if (displayText){
                setHTML(newText);
                display();
            }


            //System.out.println(test);

        }
    }
    public class JavaApp2 {

        public void delete(String newString) {
            System.out.print("got here");
            //String test = (String) engine.executeScript("onInput();");
            //textObject.update(newString);

            //System.out.println(test);

        }
    }
    //private void update(String updateString, int selStart, int selEnd){
    //textObject.update(updateString);
    //}

    private TabPane addTabs() {

        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        tabPane.getTabs().add(tab);
        Tab tab2 = new Tab();
        tabPane.getTabs().add(tab2);
        Tab tab3 = new Tab();
        tabPane.getTabs().add(tab3);
        //tab.setContent(content.getRoot());
        tab.setText("File");
        tab2.setText("Format");
        tab3.setText("AutoComplete");

        HBox formatMenu = addFormatMenu();
        HBox fileMenu = addFileMenu();

        HBox autoMenu = addAutoMenu();
        tab3.setContent(autoMenu);
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tab);

        tab2.setContent(formatMenu);
        tab.setContent(fileMenu);
        tabPane.setStyle("-fx-background-color: #d3ffff");

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        return tabPane;

    }
    private HBox addAutoMenu(){
        HBox autoPane = new HBox();
        Menu autoCompleteMenu = new Menu("AutoComplete");
        //CheckMenuItem wordDuetMode = new CheckMenuItem("Word Duet");

        ToggleButton wordDuetMode = new ToggleButton("Word Duet");
        wordDuetMode.setSelected(false);

        Menu chooseAuthor = new Menu("Choose Author");
        CheckMenuItem jkRowling = new CheckMenuItem("J.K. Rowling");
        CheckMenuItem elJames = new CheckMenuItem("E.L. James");
        CheckMenuItem jrrTolkien = new CheckMenuItem("J.R.R. Tolkien");
        CheckMenuItem dTrump = new CheckMenuItem("Donald Trump");

        //MenuItem completeSentence = new MenuItem("Complete Sentence");
        Button completeSentence = new Button("Complete Sentence");
        completeSentence.setDisable(true);
        CheckMenuItem useOwnTextFile = new CheckMenuItem("Pick Your Own");

        Menu chooseDepth = new Menu("Coherence");
        CheckMenuItem veryLowDepth = new CheckMenuItem("Very Low");
        CheckMenuItem lowDepth = new CheckMenuItem("Low");
        CheckMenuItem moderateDepth = new CheckMenuItem("Moderate");
        CheckMenuItem highDepth = new CheckMenuItem("High");
        highDepth.setSelected(true);
        chooseDepth.getItems().addAll(highDepth, moderateDepth, lowDepth, veryLowDepth);

        Menu generateSpecialWords = new Menu("Special Words");
        MenuItem generateName = new MenuItem("Name");
        MenuItem generateSciWord = new MenuItem("Scientific Word");
        generateSpecialWords.getItems().addAll(generateName, generateSciWord);

        generateName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(textObject.getText());
                String newName = textObject.getText()+TextEditorController.generateName();
                System.out.println(newName);
                textObject.update(newName);
                setHTML(newName);
                display();
                //status.positionCaret(status.getText().length());


            }
        });

        generateSciWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //status.setText(TextEditorController.generateSciWord(status.getText()));
                //status.positionCaret(status.getText().length());
                String newName = textObject.getText()+TextEditorController.generateSciWord();
                System.out.println(newName);
                textObject.update(newName);
                setHTML(newName);
                display();
            }
        });

        highDepth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (highDepth.isSelected()) {
                    try {
                        TextEditorController.activeGenerator.setDepth(15);
                        moderateDepth.setSelected(false);
                        lowDepth.setSelected(false);
                        veryLowDepth.setSelected(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        moderateDepth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (moderateDepth.isSelected()) {
                    try {
                        TextEditorController.activeGenerator.setDepth(10);
                        highDepth.setSelected(false);
                        lowDepth.setSelected(false);
                        veryLowDepth.setSelected(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        lowDepth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (lowDepth.isSelected()) {
                    try {
                        TextEditorController.activeGenerator.setDepth(5);
                        highDepth.setSelected(false);
                        moderateDepth.setSelected(false);
                        veryLowDepth.setSelected(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        veryLowDepth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (veryLowDepth.isSelected()) {
                    try {
                        TextEditorController.activeGenerator.setDepth(3);
                        highDepth.setSelected(false);
                        moderateDepth.setSelected(false);
                        lowDepth.setSelected(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /* Calls a method in textGenerator to get the end of a sentence */
        completeSentence.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //String newText = TextEditorController.autoComplete(status.getText());
                //status.setText(newText);
                //status.positionCaret(newText.length());

                String newName = TextEditorController.autoComplete(textObject.getText());
                System.out.println(newName);
                textObject.update(newName);
                setHTML(newName);
                display();


            }
        });

        /* This sets a given text file to be a new textgenerator by getting the absolute path
         * from the fileChooser and gives it to  */
        useOwnTextFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextEditorController.useOwnText();
                if (useOwnTextFile.isSelected()) {
                    useOwnTextFile.setSelected(true);
                }
                jrrTolkien.setSelected(false);
                elJames.setSelected(false);
                dTrump.setSelected(false);
                jkRowling.setSelected(false);
                if (!authorIsChosen) {
                    //autoCompleteMenu.getItems().addAll(chooseAuthor, generateSpecialWords, chooseDepth, wordDuetMode, completeSentence);
                    //authorIsChosen = true;
                    completeSentence.setDisable(false);
                    wordDuetMode.setSelected(true);
                }
            }
        });

        /* The following methods load the preset options for authors */
        jrrTolkien.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextEditorController.jrrTolkein();
                if (jrrTolkien.isSelected()) {
                    jrrTolkien.setSelected(true);
                }
                useOwnTextFile.setSelected(false);
                elJames.setSelected(false);
                dTrump.setSelected(false);
                jkRowling.setSelected(false);
                if (!authorIsChosen) {
                    completeSentence.setDisable(false);
                    wordDuetMode.setSelected(true);
                }
            }
        });

        elJames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextEditorController.elJames();
                if (elJames.isSelected()) {
                    elJames.setSelected(true);
                }
                jrrTolkien.setSelected(false);
                useOwnTextFile.setSelected(false);
                dTrump.setSelected(false);
                jkRowling.setSelected(false);
                if (!authorIsChosen) {
                    completeSentence.setDisable(false);
                    wordDuetMode.setSelected(true);
                }
            }
        });

        jkRowling.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextEditorController.jkRowling();
                if (jkRowling.isSelected()) {
                    jkRowling.setSelected(true);
                }
                jrrTolkien.setSelected(false);
                elJames.setSelected(false);
                dTrump.setSelected(false);
                useOwnTextFile.setSelected(false);
                if (!authorIsChosen) {
                    completeSentence.setDisable(false);
                    wordDuetMode.setSelected(true);
                }
            }
        });

        /* I don't have the trump.txt file so The text file is set to something else */
        dTrump.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextEditorController.dTrump();
                if (dTrump.isSelected()) {
                    dTrump.setSelected(true);
                }
                jrrTolkien.setSelected(false);
                elJames.setSelected(false);
                useOwnTextFile.setSelected(false);
                jkRowling.setSelected(false);
                if (!authorIsChosen) {
                    completeSentence.setDisable(false);
                    wordDuetMode.setSelected(true);
                }
            }
        });


        /* The word duet function is turned on and off and the text in the menu is changed
         * to indicate the state to the user */
        wordDuetMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("turned on duet mode");
                TextEditorController.wordDuet();
            }
        });

        chooseAuthor.getItems().addAll(jrrTolkien, jkRowling, elJames, dTrump, useOwnTextFile);
        //autoCompleteMenu.getItems().addAll(chooseAuthor, generateSpecialWords);

        MenuBar autoMenuBar = new MenuBar();
        autoMenuBar.getMenus().addAll(chooseAuthor, generateSpecialWords, chooseDepth);
        //pane.getChildren().add(autoMenuBar);
        autoPane.getChildren().add(autoMenuBar);
        autoPane.getChildren().add(completeSentence);
        autoPane.getChildren().add(wordDuetMode);


        return autoPane;
    }


    private HBox addFileMenu(){
        //BorderPane formatMenu = new BorderPane();


        HBox formatPane = new HBox();
        formatPane.setAlignment(Pos.TOP_LEFT);

        Image image = new Image(getClass().getResourceAsStream("save-im.jpg"));
        Button button1 = new Button("Save");
        ImageView saveImage = new ImageView(image);
        saveImage.setFitHeight(15);
        saveImage.setFitWidth(15);
        button1.setGraphic(saveImage);
        button1.setStyle("-fx-background-color: transparent");

        Image openImage = new Image(getClass().getResourceAsStream("open.png"));
        Button button2 = new Button("Open");
        ImageView openImageView = new ImageView(openImage);
        openImageView.setFitHeight(15);
        openImageView.setFitWidth(15);
        button2.setGraphic(openImageView);
        button2.setStyle("-fx-background-color: transparent");



        //Button saveButton = new Button("Save");
        //Button openButton = new Button("Open");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                status.setText(newString);

            }
        });


        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                File saveFile = fc.showSaveDialog(null);
                String saveFileName = saveFile.getAbsolutePath();
                try (FileOutputStream fos = new FileOutputStream(saveFileName);
                     BufferedOutputStream bos = new BufferedOutputStream(fos) ) {
                    String text = status.getText();
                    bos.write(text.getBytes());
                    bos.flush();

                }
                catch ( Exception e ) {

                }
            }
        });

        Button displayButton = new Button("Display");

        displayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                display();

            }
        });

        formatPane.getChildren().add(button1);
        formatPane.getChildren().add(button2);
        formatPane.getChildren().add(displayButton);

        //formatPane.getChildren().add(saveButton);
        //formatPane.getChildren().add(openButton);

        //formatMenu.setTop(formatPane);
        //return formatMenu;
        return formatPane;
    }

    private void display(){
        //Text newText = new Text(textObject.getText());
        TextFlow newDisplay = createTextFlow();
        root.setCenter(newDisplay);
    }

    private TextFlow createTextFlow(){

        List<Span> spanList = textObject.useSpans();

        List<Text> textObjectsList = new ArrayList<>();

        String text = textObject.getText();
        System.out.println("Spans we add to box");
        for (Span span : spanList){
            System.out.println(span.getStartPos());
            System.out.println(span.getEndPos());
            String subString = text.substring(span.getStartPos(), span.getEndPos());

            Text newText = new Text(subString);
            newText.setFont(Font.font(span.getFontName(), FontPosture.REGULAR, 12));

            textObjectsList.add(newText);

        }


        TextFlow newFlow = new TextFlow();
        for (Text textObject : textObjectsList){
            newFlow.getChildren().addAll(textObject);
        }

        return newFlow;
    }

    private HBox addFormatMenu(){
        //BorderPane formatMenu = new BorderPane();

        HBox formatPane = new HBox();
        formatPane.setAlignment(Pos.TOP_LEFT);
        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(SCENE_WIDTH);
        Menu font = new Menu("Font: Times New Roman");
        status.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 12));




        MenuItem American_Typewriter = new MenuItem("American Typewriter");
        MenuItem Arial = new MenuItem("Arial");
        MenuItem Baghdad = new MenuItem("Baghdad");
        MenuItem Baskerville = new MenuItem("Baskerville");
        MenuItem Chalkboard = new MenuItem("Chalkboard");
        MenuItem ComicSans = new MenuItem("Comic Sans");
        MenuItem Courier = new MenuItem("Courier");
        MenuItem Damascus = new MenuItem("Damascus");
        MenuItem Dialog = new MenuItem("Dialog");
        MenuItem Didot = new MenuItem("Didot");
        MenuItem Diwan_Kufi = new MenuItem("Diwan Kufi");
        MenuItem Euphemia_UCAS = new MenuItem("Euphemia UCAS");
        MenuItem Farah = new MenuItem("Farah");
        MenuItem Geneva = new MenuItem("Geneva");
        MenuItem Georgia = new MenuItem("Georgia");
        MenuItem Helvetica = new MenuItem("Helvetica");
        MenuItem Impact = new MenuItem("Impact");
        MenuItem Kefa = new MenuItem("Kefa");
        MenuItem Lucida_Grande = new MenuItem("Lucida Grande");
        MenuItem Luminari = new MenuItem("Luminari");
        MenuItem Microsoft_Sans_Serif = new MenuItem("Microsoft Sans Serif");
        MenuItem Myanmar_MN = new MenuItem("Myanmar MN");
        MenuItem Noteworthy = new MenuItem("Noteworthy");
        MenuItem Osaka = new MenuItem("Osaka");
        MenuItem Papyrus = new MenuItem("Papyrus");
        MenuItem Raanana = new MenuItem("Raanana");
        MenuItem Serif = new MenuItem("Serif");
        MenuItem Tahoma = new MenuItem("Tahoma");
        MenuItem TimesNewRoman = new MenuItem("Times New Roman");
        MenuItem Verdana = new MenuItem("Verdana");
        MenuItem Wingdings = new MenuItem("Wingdings");
        MenuItem Zapfino = new MenuItem("Zapfino");

        font.getItems().addAll(American_Typewriter,
                Arial, Baghdad, Baskerville, Chalkboard, ComicSans, Courier, Damascus, Dialog, Didot, Diwan_Kufi, Euphemia_UCAS, Farah, Geneva, Georgia, Helvetica,
                Impact, Kefa, Lucida_Grande, Luminari, Microsoft_Sans_Serif, Myanmar_MN, Noteworthy, Osaka, Papyrus,
                Raanana, Serif, Tahoma, TimesNewRoman, Verdana, Wingdings, Zapfino);

        Menu size = new Menu("Size: 16");


        MenuItem size1 = new MenuItem("8");
        MenuItem size2 = new MenuItem("10");
        MenuItem size3 = new MenuItem("12");
        MenuItem size4 = new MenuItem("14");
        MenuItem size5 = new MenuItem("16");
        MenuItem size6 = new MenuItem("18");
        MenuItem size7 = new MenuItem("20");
        MenuItem size8 = new MenuItem("22");
        MenuItem size9 = new MenuItem("26");
        MenuItem size10 = new MenuItem("30");
        MenuItem size11 = new MenuItem("36");
        MenuItem size12 = new MenuItem("45");

        size.getItems().addAll(size1, size2, size3, size4, size5,
                size6, size7, size8, size9, size10, size11, size12);


        for (MenuItem sizeItem : size.getItems()){
            sizeItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    IndexRange selection = status.getSelection();
                    int val = Integer.parseInt(sizeItem.getText());
                    System.out.println(val);
                    status.setFont(Font.font(currentFont, FontWeight.NORMAL, val));
                    size.setText("Size: " + sizeItem.getText());


                }
            });
        }


        Menu bold = new Menu("Bold");
        Menu underline = new Menu("Under Line");
        Menu italics = new Menu("Italics");


        for (MenuItem item : font.getItems()){
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    IndexRange selection = status.getSelection();
                    status.setFont(Font.font(item.getText()));
                    currentFont = item.getText();
                    font.setText("Font: " + item.getText());
                    int selStart = (int) engine.executeScript("getSelStart();");
                    int selEnd = (int) engine.executeScript("getSelEnd();");
                    textObject.newFont(selStart, selEnd, item.getText());
                    display();
                    //String html = textObject.createHTML();
                    //engine.loadContent(html);
                    //System.out.println(selStart);
                    //System.out.println(selEnd);
                    //System.out.println(item.getText());

                }
            });
        }
        /*font.setStyle("-fx-background-color: transparent");
        size.setStyle("-fx-background-color: transparent");
        bold.setStyle("-fx-background-color: transparent");
        underline.setStyle("-fx-background-color: transparent");
        italics.setStyle("-fx-background-color: transparent");
        */
        menuBar.getMenus().addAll(font, size, bold, underline, italics);
        menuBar.setStyle("-fx-background-color: transparent");
        formatPane.getChildren().add(menuBar);
        //formatPane.getChildren().add(displayButton);
        //formatMenu.setTop(formatPane);
        //return formatMenu;
        return formatPane;
    }


    private Node addStatus() {
        pane = new FlowPane();
        status.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
        status.setWrapText(true);

        status.setPrefWidth(SCENE_WIDTH);
        status.setPrefHeight(SCENE_HEIGHT);
        status.setStyle("padding : 100px; margin : 100px;");
        //status.setStyle("textarea {padding : 20px; margin : 10px;}");
        pane.getChildren().add(status);




        return pane;
    }

    @FXML
    void onKeyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.DELETE) {
            System.out.println("delete!");
        }
    }

    public void setHTML(String newText){
        engine.loadContent("<html><head><script>\n" +
                "    function onInput() {\n" +
                "     textArea = document.getElementById(\"mainText\");\n" +
                "        return textArea.value;\n" +
                "    }\n" +
                "    </script><style media=\"screen\" type=\"text/css\">\n" +
                "textarea {\n" +
                "    height: 200px;\n" +
                "    width: 200px;\n" +
                "}\n" +
                "</style>\n" +
                "    <script>\n" +
                "            function getSelStart(){\n" +
                "                textArea = document.getElementById(\"mainText\");\n" +
                "                return textArea.selectionStart;\n" +
                "            }\n" +
                "        </script>\n" +
                "        <script>\n" +
                "            function getSelEnd(){\n" +
                "                textArea = document.getElementById(\"mainText\");\n" +
                "                return textArea.selectionEnd;\n" +
                "            }\n" +
                "        </script>\n" +
                "        </head>\n" +
                "<body>\n" +
                "<div id=\"left\" style=\"float: left;\"></div>\n" +
                "<form>\n" +
                "        <textarea id=\"mainText\" style=\"width: 375px; height: 405\" oninput=\"java.exit(onInput());\">" +
                newText +
                "</textarea>\n" +
                "    </form></div>\n" +
                "</body>\n" +
                "</html>");
    }



    public static void main(String[] args) {
        launch(args);
    }
}

