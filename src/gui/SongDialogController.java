package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javax.swing.*;

public class SongDialogController {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtPath;

    private MyTunesModel myTunesModel;

    public SongDialogController() throws Exception {
        myTunesModel = new MyTunesModel();


    }

    public void choosePath(ActionEvent actionEvent) {
        // TODO: Open window to pick file
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new java.io.File("."));
        jFileChooser.setDialogTitle("Chose a Song");
        jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);

        if (jFileChooser.showOpenDialog(null) == jFileChooser.APPROVE_OPTION)
            txtPath.setText(jFileChooser.getSelectedFile().getPath());
    }

    public void cancel(ActionEvent actionEvent)
    {
    }

    //Mangler if statement der kan determinere om vi vil lave en ny sang eller redigere en sang
    public void save(ActionEvent actionEvent)
    {

        /*if()
        {
            myTunesModel.createSong(txtTitle.getText(), txtArtist.getText(), txtPath.getText(), Integer.parseInt(txtTime.getText());
        }*/
    }

    public void setTxtTitle(String title){
        txtTitle.setText(title);
    }
}
