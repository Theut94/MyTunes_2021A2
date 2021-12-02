package bll;

import be.Song;
import dal.SongDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This is the class where we handle input from the Gui and  use methods from the dal and be, to return the right output.
 */
public class LogicModel
{
    SongDAO songDAO = new SongDAO();

    public LogicModel() throws IOException
    {
    }
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
}
