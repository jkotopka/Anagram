package org.kotopka;

import org.kotopka.CommandlineParser.*;

import java.util.List;
import java.util.Set;

public class NewTestClient {

    private static final String NEWLINE = System.lineSeparator();

    private static void findAndPrintSubWords(Anagram anagram, String word) {
        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.println("No sub-words of \"" + word + "\" found");
        } else {
            System.out.println("Valid sub-words of \"" + word + "\" found: " + allSubWords.size());
            allSubWords.forEach(System.out::println);
        }
    }

    private static void findAndPrintAnagrams(Anagram anagram, String word) {
        long startTime = System.currentTimeMillis();
        List<String> allAnagrams = anagram.findMultipleWordAnagramsOf(word);
        long endTime = System.currentTimeMillis();

        if (allAnagrams.isEmpty()) {
            System.out.println(NEWLINE + "No anagrams of \"" + word + "\" found");
        } else {
            System.out.println(NEWLINE + "Anagrams of \"" + word + "\" found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }

        System.out.println("Elapsed time: " + (double)(endTime - startTime) / 1000 + " seconds");
    }

    public static void main(String[] args) {
        Parser parser = new CommandlineParser(args, Switch.COLLECT_PHRASE);
        parser.addOptions(
                new DictFile(),
                new MinWordLen(),
                new MaxWordLen(),
                new ExcludeFile(),
                new MaxResults(),
                new Timeout(),
                new MaxWords(),
                new StartFrom(),
                new IncludeWord(),
                new ExcludeWord(),
                new IncludeWordWithSuffix(),
                new RestrictPermutations(),
                new ExcludeDuplicates(),
                new HelpMessage(),
                new ExtendedHelpMessage(),
                new CollectPhrase()
        );

        parser.parseArgs();

        Dictionary dictionary = new Dictionary.Builder("dictionary-large.txt")
                .setDictionaryFile(parser.getOption(Switch.DICT_FILE).getString())
                .setMaxWordLength(parser.getOption(Switch.MAX_WORD_LENGTH).getInt())
                .setMinWordLength(parser.getOption(Switch.MIN_WORD_LENGTH).getInt())
                .excludeWordsFromFile(parser.getOption(Switch.EXCLUDE_FROM_DICT_FILE).getString())
                .build();

        Anagram anagram = new Anagram(dictionary)
                .setMaxResults(parser.getOption(Switch.MAX_RESULTS).getInt())
                .setMaxWordsInAnagram(parser.getOption(Switch.MAX_WORDS).getInt())
                .setMaxTimeoutInSeconds(parser.getOption(Switch.TIMEOUT).getInt())
                .shouldExcludeDuplicates(parser.getOption(Switch.EXCLUDE_DUPLICATES).getBool())
                .shouldRestrictPermutations(parser.getOption(Switch.RESTRICT_PERMUTATIONS).getBool())
                .startFrom(parser.getOption(Switch.START_FROM).getString())
                .includeWord(parser.getOption(Switch.INCLUDE_WORD).getString())
                .excludeWord(parser.getOption(Switch.EXCLUDE_WORD).getString())
                .includeWordWithSuffix(parser.getOption(Switch.INCLUDE_WORD_WITH_SUFFIX).getString());

        parser.printState();

        String word = parser.getOption(Switch.COLLECT_PHRASE).getString();

        findAndPrintSubWords(anagram, word);
        findAndPrintAnagrams(anagram, word);
    }

}
