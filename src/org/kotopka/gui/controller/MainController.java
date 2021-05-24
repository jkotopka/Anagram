package org.kotopka.gui.controller;

import org.kotopka.gui.model.AnagramGenerator;
import org.kotopka.gui.view.MainFrame;
import org.kotopka.gui.view.OptionsDialog;

public class MainController {

    private final MainFrame mainFrame;
    private final OptionsDialog optionsDialog;
    private AnagramGenerator anagramGenerator;

    public MainController() {
        this.mainFrame = new MainFrame(this);
        this.optionsDialog = new OptionsDialog(this);
    }

    public void showOptionsDialog() {
        optionsDialog.setLocationRelativeTo(mainFrame);
        optionsDialog.setVisible(true);
    }

    public void updateOptions() {
        String[] args = optionsDialog.getArgs();

        anagramGenerator = new AnagramGenerator(args, this);
    }

    public void updateOptions(String[] args) {
        anagramGenerator = new AnagramGenerator(args, this);
    }

    public void generateAnagrams(String inputString) {
        anagramGenerator.generateAnagrams(inputString);
    }

    public void generateSubWords(String inputString) {
        anagramGenerator.generateSubWords(inputString);
    }

    public void updateAnagrams(String anagrams) {
        mainFrame.setAnagramTextArea(anagrams);
    }

    public void updateSubWords(String subWords) {
        mainFrame.setSubWordsTextArea(subWords);
    }

    public void updateStatus() {
        String status = anagramGenerator.getStatus();

        mainFrame.setStatusBar(status);
    }

}
