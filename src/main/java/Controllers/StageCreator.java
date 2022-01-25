package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

interface Command {
    public void execute(Object o);
}

public class StageCreator {
    public static Stage create(String fxmlFile, String title, Command c) {
        Stage stage = new Stage();
        try {
            FXMLLoader root = new FXMLLoader(StageCreator.class.getResource(fxmlFile));
            Parent p = root.load();

            if(c != null)
                c.execute(root.getController());

            Scene scene = new Scene(p);
            //stage.getIcons().add(new Image(Program.class.getResourceAsStream(Main.appIconPath)));
            stage.setScene(scene);
            stage.setResizable(false);

            if(title != null)
                stage.setTitle(title);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }

        return stage;
    }

    public static Stage create(String fxmlFile, String title) {
        return create(fxmlFile, title, null);
    }

    public static Stage create(String fxmlFile) {
        return create(fxmlFile, null, null);
    }
}
