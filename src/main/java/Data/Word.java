package Data;

import java.nio.charset.StandardCharsets;

public class Word {

    private final String word;
    private final String translation;
    private final String difficulty;

    public Word(String word, String translation, String difficulty)
    {
        this.word = word;
        this.translation = translation;
        this.difficulty = difficulty;
    }

    public String getWord() {
        return this.word;
    }
    public String getTranslation() {
        return this.translation;
    }
    public String getDifficulty()
    {
        return this.difficulty;
    }
}
