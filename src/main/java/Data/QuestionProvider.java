package Data;

import DesignPatterns.DifficultyStrategy;
import DesignPatterns.EasyDifficulty;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionProvider {
	public enum Level { NONE, EASY, MEDIUM, HARD }

	private ArrayList<Word> wordsPool = new ArrayList<>();
	private DifficultyStrategy strategy;

	public QuestionProvider(ArrayList<Word> wordsPool) {
		strategy = new EasyDifficulty();
		this.wordsPool = wordsPool;
	}

	public QuestionProvider(DifficultyStrategy strategy, ArrayList<Word> wordsPool) {
		this.strategy = strategy;
		this.wordsPool = wordsPool;
	}

	public void setStrategy(DifficultyStrategy newStrategy){
		this.strategy = newStrategy;
	}

	public Question getNextQuestion() {
		Word word = strategy.chooseNextQuestion(this);
		Question question = new Question(word);

		ArrayList<Word> answers = strategy.chooseAnswers(this, word);
		question.setAnswers(answers);

		return question;
	}

	public ArrayList<Word> getWordsByDifficulty(Level level) {
		switch (level){
			case EASY:
				return new ArrayList(Arrays.asList(wordsPool.stream().filter(word -> word.getDifficulty().toLowerCase().equals("easy")).toArray()));
			case MEDIUM:
				return new ArrayList(Arrays.asList(wordsPool.stream().filter(word -> word.getDifficulty().toLowerCase().equals("medium")).toArray()));
			case HARD:
				return new ArrayList(Arrays.asList(wordsPool.stream().filter(word -> word.getDifficulty().toLowerCase().equals("hard")).toArray()));
			default:
				return wordsPool;
		}
	}
}
