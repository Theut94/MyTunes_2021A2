package bll;

import be.Song;
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

    public LogicManager() throws IOException
    {
    }

    /**
     *Methods for SongDAO.
     */
    // here we create a song with the input from the gui, sending it to Dal and we need to add it to the observablelist ?
    public Song createSong (String name, String artistName, String filePath, Integer songLength) throws Exception
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

}
