package org.kotopka;

import java.nio.file.Paths;
import java.util.*;

/**
 * {@code Anagram} - Finds and prints a configurable list of anagrams of input word.
 */
public class Anagram {

    public static class Builder {
        private final String dictFile;
        private int minWordLength;
        private int maxWordLength;
        private int maxResults;

        private String startFrom;
        private String includeWord;
        private String suffix;

        private boolean excludeDuplicates;
        private Set<String> excludeWordsSet;

        public Builder(String dictFile) {
            Objects.requireNonNull(dictFile);

            this.dictFile = dictFile;
            this.minWordLength = 1;
            this.maxWordLength = Integer.MAX_VALUE;
            this.maxResults = Integer.MAX_VALUE;
            this.startFrom = "";
            this.includeWord = "";
            this.suffix = "";
            this.excludeWordsSet = new HashSet<>();
        }

        public Builder setMinWordLength(int minWordLength) {
            if (minWordLength < 0)
                throw new IllegalArgumentException("Invalid min word length: " + minWordLength);

            this.minWordLength = minWordLength;

            return this;
        }

        public Builder setMaxWordLength(int maxWordLength) {
            if (maxWordLength < 0)
                throw new IllegalArgumentException("Invalid max word length: " + maxWordLength);

            this.maxWordLength = maxWordLength;

            return this;
        }

        public Builder setMaxResults(int maxResults) {
            if (maxResults < 0)
                throw new IllegalArgumentException("Invalid max results: " + maxResults);

            this.maxResults = maxResults;

            return this;
        }

        public Builder startFrom(String word) {
            Objects.requireNonNull(word);

            this.startFrom = word;

            return this;
        }

        public Builder includeWord(String word) {
            Objects.requireNonNull(word);

            this.includeWord = word;

            return this;
        }

        public Builder setSuffix(String suffix) {
            Objects.requireNonNull(suffix);

            this.suffix = suffix;

            return this;
        }

        public Builder excludeDuplicates(boolean shouldExclude) {
            this.excludeDuplicates = shouldExclude;

            return this;
        }

        public Builder excludeWordSet(Set<String> excludeWordsSet) {
            Objects.requireNonNull(excludeWordsSet);

            // do NOT want an immutable copy here because it gets updated during use
            this.excludeWordsSet = excludeWordsSet;

            return this;
        }

        public Anagram build() {
            return new Anagram(this);
        }

    }

    private final Dictionary dictionary;
    private int count;
    private int minWordLength;
    private int maxWordLength;
    private int maxResults;
    private String startFrom;
    private String includeWord;
    private String suffix;
    private boolean excludeDuplicates;
    private final Set<String> excludeWordsSet;

    private Anagram(Builder builder) {
        this.minWordLength = builder.minWordLength;
        this.maxWordLength = builder.maxWordLength;
        this.maxResults = builder.maxResults;
        this.startFrom = builder.startFrom;
        this.includeWord = builder.includeWord;
        this.suffix = builder.suffix;
        this.excludeDuplicates = builder.excludeDuplicates;
        this.excludeWordsSet = builder.excludeWordsSet;

        this.dictionary = new Dictionary.Builder(Paths.get(builder.dictFile))
                .setMinWordLength(minWordLength)
                .setMaxWordLength(maxWordLength)
                .excludeWordsFromSet(excludeWordsSet)
                .build();
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

    public void startFrom(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        startFrom = word;
    }

    public void includeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        includeWord = word;
    }

    public void excludeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        excludeWordsSet.add(word);
    }

    public void setSuffix(String suffix) {
        Objects.requireNonNull(suffix, "Suffix cannot be null");

        this.suffix = suffix;
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
        LinkedList<String> anagram = new LinkedList<>();
        List<String> anagramList   = new ArrayList<>();

        count = 0;

        buildAnagramListRecursively(word, startFrom, anagram, anagramList);
        Collections.sort(anagramList);

        return anagramList;
    }

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

            buildAnagramListRecursively(diff, "", anagram, anagramList);

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

    private boolean isAnagramValid(String diff, LinkedList<String> stack) {
        boolean wordWithSuffixFound;

        if (suffix.isBlank())
            wordWithSuffixFound = true;
        else
            wordWithSuffixFound = isWordWithSuffixFound(stack);

        return (diff.isBlank() && wordWithSuffixFound && (includeWord.isBlank() || stack.contains(includeWord)));
    }

    private boolean isWordWithSuffixFound(LinkedList<String> stack) {
        for (String s : stack)
            if (s.endsWith(suffix))
                return true;

        return false;
    }

    private void addAnagramToList(LinkedList<String> anagram, List<String> anagramList) {
        anagramList.add(String.join(" ", anagram));
        count++;
    }

}
