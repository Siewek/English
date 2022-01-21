
public class QuestionProvider {
	private int score;
	public DifficultyStrategy chosenStrategy;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void getNewQuestion() {
		//To Do
	}
	
	public void setStrategy(DifficultyStrategy newStrategy){
		chosenStrategy = newStrategy;
	}
	
		
}
