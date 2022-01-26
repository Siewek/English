package DesignPatterns;

import Data.QuestionProvider;
import Data.Word;

import java.util.ArrayList;

public class EasyDifficulty implements DifficultyStrategy {
    private static long ctr = 0; // counter;
    private static int answersNumber = 2;

    public Word chooseNextQuestion(QuestionProvider provider) {
        ArrayList<Word> words = provider.getWordsByDifficulty(QuestionProvider.Level.EASY);
        return words.get((int) (ctr++ % words.size()));
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