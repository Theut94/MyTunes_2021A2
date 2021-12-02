package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is where we access data for all songs.
 */
public class SongDAO
{
    DatabaseConnector DC = new DatabaseConnector();


    public SongDAO() throws IOException
    {


    }

    public static void main(String[] args) throws SQLException, IOException {
        SongDAO SDAO = new SongDAO();
        System.out.println(SDAO.getAllSongs());
    }

    // This is the method to get all available songs in the database.
    public List<Song> getAllSongs() throws SQLException
    {
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = DC.getConnection()) {
            String sql = "SELECT * FROM songsTable;";

            Statement statement = connection.createStatement();
            if (statement.execute(sql))
            {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next())
                {
                    int songÍD = resultSet.getInt("songID");


                    String songName = resultSet.getString("songName");

                    String artistName = resultSet.getString("artist");

                    String filePath = resultSet.getString("filePath");

                    //int songlength = 1337;

                    Song song = new Song(songÍD, songName,artistName,filePath);
                    allSongs.add(song);

                }

            }

        }
        return allSongs;
    }

    // This is the method to create a song in the Database. This is also where the song gets an ID.
    public Song createSong(String name, String artistName, String filePath, Integer songLength) throws Exception
    {   
        int songId = -1;

        
        String sql = "INSERT INTO songsTable VALUES (?,?,?,?);";
        try (Connection connection = DC.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //statement.setString(parameterindex, parameter) for alle parametre tabellen skal bruge.
            //statement.executeUpdate(); for at få indsætte parametrene.
            try (ResultSet generatedKeys = statement.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    songId = (int) generatedKeys.getInt("songID");
                }
                else
                    throw new SQLException("No ID obtained for song");
            }
        }
        if ( songId != -1)
        return new Song(songId, name, artistName,filePath);
        else return null;
    }

    public void deleteSong(Song song)
    {
        String sql1 = "DELETE WHERE songID = (?) FROM playlistContentTable;";

        String sql2 = "DELETE where songID = (?) FROM songsTable;";

        try(Connection connection = DC.getConnection())
        {
            PreparedStatement statement1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            statement1.setInt(1, song.getSongId());
            statement1.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
            statement2.setInt(1, song.getSongId());
            statement2.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateSong(Song song)
    {
        String sql = "UPDATE songsTable SET songName= (?), artist=(?),time=(?) WHERE songID = (?);";
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
