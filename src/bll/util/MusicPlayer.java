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
    private Media oldMedia;

    public MusicPlayer()
    {
    }

    //We update the media and play the file.
    public void playSong(String file)
    {
        try{
            media = new Media(file);
            mp = new MediaPlayer(media);
        } catch (Exception e) {
            e.printStackTrace();
        }

       if(!hasBeenPaused || !oldMedia.getSource().equals(media.getSource()))
       {
           mp.play();
           mp.setAutoPlay(true);
           isPlaying = true;
           hasBeenPaused = false;

       }

       else
       {
           mp.setStartTime(timeWhenPaused);
           mp.play();
           hasBeenPaused = false;
           isPlaying = true;
       }
    }

    // we pause and save the duration we are at.
    public  void stopPlaying()
    {
        timeWhenPaused= mp.getCurrentTime();
        oldMedia = mp.getMedia();
        mp.pause();
        isPlaying = false;
        hasBeenPaused = true;

    }

    //we get the song after the on we are listening to, and reset the mediaplayer with this song.
    // mp = null; because the garbage collector will clean up the old mp.
    public void nextSong(String nextSongFile)
    {
        mp.pause();
        mp = null;
        isPlaying = false;
        playSong(nextSongFile);
    }

    //we get the song prior to the on we are listening to, and reset the mediaplayer with this song.
    public void previousSong(String previousSongFile)
    {
        mp.pause();
        mp = null;
        isPlaying = false;
        playSong(previousSongFile);
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

}
