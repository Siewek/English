import Data.QuestionProvider;

public class EndlessState implements StateMode{
    LearningSegment learningSegment;
    private QuestionProvider question;
    public EndlessState(LearningSegment newSegment){
        learningSegment = newSegment;
        System.out.println("now in Endless state");
    }
    @Override
    public void goToNextQuestion() {

    }

    @Override
    public void addPoints() {
        //question.setScore(question.getScore()+2);
    }

    @Override
    public void removePoints() {
       // question.setScore(question.getScore()-1);
    }

    @Override
    public void showSummary() {

    }
}
