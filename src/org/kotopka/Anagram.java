package org.kotopka;

import java.util.*;

/**
 * {@code Anagram} - Finds and prints a configurable list of anagrams of input word.
 */
public class Anagram {

    private final Dictionary dictionary;
    private int count;
    private int maxResults;
    private String startFrom;
    private String includeWord;
    private String suffix;
    private boolean excludeDuplicates;
    private boolean restrictPermutations;
    private final Set<String> excludeWordsSet;

    public Anagram(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.maxResults = Integer.MAX_VALUE;
        this.startFrom = "";
        this.includeWord = "";
        this.suffix = "";
        this.excludeWordsSet = new HashSet<>();
    }

    public Anagram setMaxResults(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max must be positive");

        maxResults = max;

        return this;
    }

    public Anagram startFrom(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        startFrom = word;

        return this;
    }

    public Anagram includeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        includeWord = word;

        return this;
    }

    public Anagram excludeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        excludeWordsSet.add(word);

        return this;
    }

    public Anagram setSuffix(String suffix) {
        Objects.requireNonNull(suffix, "Suffix cannot be null");

        this.suffix = suffix;

        return this;
    }

    public Anagram shouldExcludeDuplicates(boolean shouldExclude) {
        excludeDuplicates = shouldExclude;

        return this;
    }

    public Anagram shouldRestrictPermutations(boolean shouldRestrict) {
        restrictPermutations = shouldRestrict;

        return this;
    }

    public List<String> findSingleAnagramsOf(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = Word.sortLetters(word);

        return dictionary.getListOrEmpty(sortedWord);
    }

    public List<String> findAllAnagramsOf(String word) {
        LinkedList<String> anagram = new LinkedList<>();
        List<String> anagramList   = new ArrayList<>();

        count = 0;

        buildAnagramListRecursively(word, startFrom, anagram, anagramList);
        Collections.sort(anagramList);

        return anagramList;
    }

    // TODO: try to improve performance here
    private void buildAnagramListRecursively(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        for (String s : findAllValidSubWordsAsSet(word).tailSet(startWord)) {
            if (excludeDuplicates) {
                if (excludeWordsSet.contains(s)) continue;

                excludeWordsSet.add(s.toLowerCase());
            }

            // TODO: possibly extract this as a method
            anagram.push(s);

            String diff = Word.subtract(word, s);

            if (isAnagramValid(diff, anagram))
                addAnagramToList(anagram, anagramList);

            if (count == maxResults) return;

            // TODO: possible performance increase: don't search for words, search for sorted-strings
            //  and get the anagram list associated with the string, rebuild the anagrams later
            String nextStart = "";

            if (restrictPermutations) nextStart = s;

            buildAnagramListRecursively(diff, nextStart, anagram, anagramList);

            if (excludeDuplicates) excludeWordsSet.remove(s);

            anagram.pop();
        }
    }

    public TreeSet<String> findAllValidSubWordsAsSet(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        TreeSet<String> validSubWords = new TreeSet<>();

        for (String subString : generateSubStrings(word))
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

    private boolean isAnagramValid(String diff, LinkedList<String> anagram) {
        boolean wordWithSuffixFound;

        if (suffix.isBlank())
            wordWithSuffixFound = true;
        else
            wordWithSuffixFound = isWordWithSuffixFound(anagram);

        return (diff.isBlank() && wordWithSuffixFound && (includeWord.isBlank() || anagram.contains(includeWord)));
    }

    private boolean isWordWithSuffixFound(LinkedList<String> anagram) {
        for (String s : anagram)
            if (s.endsWith(suffix)) return true;

        return false;
    }

    private void addAnagramToList(LinkedList<String> anagram, List<String> anagramList) {
        anagramList.add(String.join(" ", anagram));
        count++;
    }

}
