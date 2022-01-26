package DesignPatterns;

import Data.Word;

public class BeautifyWord extends Word {
    public BeautifyWord(Word word) {
        super(word.getId(),word.getWord(), word.getTranslation(), word.getDifficulty());
    }

    @Override
    public String getWord() {
        return (super.getWord().substring(0, 1).toUpperCase() + super.getWord().substring(1)).trim();
    }

    @Override
    public String getTranslation() {
        return (super.getTranslation().substring(0, 1).toUpperCase() + super.getTranslation().substring(1)).trim();
    }
}
