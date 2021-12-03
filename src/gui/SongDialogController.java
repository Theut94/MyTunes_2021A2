package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
