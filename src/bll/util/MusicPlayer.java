package bll.util;

import be.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * In this class, we have the methods for playing songs.
 */
public class MusicPlayer
{
    private Media media;
    private MediaPlayer mp;
    private boolean isPlaying = false;

    public MusicPlayer()
    {




    }

    public void playSong(String file)
    {
        media = new Media(file);
        mp = new MediaPlayer(media);
        mp.play();
        isPlaying = true;
    }
    public  void stopPlaying()
    {
        mp.stop();
        isPlaying = false;
    }
    public void nextSong()
    {
    }
    public boolean isPlaying()
    {
        return isPlaying;
    }

}
