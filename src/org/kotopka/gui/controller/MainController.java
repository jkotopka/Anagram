package org.kotopka.gui.controller;

import org.kotopka.anagram.Anagram;
import org.kotopka.dictionary.Dictionary;
import org.kotopka.gui.view.MainFrame;

public class MainController {

    private final MainFrame frame;

    public MainController() {
        this.frame = new MainFrame();

        Dictionary dictionary = new Dictionary.Builder("dictionary-large.txt").build();
        Anagram anagram = new Anagram(dictionary);

        frame.addActionListener(e -> {
            String anagramString = frame.getAnagramString();
            java.util.List<String> anagrams = anagram.findMultipleWordAnagramsOf(anagramString);
            java.util.Set<String> subWords = anagram.findAllValidSubWordsAsSet(anagramString);

            frame.setAnagram(anagrams);
            frame.setSubWords(subWords);
        });
    }
}
