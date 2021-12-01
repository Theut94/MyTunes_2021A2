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


    public void AddToPlaylist(ActionEvent actionEvent) {

    }

    public void NewPlaylist(ActionEvent actionEvent) {
    }

    public void UpdatePlaylist(ActionEvent actionEvent) {
    }

    public void DeletePlaylist(ActionEvent actionEvent) {
    }

    public void PositionUp(ActionEvent actionEvent) {
    }

    public void PositionDown(ActionEvent actionEvent) {
    }

    public void RemoveFromPlaylist(ActionEvent actionEvent) {
    }

    public void NewSong(ActionEvent actionEvent) {
    }

    public void EditSong(ActionEvent actionEvent) {
    }

    public void DeleteSong(ActionEvent actionEvent) {
    }

    public void Search(ActionEvent actionEvent) {
    }
}
