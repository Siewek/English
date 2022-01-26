package Controllers;

import Data.Question;
import Data.QuestionProvider;
import Data.Word;
import DesignPatterns.EasyDifficulty;
import DesignPatterns.MediumDifficulty;
import DesignPatterns.SqliteFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionScene implements Initializable {
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Label infoLabel;
    @FXML private VBox answersVBox;
    @FXML private HBox progress;
    @FXML private Label questionLabel;

    private QuestionProvider provider = null;
    private Question currentQuestion = null;
    private int wrongAnswers = 0;
    private int rightAnswers = 0;

    private String selectedAnswer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        progress.setVisible(false);
        infoLabel.setVisible(false);

        answersVBox.setAlignment(Pos.CENTER);
        answersVBox.setSpacing(15d);

        try {
            SqliteFacade sqliteFacade = new SqliteFacade();
            ArrayList<Word> words = sqliteFacade.getAllWords();

            provider = new QuestionProvider(words);
            this.setUpScene();
        } catch (SQLException exception) {
            exception.getCause();
        }

    }

    @FXML public void OnNextQuestion(ActionEvent actionEvent) {

        boolean result = currentQuestion.validate(selectedAnswer);

        if(result) {
            if(++rightAnswers >= 3) {
                provider.setStrategy(new MediumDifficulty());
                rightAnswers = 0;
            }

            wrongAnswers = 0;

            infoLabel.setVisible(true);
            infoLabel.setText("Correct answer");
            infoLabel.setStyle("-fx-text-fill: green;");
        }
        else {
            if(++wrongAnswers >= 3) {
                provider.setStrategy(new EasyDifficulty());
                wrongAnswers = 0;
            }

            rightAnswers = 0;

            infoLabel.setVisible(true);
            infoLabel.setText("Incorrect answer");
            infoLabel.setStyle("-fx-text-fill: red;");
        }

        this.setUpScene();
    }

    @FXML public void OnPreviousQuestion(ActionEvent actionEvent) {
    }

    private void setUpScene() {
        answersVBox.getChildren().clear();
        currentQuestion = provider.getNextQuestion();
        questionLabel.setText(currentQuestion.getQuestionWord().getWord());

        ToggleGroup toggleGroup = new ToggleGroup();
        for (Word answer: currentQuestion.getAnswers()) {
            RadioButton b = new RadioButton(answer.getTranslation());
            b.setToggleGroup(toggleGroup);

            b.setOnAction(actionEvent -> {
                RadioButton radioButton = (RadioButton) actionEvent.getSource();

                if(radioButton == null)
                    return;

                selectedAnswer = radioButton.getText();
            });

            answersVBox.getChildren().add(b);
        }
    }
}
