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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

public class MyTunesController implements Initializable {
    public Slider volumeController;
    @FXML
    private TableView<Song> tvPlaylistSongTable;
    @FXML
    private TableColumn<Song,String> tcPlaylistSongs;
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
    private TextField lblNowPlaying;


    private MyTunesModel myTunesModel;
    private SongDialogController songController;


    public MyTunesController() throws Exception {
        myTunesModel = new MyTunesModel();

    }

    /**
     * Adds the selected song to the selected playlist when then button is pressed
     */
    public void addToPlaylist(ActionEvent actionEvent) throws Exception {
        myTunesModel.addToPlaylist(tvPlaylists.getSelectionModel().getSelectedItem(), tvSongTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Opens the dialog to get user input to name a new playlist, then creates playlist with that name
     */
    public void newPlaylist(ActionEvent actionEvent) throws Exception {
        String name = SimpleDialog.playlist();
        myTunesModel.createPlaylist(name);
    }

    /**
     * Opens the dialog to get user input for the name of the playlist, then updates the selected playlist with the new name
     */
    public void updatePlaylist(ActionEvent actionEvent) throws Exception {
        if(tvPlaylists.getSelectionModel().getSelectedItem() != null) {
            String name = SimpleDialog.playlist();
            Playlist pl = new Playlist(tvPlaylists.getSelectionModel().getSelectedItem().getPlaylistId(), name);
            myTunesModel.updatePlaylist(pl);
        }
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected playlist
     */
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
        myTunesModel.swapSongsInPlaylist(tvPlaylists.getSelectionModel().getSelectedItem(),tvPlaylistSongTable.getSelectionModel().getSelectedIndex(),
                tvPlaylistSongTable.getSelectionModel().getSelectedIndex() + upOrDown);
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then removes the selected song from the current playlist
     */
    public void removeFromPlaylist(ActionEvent actionEvent) throws Exception {
        if(SimpleDialog.delete())
        {
         myTunesModel.removeFromPlaylist(tvPlaylists.getSelectionModel().getSelectedItem(),
                 tvPlaylistSongTable.getSelectionModel().getSelectedItem(),
                 tvPlaylistSongTable.getSelectionModel().getSelectedIndex());
        }
    }

    /**
     * Opens a new Song Dialog window
     */
    public void newSong(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage stage = createSongDialog("New Song");
        Stage mainStage = ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow()));
        stage.initOwner(mainStage);
        stage.showAndWait();
    }

    /**
     * Opens a new Song Dialog window with the current info of the selected song already in the text fields
     */
    public void editSong(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(tvSongTable.getSelectionModel().getSelectedItem() != null) {
            Stage stage = createSongDialog("Edit Song");
            Stage mainStage = ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow()));
            stage.initOwner(mainStage);

            Song song = tvSongTable.getSelectionModel().getSelectedItem();
            String filepath = song.getFilePath().replace("file:/", "");
            songController.setSongValues(song.getSongId(), song.getName(), song.getArtistName(), song.getSongLength(), filepath);
            stage.showAndWait();
        }
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected song
     */
    public void deleteSong(ActionEvent actionEvent) throws Exception {
        if(SimpleDialog.delete() && tvSongTable.getSelectionModel().getSelectedItem() != null) {
            myTunesModel.deleteSong(tvSongTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Plays or pauses a song - it detects both if the music player is playing or if it is paused
     * it also checks if there is no song selected from the song table and tries to play the start of a playlist.
     */
    public void playPause(ActionEvent actionEvent)
    {
        if(!myTunesModel.isPlaying())
        {
            if(tvPlaylistSongTable.getSelectionModel().getSelectedItem() != null)
            {
                myTunesModel.playSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
                lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
            }
            else if (tvSongTable.getSelectionModel().getSelectedItem()!= null)
            {
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.playSong(tvSongTable.getSelectionModel().getSelectedItem());

            }

        }
        else if(myTunesModel.isPlaying())
            myTunesModel.stopPlaying();
        else if (tvSongTable.getSelectionModel().getSelectedItem() == null && tvPlaylistSongTable.getItems() != null)
        {
            System.out.println("test");
            tvPlaylistSongTable.getSelectionModel().select(1);
            myTunesModel.playSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * Gets the previous song from the playlist or song table
     */
    public void previousSong(ActionEvent actionEvent)
    {
        if(tvSongTable.getSelectionModel().getSelectedItem() == null) {
            if (tvPlaylistSongTable.getSelectionModel().getSelectedIndex() == 0) {
                tvPlaylistSongTable.getSelectionModel().selectLast();
                lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            } else {
                tvPlaylistSongTable.getSelectionModel().selectPrevious();
                lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.previousSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            }
        }
        else
        {
            if(tvSongTable.getSelectionModel().getSelectedIndex() == 0)
            {
                tvSongTable.getSelectionModel().selectLast();
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.previousSong(tvSongTable.getSelectionModel().getSelectedItem());}
            else
            {
                tvSongTable.getSelectionModel().selectPrevious();
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.previousSong(tvSongTable.getSelectionModel().getSelectedItem());
            }
        }

    }

    /**
     * Gets the next song from the playlist or song table
     */
    public void nextSong(ActionEvent actionEvent)
    {
        if(tvSongTable.getSelectionModel().getSelectedItem() == null)
        {
            if(tvPlaylistSongTable.getSelectionModel().getSelectedIndex()+1 == tvPlaylistSongTable.getItems().size())
            {
                tvPlaylistSongTable.getSelectionModel().selectFirst();
                lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            }
            else
            {
                tvPlaylistSongTable.getSelectionModel().selectNext();
                lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            }
        }
        else
        {
            if(tvSongTable.getSelectionModel().getSelectedIndex()+1 == tvSongTable.getItems().size())
            {
                tvSongTable.getSelectionModel().selectFirst();
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
            }
            else
            {
                tvSongTable.getSelectionModel().selectNext();
                lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                myTunesModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
            }
        }
    }

    /**
     * Handles the autoplay functionality
     */
    public TimerTask continuePlaying()
    {
        TimerTask t = new TimerTask() {
            @Override
            public void run()
            {{if(myTunesModel.isSongFinished())
                if(tvSongTable.getSelectionModel().getSelectedItem() == null && tvPlaylistSongTable.getSelectionModel().getSelectedItem() != null)
                {
                    if(tvPlaylistSongTable.getSelectionModel().getSelectedIndex()+1 == tvPlaylistSongTable.getItems().size())
                    {
                        tvPlaylistSongTable.getSelectionModel().selectFirst();
                        lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                        myTunesModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
                    }
                    else
                    {
                        tvPlaylistSongTable.getSelectionModel().selectNext();
                        lblNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                        myTunesModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
                    }
                }
                else if (tvSongTable.getSelectionModel().getSelectedItem() != null)
                {
                    if(tvSongTable.getSelectionModel().getSelectedIndex()+1 == tvSongTable.getItems().size())
                    {
                        tvSongTable.getSelectionModel().selectFirst();
                        lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                        myTunesModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
                    }
                    else
                    {
                        tvSongTable.getSelectionModel().selectNext();
                        lblNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                        myTunesModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
                    }
                }
            }
            myTunesModel.setSongFinished(false);
            }
        };
        return t;
    }

    /**
     * When a playlist is clicked then songs on the playlist are shown in the middle table
     */
    public void showPlaylist(MouseEvent mouseEvent) {
        tvPlaylistSongTable.getItems().clear();
        try{
            if(tvPlaylists.getSelectionModel().getSelectedItem() != null)
            {tvPlaylistSongTable.setItems(myTunesModel.getPlaylist(tvPlaylists.getSelectionModel().getSelectedItem()));
                tcPlaylistSongs.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
                tvPlaylistSongTable.getItems();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the Song Dialog window for New song and Edit song
     */
    public Stage createSongDialog(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SongDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        songController = fxmlLoader.getController();
        songController.setModel(myTunesModel);
        return stage;
    }

    /**
     * Initializes the songs, playlists, autoplay timer and search function when the program is launched
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTvSongTable();
        setTcPlaylistTable();

        myTunesModel.timer(continuePlaying());
        txtSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try{
                myTunesModel.searchSongs(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method used for initializing the playlists
     */
    public void setTcPlaylistTable() {
        tcPlaylistName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistName"));
        tcPlaylistTime.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistTimelength"));
        tcNumberSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("playlistSongCount"));
        try {
            tvPlaylists.setItems(myTunesModel.getAllPlaylists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for initializing the song table
     */
    private void setTvSongTable() {
        tcSongArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistName"));
        tcSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
        tcSongTime.setCellValueFactory(new PropertyValueFactory<Song, String>("songLength"));
        try{
            tvSongTable.setItems(myTunesModel.getSonglist());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the selected song on the song table
     */
    public void clearSongTableSelection(MouseEvent mouseEvent) {
        tvSongTable.getSelectionModel().clearSelection();
    }

    /**
     * Clears the selected song on the playlist song table
     */
    public void clearPlaylistSongTable(MouseEvent mouseEvent) {

        tvPlaylistSongTable.getSelectionModel().clearSelection();
    }

    public void setVolume(MouseEvent mouseDragEvent)
    {
        myTunesModel.setVolume(volumeController.getValue());
        System.out.println(volumeController.getValue());
    }

    public void setFinishVolume(MouseEvent mouseDragEvent)
    {
        myTunesModel.setVolume(volumeController.getValue());
    }
}
