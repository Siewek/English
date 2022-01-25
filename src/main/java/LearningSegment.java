public class LearningSegment {
    StateMode quizState;
    StateMode endlessState;

    StateMode currentState;

    public LearningSegment()
    {
        quizState = new QuizState(this);
        endlessState = new EndlessState(this);
    }

    void setState(StateMode mode)
    {
        currentState = mode;
    }

    public StateMode getQuizState() {return quizState;}
    public StateMode getEndlessState() {return  endlessState;}
}
