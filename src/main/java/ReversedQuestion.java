import Data.Word;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReversedQuestion extends QuestionDecorator{

    public ReversedQuestion(Question newQuestion) {
        super(newQuestion);
    }
    @Override
    public void setQuestion(String difficulty) throws SQLException {
       basicQuestion.setQuestion(difficulty);
       basicQuestion.word = new Word(basicQuestion.word.getId(),basicQuestion.word.getTranslation(),basicQuestion.word.getWord(),basicQuestion.word.getDifficulty());
    }

    @Override
    public ArrayList<String> generateAnswers() {
        return basicQuestion.generateAnswers();
    }
}
