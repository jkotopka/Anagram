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

        dictFileStream.forEach(word -> {
            String sortedWord = sortWordLetters(word);

            if (!dictionary.containsKey(sortedWord)) {
                dictionary.put(sortedWord, new ArrayList<>());
            }

            dictionary.get(sortedWord).add(word);
        });

        dictFileStream.close();
    }

    private String sortWordLetters(String word) {
        char[] wordChars = word.toUpperCase().toCharArray();

        Arrays.sort(wordChars);

        return String.valueOf(wordChars).trim();
    }

    public List<String> findAnagramsOfWord(String word) {
        String sortedWord = sortWordLetters(word);

        if (dictionary.containsKey(sortedWord))
            return List.copyOf(dictionary.get(sortWordLetters(word)));

        return null;
    }

    public Set<String> findAllValidSubWords(String word) {
        List<String> subStrings = new ArrayList<>();

        generateSubStrings("", sortWordLetters(word), 0, subStrings);

        // TODO: might not want a set because an anagram might contain multiple copies of the same word if possible
        Set<String> validSubWords = new TreeSet<>();

        for (String subString : subStrings) {
            if (dictionary.containsKey(subString)) {
                validSubWords.addAll(dictionary.get(subString));
            }
        }

        return validSubWords;
    }

    private void generateSubStrings(String prefix, String word, int index, List<String> subStrings) {
        if (index == word.length()) return;

        for (int i = index; i < word.length(); i++) {
            subStrings.add(prefix + word.charAt(i));
            generateSubStrings(prefix + word.charAt(i), word, i + 1, subStrings);
        }
    }

    private String concatenateStringArrayToString(String[] arr) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(arr).forEach(word -> sb.append(word).append(" "));

        return sb.toString().trim();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Anagram <word>");
            System.exit(0);
        }

        Anagram anagram = new Anagram("dictionary-yawl.txt");
        String word = anagram.concatenateStringArrayToString(args);
        List<String> anagrams = anagram.findAnagramsOfWord(word);

        if (anagrams != null) {
            System.out.println("Valid anagrams:");
            anagrams.forEach(System.out::println);
        } else {
            System.out.printf("No anagrams of \"%s\" found\n", word);
        }

        Set<String> allWordsFound = anagram.findAllValidSubWords(word);

        if (allWordsFound != null) {
            System.out.println("\nValid sub-words:");
            allWordsFound.forEach(System.out::println);
        } else {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        }
    }

}
