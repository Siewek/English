public class Word {

    private String word;

    private String translation;

    private String difficulty;

    public Word(String word, String translation, String difficulty)
    {
        this.word = word;
        this.translation = translation;
        this.difficulty = difficulty;
    }

    public String getWordWord()
    {
        return this.word;
    }
    public String getWordTranslation()
    {
        return this.translation;
    }
    public String getWordDifficulty()
    {
        return this.difficulty;
    }
}
