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
    private TableColumn<Playlist, String> tcPlaylistTime;
    @FXML
    private TableView<Song> tvSongTable;
    @FXML
    private TableColumn<Song, String> tcSongTitle;
    @FXML
    private TableColumn<Song, String> tcSongArtist;
    @FXML
    private TableColumn<Song, String> tcSongTime;
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

    public void positionUp(ActionEvent actionEvent) throws Exception  {
        changeOrderInPlaylist(-1);
    }

    public void positionDown(ActionEvent actionEvent) throws Exception  {
        changeOrderInPlaylist(+1);
    }

    private void changeOrderInPlaylist(int upOrDown) throws Exception {
        myTunesModel.swapSongsInPlaylist(lvPlaylistSongs.getSelectionModel().getSelectedIndex(),
                lvPlaylistSongs.getSelectionModel().getSelectedIndex() + upOrDown);
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
        createSongDialog("New Song");
    }

    public void editSong(ActionEvent actionEvent) throws IOException {
        createSongDialog("Edit Song");
    }

    public void deleteSong(ActionEvent actionEvent) throws Exception {
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
        //Beneath we initialize the playlist
        tcPlaylistName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistName"));
        tcPlaylistTime.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistTimelength"));
        tcNumberSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("playlistSongCount"));
        try{
            tvPlaylists.setItems(myTunesModel.getAllPlaylists());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Beneath we initialize the songs
        tcSongArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistName"));
        tcSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
        tcSongTime.setCellValueFactory(new PropertyValueFactory<Song, String>("songLength"));
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

        // a try to get our songs into the "playlist" view - the thought is to get the selected playlist and have that return
        //the list of songs - but the issue seems to be that it only runs once - when initialized and doesnt update in real time.

        try{
            if(tvPlaylists.getSelectionModel().getSelectedItem() != null)
            {lvPlaylistSongs.setItems(myTunesModel.getPlaylist(tvPlaylists.getSelectionModel().getSelectedItem()));}
            lvPlaylistSongs.getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createSongDialog(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SongDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }

}
