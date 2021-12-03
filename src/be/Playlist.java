package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we describe the Playlist class, and what it can do.
 */
public class Playlist
{
    private StringProperty playlistName = new SimpleStringProperty();
    private List listOfSongs = new ArrayList();
    private IntegerProperty playlistSongCount;
    private StringProperty playlistTimelength = new SimpleStringProperty();
    private int playlistId;

    public Playlist(int playlistID, String playlistName)
    {
        this.playlistId = playlistID;
        this.playlistName.setValue(playlistName);

    }
    /*
    Beneath we have getters for all the properties of the class, and a single setter for the playlist name.
     */
    /*
    Here we want to return the list of songs
     */
    public List<Song> getListOfSongs()
    {
        return listOfSongs;
    }
    public void addSongToList(Song song)
    {
        listOfSongs.add(song);
    }
    public void removeSong(Song song)
    {
        listOfSongs.remove(song);
    }

    public String getPlaylistName() {
        return playlistName.get();
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.set(playlistName);
    }

    public String getPlaylistTimelength() {
        return playlistTimelength.get();
    }

    public int getPlaylistSongCount() {
        return playlistSongCount.get();
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistSongCount(int playlistSongCount) {
        this.playlistSongCount.set(playlistSongCount);
    }

    public void setPlaylistTimelength(String playlistTimelength) {
        this.playlistTimelength.set(playlistTimelength);
    }

    public void setListOfSongs(List listOfSongs) {
        this.listOfSongs = listOfSongs;
    }
}
