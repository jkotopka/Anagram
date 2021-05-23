package org.kotopka.gui.controller;

import org.kotopka.anagram.Anagram;
import org.kotopka.gui.model.AnagramGenerator;
import org.kotopka.gui.view.MainFrame;

public class MainController {

    private final MainFrame frame;
    private final AnagramGenerator anagramGenerator;

    public MainController() {
        this.frame = new MainFrame();
        this.anagramGenerator = new AnagramGenerator();

        invokeAnagramGenerate(anagramGenerator.getAnagram());
    }

    private void invokeAnagramGenerate(Anagram anagram) {
        frame.addActionListener(e -> {
            String anagramString = frame.getAnagramString();
            java.util.List<String> anagrams = anagram.findMultipleWordAnagramsOf(anagramString);
            java.util.Set<String> subWords = anagram.findAllValidSubWordsAsSet(anagramString);

            frame.setAnagram(anagrams);
            frame.setSubWords(subWords);
        });
    }
}
