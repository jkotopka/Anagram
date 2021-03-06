package org.kotopka.dictionary;

import org.kotopka.word.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * {@code Dictionary} - Contains the words used in anagram generation. The {@code Dictionary} object is a key-value
 * dictionary that uses the letters of the word in alphabetically-sorted form as the key and each word that has the
 * same sorted form is stored in a collection which is the value.
 */
public class Dictionary {

    public static class Builder {
        private Path dictFile;
        private int minWordLength;
        private int maxWordLength;
        private final Set<String> excludeWordsSet;
        private String excludeWordsFilename;

        public Builder(String dictFile) {
            Objects.requireNonNull(dictFile);

            this.dictFile = Paths.get(dictFile);
            this.minWordLength = 1;
            this.maxWordLength = Integer.MAX_VALUE;
            this.excludeWordsSet = new HashSet<>();
            this.excludeWordsFilename = "";
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

        public Builder setDictionaryFile(String filename) {
            Objects.requireNonNull(filename);

            if (!filename.isBlank())
                this.dictFile = Paths.get(filename);

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

        public Builder excludeWordsFromFile(String excludeWordsFilename) {
            Objects.requireNonNull(excludeWordsFilename);

            this.excludeWordsFilename = excludeWordsFilename;

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

        String excludeWordsFilename = builder.excludeWordsFilename;

        if (!excludeWordsFilename.isBlank()) {
            try (Stream<String> ews = Files.lines(Paths.get(excludeWordsFilename))) {
                ews.forEach(excludeWordsSet::add);
            } catch (IOException e) {
                System.err.println("Error reading exclude words file!");
                System.exit(-1);
            }
        }

        try (Stream<String> dfs = Files.lines(builder.dictFile)) {
            dfs.forEach(this::addWordToDictionary);
        } catch (IOException e) {
            System.err.println("Error reading dictionary file!");
            System.exit(-1);
        }
    }

    private void addWordToDictionary(String word) {
        String sortedWord = Word.sortLetters(word);

        if (validateWordLength(sortedWord) && !dictionary.containsKey(sortedWord) && !excludeWordsSet.contains(word))
            dictionary.put(sortedWord, new ArrayList<>());

        if (validateWordLength(sortedWord) && !excludeWordsSet.contains(word))
            dictionary.get(sortedWord).add(word);
    }

    private boolean validateWordLength(String sortedWord) {
        int wordLen = sortedWord.length();

        return wordLen >= minWordLength && wordLen <= maxWordLength;
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
