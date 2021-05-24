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
    private final JLabel maxTimeoutLabel;
    private final JLabel restrictPermutationsLabel;
    private final JLabel excludeDuplicatesLabel;
    private final JLabel startFromLabel;
    private final JLabel includeWordLabel;
    private final JLabel excludeWordLabel;
    private final JLabel includeSuffixLabel;

    // fields
    private final JFormattedTextField maxWordLenField;
    private final JFormattedTextField minWordLenField;
    private final JFormattedTextField maxResultsField;
    private final JFormattedTextField maxWordsInAnagramField;
    private final JFormattedTextField maxTimeoutField;
    private final JCheckBox restrictPermutationsCheckBox;
    private final JCheckBox excludeDuplicatesCheckBox;
    private final JTextField startFromField;
    private final JTextField includeWordField;
    private final JTextField excludeWordField;
    private final JTextField includeSuffixField;

    // buttons
    private final JButton okButton;
    private final JButton cancelButton;

    public OptionsDialog(MainController mainController) {
        this.mainController = mainController;
        this.mainPanel = new JPanel();

        // integer number format
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);

        this.maxWordLenLabel = new JLabel("Max word length: ");
        this.maxWordLenField = new JFormattedTextField(numberFormat);

        this.minWordLenLabel = new JLabel("Min word length: ");
        this.minWordLenField = new JFormattedTextField(numberFormat);

        this.maxResultsLabel = new JLabel("Max results: ");
        this.maxResultsField = new JFormattedTextField(numberFormat);

        this.maxWordsInAnagramLabel = new JLabel("Max words in anagram: ");
        this.maxWordsInAnagramField = new JFormattedTextField(numberFormat);

        this.maxTimeoutLabel = new JLabel("Max timeout in seconds");
        this.maxTimeoutField = new JFormattedTextField(numberFormat);

        this.restrictPermutationsLabel = new JLabel("Restrict permutations: ");
        this.restrictPermutationsCheckBox = new JCheckBox();

        this.excludeDuplicatesLabel = new JLabel("Exclude duplicate words: ");
        this.excludeDuplicatesCheckBox = new JCheckBox();

        this.startFromLabel = new JLabel("Start from word: ");
        this.startFromField = new JTextField();

        this.includeWordLabel = new JLabel("Include word: ");
        this.includeWordField = new JTextField();

        this.excludeWordLabel = new JLabel("Exclude word: ");
        this.excludeWordField = new JTextField();

        this.includeSuffixLabel = new JLabel("Include word with suffix: ");
        this.includeSuffixField = new JTextField();

        this.okButton = new JButton("Ok");
        this.cancelButton = new JButton("Cancel");

        setupPanel();
        setupFrame();
        setupButtonListeners();
        updateOptions();
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

        maxTimeoutLabel.setLabelFor(maxTimeoutField);
        maxTimeoutField.setValue(10);
        maxTimeoutField.setColumns(10);

        restrictPermutationsLabel.setLabelFor(restrictPermutationsCheckBox);

        excludeDuplicatesLabel.setLabelFor(excludeDuplicatesCheckBox);

        startFromLabel.setLabelFor(startFromField);
        startFromField.setColumns(10);

        includeWordLabel.setLabelFor(includeWordField);
        includeWordField.setColumns(10);

        excludeWordLabel.setLabelFor(excludeWordField);
        excludeWordField.setColumns(10);

        includeSuffixLabel.setLabelFor(includeSuffixField);
        includeSuffixField.setColumns(10);

        // layout labels in a panel
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        labelPane.add(maxWordLenLabel);
        labelPane.add(minWordLenLabel);
        labelPane.add(maxResultsLabel);
        labelPane.add(maxWordsInAnagramLabel);
        labelPane.add(maxTimeoutLabel);
        labelPane.add(restrictPermutationsLabel);
        labelPane.add(excludeDuplicatesLabel);
        labelPane.add(startFromLabel);
        labelPane.add(includeWordLabel);
        labelPane.add(excludeWordLabel);
        labelPane.add(includeSuffixLabel);

        // layout the inputs
        JPanel inputPane = new JPanel(new GridLayout(0, 1));
        inputPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPane.add(maxWordLenField);
        inputPane.add(minWordLenField);
        inputPane.add(maxResultsField);
        inputPane.add(maxWordsInAnagramField);
        inputPane.add(maxTimeoutField);
        inputPane.add(restrictPermutationsCheckBox);
        inputPane.add(excludeDuplicatesCheckBox);
        inputPane.add(startFromField);
        inputPane.add(includeWordField);
        inputPane.add(excludeWordField);
        inputPane.add(includeSuffixField);

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

    private void setupButtonListeners() {
        okButton.addActionListener(e -> {
            updateOptions();
            this.dispose();
        });

        cancelButton.addActionListener(e -> this.dispose());
    }

    private void updateOptions() {
        List<String> optionsArgs = new ArrayList<>();

        setMinWordLength(optionsArgs);
        setMaxWordLength(optionsArgs);
        setMaxResults(optionsArgs);
        setMaxWordsInAnagram(optionsArgs);
        setTimeout(optionsArgs);
        setRestrictPermutations(optionsArgs);
        setExcludeDuplicates(optionsArgs);
        setStartFromWord(optionsArgs);
        setIncludeWord(optionsArgs);
        setExcludeWord(optionsArgs);
        setIncludeWordWithSuffix(optionsArgs);

        String[] args = optionsArgs.toArray(new String[0]);

        mainController.updateOptions(args);
    }

    private void setMinWordLength(List<String> optionsArgs) {
        optionsArgs.add(Switch.MIN_WORD_LENGTH.getLabel());
        optionsArgs.add(minWordLenField.getText());
    }

    private void setMaxWordLength(List<String> optionsArgs) {
        optionsArgs.add(Switch.MAX_WORD_LENGTH.getLabel());
        optionsArgs.add(maxWordLenField.getText());
    }

    private void setMaxResults(List<String> optionsArgs) {
        optionsArgs.add(Switch.MAX_RESULTS.getLabel());
        optionsArgs.add(maxResultsField.getText());
    }

    private void setMaxWordsInAnagram(List<String> optionsArgs) {
        optionsArgs.add(Switch.MAX_WORDS.getLabel());
        optionsArgs.add(maxWordsInAnagramField.getText());
    }

    private void setTimeout(List<String> optionsArgs) {
        optionsArgs.add(Switch.TIMEOUT.getLabel());
        optionsArgs.add(maxTimeoutField.getText());
    }

    private void setRestrictPermutations(List<String> optionsArgs) {
        if (restrictPermutationsCheckBox.isSelected())
            optionsArgs.add(Switch.RESTRICT_PERMUTATIONS.getLabel());
    }

    private void setExcludeDuplicates(List<String> optionsArgs) {
        if (excludeDuplicatesCheckBox.isSelected())
            optionsArgs.add(Switch.EXCLUDE_DUPLICATES.getLabel());
    }

    private void setStartFromWord(List<String> optionsArgs) {
        if (!startFromField.getText().isBlank()) {
            optionsArgs.add(Switch.START_FROM.getLabel());
            optionsArgs.add(startFromField.getText());
        }
    }

    private void setIncludeWord(List<String> optionsArgs) {
        if (!includeWordField.getText().isBlank()) {
            optionsArgs.add(Switch.INCLUDE_WORD.getLabel());
            optionsArgs.add(includeWordField.getText());
        }
    }

    private void setExcludeWord(List<String> optionsArgs) {
        if (!excludeWordField.getText().isBlank()) {
            optionsArgs.add(Switch.EXCLUDE_WORD.getLabel());
            optionsArgs.add(excludeWordField.getText());
        }
    }

    private void setIncludeWordWithSuffix(List<String> optionsArgs) {
        if (!includeSuffixField.getText().isBlank()) {
            optionsArgs.add(Switch.INCLUDE_WORD_WITH_SUFFIX.getLabel());
            optionsArgs.add(includeSuffixField.getText());
        }
    }

}
