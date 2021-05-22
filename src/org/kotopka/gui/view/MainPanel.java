package org.kotopka.gui.view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private final JPanel innerPanel;
    private final JPanel textEntryPanel;
    private final JPanel outputPanel;

    private final JLabel textFieldLabel;
    private final JTextField textField;
    private final JButton button;

    private final JLabel anagramLabel;
    private final JScrollPane anagramScrollPane;
    private final JTextArea anagramTextArea;
    private final JPanel anagramPanel;

    private final JLabel subWordsLabel;
    private final JTextArea subWordsTextArea;
    private final JScrollPane subWordsScrollPane;
    private final JPanel subWordPanel;

    private String anagramString;

    public MainPanel() {
        this.innerPanel = new JPanel();
        this.textEntryPanel = new JPanel();
        this.outputPanel = new JPanel();

        this.textFieldLabel = new JLabel("Anagram text: ");
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

        setupPanel();
        setupListeners();
    }

    private void setupPanel() {
        // main panel hierarchy
        innerPanel.setLayout(new BorderLayout());
        textEntryPanel.setLayout(new FlowLayout());
        outputPanel.setLayout(new FlowLayout());

        // anagram entry
        innerPanel.add(textEntryPanel, BorderLayout.NORTH);
        textEntryPanel.add(textFieldLabel);
        textEntryPanel.add(textField);
        textEntryPanel.add(button);
        add(innerPanel, BorderLayout.NORTH);
        innerPanel.add(outputPanel, BorderLayout.CENTER);

        // anagram output
        anagramPanel.setLayout(new BoxLayout(anagramPanel, BoxLayout.Y_AXIS));
        anagramScrollPane.setViewportView(anagramTextArea);
        anagramScrollPane.setSize(300, 300);
        anagramPanel.add(anagramLabel);
        anagramPanel.add(anagramScrollPane);

        // subWord output
        subWordPanel.setLayout(new BoxLayout(subWordPanel, BoxLayout.Y_AXIS));
        subWordsScrollPane.setViewportView(subWordsTextArea);
        subWordsScrollPane.setSize(30, 300);
        subWordPanel.add(subWordsLabel);
        subWordPanel.add(subWordsScrollPane);

        // add anagram and subWord to window
        outputPanel.add(anagramPanel);
        outputPanel.add(subWordPanel);
    }

    private void setupListeners() {
        // only the one really...
        button.addActionListener(e -> {
            anagramString = textField.getText();
        });
    }

    public String getAnagramString() { return anagramString; }

}
