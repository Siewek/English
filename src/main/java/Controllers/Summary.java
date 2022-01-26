package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Summary {
    private int rightAnswers = 0;

    @FXML private Label scoreLabel;

    @FXML public void OnMainMenuClick(ActionEvent actionEvent) {
        StageCreator.create("../main.fxml", "Learn");
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
        scoreLabel.setText(String.valueOf(rightAnswers));
    }
}
