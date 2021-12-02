package dal;

import java.io.IOException;

public class PlaylistDAO {

    private DatabaseConnector dbCon;

    public PlaylistDAO() throws IOException
    {
        dbCon = new DatabaseConnector();

    }
}
