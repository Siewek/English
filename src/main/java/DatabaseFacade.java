import java.lang.reflect.Array;
import java.util.ArrayList;

public interface DatabaseFacade {
    public void addWord(Word word);
    public void updateWord(Word word);
    public void deleteWord(int ID);
    public Word getWord(int ID);
    public ArrayList<Word> getWords();
}
