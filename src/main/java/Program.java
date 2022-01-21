import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Program extends JFrame {

    public static void main(String[] args){
        new Program();
    }
    final JFXPanel fxPanel = new JFXPanel();
    private JButton saveBut,undoBut,redoBut, musicBut;
    private Question question = new Question(); // !!! implement what exactly the question is later !!!
    private MusicPlayer musicPlayer = new Track2();
    Caretaker caretaker = new Caretaker();
    QuestionOriginator originator = new QuestionOriginator();

    int saveFiles = 0; // how many questions saved
    int questionIndex = 0; //which question is displayed on the screen
    public Program(){
        this.setSize(750,780);
        this.setTitle("Memento Test"); //!!!!!!!!!!!!!!!!!!! Change later
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
            }
        }
    }
}
