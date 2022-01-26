package Data;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && Objects.equals(translation, word1.translation) && Objects.equals(difficulty, word1.difficulty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, translation, difficulty);
    }
}
