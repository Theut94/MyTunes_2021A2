package dal;


import be.Song;
import bll.util.ConvertTime;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

/**
 * This is where we access data for all songs.
 */
public class SongDAO {

    private DatabaseConnector DC;

    public SongDAO() throws IOException
    {
        DC = new DatabaseConnector();
    }

    public String filePathToURI(String filePath)
    {
        return "file:/" + filePath.replace("\\", "/");
    }

    // This is the method to create a song in the Database. This is also where the song gets an ID.
    public Song createSong(String songName, String artist, String filePath, String songLength) throws Exception
    {
        Connection con = DC.getConnection();

        String sql = "INSERT INTO songsTable (songName,artist,filePath,songLength) VALUES (?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, songName);
        ps.setString(2, artist);
        ps.setString(3, filePathToURI(filePath));
        ps.setInt(4, ConvertTime.timeToSec(songLength));

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                Song song = new Song(id, songName, artist, filePath, songLength);
                return song;
            }

        }
        return null;
    }

    // This is the method to get all available songs in the database.
    public ObservableList<Song> getAllSongs() throws SQLException
    {
        try(Connection connection = DC.getConnection())
        {
            String sql = "SELECT * FROM songsTable;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            ObservableList<Song> allSongs = FXCollections.observableArrayList();

            while(rs.next())
            {
                int song??D = rs.getInt("songID");
                String songName = rs.getString("songName");
                String artistName = rs.getString("artist");
                String filePath = rs.getString("filePath");
                String songLength = ConvertTime.secToTime(rs.getInt("songLength"));

                Song song = new Song(song??D, songName,artistName,filePath,songLength);
                allSongs.add(song);
            }

            return allSongs;
        }
    }

    public void deleteSong(Song song)
    {
        String sql1 = "DELETE FROM playlistContentTable WHERE songID = (?);";
        String sql2 = "DELETE FROM songsTable WHERE songID = (?);";

        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);

            ps1.setInt(1, song.getSongId());
            ps2.setInt(1, song.getSongId());
            ps1.executeUpdate();
            ps2.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateSong(Song song)
    {

        String sql = "UPDATE songsTable SET songName= (?), artist=(?), songLength=(?), filePath=(?) WHERE songID = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, song.getName());
            statement.setString(2, song.getArtistName());
            statement.setInt(3,ConvertTime.timeToSec(song.getSongLength()));
            statement.setString(4, filePathToURI(song.getFilePath()));
            statement.setInt(5,song.getSongId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
