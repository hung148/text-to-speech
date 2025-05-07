package com.example.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// more to enhanve
// Punctuation & Tone Handling
// Language & Accent Support
// Prosody & Rhythm
// Dialogue/Conversation

public class Main extends Application {
    private static final int APP_WIDTH = 375;
    private static final int APP_HEIGHT = 475;

    // allow user to input text
    private TextArea textArea; // make this instance variable to access outside createScene

    private ComboBox<String> voices, rates, volumes;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Text-to-Speech App");
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene() {
        // arrange UI component in vertical order
        VBox vBox = new VBox();
        vBox.getStyleClass().add("body");
        Label textToSpeechLbl = new Label("Text to Speech");
        textToSpeechLbl.getStyleClass().add("text-to-speech-lbl");
        textToSpeechLbl.setMaxWidth(Double.MAX_VALUE);
        textToSpeechLbl.setAlignment(Pos.CENTER);
        vBox.getChildren().add(textToSpeechLbl);

        textArea = new TextArea();
        textArea.setWrapText(true); // any text that exceed the screen width will be moved to next line.
        textArea.getStyleClass().add("text-area");

        StackPane textAreaPane = new StackPane();
        // add "margin" around left and right area of text area
        textAreaPane.setPadding(new Insets(0, 15, 0, 15));
        textAreaPane.getChildren().add(textArea);

        // adding setting voice, rate, volume
        GridPane settingPane = createSettingComponents();

        vBox.getChildren().add(textAreaPane);
        vBox.getChildren().add(settingPane);

        Button speakBtn = createImageBtn();
        // set to do speak method when button is clicked
        speakBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // retrieving the values from the textArea and the settings
                String msg = textArea.getText();
                String voice = voices.getValue();
                String rate = rates.getValue();
                String volume = volumes.getValue();

                // call speak method
                MainController.speak(msg, voice, rate, volume);
            }
        });
        StackPane speakBtnPane = new StackPane();
        // 40 px top, 20 px left and right
        speakBtnPane.setPadding(new Insets(40, 20, 0, 20));
        speakBtnPane.getChildren().add(speakBtn);
        vBox.getChildren().add(speakBtnPane);

        return new Scene(vBox, APP_WIDTH, APP_HEIGHT);
    }

    private GridPane createSettingComponents() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // set 10px horizontal gap between each component
        gridPane.setPadding(new Insets(10, 0, 0,0)); // 10px gap at the top

        Label voiceLbl = new Label("Voice");
        voiceLbl.getStyleClass().add("setting-lbl");
        Label rateLbl = new Label("Rate");
        rateLbl.getStyleClass().add("setting-lbl");
        Label volumeLbl = new Label("Volume");
        volumeLbl.getStyleClass().add("setting-lbl");
        gridPane.add(voiceLbl, 0, 0);
        gridPane.add(rateLbl, 1, 0);
        gridPane.add(volumeLbl, 2, 0);

        voices = new ComboBox<>();
        voices.getItems().addAll(MainController.getVoices());
        voices.setValue(voices.getItems().get(0)); // set default value which will be the first choice
        voices.getStyleClass().add("setting-combo-box");

        rates = new ComboBox<>();
        rates.getItems().addAll(MainController.getSpeedRates());
        rates.setValue(rates.getItems().get(2)); // set default value
        rates.getStyleClass().add("setting-combo-box");

        volumes = new ComboBox<>();
        volumes.getStyleClass().add("setting-combo-box");
        volumes.getItems().addAll(MainController.getVolumeLevels());
        volumes.setValue(volumes.getItems().get(4)); // set default value

        gridPane.add(voices, 0, 1);
        gridPane.add(rates, 1, 1);
        gridPane.add(volumes, 2, 1);

        // center label
        GridPane.setHalignment(voiceLbl, HPos.CENTER);
        GridPane.setHalignment(rateLbl, HPos.CENTER);
        GridPane.setHalignment(volumeLbl, HPos.CENTER);

        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private Button createImageBtn() {
        Button speakBtn = new Button("Speak");
        speakBtn.getStyleClass().add("speak-btn");
        speakBtn.setMaxWidth(Double.MAX_VALUE);
        speakBtn.setAlignment(Pos.CENTER);

        // add image to button
        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("speak.png"))
        );

        // dimension ot 50pxX50px
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        speakBtn.setGraphic(imageView);
        return speakBtn;
    }

    public static void main(String[] args) {
        launch();
    }
}
