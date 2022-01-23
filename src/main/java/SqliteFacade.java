import java.sql.*;
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
    public void updateWord(String word, Word word2) {
    String sql = "UPDATE words SET word = ?," + "translation = ?, " + "difficulty = ? " + "WHERE word = ?";
    //System.out.println(word2.getWordWord() +" "+ word2.getWordDifficulty() + " "+ word2.getWordTranslation());
    try{
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,word2.getWordWord());
        pstmt.setString(2,word2.getWordTranslation());
        pstmt.setString(3,word2.getWordDifficulty());
        pstmt.setString(4,word);
        pstmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void deleteWord(int ID) {
        String sql = "DELETE FROM words WHERE rowid = ?";
        try {
           PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Word getWord(int ID) {
        String sql = "select rowid, * from words";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int rowid = result.getInt("rowid");
                if(rowid ==ID)
                {
                String word = result.getString("word");
                String translation = result.getString("translation");
                String difficulty = result.getString("difficulty");
                    Word nextWord = new Word(word, translation, difficulty);
                    return nextWord;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Word> getWords() {
        String sql = "select rowid, * from words";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int rowid = result.getInt("rowid");
                String word = result.getString("word");
                String translation = result.getString("translation");
                String difficulty = result.getString("difficulty");

                Word nextWord = new Word(word,translation,difficulty);
                //System.out.println(word +" "+ translation + " "+difficulty );
                this.words.add(nextWord);
            }
            return this.words;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
