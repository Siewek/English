package Data;

import Data.Word;
import DesignPatterns.BeautifyWord;

import java.util.ArrayList;
import java.util.Comparator;

public class Question {
    private final Word questionWord;
    private ArrayList<Word> answers;

    public Question(Word questionWord) {
        this.questionWord = questionWord;
        this.answers = answers;
    }

    public void setAnswers(ArrayList<Word> answers) {
        this.answers = answers;
        this.answers.add(questionWord);
        this.answers.sort(new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getTranslation().compareTo(o2.getTranslation());
            }
        });

        for(int i = 0; i < this.answers.size(); i++) {
            this.answers.set(i, new BeautifyWord(this.answers.get(i)));
        }
    }

    public Word getQuestionWord() {
        return new BeautifyWord(questionWord); // dekorator
    }

    public ArrayList<Word> getAnswers() {
        return answers;
    }

    public boolean validate(String answer) {
        if(answer != null && questionWord.getTranslation().toLowerCase().equals(answer.toLowerCase()))
                return true;

        return false;
    }
}
