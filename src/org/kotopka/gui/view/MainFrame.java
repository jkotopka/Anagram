package org.kotopka.gui.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private final MainPanel mainPanel;
    private final OptionsDialog optionsDialog;
    private final JMenuBar menuBar;
    private final JMenu optionsMenu;
    private final JMenuItem optionsMenuItem;

    public MainFrame() {
        this.mainPanel = new MainPanel();
        this.optionsDialog = new OptionsDialog(this);
        this.menuBar = new JMenuBar();
        this.optionsMenu = new JMenu("Options");
        this.optionsMenuItem = new JMenuItem("Options...");

        setupFrame();
        setupActionListeners();
    }

    private void setupFrame() {
        menuBar.add(optionsMenu);
        optionsMenu.add(optionsMenuItem);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setJMenuBar(menuBar);
        setSize(640, 480);
        setVisible(true);
    }

    private void setupActionListeners() {
        optionsMenuItem.addActionListener(e -> {
            optionsDialog.setVisible(true);
        });
    }

    public String[] getOptions() {
        return optionsDialog.getOptionsArgs();
    }

    public String getAnagramString() {
        return mainPanel.getAnagramString();
    }

    public void setAnagram(java.util.List<String> anagrams) {
        mainPanel.setAnagram(anagrams);
    }

    public void setSubWords(java.util.Set<String> subWords) {
        mainPanel.setSubWords(subWords);
    }

    public void addActionListener(ActionListener e) {
        mainPanel.addActionListener(e);
    }
}
