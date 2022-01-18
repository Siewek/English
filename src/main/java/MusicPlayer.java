import javafx.scene.media.MediaPlayer;

public abstract class MusicPlayer {

protected MediaPlayer media;
protected String filePath;

public void Play(MediaPlayer m)
{
m.play();
}
public void Stop(MediaPlayer m)
{
m.stop();
}
    boolean track1() {return true;}
    boolean track2(){return true;}
    abstract void playTrack1();
    abstract void playTrack2();

    protected void setUpBeforePlay()
    {
    if(track1())
    {
        playTrack1();
    }
    if (track2())
    {
        playTrack2();
    }
    }
}
