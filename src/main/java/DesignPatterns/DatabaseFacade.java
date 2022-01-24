package DesignPatterns;

import Data.Word;

import java.util.ArrayList;

public interface DatabaseFacade {
    public ArrayList<Word> words = new ArrayList<Word>();
    public void addWord(Word word);
    public void updateWord(String word, Word word2);
    public void deleteWord(int ID);
    public Word getWord(int ID);
    public ArrayList<Word> getWords();
}
