package org.kotopka.deprecated;

import org.kotopka.anagram.Anagram;
import org.kotopka.dictionary.Dictionary;

import java.util.List;
import java.util.Set;

public class TestClient {

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
        OldCommandlineParser parser = new OldCommandlineParser("TestClient", args);

        parser.parseArgs();

        Dictionary dictionary = new Dictionary.Builder("dictionary-large.txt")
                .setDictionaryFile(parser.getDictFile())
                .setMinWordLength(parser.getMinWordLength())
                .setMaxWordLength(parser.getMaxWordLength())
                .excludeWordsFromFile(parser.getExcludeFromDictionaryFilename())
                .build();

        Anagram anagram = new Anagram(dictionary)
                .setMaxResults(parser.getMaxResults())
                .setMaxWordsInAnagram(parser.getMaxWordsInAnagram())
                .setMaxTimeoutInSeconds(parser.getMaxTimeout())
                .setShouldExcludeDuplicates(parser.shouldExcludeDuplicates())
                .setShouldRestrictPermutations(parser.shouldRestrictPermutations())
                .setStartFrom(parser.getStartFrom())
                .setIncludeWord(parser.getIncludeWord())
                .setExcludeWord(parser.getExcludeWord())
                .setIncludeWordWithSuffix(parser.getSuffix());

        String word = parser.getPhrase();

        parser.printState();

        findAndPrintSubWords(anagram, word);
        findAndPrintAnagrams(anagram, word);
    }

}
