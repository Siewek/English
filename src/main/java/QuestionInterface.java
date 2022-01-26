import java.sql.SQLException;
import java.util.ArrayList;

public interface QuestionInterface {

    public void setQuestion(String difficulty) throws SQLException;

    public ArrayList<String> generateAnswers();
}
