package DesignPatterns;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

abstract class AbstractMusicPlayer {
    protected MediaPlayer player;

    public AbstractMusicPlayer(Media media) {
        player = new MediaPlayer(media);
    }

    public void play() {
        this.setUpBeforePlay();
        player.play();
    }
    public void stop() {
        player.stop();
    }

    protected abstract void setUpBeforePlay();
}

public class MusicPlayer extends AbstractMusicPlayer {
    public MusicPlayer(String path) {
        super(new Media(MusicPlayer.class.getResource(path).toString()));
    }

    @Override
    protected void setUpBeforePlay() {
        player.setVolume(0.02);
        player.setAutoPlay(true);
        player.setBalance(-1);

        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
                player.play();
            }
        });
    }
}
