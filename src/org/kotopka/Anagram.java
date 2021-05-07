package org.kotopka;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * {@code Anagram} - Finds and prints a list of all single-word anagrams from a
 * single input word. Valid words are those contained within a dictionary file supplied during construction.
 * <br><br>
 * This version considers a valid word to be its own anagram and will include it in the output list.
 */
public class Anagram {

    private final Map<String, List<String>> dictionary;

    public Anagram(String dictFile) {
        Objects.requireNonNull(dictFile, "Constructor argument cannot be null");

        this.dictionary = new HashMap<>();

        try {
            buildDictionary(dictFile);
        } catch (IOException e) {
            System.err.println("Error building dictionary file!");
            e.printStackTrace();
        }
    }

    private void buildDictionary(String dictFile) throws IOException {
        Stream<String> dictFileStream = Files.lines(Paths.get(dictFile));

        dictFileStream.forEach(this::addWordToDictionary);
        dictFileStream.close();
    }

    private void addWordToDictionary(String word) {
        String sortedWord = sortWordLetters(word);

        if (!dictionary.containsKey(sortedWord))
            dictionary.put(sortedWord, new ArrayList<>());

        dictionary.get(sortedWord).add(word);
    }

    private String sortWordLetters(String word) {
        char[] wordChars = word.toUpperCase().toCharArray();

        Arrays.sort(wordChars);

        return String.valueOf(wordChars).trim();
    }

    public List<String> findAnagramsOfWord(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = sortWordLetters(word);

        if (dictionary.containsKey(sortedWord))
            return List.copyOf(dictionary.get(sortedWord));

        return null; // eh, not a big fan, maybe better to return a new empty List<> or something...
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

    private void populateSubstringCollection(String word, Collection<String> collection) {
        for (String subString : generateSubStrings(word))
            if (dictionary.containsKey(subString))
                collection.addAll(dictionary.get(subString));
    }

    private List<String> generateSubStrings(String word) {
        List<String> subStrings = new ArrayList<>();

        generateSubStringsRecursively("", sortWordLetters(word), 0, subStrings);

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

        if (anagrams != null) {
            System.out.println("Valid anagrams:");
            anagrams.forEach(System.out::println);
        } else {
            System.out.printf("No anagrams of \"%s\" found\n", word);
        }

        Set<String> allWordsFound = anagram.findAllValidSubWordsAsSet(word);

        if (allWordsFound != null) {
            System.out.println("\nValid sub-words of \"" + word + "\" found: " + allWordsFound.size());
            allWordsFound.forEach(System.out::println);
        } else {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        }
    }

}
