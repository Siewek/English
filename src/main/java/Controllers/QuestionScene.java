package Controllers;

import Data.Question;
import Data.QuestionProvider;
import Data.Word;
import DesignPatterns.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private int level = 1;
    private final int maxLevel = 3;

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

        System.out.println(selectedAnswer);

        boolean result = currentQuestion.validate(selectedAnswer);

        if(result) {
            if(++rightAnswers >= 3) {
                DifficultyStrategy strategy = null;
                rightAnswers = 0;

                if(level < maxLevel) {
                    switch (++level) {
                        case 2:
                            strategy = new MediumDifficulty();
                            break;
                        case 3:
                            strategy = new HardDifficulty();
                            break;
                        default:
                            strategy = new EasyDifficulty();
                            break;
                    }
                    provider.setStrategy(strategy);
                } else if(level == maxLevel) {
                    level = maxLevel+1;
                }
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

                DifficultyStrategy strategy = null;

                if(level != 1) {
                    switch (--level) {
                        case 2:
                            strategy = new MediumDifficulty();
                            break;
                        case 3:
                            strategy = new HardDifficulty();
                            break;
                        default:
                            strategy = new EasyDifficulty();
                            break;
                    }

                    provider.setStrategy(strategy);
                }
            }

            rightAnswers = 0;

            infoLabel.setVisible(true);
            infoLabel.setText("Incorrect answer");
            infoLabel.setStyle("-fx-text-fill: red;");
        }

        this.setUpScene();
        selectedAnswer = null;
    }

    @FXML public void OnPreviousQuestion(ActionEvent actionEvent) {
    }

    private void setUpScene() {
        answersVBox.getChildren().clear();
        currentQuestion = provider.getNextQuestion();
        questionLabel.setText(currentQuestion.getQuestionWord().getWord());

        if(level == (maxLevel+1)) {
            TextField textField = new TextField();
            textField.setOnAction(actionEvent -> {
                selectedAnswer = textField.getText();
            });

            answersVBox.getChildren().add(textField);
            return;
        }

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
