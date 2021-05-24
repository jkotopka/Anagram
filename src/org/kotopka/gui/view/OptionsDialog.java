package org.kotopka.gui.view;

import org.kotopka.gui.controller.MainController;
import org.kotopka.parser.Switch;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OptionsDialog extends JDialog {

    private final MainController mainController;

    // panel stuff
    private final JPanel mainPanel;

    // labels
    private final JLabel maxWordLenLabel;
    private final JLabel minWordLenLabel;
    private final JLabel maxResultsLabel;
    private final JLabel maxWordsInAnagramLabel;
    private final JLabel restrictPermutationsLabel;
    private final JLabel excludeDuplicatesLabel;
    private final JLabel includeWordLabel;
    private final JLabel excludeWordLabel;

    // fields
    private final JFormattedTextField maxWordLenField;
    private final JFormattedTextField minWordLenField;
    private final JFormattedTextField maxResultsField;
    private final JFormattedTextField maxWordsInAnagramField;
    private final JCheckBox restrictPermutationsCheckBox;
    private final JCheckBox excludeDuplicatesCheckBox;
    private final JTextField includeWordField;
    private final JTextField excludeWordField;

    // buttons
    private final JButton okButton;
    private final JButton cancelButton;

    public OptionsDialog(MainController mainController) {
        this.mainController = mainController;
        this.mainPanel = new JPanel();

        // integer number format
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        this.maxWordLenLabel = new JLabel("Max word length: ");
        this.maxWordLenField = new JFormattedTextField(numberFormat);

        this.minWordLenLabel = new JLabel("Min word length: ");
        this.minWordLenField = new JFormattedTextField(numberFormat);

        this.maxResultsLabel = new JLabel("Max results: ");
        this.maxResultsField = new JFormattedTextField(numberFormat);

        this.maxWordsInAnagramLabel = new JLabel("Max words in anagram: ");
        this.maxWordsInAnagramField = new JFormattedTextField(numberFormat);

        this.restrictPermutationsLabel = new JLabel("Restrict permutations: ");
        this.restrictPermutationsCheckBox = new JCheckBox();

        this.excludeDuplicatesLabel = new JLabel("Exclude duplicate words: ");
        this.excludeDuplicatesCheckBox = new JCheckBox();

        this.includeWordLabel = new JLabel("Include word: ");
        this.includeWordField = new JTextField();

        this.excludeWordLabel = new JLabel("Exclude word: ");
        this.excludeWordField = new JTextField();

        this.okButton = new JButton("Ok");
        this.cancelButton = new JButton("Cancel");

        setupPanel();
        setupFrame();
        setupButtonListeners();
    }

    private void setupPanel() {
        mainPanel.setLayout(new BorderLayout());

        maxWordLenLabel.setLabelFor(maxWordLenField);
        maxWordLenField.setValue(10);
        maxWordLenField.setColumns(10);

        minWordLenLabel.setLabelFor(minWordLenField);
        minWordLenField.setValue(1);
        minWordLenField.setColumns(10);

        maxResultsLabel.setLabelFor(maxResultsField);
        maxResultsField.setValue(10000);
        maxResultsField.setColumns(10);

        maxWordsInAnagramLabel.setLabelFor(maxWordsInAnagramField);
        maxWordsInAnagramField.setValue(10);
        maxWordsInAnagramField.setColumns(10);

        restrictPermutationsLabel.setLabelFor(restrictPermutationsCheckBox);

        excludeDuplicatesLabel.setLabelFor(excludeDuplicatesCheckBox);

        includeWordLabel.setLabelFor(includeWordField);
        includeWordField.setColumns(10);

        excludeWordLabel.setLabelFor(excludeWordField);
        excludeWordField.setColumns(10);

        // layout labels in a panel
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        labelPane.add(maxWordLenLabel);
        labelPane.add(minWordLenLabel);
        labelPane.add(maxResultsLabel);
        labelPane.add(maxWordsInAnagramLabel);
        labelPane.add(restrictPermutationsLabel);
        labelPane.add(excludeDuplicatesLabel);
        labelPane.add(includeWordLabel);
        labelPane.add(excludeWordLabel);

        // layout the inputs
        JPanel inputPane = new JPanel(new GridLayout(0, 1));
        inputPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPane.add(maxWordLenField);
        inputPane.add(minWordLenField);
        inputPane.add(maxResultsField);
        inputPane.add(maxWordsInAnagramField);
        inputPane.add(restrictPermutationsCheckBox);
        inputPane.add(excludeDuplicatesCheckBox);
        inputPane.add(includeWordField);
        inputPane.add(excludeWordField);

        /// layout the ok/cancel buttons
        JPanel buttonPane = new JPanel(new GridLayout(0, 1));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        // populate the panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(labelPane, BorderLayout.CENTER);
        mainPanel.add(inputPane, BorderLayout.LINE_END);
        mainPanel.add(buttonPane, BorderLayout.AFTER_LAST_LINE);
    }

    private void setupFrame() {
        setTitle("Options");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setModal(true);
        setResizable(false);
        pack();
    }

    public void setupButtonListeners() {
        // setup the buttons
        okButton.addActionListener(e -> {
            List<String> optionsArgs = new ArrayList<>();

            // TODO: extract these as methods
            // set up the optionsArgs list as if it were an array
            optionsArgs.add(Switch.MIN_WORD_LENGTH.getLabel());
            optionsArgs.add(minWordLenField.getText());

            optionsArgs.add(Switch.MAX_WORD_LENGTH.getLabel());
            optionsArgs.add(maxWordLenField.getText());

            optionsArgs.add(Switch.MAX_RESULTS.getLabel());
            optionsArgs.add(maxResultsField.getText());

            optionsArgs.add(Switch.MAX_WORDS.getLabel());
            optionsArgs.add(maxWordsInAnagramField.getText());

            if (restrictPermutationsCheckBox.isSelected())
                optionsArgs.add(Switch.RESTRICT_PERMUTATIONS.getLabel());

            if (excludeDuplicatesCheckBox.isSelected())
                optionsArgs.add(Switch.EXCLUDE_DUPLICATES.getLabel());

            if (!includeWordField.getText().isBlank()) {
                optionsArgs.add(Switch.INCLUDE_WORD.getLabel());
                optionsArgs.add(includeWordField.getText());
            }

            if (!excludeWordField.getText().isBlank()) {
                optionsArgs.add(Switch.EXCLUDE_WORD.getLabel());
                optionsArgs.add(excludeWordField.getText());
            }

            String[] args = optionsArgs.toArray(new String[0]);

            mainController.updateOptions(args);

            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            this.dispose();
        });
    }

}
