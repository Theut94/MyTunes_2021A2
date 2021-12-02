package gui;

import be.Playlist;
import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MyTunesController implements Initializable {
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
    private ListView<Song> lvPlaylistSongs;

    private MyTunesModel myTunesModel;

    public MyTunesController() throws Exception {

        myTunesModel = new MyTunesModel();
    }
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

    // needs fixing
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        tvSongTable.setItems(myTunesModel.getSonglist());
    }
}
