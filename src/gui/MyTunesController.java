package gui;

import be.Playlist;
import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void newSong(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SongDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Song");
        stage.setScene(scene);
        stage.show();
    }

    public void editSong(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SongDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Edit Song");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteSong(ActionEvent actionEvent) {
        int confirm = SimpleDialog.delete();
        System.out.println(confirm);
    }

    public void search(ActionEvent actionEvent) {
    }
}
