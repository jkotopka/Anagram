package org.kotopka.gui.view;

import javax.swing.*;

public class OptionsDialog extends JDialog {

    private final OptionsPanel optionsPanel;

    public OptionsDialog(MainFrame frame) {

        this.optionsPanel = new OptionsPanel(this);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(optionsPanel);
        setModal(true);
//        setSize(400, 400);
        pack();
        setLocationRelativeTo(frame);
    }

    public String[] getOptionsArgs() {
        return optionsPanel.getOptionsArgs();
    }

}
