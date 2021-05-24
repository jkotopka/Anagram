package org.kotopka.gui;

import org.kotopka.gui.controller.MainController;

import javax.swing.*;

public class AnagramAppGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainController::new);
    }
}
