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

    public void choosePath(ActionEvent actionEvent) {
        // TODO: Open window to pick file
    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {
    }

    public void setTxtTitle(String title){
        txtTitle.setText(title);
    }
}
