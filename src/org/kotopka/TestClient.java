package org.kotopka;

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
        List<String> allAnagrams = anagram.findAllAnagramsOf(word);
        long endTime = System.currentTimeMillis();

        if (allAnagrams.isEmpty()) {
            System.out.printf("\nNo anagrams of \"%s\" found\n", word);
        } else {
            System.out.println("\nAll anagrams of " + word + " found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }

        System.out.println("Elapsed time: " + (double)(endTime - startTime) / 1000);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Anagram <word>");
            System.exit(0);
        }

        Set<String> toExclude = new HashSet<>();

        Anagram anagram = new Anagram.Builder("dictionary-large.txt")
                .setMinWordLength(3)
                .setMaxWordLength(20)
                .setMaxResults(30000)
                .excludeDuplicates(true)
                .excludeWordSet(toExclude)
                .startFrom("")
                .includeWord("")
                .build();

        String word = String.join(" ",args);

//        findAndPrintSubWords(anagram, word);

        findAndPrintAnagrams(anagram, word);
//        anagram.startFrom("");
//        findAndPrintAnagrams(anagram, word);
//        anagram.startFrom("");
//        anagram.setMaxResults(3);
//        findAndPrintAnagrams(anagram, word);
    }

}
