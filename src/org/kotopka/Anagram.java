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
    private final Set<String> wordsToExclude;
    private final Map<String, TreeSet<String>> cachedSubWords;

    public Anagram(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.maxResults = Integer.MAX_VALUE;
        this.maxWordsInAnagram = Integer.MAX_VALUE;
        this.startFrom = "";
        this.includeWord = "";
        this.suffix = "";
        this.wordsToExclude = new HashSet<>();
        this.cachedSubWords = new HashMap<>();
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

        wordsToExclude.add(word);

        return this;
    }

    public Anagram includeWordWithSuffix(String suffix) {
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

    public TreeSet<String> findAllValidSubWordsAsSet(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        TreeSet<String> validSubWords = new TreeSet<>();

        for (String subString : Word.generateSubStrings(word))
            validSubWords.addAll(dictionary.getListOrEmpty(subString));

        return validSubWords;
    }

    public List<String> findSingleWordAnagramsOf(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = Word.sortLetters(word);

        return dictionary.getListOrEmpty(sortedWord);
    }

    public List<String> findMultipleWordAnagramsOf(String word) {
        LinkedList<String> anagram = new LinkedList<>();
        List<String> anagramList   = new ArrayList<>();

        count = 0;

        buildAnagramList(word, startFrom, anagram, anagramList);
        Collections.sort(anagramList);

        return anagramList;
    }

    // TODO: refactored a bit with extracted methods, not sure if cleaner,
    //  mostly not sure if having the "recursive" call in another method is bad practice or not
    private void buildAnagramList(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        if (count == maxResults) return;

        validateSubWordCache(word);
        buildAnagramsFromSubWords(word, startWord, anagram, anagramList);
    }

    private void validateSubWordCache(String word) {
        if (!cachedSubWords.containsKey(word))
            cachedSubWords.put(word, findAllValidSubWordsAsSet(word));
    }

    private SortedSet<String> getSubWordCacheOf(String word, String startWord) {
        return cachedSubWords.get(word).tailSet(startWord);
    }

    private void buildAnagramsFromSubWords(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        for (String subWord : getSubWordCacheOf(word, startWord)) {
            if (excludeDuplicates) {
                if (wordsToExclude.contains(subWord)) continue;

                wordsToExclude.add(subWord.toLowerCase());
            }

            buildAnagram(word, anagram, anagramList, subWord);

            if (excludeDuplicates) wordsToExclude.remove(subWord);
        }
    }

    private void buildAnagram(String word, LinkedList<String> anagram, List<String> anagramList, String subWord) {
        String diff = Word.subtract(word, subWord);

        anagram.push(subWord);

        if (isAnagramValid(diff, anagram))
            addAnagramToList(anagram, anagramList);

        // XXX: this is where the mind-bender starts...
        startIndirectRecursion(anagram, anagramList, subWord, diff);
        anagram.pop();
    }

    private void startIndirectRecursion(LinkedList<String> anagram, List<String> anagramList, String subWord, String diff) {
        String nextStart = (restrictPermutations) ? subWord : "";

        buildAnagramList(diff, nextStart, anagram, anagramList);
    }

    private boolean isAnagramValid(String diff, LinkedList<String> anagram) {
        boolean wordWithSuffixFound;

        if (suffix.isBlank())
            wordWithSuffixFound = true;
        else
            wordWithSuffixFound = isWordWithSuffixFound(anagram);

        return (diff.isBlank() && wordWithSuffixFound && anagram.size() <= maxWordsInAnagram &&
                (includeWord.isBlank() || anagram.contains(includeWord)));
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

    // TODO: TESTING---------------------------------------------------------------------------------------------------
    //  these methods have different behavior than the normal anagram methods above and don't really work as one
    //  might expect, nevertheless, I intend to explore these ideas more in the future hence leaving them here for now

    // TODO: have a Set and List version of this method so that e.g. repeated words in the input could be considered
    public Set<String> getValidSubstringGroupsOf(String word) {
        Set<String> subStrings = new TreeSet<>();

        for (String s : Word.generateSubStrings(word))
            if (dictionary.containsWord(s))
                subStrings.add(s);

        return subStrings;
    }

    public List<List<String>> findAnagramGroupsOf(String word) {
        LinkedList<String> anagram = new LinkedList<>();
        List<List<String>> anagramList = new ArrayList<>();
        List<String> wordList = new ArrayList<>(getValidSubstringGroupsOf(word));

        wordList.sort(Comparator.comparing(String::length).reversed());

        // TODO: not sure about this, needs testing, only works if the word (or its anagrams)
        //  is contained in the wordlist, doesn't really work as expected
        int startIndex = Math.max(wordList.indexOf(Word.sortLetters(startFrom)), 0);

        findAnagramGroupsRecursively(Word.sortLetters(word), wordList, startIndex, anagram, anagramList);

        return anagramList;
    }

    private void findAnagramGroupsRecursively(String word, List<String> wordList, int index,
                                              LinkedList<String> anagram, List<List<String>> anagramList) {
        for (int i = index; i < wordList.size(); i++) {
            String current = wordList.get(i);

            // XXX: might change the Word class to just return the word if there's a length problem
            // then this would be unnecessary...
            if (word.length() < current.length()) continue;

            String diff = Word.subtract(word, current);

            if(word.equals(diff)) continue;

            anagram.push(current);

            if (diff.isBlank())
                anagramList.add(List.copyOf(anagram));

            int nextStart = (restrictPermutations) ? i + 1 : 0;

            findAnagramGroupsRecursively(diff, wordList, nextStart, anagram, anagramList);

            anagram.pop();
        }
    }

}
