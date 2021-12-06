package bll;

import be.Playlist;
import be.Song;
import bll.util.MusicPlayer;
import bll.util.SongSearcher;
import dal.PlaylistDAO;
import dal.SongDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This is the class where we handle input from the Gui and  use methods from the dal and be, to return the right output.
 */
public class LogicManager
{
    SongDAO songDAO = new SongDAO();
    PlaylistDAO playlistDAO = new PlaylistDAO();
    SongSearcher songSearcher = new SongSearcher();
    MusicPlayer mp;

    public LogicManager() throws IOException
    {

    }

    /**
     * Methods for playing Songs
     */
    public void playSong(Song song)
    {
      mp.playSong(song.getFilePath());


    }
    public void stopPlaying()
    {
        mp.stopPlaying();
    }
    public boolean isPlaying()
    {
        return mp.isPlaying();
    }

    /**
     *Methods for SongDAO.
     */
    // here we create a song with the input from the gui, sending it to Dal.
    public Song createSong (String name, String artistName, String filePath, String songLength) throws Exception
    {
       return(songDAO.createSong(name, artistName, filePath, songLength));
    }
    //Here we get all songs from the dal.
    public List<Song> getAllSongs() throws SQLException
    {
        return (songDAO.getAllSongs());
    }
    // here we delete a song from the database.
    public void deleteSong(Song song)
    {
        songDAO.deleteSong(song);
    }
    //here we update a song in the database.
    public void updateSong(Song song)
    {
        songDAO.updateSong(song);
    }

    public List<Song> searchSongs(String query) throws Exception {
        List<Song> allSongs = getAllSongs();
        List<Song> searchedSongs= songSearcher.SearchSongs(allSongs, query);
        return searchedSongs;

    }

    /**
     * Methods for PlaylistDAO.
     */

    public List<Playlist> getAllPlaylists() throws Exception {
        return playlistDAO.getAllPlaylist();
    }
    public Playlist createPlaylist(String name) throws Exception
    {
       return playlistDAO.createPlaylist(name);
    }
    public List<Song> getPlaylist (Playlist playlist) throws Exception
    {
        return playlistDAO.getPlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist)
    {
        playlistDAO.deletePlaylist(playlist);
    }

    public void addToPlaylist(Playlist playlist, Song song) throws Exception
    {
        playlistDAO.addToPlaylist(playlist, song);
    }
    public void removeFromPlaylist (Playlist playlist, Song song) throws Exception
    {
        playlistDAO.removeFromPlaylist(playlist, song);
    }
    public void clearPlaylist(Playlist playlist) throws Exception
    {
        playlistDAO.clearPlaylist(playlist);
    }
    public void updatePlaylist (Playlist playlist) throws Exception
    {
        playlistDAO.updatePlaylist(playlist);
    }


}

