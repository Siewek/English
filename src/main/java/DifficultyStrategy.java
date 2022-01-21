
public interface DifficultyStrategy {
	Question chooseNextQuestion();
}

class EasyDifficulty implements DifficultyStrategy{
	public Question chooseNextQuestion() {
		return null;
	}
}

class MediumDifficulty implements DifficultyStrategy{
	public Question chooseNextQuestion() {
		return null;
	}
}

class HardDifficulty implements DifficultyStrategy{
	public Question chooseNextQuestion() {
		return null;
	}
}

