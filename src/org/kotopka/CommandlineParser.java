package org.kotopka;

import java.util.*;

public class CommandlineParser {

    private final static String NEWLINE = System.lineSeparator();

    private final String clientProgramName;
    private final String[] args;
    private final List<String> phrase;
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

    public CommandlineParser(String clientProgramName, String[] args) {
        this.clientProgramName = clientProgramName;
        this.args = args.clone();
        this.phrase = new ArrayList<>();
        this.validCommands = new HashSet<>();

        // dictionary options
        validCommands.add("-d");
        validCommands.add("-minwl");
        validCommands.add("-maxwl");
        validCommands.add("-ef");

        // anagram options
        validCommands.add("-mr");
        validCommands.add("-mw");
        validCommands.add("-ed");
        validCommands.add("-rp");
        validCommands.add("-sf");
        validCommands.add("-iw");
        validCommands.add("-ew");
        validCommands.add("-iws");

        // help
        validCommands.add("-h");

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
    }

    public void parseArgs() {
        if (args.length == 0)
            printOptions();

        while (argIndex < args.length) {
            String option = args[argIndex];

            validateOption(option);

            switch (option) {
                case "-d" -> setDictFileName();
                case "-minwl" -> setMinWordLength();
                case "-maxwl" -> setMaxWordLength();
                case "-ef" -> setExcludeFromDictionaryFilename();
                case "-mr" -> setMaxResults();
                case "-mw" -> setMaxWordsInAnagram();
                case "-ed" -> setExcludeDuplicates();
                case "-rp" -> setRestrictPermutations();
                case "-sf" -> setStartFrom();
                case "-iw" -> setIncludeWord();
                case "-ew" -> setExcludeWord();
                case "-iws" -> setIncludeWordWithSuffix();
                case "-h" -> printOptions();
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

    private void setMaxResults() {
        maxResults = getIntValueFromArg();
    }

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

    public void printOptions() {
        System.out.println("usage: java " + clientProgramName + " <options> input string");
        System.out.println(NEWLINE + "These options affect dictionary creation:");

        System.out.printf("\t%-5s\t%s%s", "-d", "Dictionary filename", NEWLINE);
        System.out.printf("\t%-5s\t%s%s", "-minwl", "Minimum word length", NEWLINE);
        System.out.printf("\t%-5s\t%s%s", "-maxwl", "Maximum word length", NEWLINE);
        System.out.printf("\t%-5s\t%s%s", "-ef", "Filename of words to exclude from dictionary", NEWLINE);

        System.out.println(NEWLINE + "These options affect anagram creation:");

        System.out.printf("\t%-5s\t%s%S", "-mr", "Maximum results", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-mw", "Maximum words in anagram", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-ed", "Exclude duplicates", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-rp", "Restrict permutations", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-sf", "Start from word or letter", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-iw", "Include word in anagram", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-iws", "Include word with suffix in anagram", NEWLINE);
        System.out.printf("\t%-5s\t%s%S", "-h", "This help message", NEWLINE);

        System.out.println();

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

    public void printState() {
        String state =
            "--------------------------------" + NEWLINE +
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

        System.out.println(state);
    }

    public static void main(String[] args) {
        CommandlineParser clp = new CommandlineParser("CommandlineParser", args);

        clp.parseArgs();
        clp.printState();
    }
}
