package DesignPatterns;

import Data.Word;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SqliteFacade implements DatabaseFacade {
    private static String jdbcUrl = "jdbc:sqlite:wordsdb.db";
    private final Connection connection;

    public SqliteFacade() throws SQLException {
        Properties connectionProperties = new Properties();
        connectionProperties.put("charSet", "UTF8");
        connectionProperties.put("encoding", "UTF8");
        connection= DriverManager.getConnection(jdbcUrl, connectionProperties);
    }

    @Override
    public void addWord(Word word) {
        String sql = "insert into words values('"+word.getWord()+"','"+word.getTranslation()+"','"+word.getDifficulty()+"')";
        Statement statement = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql,statement.RETURN_GENERATED_KEYS);
            pstmt.execute();
            ResultSet result = pstmt.getGeneratedKeys();
            int generatedKey = 0;
            if(result.next())
            {
                generatedKey = result.getInt(1); // ID świeżo dodanego rzędu
            }
            System.out.println("RowID of the newly added word is: " + generatedKey);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       /* try {
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
        }*/


    }

    @Override
    public void updateWord(String word, Word word2) {
    String sql = "UPDATE words SET word = ?," + "translation = ?, " + "difficulty = ? " + "WHERE word = ?";
    //System.out.println(word2.getWordWord() +" "+ word2.getWordDifficulty() + " "+ word2.getWordTranslation());
    try{
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,word2.getWord());
        pstmt.setString(2,word2.getTranslation());
        pstmt.setString(3,word2.getDifficulty());
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
    public ArrayList<Word> getAllWords() {
        String sql = "select rowid, * from words";
        words.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int rowid = result.getInt("rowid");
                String word = result.getString("word");
                String translation = result.getString("translation");
                String difficulty = result.getString("difficulty");

                Word nextWord = new Word(word,translation,difficulty);
                //System.out.println(nextWord.getWord() +" "+ nextWord.getTranslation() + " "+difficulty );
                this.words.add(nextWord);
            }
            return this.words;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public ArrayList<Word> getWordsOfDifficulty(String wantedDifficutly)
    {String sql = "select rowid, * from words WHERE difficulty = ?";
        try{
            words.clear();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,wantedDifficutly);
            ResultSet result = pstmt.executeQuery();
            while (result.next())
            {
                int rowid = result.getInt("rowid");
                String word = result.getString("word");
                String translation = result.getString("translation");
                String difficulty = result.getString("difficulty");
                Word nextWord = new Word(word,translation,difficulty);
                //System.out.println(nextWord.getWord() +" "+ nextWord.getTranslation() + " "+difficulty );
                this.words.add(nextWord);
            }
            return this.words;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
