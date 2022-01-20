import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Track2 extends MusicPlayer{
    boolean track1(){return false;}
    @Override
    void playTrack1() {

    }

    @Override
    void playTrack2() {
        filePath = "src\\main\\resources\\Glitter Blast.mp3";
        media = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
        Play(media);
    }
}
