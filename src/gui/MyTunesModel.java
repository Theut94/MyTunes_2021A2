package gui;

import be.Song;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * In this class we acces the bll, to handle the connection from gui to bll.
 */
public class MyTunesModel
{
    private ObservableList<Song> songlist;
    private LogicManager lm;

    public MyTunesModel() throws Exception{
        lm = new LogicManager();
        songlist= FXCollections.observableArrayList();
        songlist.addAll(lm.getAllSongs());
    }

    public void createSong(String name, String artistName, String filePath, Integer songLength) throws Exception
    {
        songlist.add(lm.createSong(name, artistName, filePath, songLength));
    }

    public ObservableList<Song> getSonglist() {
        return songlist;
    }

    public void searchSongs(String query) throws Exception {
        List<Song> searchedSongs = lm.searchSongs(query);
        songlist.clear();
        songlist.addAll(searchedSongs);
    }
    public void deleteSong(Song song)
    {
        lm.deleteSong(song);
        songlist.remove(song);
    }
    public void updateSong(Song song)
    {
        lm.updateSong(song);
    }

}
