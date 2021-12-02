package dal;

import be.Playlist;
import be.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private DatabaseConnector DC;

    public PlaylistDAO() throws IOException
    {
        DC = new DatabaseConnector();
    }

    //creates a new playlist
    //@param name
    //@return Playlist
    public Playlist createPlaylist(String name) throws Exception
    {
        Connection con = DC.getConnection();

        String sql = "INSERT INTO playlistTable VALUES (?);";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                Playlist playlist = new Playlist(id, name);
                return playlist;
            }

        }
        return null;
    }

    //returns an ObservableList with playlists from playlist table
    //@return ObservableList with playlists
    public ObservableList<Playlist> getAllPlaylist() throws Exception
    {
        try (Connection con = DC.getConnection())
        {
            String sql = "SELECT * FROM playlistTable;";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // new empty list to hold all playlists
            ObservableList<Playlist> allPlaylist = FXCollections.observableArrayList();

            // get all playlists from database and put them in the list of playlists
            while (rs.next())
            {
                int id = rs.getInt("playlistID");
                String name = rs.getString("playlistName");

                // save the playlist data in a new playlist object
                Playlist playlist = new Playlist(id, name);

                // get songs in the playlist and put them in the playlists songlist
                for (Song song : getPlaylist(playlist)) {
                    playlist.addSongToList(song);
                }

                // add the complete playlist to the list of playlists
                allPlaylist.add(playlist);
            }

            return allPlaylist;
        }
    }

    //returens a single playlist with its songs
    //@param playlist
    //@return a List of medias
    public List<Song> getPlaylist(Playlist playlist) throws Exception
    {
        Connection con = DC.getConnection();
        int p_id = playlist.getPlaylistId();

        String sql = "select s.songID, s.songName , s.artist, s.filePath from songsTable s, playlistContentTable pc where s.songID  = pc.songID  and pc.playlistID ="+ p_id +";";

        Statement ps = con.createStatement();
        ResultSet rs = ps.executeQuery(sql);
        ArrayList<Song> playlistWithSongs = new ArrayList<>();
        while (rs.next())
        {
            int id = rs.getInt("songID");
            String title = rs.getString("songName");
            String artist = rs.getString("artist");
            String source = rs.getString("filePath");

            Song med = new Song(id, title, artist, source);

            playlistWithSongs.add(med);

        }
        return playlistWithSongs;
    }


    //updates a single playlist with is new name
    //@param playlist
    public void updatePlaylist(Playlist playlist) throws Exception
    {
        Connection con = DC.getConnection();

        int pId = playlist.getPlaylistId();
        String name = playlist.getPlaylistName();

        String sql = "Update playlist set playlistName = (?) where playlistID = (?);";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, name);
        pst.setInt(2, pId);

        pst.executeUpdate();
        pst.close();
    }


    public static void main(String[] args) throws Exception{
        PlaylistDAO DAO = new PlaylistDAO();
        Playlist playlist = new Playlist(1,"Power Metal");
        List<Song> songs = DAO.getPlaylist(playlist);
        for (Song s: songs) {
            System.out.println(s.getName() + " : " + s.getArtistName());
        }
        DAO.createPlaylist("TestPlaylist");
    }
}
