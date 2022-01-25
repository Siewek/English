import Data.Word;
import DesignPatterns.SqliteFacade;

import java.sql.SQLException;
import java.util.ArrayList;

public class Question {

    private Word word;
    private String correctAnswer;
    private DifficultyStrategy questionType;



    public Question getQuestion()
    {
        return this;
    }

    public void setQuestion(String difficulty) throws SQLException {
        if(difficulty == "Easy")
        {
            questionType = new EasyDifficulty();
        }
        else if(difficulty == "Medium")
        {
            questionType = new MediumDifficulty();
        }
        else if (difficulty == "Hard")
        {
            questionType = new HardDifficulty();
        }
        word = questionType.chooseNextQuestion();
    }
}
