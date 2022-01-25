import Controllers.StageCreator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class Program extends Application {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        launch(args);
       // String jdbcUrl = "jdbc:sqlite:wordsdb.db";
       // Connection connection = DriverManager.getConnection(jdbcUrl);

        /* String sql = "create table words (word varchar(20), translation varchar(20), difficulty varchar(10))";
         Statement statement = connection.createStatement();
        statement.executeUpdate(sql);*/

     /*  String sql = "insert into words values('bad', 'zły', 'easy')";
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(sql);
        if(rows > 0)
        {
            System.out.println("row created");
        }*/

       /* String sql = "select rowid, * from words";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while(result.next())
        {
            int rowid = result.getInt("rowid");
            String word = result.getString("word");
            String translation = result.getString("translation");
            String difficulty = result.getString("difficulty");

            System.out.println(rowid + " " + word + " " + translation + " "+ difficulty);
        }*/


        /*ScriptRunner sr = new ScriptRunner(connection);
        sr.setEscapeProcessing(false);
        Reader reader = new BufferedReader(new FileReader("src\\main\\resources\\script.sql"));
        sr.runScript(reader);*/
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageCreator.create("../main.fxml", "Learn languagues");
    }
}
