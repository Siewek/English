import Data.Word;
import DesignPatterns.SqliteFacade;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DifficultyStrategy {
	Word chooseNextQuestion();
}

class EasyDifficulty implements DifficultyStrategy{
	private SqliteFacade db = new SqliteFacade();
	private  ArrayList<Word> words = new ArrayList<>();
	EasyDifficulty() throws SQLException {
	}
	public Word chooseNextQuestion() {
		words = db.getWordsOfDifficulty("easy");
		int min = 0;
		int max = (int) words.stream().count();
		int decide = (int) (Math.random() * (max - min)+min);
		System.out.println(words.get(decide).getWord() + " " +words.get(decide).getTranslation() + " "+ words.get(decide).getDifficulty());
		return words.get(decide);
	}
}

class MediumDifficulty implements DifficultyStrategy{
	private SqliteFacade db = new SqliteFacade();
	private  ArrayList<Word> words = new ArrayList<>();

	MediumDifficulty() throws SQLException {
	}

	public Word chooseNextQuestion() {
		words = db.getWordsOfDifficulty("medium");
		int min = 0;
		int max = (int) words.stream().count();
		int decide = (int) (Math.random() * (max - min)+min);
		System.out.println(words.get(decide).getWord() + " " +words.get(decide).getTranslation() + " "+ words.get(decide).getDifficulty());
		return words.get(decide);
	}
}

class HardDifficulty implements DifficultyStrategy{
	private SqliteFacade db = new SqliteFacade();
	private  ArrayList<Word> words = new ArrayList<>();

	HardDifficulty() throws SQLException {
	}

	public Word chooseNextQuestion() {
		words = db.getWordsOfDifficulty("hard");
		int min = 0;
		int max = (int) words.stream().count();
		int decide = (int) (Math.random() * (max - min)+min);
		System.out.println(words.get(decide).getWord() + " " +words.get(decide).getTranslation() + " "+ words.get(decide).getDifficulty());
		return words.get(decide);
	}
}

