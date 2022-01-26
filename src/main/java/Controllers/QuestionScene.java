package Controllers;

import Data.Question;
import Data.QuestionProvider;
import Data.Word;
import DesignPatterns.SqliteFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        this.setUpScene();
    }

    @FXML public void OnPreviousQuestion(ActionEvent actionEvent) {
    }

    private void setUpScene() {
        answersVBox.getChildren().clear();
        currentQuestion = provider.getNextQuestion();
        questionLabel.setText(currentQuestion.getQuestionWord().getWord());

        for (Word answer: currentQuestion.getAnswers()) {
            answersVBox.getChildren().add(new Button(answer.getTranslation()));
        }
    }
}
