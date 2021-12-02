package gui;

import javax.swing.*;

public class DeleteDialog {
    JFrame f;

    public DeleteDialog(){f = new JFrame();}

    public static int run(){
        int input = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete?", "Delete...?",JOptionPane.OK_CANCEL_OPTION
        , JOptionPane.WARNING_MESSAGE);

        return input;
    }
}
