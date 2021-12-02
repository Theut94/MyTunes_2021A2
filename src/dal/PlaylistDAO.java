package dal;

import be.Playlist;
import be.Song;

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


    //updates a singel playlist with is new name
    //@param playlist
    public void updatePlaylist(Playlist playlist) throws Exception
    {
        Connection con = DC.getConnection();

        int pId = playlist.getPlaylistId();
        String name = playlist.getPlaylistName();

        String sql = "Update playlist set name = (?) where id = (?);";

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
            System.out.println(s.getName());
        }
    }
}
