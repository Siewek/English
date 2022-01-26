package DesignPatterns;

import Data.QuestionProvider;
import Data.Word;

import java.util.ArrayList;

public class MediumDifficulty implements DifficultyStrategy {
    private char lastChar = ' ';
    private static int answersNumber = 4;

    public Word chooseNextQuestion(QuestionProvider provider) {
        ArrayList<Word> words = provider.getWordsByDifficulty(QuestionProvider.Level.MEDIUM);
        Word newWord = null;

        do {
            int min = 0;
            int max = words.size();
            int decide = (int) (Math.random() * (max - min)+min);
            newWord = words.get(decide);
        } while (newWord.getWord().toLowerCase().charAt(0) == lastChar);

        lastChar = newWord.getWord().toLowerCase().charAt(0);
        return newWord;
    }

    public ArrayList<Word> chooseAnswers(QuestionProvider provider, Word Question) {
        ArrayList<Word> words = provider.getWordsByDifficulty(QuestionProvider.Level.NONE);
        ArrayList<Word> answersArray = new ArrayList<>();
        int answers = 0;

        while(answers != answersNumber) {
            int min = 0;
            int max = words.size();
            int decide = (int) (Math.random() * (max - min)+min);

            if(words.get(decide).equals(Question))
                continue;

            answersArray.add(words.get(decide));
            answers++;
        }

        return answersArray;
    }
}