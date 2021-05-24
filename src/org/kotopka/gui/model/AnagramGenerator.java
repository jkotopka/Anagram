package org.kotopka.gui.model;

import org.kotopka.anagram.Anagram;
import org.kotopka.anagram.AnagramFactory;
import org.kotopka.dictionary.Dictionary;
import org.kotopka.dictionary.DictionaryFactory;
import org.kotopka.gui.controller.MainController;
import org.kotopka.parser.Parser;
import org.kotopka.parser.ParserFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AnagramGenerator {

    private final Anagram anagram;
    private final MainController mainController;

    private int anagramCount;
    private int subWordCount;
    private double executionTimeInSeconds;

    public AnagramGenerator(String[] args, MainController mainController) {
        this.mainController = mainController;

        Parser parser = ParserFactory.getParser(args);
        Dictionary dictionary = DictionaryFactory.getDictionary(parser);

        this.anagram = AnagramFactory.getAnagram(parser, dictionary);
    }

    public void generateAnagrams(String anagramString) {
        long start = System.currentTimeMillis();
        List<String> anagrams = anagram.findMultipleWordAnagramsOf(anagramString);
        long end = System.currentTimeMillis();

        anagramCount = anagrams.size();
        executionTimeInSeconds = (double) (end - start) / 1000;

        mainController.updateAnagrams(collectionToString(anagrams));
    }

    public void generateSubWords(String subWordString) {
        Set<String> subWords = anagram.findAllValidSubWordsAsSet(subWordString);

        subWordCount = subWords.size();

        mainController.updateSubWords(collectionToString(subWords));
    }

    private String collectionToString(Collection<String> anagrams) {
        StringBuilder sb = new StringBuilder();

        for (String s : anagrams) {
            sb.append(s);
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public String getStatus() {
        return  "Anagrams found: "     + anagramCount +
                "   Sub-words found: " + subWordCount +
                "   Generation time: " + executionTimeInSeconds + " seconds";
    }

}
