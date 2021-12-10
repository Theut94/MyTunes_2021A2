package bll.util;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

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

    private Timer timer;
    private double timeElapsed;
    private double timeRemaning;
    private boolean isSongFinished = false;



    public MusicPlayer()
    {
        timer=new Timer();
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

    public TimerTask setElapsedTime()
    {
        TimerTask t = new TimerTask()
        {
            @Override
            public void run() {
                if (mp != null) {
                    timeElapsed = mp.getCurrentTime().toSeconds();
                }
            }
        };
        return t;
    }
    public TimerTask setTimeRemaning()
    {
        TimerTask t= new TimerTask() {
            @Override
            public void run() {
                if(timeElapsed != 0.00 && mp != null)
                {
                    timeRemaning= mp.getTotalDuration().toSeconds()-timeElapsed;
                }
            }
        };
        return t;
    }

    public void setSongFinished(boolean b)
    {
        isSongFinished=b;
    }
    public TimerTask isSongFinishedTask() {

        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                if(timeRemaning <=0.00 && isPlaying)
                {
                    isSongFinished = true;
                }
            }
        };

        return t;
    }
    public boolean isSongFinished()
    {
        return isSongFinished;
    }
    public void timer(TimerTask t)
    {
        timer.scheduleAtFixedRate(setElapsedTime(), 1000,1000);
        timer.scheduleAtFixedRate(setTimeRemaning(), 1000,1000);
        timer.scheduleAtFixedRate(isSongFinishedTask(), 1000, 3000);
        timer.scheduleAtFixedRate(t, 1000, 1000);

    }

}
