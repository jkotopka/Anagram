package org.kotopka.gui.controller;

import org.kotopka.anagram.Anagram;
import org.kotopka.gui.model.AnagramGenerator;
import org.kotopka.gui.view.MainFrame;
import org.kotopka.parser.Parser;
import org.kotopka.parser.ParserFactory;

import java.util.Arrays;

public class MainController {

    private final MainFrame frame;

    public MainController() {
        this.frame = new MainFrame();

        invokeAnagramGenerate();
    }

    private void invokeAnagramGenerate() {
        frame.addActionListener(e -> {
            String[] args = frame.getOptions();

            System.out.println("In invokeAnagramGenerate(): " + Arrays.toString(args));

            Parser parser = ParserFactory.getParser(args);
            AnagramGenerator anagramGenerator = new AnagramGenerator(parser);
            Anagram anagram = anagramGenerator.getAnagram();
            String anagramString = frame.getAnagramString();
            java.util.List<String> anagrams = anagram.findMultipleWordAnagramsOf(anagramString);
            java.util.Set<String> subWords = anagram.findAllValidSubWordsAsSet(anagramString);

            frame.setAnagram(anagrams);
            frame.setSubWords(subWords);
        });
    }
}
