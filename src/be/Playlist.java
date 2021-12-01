package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we describe the Playlist class, and what it can do.
 */
public class Playlist
{
    private StringProperty playlistName;
    private List listOfSongs;
    private IntegerProperty playlistSongCount;
    private IntegerProperty playlistTimelength;
    private int playlistId;

    public Playlist(StringProperty playlistName)
    {
        this.playlistName = playlistName;

    }

    /*
    Here we want to return the list of songs
     */
    public List<Song> getListOfSongs()
    {
        return listOfSongs;
    }

    public String getPlaylistName() {
        return playlistName.get();
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.set(playlistName);
    }

    public int getPlaylistTimelength() {
        return playlistTimelength.get();
    }

    public int getPlaylistSongCount() {
        return playlistSongCount.get();
    }

    public int getPlaylistId() {
        return playlistId;
    }
}
