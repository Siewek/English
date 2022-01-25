import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDecorator implements QuestionInterface{

    protected Question basicQuestion;

    public  QuestionDecorator( Question newQuestion)
    {
        basicQuestion = newQuestion;
    }
    @Override
    public void setQuestion(String difficulty) throws SQLException {
        basicQuestion.setQuestion(difficulty);
    }

    @Override
    public ArrayList<String> generateAnswers() {
        return basicQuestion.generateAnswers();
    }
}
