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
    private int minWordLength;
    private int maxWordLength;
    private int maxResults;

    private String includeWord;

    private boolean excludeDuplicates;
    private final Set<String> excludeWordsSet;

    public Anagram(String dictFile) {
        Objects.requireNonNull(dictFile, "Constructor argument cannot be null");

        this.dictionary = new Dictionary(dictFile);
        this.maxWordLength = Integer.MAX_VALUE;
        this.maxResults = Integer.MAX_VALUE;
        this.includeWord = "";
        this.excludeWordsSet = new HashSet<>();
    }

    private void setMinWordLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be positive");

        minWordLength = length;
    }

    public void setMaxWordLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be positive");

        maxWordLength = length;
    }

    public void setMaxResults(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max must be positive");

        maxResults = max;
    }

    public void includeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        includeWord = word;
    }

    public void excludeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        excludeWordsSet.add(word);
    }

    public void shouldExcludeDuplicates(boolean shouldExclude) {
        excludeDuplicates = shouldExclude;
    }

    public List<String> findSingleAnagramsOf(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = Word.sortLetters(word);

        return dictionary.getListOrEmpty(sortedWord);
    }

    public List<String> findAllAnagramsOf(String word) {
        LinkedList<String> stack = new LinkedList<>();
        List<String> anagrams    = new ArrayList<>();

        count = 0;

        buildAnagramList(word, stack, anagrams);
        Collections.sort(anagrams);

        return anagrams;
    }

    private void buildAnagramList(String word, LinkedList<String> stack, List<String> anagrams) {
        for (String s : findAllValidSubWordsAsSet(word)) {
            if (excludeWordsSet.contains(s)) continue;
            if (excludeDuplicates) excludeWordsSet.add(s);

            stack.push(s);

            String diff = Word.subtract(word, s);

            if (diff.isBlank() && (includeWord.isBlank() || stack.contains(includeWord))) {
                anagrams.add(String.join(" ", stack));
                count++;
            }

            if (count == maxResults) return;

            buildAnagramList(diff, stack, anagrams);

            if (excludeDuplicates) excludeWordsSet.remove(s);

            stack.pop();
        }
    }

    public Set<String> findAllValidSubWordsAsSet(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        Set<String> validSubWords = new TreeSet<>();

        for (String subString : generateSubStrings(word)) {
            int len = subString.length();

            if(len >= minWordLength && len <= maxWordLength && !excludeWordsSet.contains(word))
                validSubWords.addAll(dictionary.getListOrEmpty(subString));
        }


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

        Anagram anagram = new Anagram("dictionary-large.txt");



        // for "base ball"
        anagram.excludeWord("bas");
        anagram.excludeWord("lb");
        anagram.excludeWord("bs");
        anagram.excludeWord("ab");
        anagram.includeWord("ball");
        anagram.setMinWordLength(2);
        anagram.setMaxWordLength(4);
        anagram.setMaxResults(4);
        anagram.shouldExcludeDuplicates(true);




        String word = String.join(" ",args);

        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        } else {
            System.out.println("Valid sub-words of \"" + word + "\" found: " + allSubWords.size());
            allSubWords.forEach(System.out::println);
        }

        List<String> allAnagrams = anagram.findAllAnagramsOf(word);

        if (allAnagrams.isEmpty()) {
            System.out.printf("\nNo anagrams of \"%s\" found\n", word);
        } else {
            System.out.println("\nAll anagrams of " + word + " found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }
    }

}
