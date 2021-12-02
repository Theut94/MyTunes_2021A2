package gui;

import javax.swing.*;

public class SimpleDialog {
    JFrame f;

    public SimpleDialog(){f = new JFrame();}

    public static int delete(){
        int input = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete?", "Delete...?",JOptionPane.OK_CANCEL_OPTION
        , JOptionPane.WARNING_MESSAGE);

        return input;
    }

    public static String playlist(){
        return JOptionPane.showInputDialog(null,"Playlist name: ", "New/Edit Playlist", JOptionPane.PLAIN_MESSAGE);
    }
}
