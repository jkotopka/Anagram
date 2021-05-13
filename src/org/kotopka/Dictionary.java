package org.kotopka;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Dictionary {

    public static class Builder {
        private int minWordLength;
        private int maxWordLength;
        private Path dictFile;
        private final Set<String> excludeWordsSet;

        public Builder(Path dictFile) {
            Objects.requireNonNull(dictFile);

            this.minWordLength = 1;
            this.maxWordLength = Integer.MAX_VALUE;
            this.dictFile = dictFile;
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
                throw new IllegalArgumentException("Invalid max word length: " + minWordLength);

            this.maxWordLength = maxWordLength;

            return this;
        }

        public Builder setDictionaryFile(Path filename) {
            Objects.requireNonNull(filename);

            this.dictFile = filename;

            return this;
        }

        public Builder excludeWord(String word) {
            // XXX: might not really matter if null is inserted into a HashSet
            Objects.requireNonNull(word);

            excludeWordsSet.add(word.toLowerCase());

            return this;
        }

        public Builder excludeWordsFromSet(Set<String> words) {
            Objects.requireNonNull(words);

            for (String w : words)
                if (w != null) excludeWordsSet.add(w.toLowerCase());

            return this;
        }

        public Dictionary build() {
            if (maxWordLength < minWordLength)
                throw new IllegalArgumentException("Max word length cannot be smaller than min word length");
            else
                return new Dictionary(this);
        }

    }

    private final int minWordLength;
    private final int maxWordLength;
    private final Set<String> excludeWordsSet;
    private final Map<String, List<String>> dictionary;

    private Dictionary(Builder builder) {
        this.minWordLength = builder.minWordLength;
        this.maxWordLength = builder.maxWordLength;
        this.excludeWordsSet = builder.excludeWordsSet;
        this.dictionary = new HashMap<>();

        try (Stream<String> dfs = Files.lines(builder.dictFile)) {
            dfs.forEach(this::addWordToDictionary);
        } catch (IOException e) {
            System.err.println("Error building dictionary file!");
            e.printStackTrace();
        }
    }

    private void addWordToDictionary(String word) {
        String sortedWord = Word.sortLetters(word);

        if (!dictionary.containsKey(sortedWord) && !excludeWordsSet.contains(word))
            dictionary.put(sortedWord, new ArrayList<>());

        int wordLen = sortedWord.length();

        if (wordLen >= minWordLength && wordLen <= maxWordLength && !excludeWordsSet.contains(word))
            dictionary.get(sortedWord).add(word);
    }

    public boolean containsWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        return dictionary.containsKey(word);
    }

    public List<String> getListOf(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        return List.copyOf(dictionary.get(word));
    }

    public List<String> getListOrEmpty(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        if (dictionary.containsKey(word))
            return List.copyOf(dictionary.get(word));
        else
            return Collections.emptyList();
    }

}
