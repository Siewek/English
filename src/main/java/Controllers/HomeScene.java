package Controllers;

import DesignPatterns.ControllerCmd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class HomeScene {
    @FXML private RadioButton medLvl;
    @FXML private RadioButton hardLvl;
    @FXML private RadioButton easyLvl;

    @FXML private RadioButton englishRadio;
    @FXML private RadioButton polishRadio;

    @FXML public void OnQuizStart(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();

        StageCreator.create("../questionScene.fxml", "Quiz", new ControllerCmd() {
                    @Override
                    public void execute(Object oController) {
                        QuestionScene questionScene = (QuestionScene) oController;
                        questionScene.setLang(polishRadio.isSelected() ? QuestionScene.Lang.POLISH : QuestionScene.Lang.ENGLISH);
                        questionScene.setState(new QuestionScene.QuizState(questionScene));
                        questionScene.setSceneConfig();
                    }
                }
        );

        theStage.close();
    }

    @FXML public void OnTestStart(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();

        StageCreator.create("../questionScene.fxml", "TEST", new ControllerCmd() {
                    @Override
                    public void execute(Object oController) {
                        QuestionScene questionScene = (QuestionScene) oController;
                        questionScene.setState(new QuestionScene.TestState(questionScene));
                        questionScene.setLang(polishRadio.isSelected() ? QuestionScene.Lang.POLISH : QuestionScene.Lang.ENGLISH);
                        questionScene.setSceneConfig();
                        questionScene.setPolicy(easyLvl.isSelected() ? QuestionScene.DifficultyPolicy.EASY :
                                (medLvl.isSelected() ? QuestionScene.DifficultyPolicy.MEDIUM : QuestionScene.DifficultyPolicy.HARD));
                    }
                });

        theStage.close();
    }

    @FXML public void OnManageDb(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();

        StageCreator.create("../dbmanager.fxml");
        theStage.close();
    }
}
