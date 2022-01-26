package Data;

import Data.Word;
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
    }

    public Word getQuestionWord() {
        return questionWord;
    }

    public ArrayList<Word> getAnswers() {
        return answers;
    }

    public boolean validate(String answer) {
        for (Word word: answers) {
            if(word.getTranslation().toLowerCase().equals(answer.toLowerCase()))
                return true;
        }

        return false;
    }
}
