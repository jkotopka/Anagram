package org.kotopka;

import java.util.*;

public class CommandlineParser {

    private final String[] args;
    private final List<String> phrase;
    private final HashSet<String> validCommands;

    private int currentArg;

    // dictionary stuff
    private int minWordLength;
    private int maxWordLength;

    // anagram stuff
    private int maxResults;
    private int maxWordsInAnagram;
    private boolean excludeDuplicates;
    private boolean restrictPermutations;
    private String startFrom;
    private String includeWord;
    private String excludeWord;
    private String includeWordWithSuffix;

    public CommandlineParser(String[] args) {
        this.args = args.clone();
        this.phrase = new ArrayList<>();
        this.validCommands = new HashSet<>();

        validCommands.add("-minwl");
        validCommands.add("-maxwl");

        validCommands.add("-mr");
        validCommands.add("-mw");
        validCommands.add("-ed");
        validCommands.add("-rp");
        validCommands.add("-sf");
        validCommands.add("-iw");
        validCommands.add("-ew");
        validCommands.add("-iws");

        // dictionary stuff
        this.maxWordLength = Integer.MAX_VALUE;

        // anagram stuff
        this.maxResults = Integer.MAX_VALUE;
        this.maxWordsInAnagram = Integer.MAX_VALUE;
        this.startFrom = "";
        this.includeWord = "";
        this.excludeWord = "";
        this.includeWordWithSuffix = "";
    }

    public void parseArgs() {
        while (currentArg < args.length) {
            String command = args[currentArg];

            validateCommand(command);

            switch (command) {
                case "-minwl":
                    setMinWordLength();
                    break;

                case "-maxwl":
                    setMaxWordLength();
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
                    setExcludeWord();
                    break;

                case "-iws":
                    setIncludeWordWithSuffix();
                    break;

                default:
                    setPhrase();
            }

            currentArg++;
        }
    }

    private void validateCommand(String command) {
        if (command.startsWith("-") && !validCommands.contains(command)) {
            System.out.println("Unknown command: " + command);
            System.exit(-1);
        }
    }

    private int getIntValueFromArg() {
        int valueFromArg = 0;

        try {
            valueFromArg = Integer.parseInt(args[++currentArg]);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        return valueFromArg;
    }

    private void setMinWordLength() {
        minWordLength = getIntValueFromArg();
    }

    private void setMaxWordLength() {
        maxWordLength = getIntValueFromArg();
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
        startFrom = args[++currentArg];
    }

    private void setIncludeWord() {
        includeWord = args[++currentArg];
    }

    private void setExcludeWord() {
        excludeWord = args[++currentArg];
    }

    private void setIncludeWordWithSuffix() {
        includeWordWithSuffix = args[++currentArg];
    }

    private void setPhrase() {
        while (currentArg < args.length)
            phrase.add(args[currentArg++]);
    }

    public int getMinWordLength() { return minWordLength; }

    public int getMaxWordLength() { return maxWordLength; }

    public int getMaxResults() { return maxResults; }

    public int getMaxWordsInAnagram() { return maxWordsInAnagram; }

    public boolean isExcludeDuplicates() { return excludeDuplicates; }

    public boolean isRestrictPermutations() { return restrictPermutations; }

    public String getStartFrom() { return  startFrom; }

    public String getIncludeWord() { return includeWord; }

    public String getExcludeWord() { return excludeWord; }

    public String getPhrase() { return String.join(" ", phrase); }

    public String getSuffix() { return includeWordWithSuffix; }

    public static void main(String[] args) {
        CommandlineParser clp = new CommandlineParser(args);

        clp.parseArgs();

        System.out.println("Min: " + clp.getMinWordLength());
        System.out.println("Max: " + clp.getMaxWordLength());
        System.out.println("Max results: " + clp.getMaxResults());
        System.out.println("Max words in anagram: " + clp.getMaxWordsInAnagram());
        System.out.println("Exclude Duplicates: " + clp.isExcludeDuplicates());
        System.out.println("Restrict Permutations: " + clp.isRestrictPermutations());
        System.out.println("Start from: " + clp.getStartFrom());
        System.out.println("Include word: " + clp.getIncludeWord());
        System.out.println("Exclude word: " + clp.getExcludeWord());
        System.out.println("Include word with suffix: " + clp.getSuffix());
        System.out.println("Phrase: " + String.join(" ", clp.getPhrase()));
    }
}
