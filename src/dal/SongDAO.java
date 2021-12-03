package dal;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is where we access data for all songs.
 */
public class SongDAO {

    private DatabaseConnector DC;

    public SongDAO() throws IOException
    {
        DC = new DatabaseConnector();
    }

    public static void main(String[] args) throws Exception {
        Song song1 = new Song(1,"Let It Go 2 Electric Boogaloo", "Frozen 2: The cooler Frozen","still none");
        SongDAO DAO = new SongDAO();
        DAO.createSong(song1.getName(),song1.getArtistName(),song1.getFilePath(),1);
        //System.out.println(SDAO.getAllSongs());
    }

    // This is the method to create a song in the Database. This is also where the song gets an ID.
    public Song createSong(String songName, String artist, String filePath, Integer songLength) throws Exception
    {
        Connection con = DC.getConnection();

        String sql = "INSERT INTO songsTable (songName,artist,filePath) VALUES (?,?,?);";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, songName);
        ps.setString(2, artist);
        ps.setString(3, filePath);

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                Song song = new Song(id, songName, artist, filePath);
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
                int songÍD = rs.getInt("songID");
                String songName = rs.getString("songName");
                String artistName = rs.getString("artist");
                String filePath = rs.getString("filePath");

                Song song = new Song(songÍD, songName,artistName,filePath);
                allSongs.add(song);
            }

            return allSongs;
        }
    }

    public void deleteSong(Song song)
    {

        String sql1 = "DELETE WHERE songID = (?) FROM playlistContentTable;";
        String sql2 = "DELETE WHERE songID = (?) FROM songsTable;";

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

        String sql = "UPDATE songsTable SET songName= (?), artist=(?), time=(?) WHERE songID = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, song.getName());
            statement.setString(2, song.getArtistName());
            statement.setInt(3,song.getSongLength());
            statement.setInt(4,song.getSongId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
