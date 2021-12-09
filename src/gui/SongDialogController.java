package gui;

import be.Song;
import bll.util.ConvertTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Handles the Song dialog window
 */
public class SongDialogController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtPath;

    private int songID;
    private boolean edit;
    private MyTunesModel myTunesModel;


    public SongDialogController() throws Exception {
        songID = 0;
    }

    /**
     * Opens a new window for the user to pick which file to use. Only allows for .mp3 and .wav files.
     * Initial directory is the project folder.
     */
    public void choosePath(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new java.io.File("."));
        fc.setTitle("Choose a song");

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Musik filer", "*.mp3", "*.wav"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            txtPath.setText(selectedFile.getPath());
            setTxtTime(selectedFile.getPath());
        }
    }

    /**
     * Closes the Song window when the Cancel button is clicked.
     */
    public void cancel(ActionEvent actionEvent) {
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * When the Save button is clicked the user inputs are sent on to either create a new song or update the info of
     * an already existing song, depending on which of the "New" or "Edit" button was clicked in the main view.
     */
    public void save(ActionEvent actionEvent) throws Exception {
        if (!edit) {
            myTunesModel.createSong(txtTitle.getText(), txtArtist.getText(), txtPath.getText(), txtTime.getText());

        } else {
            Song song = new Song(songID, txtTitle.getText(), txtArtist.getText(), txtPath.getText(), txtTime.getText());
            myTunesModel.updateSong(song);
        }
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * Method gets the duration from the media, creates a String from it
     * and then inputs into the time text field automatically
     */
    public void setTxtTime(String file) {
        String fileString = "file:/" + file.replace("\\", "/");
        System.out.println(fileString);
        Media media = new Media(fileString);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                txtTime.setText(ConvertTime.doubleSecToTime(mediaPlayer.getMedia().getDuration().toSeconds()));
            }
        });
    }

    /**
     * Sets the fields to contain the current information of the song that is being edited
     */
    public void setSongValues(int id, String title, String artist, String time, String path) {
        txtTitle.setText(title);
        txtArtist.setText(artist);
        txtTime.setText(time);
        txtPath.setText(path);
        songID = id;
        edit = true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTitle.setText("");
        txtArtist.setText("");
        txtTime.setText("");
        txtPath.setText("");
        edit = false;
    }

    public void setModel(MyTunesModel model) {
        myTunesModel = model;
    }
}