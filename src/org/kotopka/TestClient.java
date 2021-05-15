package org.kotopka;

import java.util.List;
import java.util.Set;

public class TestClient {

    private static void findAndPrintSubWords(Anagram anagram, String word) {
        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        } else {
            System.out.printf("Valid sub-words of \"%s\" found: %d\n", word, allSubWords.size());
            allSubWords.forEach(System.out::println);
        }
    }

    private static void findAndPrintAnagrams(Anagram anagram, String word) {
        long startTime = System.currentTimeMillis();
        List<String> allAnagrams = anagram.findMultipleWordAnagramsOf(word);
        long endTime = System.currentTimeMillis();

        if (allAnagrams.isEmpty()) {
            System.out.printf("\nNo anagrams of \"%s\" found\n", word);
        } else {
            System.out.printf("\nAnagrams of \"%s\" found: %d\n", word, allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }

        System.out.println("Elapsed time: " + (double)(endTime - startTime) / 1000);
    }

    public static void main(String[] args) {
        CommandlineParser parser = new CommandlineParser("TestClient", args);

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
                .shouldExcludeDuplicates(parser.shouldExcludeDuplicates())
                .shouldRestrictPermutations(parser.shouldRestrictPermutations())
                .startFrom(parser.getStartFrom())
                .includeWord(parser.getIncludeWord())
                .excludeWord(parser.getExcludeWord())
                .includeWordWithSuffix(parser.getSuffix());

        String word = parser.getPhrase();

        parser.printState();

        findAndPrintSubWords(anagram, word);
        findAndPrintAnagrams(anagram, word);
    }

}
