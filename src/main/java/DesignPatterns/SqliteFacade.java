package DesignPatterns;

import Data.Word;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SqliteFacade {
    private static String jdbcUrl = "jdbc:sqlite:wordsdb.db";
    private final Connection connection;

    public ArrayList<Word> words = new ArrayList<Word>();

    public SqliteFacade() throws SQLException {
        Properties connectionProperties = new Properties();
        connectionProperties.put("charSet", "UTF8");
        connectionProperties.put("encoding", "UTF8");
        connection= DriverManager.getConnection(jdbcUrl, connectionProperties);
    }

    public int addWord(Word word) {
        String sql = "insert into words values('"+word.getWord()+"','"+word.getTranslation()+"','"+word.getDifficulty()+"')";
        Statement statement = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql,statement.RETURN_GENERATED_KEYS);
            pstmt.execute();
            ResultSet result = pstmt.getGeneratedKeys();
            int generatedKey = 0;
            if(result.next())
                generatedKey = result.getInt(1); // ID świeżo dodanego rzędu

            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void updateWord(int id, Word word2) {
        String sql = "UPDATE words SET word = ?," + "translation = ?, " + "difficulty = ? " + "WHERE rowid = ?";
        //System.out.println(word2.getWordWord() +" "+ word2.getWordDifficulty() + " "+ word2.getWordTranslation());
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,word2.getWord());
            pstmt.setString(2,word2.getTranslation());
            pstmt.setString(3,word2.getDifficulty());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                    Word nextWord = new Word(rowid, word, translation, difficulty);
                    return nextWord;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

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

                Word nextWord = new Word(rowid, word,translation,difficulty);
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
                Word nextWord = new Word(rowid, word,translation,difficulty);
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
