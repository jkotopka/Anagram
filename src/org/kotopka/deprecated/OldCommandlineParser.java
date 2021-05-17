package org.kotopka.deprecated;

import java.util.*;

public class OldCommandlineParser {

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
    private int maxTimeout;
    private boolean excludeDuplicates;
    private boolean restrictPermutations;
    private String startFrom;
    private String includeWord;
    private String excludeWord;
    private String includeWordWithSuffix;
    private final List<String> phrase;

    public OldCommandlineParser(String clientProgramName, String[] args) {
        this.clientProgramName = clientProgramName;
        this.args = args.clone();
        this.validCommands = new HashSet<>();

        for (OptionSwitches o : OptionSwitches.values())
            validCommands.add(o.toString());

        // dictionary stuff
        this.dictFile = "";
        this.maxWordLength = Integer.MAX_VALUE;
        this.excludeFromDictionaryFilename = "";

        // anagram stuff
        this.maxResults = Integer.MAX_VALUE;
        this.maxWordsInAnagram = Integer.MAX_VALUE;
        this.maxTimeout = Integer.MAX_VALUE;
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

            switch (OptionSwitches.get(option)) {
                case DICT_FILE -> setDictFileName();
                case MIN_WORD_LENGTH -> setMinWordLength();
                case MAX_WORD_LENGTH -> setMaxWordLength();
                case EXCLUDE_FROM_DICT_FILE -> setExcludeFromDictionaryFilename();
                case MAX_RESULTS -> setMaxResults();
                case MAX_WORDS -> setMaxWordsInAnagram();
                case TIMEOUT -> setMaxTimeout();
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
            System.err.println("Unknown option: " + option);
            printOptions();
        }
    }

    private int getIntValueFromArg() {
        int valueFromArg = 0;
        String arg = args[++argIndex];

        try {
            valueFromArg = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid number: " + arg);
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

    private void setMaxTimeout() { maxTimeout = getIntValueFromArg(); }

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

                String.format("\t%-5s\t%s%s", OptionSwitches.DICT_FILE, "Dictionary filename", NEWLINE) +
                String.format("\t%-5s\t%s%s", OptionSwitches.MIN_WORD_LENGTH, "Minimum word length", NEWLINE) +
                String.format("\t%-5s\t%s%s", OptionSwitches.MAX_WORD_LENGTH, "Maximum word length", NEWLINE) +
                String.format("\t%-5s\t%s%s", OptionSwitches.EXCLUDE_FROM_DICT_FILE, "Filename of words to exclude from dictionary", NEWLINE) + NEWLINE +

                "These options affect anagram creation:" + NEWLINE +

                String.format("\t%-5s\t%s%S", OptionSwitches.MAX_RESULTS, "Maximum results", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.MAX_WORDS, "Maximum words in anagram", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.TIMEOUT, "Timeout in seconds", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.EXCLUDE_DUPLICATES, "Exclude duplicates", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.RESTRICT_PERMUTATIONS, "Restrict permutations", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.START_FROM, "Start from word or letter", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.INCLUDE_WORD, "Include word in anagram", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.INCLUDE_WORD_WITH_SUFFIX, "Include word with suffix in anagram", NEWLINE) +
                String.format("\t%-5s\t%s%S", OptionSwitches.HELP, "This help message", NEWLINE);
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

    public int getMaxTimeout() { return maxTimeout; }

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
                "OldCommandlineParser internal state" + NEWLINE +
                "--------------------------------" + NEWLINE +
                "Dictionary filename: " + getDictFile() + NEWLINE +
                "Min: " + getMinWordLength() + NEWLINE +
                "Max: " + getMaxWordLength() + NEWLINE +
                "Max results: " + getMaxResults() + NEWLINE +
                "Max timeout: " + getMaxTimeout() + NEWLINE +
                "Exclude from dictionary filename: " + getExcludeFromDictionaryFilename() + NEWLINE +
                "Max words in anagram: " + getMaxWordsInAnagram() + NEWLINE +
                "Exclude Duplicates: " + shouldExcludeDuplicates() + NEWLINE +
                "Restrict Permutations: " + shouldRestrictPermutations() + NEWLINE +
                "Start from: " + getStartFrom() + NEWLINE +
                "Include word: " + getIncludeWord() + NEWLINE +
                "Exclude word: " + getExcludeWord() + NEWLINE +
                "Include word with suffix: " + getSuffix() + NEWLINE +
                "Phrase: " + getPhrase() + NEWLINE +
                "--------------------------------" + NEWLINE;
    }

    public static void main(String[] args) {
        OldCommandlineParser clp = new OldCommandlineParser("OldCommandlineParser", args);

        clp.parseArgs();
        clp.printState();
    }
}
