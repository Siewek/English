import Data.Word;
import DesignPatterns.SqliteFacade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Question implements QuestionInterface{

    protected Word word;
    protected Word helpWord;
    protected DifficultyStrategy questionType;
    protected ArrayList<String> answers = new ArrayList<>();
    protected int correctAnswerIndex;
    private static Random random = new Random();

    public Question getQuestion()
    {
        return this;
    }
    @Override
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
    @Override
    public ArrayList<String> generateAnswers()
    {   answers.clear();
       correctAnswerIndex = random.nextInt(4); //generuje pomiędzy 0 a 3
        System.out.println("CorrectAnswerIndex = " + correctAnswerIndex);
        for(int i = 0; i < 4; i++)
        {
                helpWord = questionType.chooseNextQuestion();
                answers.add( helpWord.getTranslation());
                //helpWord = null;
        }
        answers.set(correctAnswerIndex,word.getTranslation());
        return answers;
    }
}
