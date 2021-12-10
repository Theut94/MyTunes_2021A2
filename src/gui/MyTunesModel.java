package gui;

import be.Playlist;
import be.Song;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

/**
 * In this class we acces the bll, to handle the connection from gui to bll.
 */
public class MyTunesModel
{
    private ObservableList<Song> songlist;
    private ObservableList<Playlist> playlistlist;
    private ObservableList<Song> playlistWithSongs;
    private LogicManager lm;

    public MyTunesModel() throws Exception{
        lm = new LogicManager();
        songlist= FXCollections.observableArrayList();
        songlist.addAll(lm.getAllSongs());
        playlistlist = FXCollections.observableArrayList();
        playlistlist.addAll(lm.getAllPlaylists());
        playlistWithSongs = FXCollections.observableArrayList();
    }

    public void playSong(Song song)
    {
        lm.playSong(song);

    }
    public void stopPlaying()
    {
        lm.stopPlaying();
    }

    public boolean isPlaying()
    {
        return lm.isPlaying();
    }
    public void nextSong(Song song){lm.nextSong(song);}

    public void previousSong( Song song)
    {
        lm.previousSong(song);
    }

    public void createSong(String name, String artistName, String filePath, String songLength) throws Exception
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
    public void deleteSong(Song song) throws Exception {
        lm.deleteSong(song);
        songlist.remove(song);
    }
    public void updateSong(Song song) throws SQLException {
        lm.updateSong(song);
        songlist.clear();
        songlist.addAll(lm.getAllSongs());

    }

    public ObservableList<Playlist> getAllPlaylists() throws Exception {
        return playlistlist;
    }

    public void createPlaylist (String name) throws Exception
    {
        lm.createPlaylist(name);
        playlistlist.clear();
        playlistlist.addAll(lm.getAllPlaylists());
    }

    public ObservableList<Song> getPlaylist (Playlist playlist) throws Exception
    {
        playlistWithSongs.addAll(lm.getPlaylist(playlist));
        return playlistWithSongs;
    }
    public void deletePlaylist (Playlist playlist)
    {
        playlistlist.remove(playlist);
        lm.deletePlaylist(playlist);
    }
    public void addToPlaylist(Playlist playlist, Song song) throws Exception
    {
        lm.addToPlaylist(playlist, song);
        playlistWithSongs.add(song);
        updatePlaylist(playlist);
    }
    public void removeFromPlaylist(Playlist playlist, Song song) throws  Exception
    {
        lm.removeFromPlaylist(playlist, song);
        playlistWithSongs.remove(song);
        updatePlaylist(playlist);
    }

    public void clearPlaylist(Playlist playlist) throws Exception
    {
        lm.clearPlaylist(playlist);
        playlistWithSongs.clear();
    }
    public void updatePlaylist(Playlist playlist) throws  Exception
    {
        lm.updatePlaylist(playlist);
        playlistlist.clear();
        playlistlist.addAll(lm.getAllPlaylists());
    }
    public void swapSongsInPlaylist(int i, int j) throws Exception {
        Collections.swap(playlistWithSongs, i, j);
        //bll.swapSongsInPlaylist(selectedPlaylist.getValue().getId(), playlistWithSongs.get(i).getId(), playlistWithSongs.get(j).getId()); // todo
    }
    public void timer(TimerTask t)
    {
        lm.timer(t);
    }
    public boolean isSongFinished()
    {
        return lm.isSongFinished();
    }

    public void setSongFinished(boolean b)
    {
        lm.setSongFinished(b);
    }
}


