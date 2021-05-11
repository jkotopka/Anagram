package org.kotopka;

import java.util.*;

/**
 * {@code Anagram} - Finds and prints a list of all single-word anagrams from a
 * single input word. Valid words are those contained within a dictionary file supplied during construction.
 * <br><br>
 * This version considers a valid word to be its own anagram and will include it in the output list.
 */
public class Anagram {

    private final Dictionary dictionary;

    public Anagram(String dictFile) {
        Objects.requireNonNull(dictFile, "Constructor argument cannot be null");

        this.dictionary = new Dictionary(dictFile);
    }

    public List<String> findAnagramsOfWord(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = Word.sortLetters(word);

        return dictionary.getListOrEmpty(sortedWord);
    }

    public List<String> findAllValidSubWordsAsList(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        List<String> validSubWords = new ArrayList<>();

        populateSubstringCollection(word, validSubWords);
        Collections.sort(validSubWords);

        return validSubWords;
    }

    public Set<String> findAllValidSubWordsAsSet(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        Set<String> validSubWords = new TreeSet<>();

        populateSubstringCollection(word, validSubWords);

        return validSubWords;
    }

    // TODO: TESTING
    public List<String> getSubstringsWhenFirstSubstringIsSubtracted(String word) {
        List<String> workingList = findAllValidSubWordsAsList(word);
        List<String> reducedList = new ArrayList<>();

        for (String s : workingList) {
            String diff = Word.subtract(Word.sortLetters(word), Word.sortLetters(s));

            reducedList.addAll(dictionary.getListOrEmpty(diff));
        }

        return reducedList;
    }
    // TODO: END TESTING

    private void populateSubstringCollection(String word, Collection<String> collection) {
        for (String subString : generateSubStrings(word))
            collection.addAll(dictionary.getListOrEmpty(subString));
    }

    private List<String> generateSubStrings(String word) {
        List<String> subStrings = new ArrayList<>();

        generateSubStringsRecursively("", Word.sortLetters(word), 0, subStrings);

        return subStrings;
    }

    private void generateSubStringsRecursively(String prefix, String word, int index, List<String> subStrings) {
        if (index == word.length()) return;

        for (int i = index; i < word.length(); i++) {
            subStrings.add(prefix + word.charAt(i));
            generateSubStringsRecursively(prefix + word.charAt(i), word, i + 1, subStrings);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Anagram <word>");
            System.exit(0);
        }

        Anagram anagram = new Anagram("dictionary-large.txt");
        String word = String.join(" ",args);
        List<String> anagrams = anagram.findAnagramsOfWord(word);

        if (anagrams.isEmpty()) {
            System.out.printf("No anagrams of \"%s\" found\n", word);
        } else {
            System.out.println("Valid anagrams:");
            anagrams.forEach(System.out::println);
        }

//        Set<String> allWordsFound = anagram.findAllValidSubWordsAsSet(word);
        List<String> allWordsFound = anagram.findAllValidSubWordsAsList(word);

        if (allWordsFound.isEmpty()) {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        } else {
            System.out.println("\nValid sub-words of \"" + word + "\" found: " + allWordsFound.size());
            allWordsFound.forEach(System.out::println);
        }

        System.out.println("\nSubstrings");
        anagram.getSubstringsWhenFirstSubstringIsSubtracted(word).forEach(System.out::println);
    }

}
