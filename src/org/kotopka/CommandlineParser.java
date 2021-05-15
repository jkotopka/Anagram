package org.kotopka;

import java.util.*;

public class CommandlineParser {

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

        validCommands.add("-d");
        validCommands.add("-minwl");
        validCommands.add("-maxwl");
        validCommands.add("-ef");

        validCommands.add("-mr");
        validCommands.add("-mw");
        validCommands.add("-ed");
        validCommands.add("-rp");
        validCommands.add("-sf");
        validCommands.add("-iw");
        validCommands.add("-ew");
        validCommands.add("-iws");

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
                case "-d":
                    setDictFileName();
                    break;

                case "-minwl":
                    setMinWordLength();
                    break;

                case "-maxwl":
                    setMaxWordLength();
                    break;

                case "-ef":
                    setExcludeFromDictionaryFilename();
                    break;

                case "-mr":
                    setMaxResults();
                    break;

                case "-mw":
                    setMaxWordsInAnagram();
                    break;

                case "-ed":
                    setExcludeDuplicates();
                    break;

                case "-rp":
                    setRestrictPermutations();
                    break;

                case "-sf":
                    setStartFrom();
                    break;

                case "-iw":
                    setIncludeWord();
                    break;

                case "-ew":
                    // TODO: currently only excludes one word, better to make (and return) a set?
                    setExcludeWord();
                    break;

                case "-iws":
                    setIncludeWordWithSuffix();
                    break;

                case "-h":
                    printOptions();
                    break;

                default:
                    setPhrase();
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
        System.out.println("\nThese options affect dictionary creation:");

        System.out.printf("\t%-5s\t%s", "-d", "Dictionary filename\n");
        System.out.printf("\t%-5s\t%s", "-minwl", "Minimum word length\n");
        System.out.printf("\t%-5s\t%s", "-maxwl", "Maximum word length\n");
        System.out.printf("\t%-5s\t%s", "-ef", "Filename of words to exclude from dictionary\n");

        System.out.println("\nThese options affect anagram creation:");

        System.out.printf("\t%-5s\t%s", "-mr", "Maximum results\n");
        System.out.printf("\t%-5s\t%s", "-mw", "Maximum words in anagram\n");
        System.out.printf("\t%-5s\t%s", "-ed", "Exclude duplicates\n");
        System.out.printf("\t%-5s\t%s", "-rp", "Restrict permutations\n");
        System.out.printf("\t%-5s\t%s", "-sf", "Start from word or letter\n");
        System.out.printf("\t%-5s\t%s", "-iw", "Include word in anagram\n");
        System.out.printf("\t%-5s\t%s", "-iws", "Include word with suffix in anagram\n");
        System.out.printf("\t%-5s\t%s", "-h", "This help message\n");

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

    public static void main(String[] args) {
        CommandlineParser clp = new CommandlineParser("commandline_thingy", args);

        clp.parseArgs();

        System.out.println("Dictionary filename: " + clp.getDictFile());
        System.out.println("Min: " + clp.getMinWordLength());
        System.out.println("Max: " + clp.getMaxWordLength());
        System.out.println("Max results: " + clp.getMaxResults());
        System.out.println("Exclude from dictionary filename: " + clp.getExcludeFromDictionaryFilename());
        System.out.println("Max words in anagram: " + clp.getMaxWordsInAnagram());
        System.out.println("Exclude Duplicates: " + clp.shouldExcludeDuplicates());
        System.out.println("Restrict Permutations: " + clp.shouldRestrictPermutations());
        System.out.println("Start from: " + clp.getStartFrom());
        System.out.println("Include word: " + clp.getIncludeWord());
        System.out.println("Exclude word: " + clp.getExcludeWord());
        System.out.println("Include word with suffix: " + clp.getSuffix());
        System.out.println("Phrase: " + String.join(" ", clp.getPhrase()));
    }
}
