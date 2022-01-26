package DesignPatterns;

import Data.QuestionProvider;
import Data.Word;

import java.util.ArrayList;

public interface DifficultyStrategy
{
	public Word chooseNextQuestion(QuestionProvider provider);
	public ArrayList<Word> chooseAnswers(QuestionProvider provider, Word Question);
}

