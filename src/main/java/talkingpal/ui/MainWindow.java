package talkingpal.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import talkingpal.TalkingPal;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image botImage = new Image(this.getClass().getResourceAsStream("/./images/YierWine.gif"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/./images/Bubu.gif"));
    private TalkingPal talkingPal = new TalkingPal();

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String startMessage = "Hello, I'm TalkingPal. What is your name?";
        dialogContainer.getChildren().addAll(
                DialogBox.getBotDialog(talkingPal.getCurrentTasks(),botImage),
                DialogBox.getBotDialog(startMessage, botImage)
        );
    }

    /** Injects the TalkingPal instance */
    public void setTalkingPal(TalkingPal p) {
        talkingPal = p;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing TalkingPal's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        String botText = talkingPal.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBotDialog(botText, botImage)
        );
        userInput.clear();
    }
}
