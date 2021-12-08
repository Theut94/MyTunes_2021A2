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
import javafx.scene.input.MouseEvent;
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
    public void addToPlaylist(ActionEvent actionEvent) throws Exception {
        myTunesModel.addToPlaylist(tvPlaylists.getSelectionModel().getSelectedItem(), tvSongTable.getSelectionModel().getSelectedItem());
        refreshViews();

    }

    public void newPlaylist(ActionEvent actionEvent) throws Exception {
        String name = SimpleDialog.playlist();
        myTunesModel.createPlaylist(name);
    }

    public void updatePlaylist(ActionEvent actionEvent) throws Exception {
        if(tvPlaylists.getSelectionModel().getSelectedItem() != null) {
            String name = SimpleDialog.playlist();
            Playlist pl = new Playlist(tvPlaylists.getSelectionModel().getSelectedItem().getPlaylistId(), name);
            myTunesModel.updatePlaylist(pl);
        }
    }

    public void refreshViews()
    {
        tvSongTable.refresh();
        tvPlaylists.refresh();
        lvPlaylistSongs.refresh();
    }
    public void deletePlaylist(ActionEvent actionEvent) {
        if(SimpleDialog.delete())
            myTunesModel.deletePlaylist(tvPlaylists.getSelectionModel().getSelectedItem());
        tvPlaylists.refresh();
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
        refreshViews();
    }

    //Method to delete a song from a playlist når der er lavet
    public void removeFromPlaylist(ActionEvent actionEvent) throws Exception {
        if(SimpleDialog.delete())
        {
         myTunesModel.removeFromPlaylist(tvPlaylists.getSelectionModel().getSelectedItem() ,lvPlaylistSongs.getSelectionModel().getSelectedItem());
        }
    }


    public void newSong(ActionEvent actionEvent) throws IOException, InterruptedException {
        SongDialogController controller = createSongDialog("New Song");


    }

    public void editSong(ActionEvent actionEvent) throws IOException {
        if(tvSongTable.getSelectionModel().getSelectedItem() != null) {
            SongDialogController controller = createSongDialog("Edit Song");
            Song song = tvSongTable.getSelectionModel().getSelectedItem();

            String filepath = song.getFilePath().replace("file:/", "");
            controller.setSongValues(song.getSongId(), song.getName(), song.getArtistName(), song.getSongLength(), filepath);
            controller.setEdit(true);
        }

    }

    public void deleteSong(ActionEvent actionEvent) throws Exception {
        if(SimpleDialog.delete() && tvSongTable.getSelectionModel().getSelectedItem() != null)
        {
            myTunesModel.deleteSong(tvSongTable.getSelectionModel().getSelectedItem());
        }


    }

    // needs fixing evt. foreach loop gennem listen og så tilføje hvert enkelt properties for hver sang?
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //Initializes the playlist
        tcPlaylistName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistName"));
        tcPlaylistTime.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistTimelength"));
        tcNumberSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("playlistSongCount"));
        try{
            tvPlaylists.setItems(myTunesModel.getAllPlaylists());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Initializes the songs
        tcSongArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistName"));
        tcSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
        tcSongTime.setCellValueFactory(new PropertyValueFactory<Song, String>("songLength"));
        try{
            tvSongTable.setItems(myTunesModel.getSonglist());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Search function
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
            {lvPlaylistSongs.setItems(myTunesModel.getPlaylist(tvPlaylists.getSelectionModel().getSelectedItem()));
            lvPlaylistSongs.getItems();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Song Dialog Window
     * @param windowTitle
     */
    public SongDialogController createSongDialog(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SongDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
        return fxmlLoader.getController();
    }

    public void playPause(ActionEvent actionEvent)
    {
        if(myTunesModel.isPlaying() != true)
        {
            if(lvPlaylistSongs.getSelectionModel().getSelectedItem() != null)
            {
                myTunesModel.playSong(lvPlaylistSongs.getSelectionModel().getSelectedItem());
                lblNowPlaying.setText(lvPlaylistSongs.getSelectionModel().getSelectedItem().getName());
            }
            else if (tvSongTable.getSelectionModel().getSelectedItem()!= null)
            {
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.playSong(tvSongTable.getSelectionModel().getSelectedItem());
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
            }

        }
        else
            myTunesModel.stopPlaying();
    }

    public void previousSong(ActionEvent actionEvent) {
    }

    public void nextSong(ActionEvent actionEvent)
    {
        myTunesModel.nextSong();
    }

}
