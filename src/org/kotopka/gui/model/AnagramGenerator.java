package org.kotopka.gui.model;

import org.kotopka.anagram.Anagram;
import org.kotopka.dictionary.Dictionary;

public class AnagramGenerator {

    private final Dictionary dictionary;
    private final Anagram anagram;

    public AnagramGenerator() {
        this.dictionary = new Dictionary.Builder("dictionary-large.txt").build();
        this.anagram = new Anagram(dictionary);
    }

    public Anagram getAnagram() {
        return anagram;
    }
}
