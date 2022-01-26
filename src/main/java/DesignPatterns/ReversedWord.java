package DesignPatterns;

import Data.Word;

public class ReversedWord extends Word {

    public ReversedWord(Word word) {
        super(word);
    }

    @Override
    public String getWord() {
        return super.getTranslation();
    }

    @Override
    public String getTranslation() {
        return super.getWord();
    }
}
