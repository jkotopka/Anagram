package org.kotopka.anagram;

import org.kotopka.parser.Parser;
import org.kotopka.parser.Switch;
import org.kotopka.dictionary.Dictionary;

public final class AnagramFactory {

    private AnagramFactory() {} // enforce non-instantiability

    // reading those lines is pretty tedious hence why they're consigned to this factory method
    public static Anagram getAnagram(Parser parser, Dictionary dictionary) {
        return new Anagram(dictionary)
                .setMaxResults(parser.getOption(Switch.MAX_RESULTS).getInt())
                .setMaxWordsInAnagram(parser.getOption(Switch.MAX_WORDS).getInt())
                .setMaxTimeoutInSeconds(parser.getOption(Switch.TIMEOUT).getInt())
                .setShouldExcludeDuplicates(parser.getOption(Switch.EXCLUDE_DUPLICATES).getBool())
                .setShouldRestrictPermutations(parser.getOption(Switch.RESTRICT_PERMUTATIONS).getBool())
                .setStartFrom(parser.getOption(Switch.START_FROM).getString())
                .setIncludeWord(parser.getOption(Switch.INCLUDE_WORD).getString())
                .setExcludeWord(parser.getOption(Switch.EXCLUDE_WORD).getString())
                .setIncludeWordWithSuffix(parser.getOption(Switch.INCLUDE_WORD_WITH_SUFFIX).getString());
    }
}
