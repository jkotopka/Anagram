package org.kotopka;

import java.util.*;

/**
 * {@code Anagram} - Finds and prints a configurable list of anagrams of input word.
 */
public class Anagram {

    private final Dictionary dictionary;
    private int count;
    private int maxResults;
    private int maxWordsInAnagram;
    private String startFrom;
    private String includeWord;
    private String suffix;
    private boolean excludeDuplicates;
    private boolean restrictPermutations;
    private final Set<String> excludeWordsSet;

    public Anagram(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.maxResults = Integer.MAX_VALUE;
        this.maxWordsInAnagram = Integer.MAX_VALUE;
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

    public Anagram setMaxWordsInAnagram(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max must be positive");

        maxWordsInAnagram = max;

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

    // XXX: not very happy with this method, sorta code-smelly and probably very inefficient due to building a new
    // word set every recursion
    private void buildAnagramListRecursively(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        for (String s : findAllValidSubWordsAsSet(word).tailSet(startWord)) {
            if (excludeDuplicates) {
                if (excludeWordsSet.contains(s)) continue;

                excludeWordsSet.add(s.toLowerCase());
            }

            anagram.push(s);

            String diff = Word.subtract(word, s);

            if (isAnagramValid(diff, anagram))
                addAnagramToList(anagram, anagramList);

            if (count == maxResults) return;

            String nextStart = (restrictPermutations) ? s : "";

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

        return (diff.isBlank() && wordWithSuffixFound && anagram.size() <= maxWordsInAnagram && (includeWord.isBlank() || anagram.contains(includeWord)));
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

    // TODO: TESTING----------------------------------------------------------------------------------------------
    public Set<String> getValidSubStringsOf(String word) {
        Set<String> subStrings = new TreeSet<>();

        for (String s : generateSubStrings(word))
            if (dictionary.containsWord(s))
                subStrings.add(s);

        return subStrings;
    }

    public List<List<String>> findAnagramGroups(String word) {
        LinkedList<String> anagram = new LinkedList<>();
        List<List<String>> anagramList = new ArrayList<>();
        List<String> wordList = new ArrayList<>(getValidSubStringsOf(word));

        wordList.sort(Comparator.comparing(String::length).reversed());

        anagramGroupsRecursive(Word.sortLetters(word), wordList,0, anagram, anagramList);

        return anagramList;
    }

    private void anagramGroupsRecursive(String word, List<String> wordList,  int index, LinkedList<String> anagram, List<List<String>> anagramList) {
        for (int i = index; i < wordList.size(); i++) {
            String current = wordList.get(i);

            if (word.length() < current.length()) continue;

            String diff = Word.subtract(word, current);

            if(word.equals(diff)) continue;

            anagram.push(current);

            if (diff.isBlank())
                anagramList.add(List.copyOf(anagram));

            int nextStart = 0;

            if (restrictPermutations) nextStart = i + 1;

            anagramGroupsRecursive(diff, wordList, nextStart, anagram, anagramList);

            anagram.pop();
        }
    }

    public static boolean validateAnagram(String word, String anagram) {
        return Word.sortLetters(word).equals(Word.sortLetters(anagram).trim());
    }

}
