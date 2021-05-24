package org.kotopka.gui.view;

import org.kotopka.gui.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final MainController mainController;

    // menu
    private final JMenuBar menuBar;
    private final JMenu optionsMenu;
    private final JMenuItem optionsMenuItem;

    // panels
    private final JPanel mainPanel;
    private final JPanel textEntryPanel;
    private final JPanel outputPanel;
    private final JPanel statusPanel;

    // anagram entry
    private final JLabel textFieldLabel;
    private final JTextField textField;
    private final JButton button;

    // anagram display
    private final JLabel anagramLabel;
    private final JScrollPane anagramScrollPane;
    private final JTextArea anagramTextArea;
    private final JPanel anagramPanel;

    // subWord display
    private final JLabel subWordsLabel;
    private final JTextArea subWordsTextArea;
    private final JScrollPane subWordsScrollPane;
    private final JPanel subWordPanel;

    // status display
    private final JLabel statusLabel;

    public MainFrame(MainController mainController) {
        this.mainController = mainController;

        // menu
        this.menuBar = new JMenuBar();
        this.optionsMenu = new JMenu("Options");
        this.optionsMenuItem = new JMenuItem("Options...");

        // panels
        this.mainPanel = new JPanel();
        this.textEntryPanel = new JPanel();
        this.outputPanel = new JPanel();
        this.statusPanel = new JPanel();

        // word text entry
        this.textFieldLabel = new JLabel("Enter text: ");
        this.textField = new JTextField(30);
        this.button = new JButton("Generate!");

        this.anagramLabel = new JLabel("Anagrams");
        this.anagramScrollPane = new JScrollPane();
        this.anagramTextArea = new JTextArea(20, 30);
        this.anagramPanel = new JPanel();

        this.subWordsLabel = new JLabel("Sub Words");
        this.subWordsScrollPane = new JScrollPane();
        this.subWordsTextArea = new JTextArea(20, 15);
        this.subWordPanel = new JPanel();

        this.statusLabel = new JLabel();

        setupPanel();
        setupFrame();
        setupActionListeners();
    }

    private void setupPanel() {
        // main panel hierarchy
        mainPanel.setLayout(new BorderLayout());
        textEntryPanel.setLayout(new FlowLayout());
        outputPanel.setLayout(new FlowLayout());
        statusPanel.setLayout(new FlowLayout());

        // anagram entry
        mainPanel.add(textEntryPanel, BorderLayout.NORTH);
        textEntryPanel.add(textFieldLabel);
        textEntryPanel.add(textField);
        textEntryPanel.add(button);
        add(mainPanel, BorderLayout.NORTH);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        // anagram output
        anagramPanel.setLayout(new BoxLayout(anagramPanel, BoxLayout.Y_AXIS));
        anagramScrollPane.setViewportView(anagramTextArea);
        anagramScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        anagramPanel.add(anagramLabel);
        anagramPanel.add(anagramScrollPane);

        // subWord output
        subWordPanel.setLayout(new BoxLayout(subWordPanel, BoxLayout.Y_AXIS));
        subWordsScrollPane.setViewportView(subWordsTextArea);
        subWordsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        subWordPanel.add(subWordsLabel);
        subWordPanel.add(subWordsScrollPane);

        // add anagram and subWord to window
        outputPanel.add(anagramPanel);
        outputPanel.add(subWordPanel);

        statusPanel.add(statusLabel);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupFrame() {
        menuBar.add(optionsMenu);
        optionsMenu.add(optionsMenuItem);

        setTitle("Anagram Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setJMenuBar(menuBar);
        setSize(640, 480);
        setResizable(false);
        setVisible(true);
    }

    private void setupActionListeners() {
        optionsMenuItem.addActionListener(e -> {
            mainController.showOptionsDialog();
        });

        button.addActionListener(e -> {
            String input = textField.getText();

            mainController.generateAnagrams(input);
            mainController.generateSubWords(input);
            mainController.updateStatus();
        });
    }

    public void setAnagramTextArea(String anagram) {
        anagramTextArea.setText(anagram);
    }

    public void setSubWordsTextArea(String subWords) {
        subWordsTextArea.setText(subWords);
    }

    public void setStatusBar(String status) {
        statusLabel.setText(status);
    }

}
