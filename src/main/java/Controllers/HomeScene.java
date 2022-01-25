package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HomeScene {
    @FXML public void OnQuizStart(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();

        StageCreator.create("../questionScene.fxml");
        theStage.close();
    }

    @FXML public void OnTestStart(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();

        StageCreator.create("../questionScene.fxml");
        theStage.close();
    }

    @FXML public void OnManageDb(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();

        StageCreator.create("../dbmanager.fxml");
        theStage.close();
    }
}
