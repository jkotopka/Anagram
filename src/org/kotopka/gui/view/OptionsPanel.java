package org.kotopka.gui.view;

import org.kotopka.parser.Switch;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsPanel extends JPanel {

    // labels
    private JLabel maxWordLenLabel;
    private JLabel minWordLenLabel;
    private JLabel restrictPermutationsLabel;
    private JLabel includeWordLabel;
    private JLabel excludeWordLabel;

    // fields
    private JFormattedTextField maxWordLenField;
    private JFormattedTextField minWordLenField;
    private JCheckBox restrictPermutationsCheckBox;
    private JTextField includeWordField;
    private JTextField excludeWordField;

    // buttons
    private JButton okButton;
    private JButton cancelButton;

    // integer number format
    private NumberFormat numberFormat;

    private OptionsDialog dialog;

    private List<String> optionsArgs;

    public OptionsPanel(OptionsDialog dialog) {
        super(new BorderLayout());

        this.dialog = dialog;
        this.optionsArgs = new ArrayList<>();

        numberFormat = NumberFormat.getNumberInstance();

        maxWordLenLabel = new JLabel("Max word length: ");
        maxWordLenField = new JFormattedTextField(numberFormat);
        maxWordLenLabel.setLabelFor(maxWordLenField);
        maxWordLenField.setValue(10);
        maxWordLenField.setColumns(10);

        minWordLenLabel = new JLabel("Min word length: ");
        minWordLenField = new JFormattedTextField(numberFormat);
        minWordLenLabel.setLabelFor(minWordLenField);
        minWordLenField.setValue(1);
        minWordLenField.setColumns(10);

        restrictPermutationsLabel = new JLabel("Restrict permutations: ");
        restrictPermutationsCheckBox = new JCheckBox();
        restrictPermutationsLabel.setLabelFor(restrictPermutationsCheckBox);

        includeWordLabel = new JLabel("Include word: ");
        includeWordField = new JTextField();
        includeWordLabel.setLabelFor(includeWordField);
        includeWordField.setColumns(10);

        excludeWordLabel = new JLabel("Exclude word: ");
        excludeWordField = new JTextField();
        excludeWordLabel.setLabelFor(excludeWordField);
        excludeWordField.setColumns(10);

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        setupPanel();
        setupButtonListeners();
    }

    private void setupPanel() {
        // layout labels in a panel
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        labelPane.add(maxWordLenLabel);
        labelPane.add(minWordLenLabel);
        labelPane.add(restrictPermutationsLabel);
        labelPane.add(includeWordLabel);
        labelPane.add(excludeWordLabel);

        // layout the inputs
        JPanel inputPane = new JPanel(new GridLayout(0, 1));
        inputPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPane.add(maxWordLenField);
        inputPane.add(minWordLenField);
        inputPane.add(restrictPermutationsCheckBox);
        inputPane.add(includeWordField);
        inputPane.add(excludeWordField);

        /// layout the ok/cancel buttons
        JPanel buttonPane = new JPanel(new GridLayout(0, 1));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        // populate the panel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(inputPane, BorderLayout.LINE_END);
        add(buttonPane, BorderLayout.AFTER_LAST_LINE);
    }

    public void setupButtonListeners() {
        // setup the buttons
        okButton.addActionListener(e -> {
            optionsArgs = new ArrayList<>();

            // set up the optionsArgs list as if it were an array
            optionsArgs.add(Switch.MIN_WORD_LENGTH.getLabel());
            optionsArgs.add(minWordLenField.getText());

            optionsArgs.add(Switch.MAX_WORD_LENGTH.getLabel());
            optionsArgs.add(maxWordLenField.getText());

            if (restrictPermutationsCheckBox.isSelected())
                optionsArgs.add(Switch.RESTRICT_PERMUTATIONS.getLabel());

            if (!includeWordField.getText().isBlank()) {
                optionsArgs.add(Switch.INCLUDE_WORD.getLabel());
                optionsArgs.add(includeWordField.getText());
            }

            if (!excludeWordField.getText().isBlank()) {
                optionsArgs.add(Switch.EXCLUDE_WORD.getLabel());
                optionsArgs.add(excludeWordField.getText());
            }

            // TODO: Testing
            // build array out of optionsArgs then build Parser from args[]
            String[] args = optionsArgs.toArray(new String[0]);

            System.out.println(Arrays.toString(args));

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });
    }

    public String[] getOptionsArgs() {
        return optionsArgs.toArray(new String[0]);
    }

}
