package dal;

import be.Playlist;
import be.Song;
import bll.util.ConvertTime;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Console;
import java.io.IOException;
import java.sql.*;
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
        Connection connection = DC.getConnection();

        String sql = "INSERT INTO playlistTable (playlistName) VALUES (?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        try (Connection connection = DC.getConnection())
        {
            String sql = "SELECT * FROM playlistTable;";
            Statement statement = connection.createStatement();
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
                playlist.updatePlaylist();

                // add the complete playlist to the list of playlists
                allPlaylist.add(playlist);
            }

            return allPlaylist;
        }
    }

    //returns a single playlist with its songs
    //@param playlist
    //@return a List of songs
    public List<Song> getPlaylist(Playlist playlist) throws Exception
    {
        Connection connection = DC.getConnection();
        int p_id = playlist.getPlaylistId();

        String sql = "SELECT s.songID, s.songName , s.artist, s.filePath, s.songLength FROM songsTable s, playlistContentTable pc WHERE s.songID = pc.songID AND pc.playlistID ="+ p_id +";";

        Statement ps = connection.createStatement();
        ResultSet rs = ps.executeQuery(sql);
        ArrayList<Song> playlistWithSongs = new ArrayList<>();
        while (rs.next())
        {
            int id = rs.getInt("songID");
            String title = rs.getString("songName");
            String artist = rs.getString("artist");
            String source = rs.getString("filePath");
            String length = ConvertTime.secToTime(rs.getInt("songLength"));

            Song med = new Song(id, title, artist, source, length);

            playlistWithSongs.add(med);

        }
        return playlistWithSongs;
    }

    //Deletes a playlist
    //@param playlist
    public void deletePlaylist(Playlist playlist)
    {
        int pId = playlist.getPlaylistId();

        String sql1 = "DELETE FROM playlistContentTable WHERE playlistID = (?); ";
        String sql2 = "DELETE FROM playlistTABLE WHERE playlistID=(?);";

        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);

            ps1.setInt(1, pId);
            ps2.setInt(1, pId);
            ps1.executeUpdate();
            ps2.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Adds a song to a playlist
    //@param playlist
    //@Param song
    public void addToPlaylist(Playlist playlist, Song song) throws Exception
    {

        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();
        int meId = song.getSongId();

        String sql = "INSERT INTO playlistContentTable (playlistID , songID) VALUES ((?), (?)); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);
        pst.setInt(2, meId);

        pst.executeUpdate();

    }

    //removes a song from a single playlist
    //@param playlist
    //@param song
    public void removeFromPlaylist(Playlist playlist, Song song) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();
        int meId = song.getSongId();

        String sql = "DELETE FROM playlistContentTable WHERE playlistID = (?) AND songID=(?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);
        pst.setInt(2, meId);

        pst.executeUpdate();

    }

    //removes all songs from a single playlist
    //@param playlist
    public void clearPlaylist(Playlist playlist) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();

        String sql = "DELETE FROM playlistContentTable WHERE playlistID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);

        pst.executeUpdate();

    }

    //updates a single playlist with is new name
    //@param playlist
    public void updatePlaylist(Playlist playlist) throws Exception
    {
        Connection connection = DC.getConnection();

        int pId = playlist.getPlaylistId();
        String name = playlist.getPlaylistName();

        String sql = "UPDATE playlistTable SET playlistName = (?) WHERE playlistID = (?);";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, name);
        pst.setInt(2, pId);

        pst.executeUpdate();

    }


    public static void main(String[] args) throws Exception {
        PlaylistDAO DAO = new PlaylistDAO();
        //Playlist playlist = new Playlist(1,"Power Metal");
        Playlist playlist2 = new Playlist(1,"TestPlaylist");
        //List<Song> songs = DAO.getPlaylist(playlist);
        DAO.createPlaylist(playlist2.getPlaylistName());
        //DAO.deletePlaylist(playlist2);
        for (Playlist p: DAO.getAllPlaylist() ) {
            System.out.println(p.getPlaylistName());
        }
        //for (Song s: songs) {
        //    System.out.println(s.getName() + " : " + s.getArtistName());
        //}
    }

}
