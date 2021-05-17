package org.kotopka;

import java.util.*;

/**
 * {@code Anagram} - Finds and prints a configurable list of anagrams of input word.
 */
public final class Anagram {

    private final Dictionary dictionary;
    private int count;
    private int maxResults;
    private int maxWordsInAnagram;
    private int maxTimeout;
    private final long startTime;
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
        this.maxTimeout = Integer.MAX_VALUE;
        this.startTime = System.currentTimeMillis();
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

    public Anagram setMaxTimeoutInSeconds(int seconds) {
        if (seconds <= 0) throw new IllegalArgumentException("Seconds must be positive");

        int timeInMillis = seconds * 1000;
        maxTimeout = (timeInMillis > 0) ? timeInMillis : Integer.MAX_VALUE;

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

    private void buildAnagramList(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        validateSubWordCache(word);
        buildAnagramsFromSubWords(word, startWord, anagram, anagramList);
    }

    private void validateSubWordCache(String word) {
        if (!cachedSubWords.containsKey(word))
            cachedSubWords.put(word, findAllValidSubWordsAsSet(word));
    }

    private void buildAnagramsFromSubWords(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        for (String subWord : getSubWordCacheOf(word, startWord)) {
            if (wordsToExclude.contains(subWord))
                continue;

            if (excludeDuplicates)
                wordsToExclude.add(subWord.toLowerCase());

            buildAnagram(word, subWord, anagram, anagramList);

            if (excludeDuplicates) wordsToExclude.remove(subWord);
            if (count == maxResults) return;
        }
    }

    private SortedSet<String> getSubWordCacheOf(String word, String startWord) {
        return cachedSubWords.get(word).tailSet(startWord);
    }

    private void buildAnagram(String word, String subWord, LinkedList<String> anagram, List<String> anagramList) {
        // XXX: not exactly the best method to terminate the search after timeout but sorta works,
        // also not sure where to place this check for maximum effectiveness, I think here is a good place
        // because it will be caught in the indirect recursion
        if (System.currentTimeMillis() - startTime > maxTimeout) return;

        String diff = Word.subtract(word, subWord);

        anagram.push(subWord);

        if (isAnagramValid(diff, anagram))
            addAnagramToList(anagram, anagramList);
        else
            continueBuildingAnagramRecursively(diff, subWord, anagram, anagramList);

        anagram.pop();
    }

    private boolean isAnagramValid(String diff, List<String> anagram) {
        boolean wordWithSuffixFound;

        if (suffix.isBlank())
            wordWithSuffixFound = true;
        else
            wordWithSuffixFound = isWordWithSuffixFound(anagram);

        return (diff.isBlank() && wordWithSuffixFound && anagram.size() <= maxWordsInAnagram &&
                (includeWord.isBlank() || anagram.contains(includeWord)));
    }

    private boolean isWordWithSuffixFound(List<String> anagram) {
        for (String s : anagram)
            if (s.endsWith(suffix)) return true;

        return false;
    }

    private void addAnagramToList(List<String> anagram, List<String> anagramList) {
        anagramList.add(String.join(" ", anagram));
        count++;
    }

    private void continueBuildingAnagramRecursively(String diff, String subWord, LinkedList<String> anagram, List<String> anagramList) {
        String nextStart = (restrictPermutations) ? subWord : "";

        // XXX: here is where the indirect recursion starts
        buildAnagramList(diff, nextStart, anagram, anagramList);
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
            String diff = Word.subtract(word, current);

            if(word.equals(diff)) continue;

            int nextStart = (restrictPermutations) ? i + 1 : 0;

            anagram.push(current);

            if (diff.isBlank())
                anagramList.add(List.copyOf(anagram));
            else
                findAnagramGroupsRecursively(diff, wordList, nextStart, anagram, anagramList);

            anagram.pop();
        }
    }

}
