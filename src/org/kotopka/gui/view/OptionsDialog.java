package org.kotopka.gui.view;

import org.kotopka.gui.controller.MainController;

import javax.swing.*;

public class OptionsDialog extends JDialog {

    private final OptionsPanel optionsPanel;

    // TODO: put OptionsPanel stuff directly in this class
    public OptionsDialog(MainController mainController) {

        this.optionsPanel = new OptionsPanel(this, mainController);

        setTitle("Options");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(optionsPanel);
        setModal(true);
        setResizable(false);
        pack();
    }

//    public String[] getOptionsArgs() {
//        return optionsPanel.getOptionsArgs();
//    }

}
