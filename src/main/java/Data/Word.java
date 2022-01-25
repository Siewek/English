package Data;

import java.nio.charset.StandardCharsets;

public class Word {

    private final long id;
    private final String word;
    private final String translation;
    private final String difficulty;

    public Word(long id, String word, String translation, String difficulty)
    {
        this.id = id;
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
    public long getId() {
        return id;
    }
}
