public class QuestionOriginator {

    private Question question;

    public void setQuestion(Question newQuestion)
    {
        question = newQuestion;
    }

    public Memento createMemento(){

        return new Memento(question);
    }

    public Question restoreMemento(Memento memento)
    {
        question = memento.getQuestion();
        return question;
    }

}

