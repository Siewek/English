package Controllers;

import Data.Question;
import Data.QuestionProvider;
import Data.Word;
import DesignPatterns.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionScene implements Initializable {

    public enum Lang { ENGLISH, POLISH };
    public enum DifficultyPolicy { AUTO, EASY, MEDIUM, HARD };

    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private ProgressBar progressBar;
    @FXML private Label infoLabel;
    @FXML private VBox answersVBox;
    @FXML private HBox progress;
    @FXML private Label questionLabel;

    private Lang lang = Lang.ENGLISH;

    private QuestionProvider provider = null;
    private Question currentQuestion = null;
    private int wrongAnswers = 0;
    private int rightAnswers = 0;

    private String selectedAnswer;
    private int level = 1;
    private final int maxLevel = 3;

    private SceneState state;

    private ArrayList<Word> words = new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answersVBox.setAlignment(Pos.CENTER);
        answersVBox.setSpacing(15d);

        MusicPlayer musicPlayer = new MusicPlayer("../Glitter Blast.mp3");
        musicPlayer.play();

        try {
            SqliteFacade sqliteFacade = new SqliteFacade();
            words = sqliteFacade.getAllWords();
        } catch (SQLException exception) {
            exception.getCause();
        }

    }

    @FXML public void OnNextQuestion(ActionEvent actionEvent) {
        state.OnNextQuestion();
    }

    @FXML public void OnPreviousQuestion(ActionEvent actionEvent) {
        state.OnPrevQuestion();
    }

    public void setSceneConfig() {
        if(lang == Lang.POLISH) {
            for(int i = 0; i < words.size(); i++) {
                words.set(i, new ReversedWord(words.get(i)));
                System.out.println(words.get(i).getWord());
            }
        }

        provider = new QuestionProvider(words);
        state.SceneConfig();
        setUpScene();
    }

    private void setUpSceneMemento(Question question) {
        answersVBox.getChildren().clear();
        currentQuestion = question;
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
    public void setPolicy(DifficultyPolicy policy) {
        this.state.SetDifficulty(policy);
    }

    public void setLang(Lang l) {
        this.lang = l;
    }

    public void setState(SceneState state) {
        this.state = state;
    }

    public static class QuizState implements SceneState {
        private final QuestionScene scene;
        private QuestionOriginator originator = new QuestionOriginator();
        private Caretaker caretaker = new Caretaker();
        private int questionIndex = 0;

        QuizState(QuestionScene scene) {
            this.scene = scene;
        }

        @Override
        public void SceneConfig() {
            scene.progress.setVisible(false);
            scene.infoLabel.setVisible(false);
        }

        @Override
        public void OnNextQuestion() {
            boolean result = scene.currentQuestion.validate(scene.selectedAnswer);
            originator.setQuestion(scene.currentQuestion);
            caretaker.addMemento(originator.createMemento());
            questionIndex++;
            if(result) {
                if(++scene.rightAnswers >= 3) {
                    DifficultyStrategy strategy = null;
                    scene.rightAnswers = 0;

                    if(scene.level < scene.maxLevel) {
                        switch (++scene.level) {
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
                        scene.provider.setStrategy(strategy);
                    } else if(scene.level == scene.maxLevel) {
                        scene.level = scene.maxLevel+1;
                    }
                }

                scene.wrongAnswers = 0;
                scene.infoLabel.setVisible(true);
                scene.infoLabel.setText("Correct answer");
                scene.infoLabel.setStyle("-fx-text-fill: green;");
            }
            else {
                if(++scene.wrongAnswers >= 3) {
                    scene.wrongAnswers = 0;
                    DifficultyStrategy strategy = null;

                    if(scene.level != 1) {
                        switch (--scene.level) {
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
                        scene.provider.setStrategy(strategy);
                    }
                }

                scene.rightAnswers = 0;
                scene.infoLabel.setVisible(true);
                scene.infoLabel.setText("Incorrect answer");
                scene.infoLabel.setStyle("-fx-text-fill: red;");
            }

            scene.setUpScene();

            scene.selectedAnswer = null;
        }

        @Override
        public void OnPrevQuestion() {
            // MOMENTO ??
            if(questionIndex - 1!=-1) {
                scene.currentQuestion = originator.restoreMemento(caretaker.getMemento(questionIndex - 1));
                scene.setUpSceneMemento(scene.currentQuestion);
                scene.selectedAnswer = null;
            }
        }

        @Override
        public void SetDifficulty(DifficultyPolicy p) {
            // ma byc puste, quiz ma automatyczny poziom zawsze
        }
    };

    public static class TestState implements SceneState {
        private static int TestLength = 15;
        private final QuestionScene scene;

        public TestState(QuestionScene scene) {
            this.scene = scene;
        }

        @Override
        public void SceneConfig() {
            scene.progress.setVisible(true);
            scene.infoLabel.setVisible(false);
            scene.progressBar.setStyle("-fx-accent: green;");
        }

        @Override
        public void OnNextQuestion() {
            boolean validate = scene.currentQuestion.validate(scene.selectedAnswer);

            if(validate)
                scene.rightAnswers++;
            else
                scene.wrongAnswers++;

            long allAnswers = scene.rightAnswers + scene.wrongAnswers;
            scene.progressBar.setProgress(allAnswers*1.0d / TestLength);

            if(allAnswers == TestLength) {
                StageCreator.create("../summary.fxml", "Summary", new ControllerCmd() {
                    @Override
                    public void execute(Object oController) {
                        Summary summary = (Summary) oController;
                        summary.setRightAnswers(scene.rightAnswers);
                        Stage stage = (Stage) TestState.this.scene.nextButton.getScene().getWindow();
                        stage.close();
                    }
                });
            }

            scene.setUpScene();
            scene.selectedAnswer = null;
        }

        @Override
        public void OnPrevQuestion() {
            scene.infoLabel.setText("This function is unavailable in Test Mode");
            scene.infoLabel.setVisible(true);
            scene.infoLabel.setStyle("-fx-text-fill: red;");
        }

        @Override
        public void SetDifficulty(DifficultyPolicy pol) {
            switch (pol) {
                case EASY:
                    scene.provider.setStrategy(new EasyDifficulty());
                    scene.level = 1;
                    break;
                case MEDIUM:
                    scene.provider.setStrategy(new MediumDifficulty());
                    scene.level = 2;
                    break;
                case HARD:
                    scene.provider.setStrategy(new HardDifficulty());
                    scene.level = 3;
                    break;
            }
        }
    }
}
