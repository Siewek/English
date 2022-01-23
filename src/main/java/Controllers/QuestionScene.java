package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuestionScene {
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Label infoLabel;
    @FXML private VBox answersVBox;
    @FXML private HBox progress;
    @FXML private Label questionLabel;

    @FXML public void OnNextQuestion(ActionEvent actionEvent) {
    }

    @FXML public void OnPreviousQuestion(ActionEvent actionEvent) {
    }
}
