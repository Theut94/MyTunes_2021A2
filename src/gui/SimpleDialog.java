package gui;

import javax.swing.*;

/**
 * Class handles the "delete" confirmation and playlist windows
 */
public class SimpleDialog {
    JFrame f;

    public SimpleDialog(){f = new JFrame();}

    /**
     * Opens a window to ask user to confirm deletion
     * @return 0 if OK is pressed, 1 if Cancel is pressed
     */
    public static int delete(){

        return JOptionPane.showConfirmDialog(null,
               "Are you sure you want to delete?", "Delete...?",JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Opens window to ask user to input playlist name
     * @return user input
     */
    public static String playlist(){
        return JOptionPane.showInputDialog(null,"Playlist name: ", "New/Edit Playlist", JOptionPane.PLAIN_MESSAGE);
    }
}
