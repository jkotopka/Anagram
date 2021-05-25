package org.kotopka.gui.view;

import org.kotopka.gui.controller.MainController;
import org.kotopka.parser.Switch;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OptionsDialog extends JDialog {

    private final MainController mainController;

    // file stuff
    private File alternateDictionaryFile;
    private File excludeWordsFile;

    // panel stuff
    private final JPanel mainPanel;

    // labels
    private final JLabel alternateDictionaryLabel;
    private final JLabel excludeWordsFileLabel;
    private final JLabel maxWordLenLabel;
    private final JLabel minWordLenLabel;
    private final JLabel maxResultsLabel;
    private final JLabel maxWordsInAnagramLabel;
    private final JLabel maxTimeoutLabel;
    private final JLabel restrictPermutationsLabel;
    private final JLabel excludeDuplicatesLabel;
    private final JLabel startFromLabel;
    private final JLabel includeWordLabel;
    private final JLabel includeSuffixLabel;
    private final JLabel excludeWordLabel;

    // fields
    private final JButton alternateDictionaryButton;
    private final JButton excludeWordsFileButton;
    private final JFormattedTextField maxWordLenField;
    private final JFormattedTextField minWordLenField;
    private final JFormattedTextField maxResultsField;
    private final JFormattedTextField maxWordsInAnagramField;
    private final JFormattedTextField maxTimeoutField;
    private final JCheckBox restrictPermutationsCheckBox;
    private final JCheckBox excludeDuplicatesCheckBox;
    private final JTextField startFromField;
    private final JTextField includeWordField;
    private final JTextField includeSuffixField;
    private final JTextField excludeWordField;

    // buttons
    private final JButton okButton;
    private final JButton cancelButton;

    public OptionsDialog(MainController mainController) {
        this.mainController = mainController;
        this.mainPanel = new JPanel();

        // integer number format
        NumberFormat format = NumberFormat.getIntegerInstance();

        format.setGroupingUsed(false);

        NumberFormatter numberFormatter = new NumberFormatter(format);

        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(Integer.MAX_VALUE);

        this.excludeWordsFileLabel = new JLabel("File of words to exclude: ");
        this.excludeWordsFileButton =  new JButton("Select file");

        this.alternateDictionaryLabel = new JLabel("Alternate dictionary file: ");
        this.alternateDictionaryButton = new JButton("Select file");

        this.maxWordLenLabel = new JLabel("Max word length: ");
        this.maxWordLenField = new JFormattedTextField(numberFormatter);

        this.minWordLenLabel = new JLabel("Min word length: ");
        this.minWordLenField = new JFormattedTextField(numberFormatter);

        this.maxResultsLabel = new JLabel("Max results: ");
        this.maxResultsField = new JFormattedTextField(numberFormatter);

        this.maxWordsInAnagramLabel = new JLabel("Max words in anagram: ");
        this.maxWordsInAnagramField = new JFormattedTextField(numberFormatter);

        this.maxTimeoutLabel = new JLabel("Max timeout in seconds");
        this.maxTimeoutField = new JFormattedTextField(numberFormatter);

        this.restrictPermutationsLabel = new JLabel("Restrict permutations: ");
        this.restrictPermutationsCheckBox = new JCheckBox();

        this.excludeDuplicatesLabel = new JLabel("Exclude duplicate words: ");
        this.excludeDuplicatesCheckBox = new JCheckBox();

        this.startFromLabel = new JLabel("Start from word: ");
        this.startFromField = new JTextField();

        this.includeWordLabel = new JLabel("Include word: ");
        this.includeWordField = new JTextField();

        this.includeSuffixLabel = new JLabel("Include word with suffix: ");
        this.includeSuffixField = new JTextField();

        this.excludeWordLabel = new JLabel("Exclude word: ");
        this.excludeWordField = new JTextField();

        this.okButton = new JButton("Ok");
        this.cancelButton = new JButton("Cancel");

        setupPanel();
        setupFrame();
        setupButtonListeners();
        updateOptions();
    }

    private void setupPanel() {
        mainPanel.setLayout(new BorderLayout());

        alternateDictionaryLabel.setLabelFor(alternateDictionaryButton);
        excludeWordsFileLabel.setLabelFor(excludeWordsFileButton);

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

        includeSuffixLabel.setLabelFor(includeSuffixField);
        includeSuffixField.setColumns(10);

        excludeWordLabel.setLabelFor(excludeWordField);
        excludeWordField.setColumns(10);

        // layout labels in a panel
        GridLayout dictionaryLabelLayout = new GridLayout(0, 1);
        dictionaryLabelLayout.setVgap(5);
        JPanel dictionaryLabelPane = new JPanel(dictionaryLabelLayout);
        dictionaryLabelPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dictionaryLabelPane.add(alternateDictionaryLabel);
        dictionaryLabelPane.add(excludeWordsFileLabel);
        dictionaryLabelPane.add(maxWordLenLabel);
        dictionaryLabelPane.add(minWordLenLabel);

        GridLayout anagramLabelLayout = new GridLayout(0, 1);
        anagramLabelLayout.setVgap(5);
        JPanel anagramLabelPane = new JPanel(anagramLabelLayout);
        anagramLabelPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        anagramLabelPane.add(maxResultsLabel);
        anagramLabelPane.add(maxWordsInAnagramLabel);
        anagramLabelPane.add(maxTimeoutLabel);
        anagramLabelPane.add(restrictPermutationsLabel);
        anagramLabelPane.add(excludeDuplicatesLabel);
        anagramLabelPane.add(startFromLabel);
        anagramLabelPane.add(includeWordLabel);
        anagramLabelPane.add(includeSuffixLabel);
        anagramLabelPane.add(excludeWordLabel);

        // layout the inputs
        GridLayout dictionaryInputLayout = new GridLayout(0, 1);
        dictionaryInputLayout.setVgap(5);
        JPanel dictionaryInputPane = new JPanel(dictionaryInputLayout);
        dictionaryInputPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dictionaryInputPane.add(alternateDictionaryButton);
        dictionaryInputPane.add(excludeWordsFileButton);
        dictionaryInputPane.add(maxWordLenField);
        dictionaryInputPane.add(minWordLenField);

        GridLayout anagramInputLayout = new GridLayout(0, 1);
        anagramInputLayout.setVgap(5);
        JPanel anagramInputPane = new JPanel(anagramInputLayout);
        anagramInputPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        anagramInputPane.add(maxResultsField);
        anagramInputPane.add(maxWordsInAnagramField);
        anagramInputPane.add(maxTimeoutField);
        anagramInputPane.add(restrictPermutationsCheckBox);
        anagramInputPane.add(excludeDuplicatesCheckBox);
        anagramInputPane.add(startFromField);
        anagramInputPane.add(includeWordField);
        anagramInputPane.add(includeSuffixField);
        anagramInputPane.add(excludeWordField);

        JPanel dictionaryOptionsPane = new JPanel(new BorderLayout());
        dictionaryOptionsPane.add(dictionaryLabelPane, BorderLayout.CENTER);
        dictionaryOptionsPane.add(dictionaryInputPane, BorderLayout.LINE_END);
        dictionaryOptionsPane.setBorder(
                new CompoundBorder(
                        BorderFactory.createEmptyBorder(0, 0, 5, 0),
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Dictionary options", TitledBorder.LEFT, TitledBorder.TOP)
                )
        );

        JPanel anagramOptionsPane = new JPanel(new BorderLayout());
        anagramOptionsPane.add(anagramLabelPane, BorderLayout.CENTER);
        anagramOptionsPane.add(anagramInputPane, BorderLayout.LINE_END);
        anagramOptionsPane.setBorder(
                new CompoundBorder(
                        BorderFactory.createEmptyBorder(5, 0, 5, 0),
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Anagram generation options", TitledBorder.LEFT, TitledBorder.TOP)
                )
        );

        /// layout the ok/cancel buttons
        GridLayout buttonPaneLayout = new GridLayout(0, 1);
        buttonPaneLayout.setVgap(5);
        JPanel buttonPane = new JPanel(buttonPaneLayout);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        // populate the panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(dictionaryOptionsPane, BorderLayout.NORTH);
        mainPanel.add(anagramOptionsPane, BorderLayout.CENTER);
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
        alternateDictionaryButton.addActionListener(e -> initializeAlternateDictionaryFile());
        excludeWordsFileButton.addActionListener(e -> initializeExcludeWordsFile());

        okButton.addActionListener(e -> {
            updateOptions();
            this.dispose();
        });

        cancelButton.addActionListener(e -> this.dispose());
    }

    private void initializeAlternateDictionaryFile() {
        alternateDictionaryFile = openFile();
    }

    private void initializeExcludeWordsFile() {
        excludeWordsFile = openFile();
    }

    private File openFile() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showOpenDialog(fileChooser.getParent());

        if (JFileChooser.APPROVE_OPTION == response)
            return fileChooser.getSelectedFile().getAbsoluteFile();
        else
            return null;
    }

    private void updateOptions() {
        List<String> optionsArgs = new ArrayList<>();

        setAlternateDictionaryFile(optionsArgs);
        setExcludeWordsFile(optionsArgs);
        setMinWordLength(optionsArgs);
        setMaxWordLength(optionsArgs);
        setMaxResults(optionsArgs);
        setMaxWordsInAnagram(optionsArgs);
        setTimeout(optionsArgs);
        setRestrictPermutations(optionsArgs);
        setExcludeDuplicates(optionsArgs);
        setStartFromWord(optionsArgs);
        setIncludeWord(optionsArgs);
        setIncludeWordWithSuffix(optionsArgs);
        setExcludeWord(optionsArgs);

        String[] args = optionsArgs.toArray(new String[0]);

        try {
            mainController.updateOptions(args);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setAlternateDictionaryFile(List<String> optionsArgs) {
        if (alternateDictionaryFile != null) {
            optionsArgs.add(Switch.DICT_FILE.getLabel());
            optionsArgs.add(alternateDictionaryFile.getName());
        }
    }

    private void setExcludeWordsFile(List<String> optionsArgs) {
        if (excludeWordsFile != null) {
            optionsArgs.add(Switch.EXCLUDE_FROM_DICT_FILE.getLabel());
            optionsArgs.add(excludeWordsFile.getName());
        }
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

    private void setIncludeWordWithSuffix(List<String> optionsArgs) {
        if (!includeSuffixField.getText().isBlank()) {
            optionsArgs.add(Switch.INCLUDE_WORD_WITH_SUFFIX.getLabel());
            optionsArgs.add(includeSuffixField.getText());
        }
    }

    private void setExcludeWord(List<String> optionsArgs) {
        if (!excludeWordField.getText().isBlank()) {
            optionsArgs.add(Switch.EXCLUDE_WORD.getLabel());
            optionsArgs.add(excludeWordField.getText());
        }
    }

}
