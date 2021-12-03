package gui;

import be.Playlist;
import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

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
    //Method to delete a song from a playlist når der er lavet
    public void removeFromPlaylist(ActionEvent actionEvent) {
        int confirm = SimpleDialog.delete();
        if(confirm == 0)
        {
        // myTunesModel.deleteSongFromPlaylist(lvPlaylistSongs.getSelectionModel().getSelectedItem());
        }
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
        if(confirm == 0 && tvSongTable.getSelectionModel().getSelectedItem() != null)
        {
            myTunesModel.deleteSong(tvSongTable.getSelectionModel().getSelectedItem());
        }

    }

    public void search(ActionEvent actionEvent)
    {

    }

    // needs fixing evt. foreach loop gennem listen og så tilføje hvert enkelt properties for hver sang?
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {


        tcSongArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistName"));
        tcSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
        //tcSongTime.setCellValueFactory(new PropertyValueFactory<Song, String>("songLength"));
        try{
            tvSongTable.setItems(myTunesModel.getSonglist());
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try{
                myTunesModel.searchSongs(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
