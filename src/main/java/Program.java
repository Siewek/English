import javafx.embed.swing.JFXPanel;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Program extends JFrame {

    public static void main(String[] args) throws SQLException, FileNotFoundException {


       // String jdbcUrl = "jdbc:sqlite:wordsdb.db";
       // Connection connection = DriverManager.getConnection(jdbcUrl);

        /* String sql = "create table words (word varchar(20), translation varchar(20), difficulty varchar(10))";
         Statement statement = connection.createStatement();
        statement.executeUpdate(sql);*/

     /*  String sql = "insert into words values('bad', 'zły', 'easy')";
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(sql);
        if(rows > 0)
        {
            System.out.println("row created");
        }*/

       /* String sql = "select rowid, * from words";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while(result.next())
        {
            int rowid = result.getInt("rowid");
            String word = result.getString("word");
            String translation = result.getString("translation");
            String difficulty = result.getString("difficulty");

            System.out.println(rowid + " " + word + " " + translation + " "+ difficulty);
        }*/


        /*ScriptRunner sr = new ScriptRunner(connection);
        sr.setEscapeProcessing(false);
        Reader reader = new BufferedReader(new FileReader("src\\main\\resources\\script.sql"));
        sr.runScript(reader);*/
       new Program();
    }
    final JFXPanel fxPanel = new JFXPanel();
    private JButton saveBut,undoBut,redoBut, musicBut;
    private Question question = new Question(); // !!! implement what exactly the question is later !!!
    private MusicPlayer musicPlayer = new Track2();
    private SqliteFacade db = new SqliteFacade();
    Caretaker caretaker = new Caretaker();
    QuestionOriginator originator = new QuestionOriginator();

    int saveFiles = 0; // how many questions saved
    int questionIndex = 0; //which question is displayed on the screen
    public Program() throws SQLException {
        this.setSize(750,780);
        this.setTitle("English"); //!!!!!!!!!!!!!!!!!!! Change later
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        musicPlayer.setUpBeforePlay();
        JPanel panel1 = new JPanel();

        panel1.add(new JLabel("Question"));
        
        //add the question here

        ButtonListener saveListener = new ButtonListener();
        ButtonListener undoListener = new ButtonListener();
        ButtonListener redoListener = new ButtonListener();
        ButtonListener changeMusic = new ButtonListener();

        saveBut = new JButton("Save");
        saveBut.addActionListener(saveListener);
        undoBut = new JButton("Undo");
        undoBut.addActionListener(undoListener);
        redoBut = new JButton("Redo");
        redoBut.addActionListener(redoListener);
        musicBut = new JButton("Change Music");
        musicBut.addActionListener(changeMusic);

        panel1.add(saveBut);
        panel1.add(undoBut);
        panel1.add(redoBut);
        panel1.add(musicBut);

        undoBut.setEnabled(false);
        redoBut.setEnabled(false);
        this.add(panel1);
        this.setVisible(true);
        //panel1.add(new JLabel("Question"));
    }

    class ButtonListener implements ActionListener{
        ButtonListener() throws SQLException {
        }

        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == saveBut)
            {
                Question currentQuestion = question.getQuestion();

                originator.setQuestion(currentQuestion);

                caretaker.addMemento(originator.createMemento());

                saveFiles++;
                questionIndex++;

            }
            else if(e.getSource() == undoBut)
            {
                if(questionIndex>=1){
                    questionIndex--;
                    Question currentQuestion = originator.restoreMemento(caretaker.getMemento(questionIndex));

                    question.setQuestion(currentQuestion);

                    redoBut.setEnabled(true);

                } else undoBut.setEnabled(false);

            }
            else  if(e.getSource() == redoBut)
            {
                if(saveFiles - 1 > questionIndex)
                {
                    questionIndex++;
                    Question currentQuestion = originator.restoreMemento(caretaker.getMemento(questionIndex));

                    question.setQuestion(currentQuestion);
                    undoBut.setEnabled(true);
                }
                else redoBut.setEnabled(false);
            }
            else if(e.getSource() == musicBut)
            {   musicPlayer.Stop(musicPlayer.media);
                if(musicPlayer.track1())
                {
                    musicPlayer = new Track2();
                    musicPlayer.setUpBeforePlay();
                }
                else  if(musicPlayer.track2())
                {
                    musicPlayer = new Track1();
                    musicPlayer.setUpBeforePlay();
                }
                //db.deleteWord(2);
                Word updatetest = new Word("balls","jajka","easy");
               // db.updateWord("young",updatetest);
                ArrayList<Word> test = db.getWords();
                for(int i = 0; i < test.stream().count() -1; i++)
                {
                    System.out.println(test.get(i).getWordWord() + " "+test.get(i).getWordDifficulty()+ " " +test.get(i).getWordTranslation());
                }

            }
        }

    }


}
