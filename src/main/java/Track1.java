import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Track1 extends MusicPlayer{
    boolean track2(){return false;}
    @Override
    void playTrack1() {

    filePath = ""; //         INPUT YOUR OWN TRACK!!!!!!         "G:\\Studia\\Semestr 5\\ZTP\\English\\src\\main\\resources\\Darkling.mp3";
    media = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
    Play(media);
    }

    @Override
    void playTrack2() {

    }

}
