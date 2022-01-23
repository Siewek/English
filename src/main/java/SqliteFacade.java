import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqliteFacade implements DatabaseFacade{

    public SqliteFacade() throws SQLException {
    }
    private String jdbcUrl = "jdbc:sqlite:wordsdb.db";
    private Connection connection = DriverManager.getConnection(jdbcUrl);


    @Override
    public void addWord(Word word) {
        String sql = "insert into words values('"+word.getWordWord()+"','"+word.getWordTranslation()+"','"+word.getWordDifficulty()+"')";
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int rows = 0;
        try {
            rows = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rows > 0)
        {
            System.out.println("row created");
        }
    }

    @Override
    public void updateWord(Word word) {

    }

    @Override
    public void deleteWord(int ID) {

    }

    @Override
    public Word getWord(int ID) {
        return null;
    }

    @Override
    public ArrayList<Word> getWords() {
        return null;
    }
}
