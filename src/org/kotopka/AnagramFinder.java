package org.kotopka;

import org.kotopka.anagram.Anagram;
import org.kotopka.anagram.AnagramFactory;
import org.kotopka.parser.Parser;
import org.kotopka.parser.ParserFactory;
import org.kotopka.parser.Switch;
import org.kotopka.dictionary.Dictionary;
import org.kotopka.dictionary.DictionaryFactory;

import java.util.List;
import java.util.Set;

public class AnagramFinder {

    private static final String NEWLINE = System.lineSeparator();

    private static void findAndPrintSubWords(Anagram anagram, String word) {
        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.println("No sub-words of \"" + word + "\" found");
        } else {
            System.out.println("Valid sub-words of \"" + word + "\" found: " + allSubWords.size());
            allSubWords.forEach(System.out::println);
        }
    }

    private static void findAndPrintAnagrams(Anagram anagram, String word) {
        long startTime = System.currentTimeMillis();
        List<String> allAnagrams = anagram.findMultipleWordAnagramsOf(word);
        long endTime = System.currentTimeMillis();

        if (allAnagrams.isEmpty()) {
            System.out.println(NEWLINE + "No anagrams of \"" + word + "\" found");
        } else {
            System.out.println(NEWLINE + "Anagrams of \"" + word + "\" found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }

        System.out.println("Elapsed time: " + (double)(endTime - startTime) / 1000 + " seconds");
    }

    public static void main(String[] args) {
        Parser commandlineParser = ParserFactory.getParser(args);
        Dictionary dictionary = DictionaryFactory.getDictionary(commandlineParser);
        Anagram anagram = AnagramFactory.getAnagram(commandlineParser, dictionary);

        commandlineParser.printState();

        String word = commandlineParser.getOption(Switch.COLLECT_PHRASE).getString();

        findAndPrintSubWords(anagram, word);
        findAndPrintAnagrams(anagram, word);
    }

}
