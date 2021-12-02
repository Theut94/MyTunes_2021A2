package dal;

import be.Song;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public Song createSong(String name, String artistName, String filePath, Integer songLength) throws Exception
    {   
        int songId = -1;

        
        String sql = "INSERT INTO (TABLENAME) VALUES (?,?,?,?);";
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

}
