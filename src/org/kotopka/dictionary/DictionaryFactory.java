package org.kotopka.dictionary;

import org.kotopka.parser.Parser;
import org.kotopka.parser.Switch;

public class DictionaryFactory {

    public static Dictionary getDictionary(Parser parser) {
        return new Dictionary.Builder("dictionary-large.txt")
            .setDictionaryFile(parser.getOption(Switch.DICT_FILE).getString())
            .setMaxWordLength(parser.getOption(Switch.MAX_WORD_LENGTH).getInt())
            .setMinWordLength(parser.getOption(Switch.MIN_WORD_LENGTH).getInt())
            .excludeWordsFromFile(parser.getOption(Switch.EXCLUDE_FROM_DICT_FILE).getString())
            .build();
    }

}
