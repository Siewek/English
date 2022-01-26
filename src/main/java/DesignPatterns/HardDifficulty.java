package DesignPatterns;

import Data.QuestionProvider;
import Data.Word;

import java.util.ArrayList;

public class HardDifficulty implements DifficultyStrategy {
    private static int answersNumber = 3;
    private final int poolSize = 5;
    private char lastChar = ' ';
    private char firstChar = ' ';
    private long ctr = 0;
    private Word[] lastWords = new Word[poolSize];

    public Word chooseNextQuestion(QuestionProvider provider) {
        ArrayList<Word> words = provider.getWordsByDifficulty(QuestionProvider.Level.HARD);
        Word newWord = null;

        do {
            int min = 0;
            int max = words.size();
            int decide = (int) (Math.random() * (max - min) + min);
            newWord = words.get(decide);
        } while(newWord.getWord().toLowerCase().charAt(0) == firstChar ||
                newWord.getWord().toLowerCase().charAt(newWord.getWord().length()-1) == lastChar || isPresent(newWord));

        firstChar = newWord.getWord().toLowerCase().charAt(0);
        lastChar = newWord.getWord().toLowerCase().charAt(newWord.getWord().length()-1);
        lastWords[(int) (ctr++ % poolSize)] = newWord;

        return newWord;
    }

    public ArrayList<Word> chooseAnswers(QuestionProvider provider, Word Question) {
        ArrayList<Word> words = provider.getWordsByDifficulty(QuestionProvider.Level.NONE);
        ArrayList<Word> answersArray = new ArrayList<>();
        int answers = 0;

        for (Word word: words) {
            if(word.getTranslation().toLowerCase().charAt(0) == Question.getTranslation().toLowerCase().charAt(0) && !word.equals(Question)) {
                answersArray.add(word);
                answers++;

                if(answers == answersNumber)
                    break;
            }
        }

        return answersArray;
    }

    private boolean isPresent(Word word) {
        for (Word w: lastWords) {
            if(word.equals(w))
                return true;
        }

        return false;
    }
}