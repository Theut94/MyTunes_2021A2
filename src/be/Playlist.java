package be;

import bll.util.ConvertTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private List<Song> listOfSongs = new ArrayList();
    private IntegerProperty playlistSongCount = new SimpleIntegerProperty();
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

    public String getPlaylistName() {
        return playlistName.get();
    }

    public int getPlaylistSongCount() {
        return playlistSongCount.get();
    }

    public int getPlaylistId() {
        return playlistId;
    }

    private void updatePlaylistSongCount()
    {
        int i = 0;
        for (Song song: listOfSongs) {
            if (song != null)
            {
                i++;
            }
        }
        this.playlistSongCount.set(i);
    }

    private void updatePlaylistTimeLength()
    {
        List<String> times = new ArrayList<>();
        for (Song song: listOfSongs) {
            if (song != null)
            {
                times.add(song.getSongLength());
            }
        }
        this.playlistTimelength.set(ConvertTime.sumTime(times));
    }

    public void updatePlaylist(){ updatePlaylistTimeLength(); updatePlaylistSongCount(); }
}
