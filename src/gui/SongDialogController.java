package gui;


import bll.util.ConvertTime;

import be.Song;

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

import javax.swing.*;
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



    public SongDialogController() throws Exception
    {
        myTunesModel = new MyTunesModel();
        edit = false;
        songID = 0;
    }

    /**
     * Handles the file chooser
     */
    public void choosePath(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new java.io.File("."));
        fc.setTitle("Choose a song");

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Musik filer","*.mp3", "*.wav"));
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null)
        {
            txtPath.setText(selectedFile.getName());
            setTxtTime(selectedFile.getPath());
        }
    }

    public void setTxtTime(String file)
    {

        String fileString = "file:/" + file.replace("\\", "/");
        System.out.println(fileString);
        Media media = new Media(fileString);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                System.out.println(mediaPlayer.getMedia().getDuration().toSeconds());
                txtTime.setText( ConvertTime.doubleSecToTime(mediaPlayer.getMedia().getDuration().toSeconds()));
            }
        });


    }

    /**
     * Closes the Song window when the Cancel button is clicked
     */
    public void cancel(ActionEvent actionEvent)
    {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    //Mangler if statement der kan determinere om vi vil lave en ny sang eller redigere en sang
    public void save(ActionEvent actionEvent) throws Exception {

        if(!edit) {
            myTunesModel.createSong(txtTitle.getText(), txtArtist.getText(), txtPath.getText(), txtTime.getText());
        }
        else{
            Song song = new Song(songID, txtTitle.getText(), txtArtist.getText(), txtPath.getText(),txtTime.getText());
            myTunesModel.updateSong(song);
        }
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void setSongValues(int id, String title, String artist, String time, String path){
        txtTitle.setText(title);
        txtArtist.setText(artist);
        txtTime.setText(time);
        txtPath.setText(path);
        songID = id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTitle.setText("");
        txtArtist.setText("");
        txtPath.setText("");
        txtTime.setText("");

    }

    public void setEdit(boolean edit){
        this.edit=edit;
    }
}
