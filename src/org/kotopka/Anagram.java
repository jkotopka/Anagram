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
    private int count;
    private int max;
    private int minWordLength;

    public Anagram(String dictFile) {
        Objects.requireNonNull(dictFile, "Constructor argument cannot be null");

        this.dictionary = new Dictionary(dictFile);
    }

    public List<String> findSingleAnagramsOf(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = Word.sortLetters(word);

        return dictionary.getListOrEmpty(sortedWord);
    }

    public List<String> findAllAnagramsOf(String word, int minWordLength, int max) {
        LinkedList<String> stack = new LinkedList<>();
        List<String> anagrams    = new ArrayList<>();

        resetInstanceFieldValues(minWordLength, max);
        buildAnagramList(word, stack, anagrams);
        Collections.sort(anagrams);

        return anagrams;
    }

    private void resetInstanceFieldValues(int minWordLength, int max) {
        this.minWordLength = minWordLength;
        this.max   = max;
        this.count = 0;
    }

    private void buildAnagramList(String word, LinkedList<String> stack, List<String> anagrams) {
        for (String s : findAllValidSubWordsAsSet(word)) {
            stack.push(s);

            String diff = Word.subtract(Word.sortLetters(word), Word.sortLetters(s));

            if (diff.isBlank()) {
                anagrams.add(String.join(" ", stack));
                count++;
            }

            if (count == max) return;

            buildAnagramList(diff, stack, anagrams);
            stack.pop();
        }
    }

    public Set<String> findAllValidSubWordsAsSet(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        Set<String> validSubWords = new TreeSet<>();

        for (String subString : generateSubStrings(word))
            if(subString.length() >= minWordLength)
                validSubWords.addAll(dictionary.getListOrEmpty(subString));

        return validSubWords;
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

        Anagram anagram = new Anagram("corncob-lowercase.txt");
        String word = String.join(" ",args);

        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        } else {
            System.out.println("Valid sub-words of \"" + word + "\" found: " + allSubWords.size());
            allSubWords.forEach(System.out::println);
        }

        List<String> allAnagrams = anagram.findAllAnagramsOf(word, 3, 1000);

        if (allAnagrams.isEmpty()) {
            System.out.printf("\nNo anagrams of \"%s\" found\n", word);
        } else {
            System.out.println("\nAll anagrams of " + word + " found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }
    }

}
