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

    // TODO: one of the words must have a certain suffix?
    //  also includeWordSet and anagram validation
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Anagram <word>");
            System.exit(0);
        }

        Set<String> toExclude = new HashSet<>();
        toExclude.add("de");
        toExclude.add("rpt");
        toExclude.add("trw");
        toExclude.add("pr");
        toExclude.add("tp");
        toExclude.add("hp");
        toExclude.add("ph");
        toExclude.add("pwt");
        toExclude.add("twp");
        toExclude.add("nw");
        toExclude.add("rd");
        toExclude.add("aaron");
        toExclude.add("koph");
        toExclude.add("phd");
        toExclude.add("tko");
        toExclude.add("rho");
        toExclude.add("tho");
        toExclude.add("dak");
        toExclude.add("apr");
        toExclude.add("arp");
        toExclude.add("doh");
        toExclude.add("dor");
        toExclude.add("tor");
        toExclude.add("ort");
        toExclude.add("owt");
        toExclude.add("ada");
        toExclude.add("nth");
        toExclude.add("ntp");

        Anagram anagram = new Anagram.Builder("dictionary-large.txt")
                .setMinWordLength(3)
                .setMaxWordLength(20)
                .setMaxResults(30000)
                .excludeDuplicates(true)
                .excludeWordSet(toExclude)
                .startFrom("rick")
                .includeWord("rick")
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
