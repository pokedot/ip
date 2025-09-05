package flyingnugget.ui;

import flyingnugget.FlyingNugget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private FlyingNugget flyingNugget;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/LittleNuggy.gif"));
    private final Image botImage = new Image(this.getClass().getResourceAsStream("/images/FlyingNugget.gif"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the FlyingNugget instance */
    public void setFlyingNugget(FlyingNugget f) {
        flyingNugget = f;
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(Ui.getIntro(), botImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing FlyingNugget's reply and
     * then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = flyingNugget.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotDialog(response, botImage)
        );
        userInput.clear();
    }
}
