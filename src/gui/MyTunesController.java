package gui;

import be.Playlist;
import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MyTunesController {
    @FXML
    private TableView<Playlist> tvPlaylists;
    @FXML
    private TableColumn<Playlist, String> tcPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> tcNumberSongs;
    @FXML
    private TableColumn<Playlist, Integer> tcPlaylistTime;
    @FXML
    private TableView<Song> tvSongTable;
    @FXML
    private TableColumn<Song, String> tcSongTitle;
    @FXML
    private TableColumn<Song, String> tcSongArtist;
    @FXML
    private TableColumn<Song, Integer> tcSongTime;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private Label lblNowPlaying;
    @FXML
    private ListView<String> lvPlaylistSongs;


    public void addToPlaylist(ActionEvent actionEvent) {

    }

    public void newPlaylist(ActionEvent actionEvent) {
        String name = SimpleDialog.playlist();
    }

    public void updatePlaylist(ActionEvent actionEvent) {
        String name = SimpleDialog.playlist();
    }

    public void deletePlaylist(ActionEvent actionEvent) {
        int confirm = SimpleDialog.delete();
    }

    public void positionUp(ActionEvent actionEvent) {
    }

    public void positionDown(ActionEvent actionEvent) {
    }

    public void removeFromPlaylist(ActionEvent actionEvent) {
        int confirm = SimpleDialog.delete();
    }

    public void newSong(ActionEvent actionEvent) {
        // TODO: Open Song window
    }

    public void editSong(ActionEvent actionEvent) {
        // TODO: Open Song window
    }

    public void deleteSong(ActionEvent actionEvent) {
        int confirm = SimpleDialog.delete();
        System.out.println(confirm);
    }

    public void search(ActionEvent actionEvent) {
    }
}
