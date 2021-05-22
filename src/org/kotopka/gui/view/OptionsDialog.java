package org.kotopka.gui.view;

import javax.swing.*;

public class OptionsDialog extends JDialog {

    public OptionsDialog(MainFrame frame) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(frame);
    }
}
