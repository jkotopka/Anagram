package org.kotopka;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Dictionary {

    private final Map<String, List<String>> dictionary;

    public Dictionary(String dictFile) {
        Objects.requireNonNull(dictFile, "Constructor argument cannot be null");

        this.dictionary = new HashMap<>();

        try (Stream<String> dfs = Files.lines(Paths.get(dictFile))) {
            dfs.forEach(this::addWordToDictionary);
        } catch (IOException e) {
            System.err.println("Error building dictionary file!");
            e.printStackTrace();
        }
    }

    private void addWordToDictionary(String word) {
        String sortedWord = Word.sortLetters(word);

        if (!dictionary.containsKey(sortedWord))
            dictionary.put(sortedWord, new ArrayList<>());

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
