package dal;

import be.Song;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SongDAO
{
    DatabaseConnector DC = new DatabaseConnector();


    public SongDAO() throws IOException
    {

    }

    public Song createSong(StringProperty name, StringProperty artistName, String filePath, IntegerProperty songLength) throws Exception
    {   String sql = "INSERT INT (TABLENAME) VALUES (?,?,?,?);";
        try (Connection connection = DC.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //statement.setString(parameterindex, parameter) for alle parametre tabellen skal bruge.
            //statement.executeUpdate(); for at få indsætte parametrene.
        }
        return new Song(name, artistName,filePath,songLength);
    }
}
