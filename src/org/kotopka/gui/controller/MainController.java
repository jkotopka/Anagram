package org.kotopka.gui.controller;

import org.kotopka.gui.model.AnagramGenerator;
import org.kotopka.gui.view.MainFrame;
import org.kotopka.gui.view.OptionsDialog;
import org.kotopka.parser.Parser;
import org.kotopka.parser.ParserFactory;

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

    public void updateOptions(String[] args) {
        // TODO: move parser creation into AnagramGenerator?
        Parser parser = ParserFactory.getParser(args);

        anagramGenerator = new AnagramGenerator(parser, this);
    }

    public void generateAnagrams(String inputString) {
        // TODO: spawn a thread for a progress bar or something
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
