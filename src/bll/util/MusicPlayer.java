package bll.util;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * In this class, we have the methods for playing songs.
 */
public class MusicPlayer
{
    private Media media;
    private MediaPlayer mp;
    private boolean isPlaying = false;
    private Duration timeWhenPaused;
    private boolean hasBeenPaused;

    public MusicPlayer()
    {
    }
    //We update the media
    public void playSong(String file)
    {
        media = new Media(file);
        mp = new MediaPlayer(media);
       if(!hasBeenPaused)
       {mp.play();
        mp.setAutoPlay(true);
        isPlaying = true;
       hasBeenPaused = false;}
      else {
           mp.setStartTime(timeWhenPaused);
           mp.play();
           hasBeenPaused = false;
           isPlaying = true;
       }


    }

    public  void stopPlaying()
    {
        timeWhenPaused= mp.getCurrentTime();
        mp.pause();
        isPlaying = false;
        hasBeenPaused = true;

    }

    public void nextSong()
    {
        mp.setOnEndOfMedia(null);
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

}
