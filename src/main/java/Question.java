import Data.Word;

public class Question {

    private Question question;
    private Word word;
    private String answer;

    /*public Question(Word word,String answer)
    {
        this.word = word;
        this.answer = answer;
    }*/

    public Question getQuestion()
    {
        return question;
    }

    public Question setQuestion(Question _question)
    {
        question = _question;
        return getQuestion();
    }
}
