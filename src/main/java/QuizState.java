import Data.QuestionProvider;

public class QuizState implements StateMode{

    LearningSegment learningSegment;
    private QuestionProvider question;
    public QuizState(LearningSegment newSegment){
        learningSegment = newSegment;
        System.out.println("now in Quiz state");
    }
    @Override
    public void goToNextQuestion() {


    }

    @Override
    public void addPoints() {
    //question.setScore(question.getScore()+1);
    }

    @Override
    public void removePoints() {
        //question.setScore(question.getScore()-1);
    }

    @Override
    public void showSummary() {

    }
}
