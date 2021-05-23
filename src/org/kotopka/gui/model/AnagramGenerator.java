package org.kotopka.gui.model;

import org.kotopka.anagram.Anagram;
import org.kotopka.anagram.AnagramFactory;
import org.kotopka.dictionary.Dictionary;
import org.kotopka.dictionary.DictionaryFactory;
import org.kotopka.parser.Parser;

public class AnagramGenerator {

    private final Anagram anagram;

    public AnagramGenerator(Parser parser) {
        Dictionary dictionary = DictionaryFactory.getDictionary(parser);
        this.anagram = AnagramFactory.getAnagram(parser, dictionary);
    }

    public Anagram getAnagram() {
        return anagram;
    }
}
