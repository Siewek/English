import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageCreator.create("main.fxml");
    }

    public void OnQuizStart(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
        StageCreator.create("QuestionScene.fxml");
    }

    public void OnTestStart(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
        StageCreator.create("QuestionScene.fxml");
    }

    public void OnManageDb(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
        StageCreator.create("dbmanager.fxml");
    }
}