package org.kotopka;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestClient {

    private static void findAndPrintSubWords(Anagram anagram, String word) {
        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.printf("No sub-words of \"%s\" found\n", word);
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
            System.out.printf("\nNo anagrams of \"%s\" found\n", word);
        } else {
            System.out.println("\nAll anagrams of " + word + " found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }

        System.out.println("Elapsed time: " + (double)(endTime - startTime) / 1000);
    }

    /*
        TODO: this testing class requires manually configuring the testing parameters as
          there is no commandline parsing beyond the basic collection of the string to be
          anagram-ized.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Anagram <word>");
            System.exit(0);
        }

        // TODO: manually call toExclude.add(someWord) to add each word to exclude
        Set<String> toExclude = new HashSet<>();

        // TODO: manually change these parameters to suit the test
        Dictionary dictionary = new Dictionary.Builder(Paths.get("dictionary-large.txt"))
                .setMinWordLength(3)
                .setMaxWordLength(20)
                .excludeWordsFromSet(toExclude)
                .build();

        Anagram anagram = new Anagram(dictionary)
                .setMaxResults(10000)
                .setMaxWordsInAnagram(10)
                .shouldExcludeDuplicates(true)
                .shouldRestrictPermutations(true)
                .startFrom("")
                .includeWord("")
                .excludeWord("")
                .includeWordWithSuffix("");

        String word = String.join(" ",args);

        findAndPrintSubWords(anagram, word);
        findAndPrintAnagrams(anagram, word);
    }

}
