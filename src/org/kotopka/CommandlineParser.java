package org.kotopka;

import java.util.*;

public class CommandlineParser {

    private final static String NEWLINE = System.lineSeparator();

    private final String clientProgramName;
    private final String[] args;
    private final HashSet<String> validCommands;
    private int argIndex;

    // dictionary stuff
    private String dictFile;
    private int minWordLength;
    private int maxWordLength;
    private String excludeFromDictionaryFilename;

    // anagram stuff
    private int maxResults;
    private int maxWordsInAnagram;
    private boolean excludeDuplicates;
    private boolean restrictPermutations;
    private String startFrom;
    private String includeWord;
    private String excludeWord;
    private String includeWordWithSuffix;
    private final List<String> phrase;

    public CommandlineParser(String clientProgramName, String[] args) {
        this.clientProgramName = clientProgramName;
        this.args = args.clone();
        this.validCommands = new HashSet<>();

        for (Option o : Option.values())
            validCommands.add(o.value());

        // dictionary stuff
        this.dictFile = "";
        this.maxWordLength = Integer.MAX_VALUE;
        this.excludeFromDictionaryFilename = "";

        // anagram stuff
        this.maxResults = Integer.MAX_VALUE;
        this.maxWordsInAnagram = Integer.MAX_VALUE;
        this.startFrom = "";
        this.includeWord = "";
        this.excludeWord = "";
        this.includeWordWithSuffix = "";
        this.phrase = new ArrayList<>();
    }

    public void parseArgs() {
        if (args.length == 0)
            printOptions();

        while (argIndex < args.length) {
            String option = args[argIndex];

            validateOption(option);

            switch (Option.get(option)) {
                case DICT_FILE -> setDictFileName();
                case MIN_WORD_LENGTH -> setMinWordLength();
                case MAX_WORD_LENGTH -> setMaxWordLength();
                case EXCLUDE_FROM_DICT_FILE -> setExcludeFromDictionaryFilename();
                case MAX_RESULTS -> setMaxResults();
                case MAX_WORDS -> setMaxWordsInAnagram();
                case EXCLUDE_DUPLICATES -> setExcludeDuplicates();
                case RESTRICT_PERMUTATIONS -> setRestrictPermutations();
                case START_FROM -> setStartFrom();
                case INCLUDE_WORD -> setIncludeWord();
                case EXCLUDE_WORD -> setExcludeWord();
                case INCLUDE_WORD_WITH_SUFFIX -> setIncludeWordWithSuffix();
                case HELP -> printOptions();
                default -> setPhrase();
            }

            argIndex++;
        }
    }

    private void validateOption(String option) {
        if (option.startsWith("-") && !validCommands.contains(option)) {
            System.out.println("Unknown option: " + option);
            printOptions();
        }
    }

    private int getIntValueFromArg() {
        int valueFromArg = 0;
        String arg = args[++argIndex];

        try {
            valueFromArg = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number: " + arg);
            printOptions();
        }

        return valueFromArg;
    }

    private void setDictFileName() {
        dictFile = args[++argIndex];
    }

    private void setMinWordLength() {
        minWordLength = getIntValueFromArg();
    }

    private void setMaxWordLength() {
        maxWordLength = getIntValueFromArg();
    }

    private void setExcludeFromDictionaryFilename() {
        excludeFromDictionaryFilename = args[++argIndex];
    }

    private void setMaxResults() { maxResults = getIntValueFromArg(); }

    private void setMaxWordsInAnagram() {
        maxWordsInAnagram = getIntValueFromArg();
    }

    private void setExcludeDuplicates() {
        excludeDuplicates = true;
    }

    private void setRestrictPermutations() {
        restrictPermutations = true;
    }

    private void setStartFrom() {
        startFrom = args[++argIndex];
    }

    private void setIncludeWord() {
        includeWord = args[++argIndex];
    }

    private void setExcludeWord() {
        excludeWord = args[++argIndex];
    }

    private void setIncludeWordWithSuffix() {
        includeWordWithSuffix = args[++argIndex];
    }

    private void setPhrase() {
        while (argIndex < args.length)
            phrase.add(args[argIndex++]);
    }

    public String getOptions() {
        return  "usage: java " + clientProgramName + " <options> input string" + NEWLINE + NEWLINE +
                "These options affect dictionary creation:" + NEWLINE +

                String.format("\t%-5s\t%s%s", Option.DICT_FILE.value(), "Dictionary filename", NEWLINE) +
                String.format("\t%-5s\t%s%s", Option.MIN_WORD_LENGTH.value(), "Minimum word length", NEWLINE) +
                String.format("\t%-5s\t%s%s", Option.MAX_WORD_LENGTH.value(), "Maximum word length", NEWLINE) +
                String.format("\t%-5s\t%s%s", Option.EXCLUDE_FROM_DICT_FILE.value(), "Filename of words to exclude from dictionary", NEWLINE) + NEWLINE +

                "These options affect anagram creation:" + NEWLINE +

                String.format("\t%-5s\t%s%S", Option.MAX_RESULTS.value(), "Maximum results", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.MAX_WORDS.value(), "Maximum words in anagram", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.EXCLUDE_DUPLICATES.value(), "Exclude duplicates", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.RESTRICT_PERMUTATIONS.value(), "Restrict permutations", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.START_FROM.value(), "Start from word or letter", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.INCLUDE_WORD.value(), "Include word in anagram", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.INCLUDE_WORD_WITH_SUFFIX.value(), "Include word with suffix in anagram", NEWLINE) +
                String.format("\t%-5s\t%s%S", Option.HELP.value(), "This help message", NEWLINE);
    }

    public void printOptions() {
        System.out.println(getOptions());

        System.exit(-1);
    }

    public String getDictFile() { return dictFile; }

    public int getMinWordLength() { return minWordLength; }

    public int getMaxWordLength() { return maxWordLength; }

    public int getMaxResults() { return maxResults; }

    public String getExcludeFromDictionaryFilename() { return excludeFromDictionaryFilename; }

    public int getMaxWordsInAnagram() { return maxWordsInAnagram; }

    public boolean shouldExcludeDuplicates() { return excludeDuplicates; }

    public boolean shouldRestrictPermutations() { return restrictPermutations; }

    public String getStartFrom() { return  startFrom; }

    public String getIncludeWord() { return includeWord; }

    public String getExcludeWord() { return excludeWord; }

    public String getPhrase() { return String.join(" ", phrase); }

    public String getSuffix() { return includeWordWithSuffix; }

    public void printState() { System.out.println(this); }

    @Override
    public String toString() {
        return  "--------------------------------" + NEWLINE +
                "CommandlineParser internal state" + NEWLINE +
                "--------------------------------" + NEWLINE +
                "Dictionary filename: " + getDictFile() + NEWLINE +
                "Min: " + getMinWordLength() + NEWLINE +
                "Max: " + getMaxWordLength() + NEWLINE +
                "Max results: " + getMaxResults() + NEWLINE +
                "Exclude from dictionary filename: " + getExcludeFromDictionaryFilename() + NEWLINE +
                "Max words in anagram: " + getMaxWordsInAnagram() + NEWLINE +
                "Exclude Duplicates: " + shouldExcludeDuplicates() + NEWLINE +
                "Restrict Permutations: " + shouldRestrictPermutations() + NEWLINE +
                "Start from: " + getStartFrom() + NEWLINE +
                "Include word: " + getIncludeWord() + NEWLINE +
                "Exclude word: " + getExcludeWord() + NEWLINE +
                "Include word with suffix: " + getSuffix() + NEWLINE +
                "Phrase: " + String.join(" ", getPhrase()) + NEWLINE +
                "--------------------------------" + NEWLINE;
    }

    public static void main(String[] args) {
        CommandlineParser clp = new CommandlineParser("CommandlineParser", args);

        clp.parseArgs();
        clp.printState();
    }
}
