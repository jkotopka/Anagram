package org.kotopka;

import java.nio.file.Paths;
import java.util.*;

/**
 * {@code Anagram} - Finds and prints a list of all single-word anagrams from a
 * single input word. Valid words are those contained within a dictionary file supplied during construction.
 * <br><br>
 * This version considers a valid word to be its own anagram and will include it in the output list.
 */
public class Anagram {

    public static class Builder {
        private final String dictFile;
        private int minWordLength;
        private int maxWordLength;
        private int maxResults;

        private String startFrom;
        private String includeWord;

        private boolean excludeDuplicates;
        private Set<String> excludeWordsSet;

        public Builder(String dictFile) {
            Objects.requireNonNull(dictFile);

            this.dictFile = dictFile;
            this.minWordLength = 1;
            this.maxWordLength = Integer.MAX_VALUE;
            this.maxResults = Integer.MAX_VALUE;
            this.startFrom = "";
            this.includeWord = "";
            this.excludeWordsSet = new HashSet<>();
        }

        public Builder setMinWordLength(int minWordLength) {
            if (minWordLength < 0)
                throw new IllegalArgumentException("Invalid min word length: " + minWordLength);

            this.minWordLength = minWordLength;

            return this;
        }

        public Builder setMaxWordLength(int maxWordLength) {
            if (maxWordLength < 0)
                throw new IllegalArgumentException("Invalid max word length: " + maxWordLength);

            this.maxWordLength = maxWordLength;

            return this;
        }

        public Builder setMaxResults(int maxResults) {
            if (maxResults < 0)
                throw new IllegalArgumentException("Invalid max results: " + maxResults);

            this.maxResults = maxResults;

            return this;
        }

        public Builder startFrom(String word) {
            Objects.requireNonNull(word);

            this.startFrom = word;

            return this;
        }

        public Builder includeWord(String word) {
            Objects.requireNonNull(word);

            this.includeWord = word;

            return this;
        }

        public Builder excludeDuplicates(boolean shouldExclude) {
            this.excludeDuplicates = shouldExclude;

            return this;
        }

        public Builder excludeWordSet(Set<String> excludeWordsSet) {
            Objects.requireNonNull(excludeWordsSet);

            // do NOT want an immutable copy here because it gets updated during use
            this.excludeWordsSet = excludeWordsSet;

            return this;
        }

        public Anagram build() {
            return new Anagram(this);
        }

    }

    private final Dictionary dictionary;
    private int count;
    private int minWordLength;
    private int maxWordLength;
    private int maxResults;
    private String startFrom;
    private String includeWord;
    private boolean excludeDuplicates;
    private final Set<String> excludeWordsSet;

    private Anagram(Builder builder) {
        this.minWordLength = builder.minWordLength;
        this.maxWordLength = builder.maxWordLength;
        this.maxResults = builder.maxResults;
        this.startFrom = builder.startFrom;
        this.includeWord = builder.includeWord;
        this.excludeDuplicates = builder.excludeDuplicates;
        this.excludeWordsSet = builder.excludeWordsSet;

        this.dictionary = new Dictionary.Builder(Paths.get(builder.dictFile))
                .setMinWordLength(minWordLength)
                .setMaxWordLength(maxWordLength)
                .excludeWordsFromSet(excludeWordsSet)
                .build();
    }

    private void setMinWordLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be positive");

        minWordLength = length;
    }

    public void setMaxWordLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be positive");

        maxWordLength = length;
    }

    public void setMaxResults(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max must be positive");

        maxResults = max;
    }

    public void startFrom(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        startFrom = word;
    }

    public void includeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        includeWord = word;
    }

    public void excludeWord(String word) {
        Objects.requireNonNull(word, "Word cannot be null");

        excludeWordsSet.add(word);
    }

    public void shouldExcludeDuplicates(boolean shouldExclude) {
        excludeDuplicates = shouldExclude;
    }

    public List<String> findSingleAnagramsOf(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        String sortedWord = Word.sortLetters(word);

        return dictionary.getListOrEmpty(sortedWord);
    }

    public List<String> findAllAnagramsOf(String word) {
        LinkedList<String> stack = new LinkedList<>();
        List<String> anagrams    = new ArrayList<>();

        count = 0;

        buildAnagramListRecursively(word, startFrom, stack, anagrams);
        Collections.sort(anagrams);

        return anagrams;
    }

    private void buildAnagramListRecursively(String word, String startWord, LinkedList<String> anagram, List<String> anagramList) {
        for (String s : findAllValidSubWordsAsSet(word).tailSet(startWord)) {
            if (excludeWordsSet.contains(s)) continue;
            if (excludeDuplicates) excludeWordsSet.add(s.toLowerCase());

            anagram.push(s);

            String diff = Word.subtract(word, s);

            if (validateAnagram(diff, anagram))
                addAnagramToList(anagram, anagramList);

            if (count == maxResults) return;

            buildAnagramListRecursively(diff, "", anagram, anagramList);

            if (excludeDuplicates) excludeWordsSet.remove(s);

            anagram.pop();
        }
    }

    public TreeSet<String> findAllValidSubWordsAsSet(String word) {
        Objects.requireNonNull(word, "Method argument cannot be null");

        TreeSet<String> validSubWords = new TreeSet<>();

        for (String subString : generateSubStrings(word))
            validSubWords.addAll(dictionary.getListOrEmpty(subString));

        return validSubWords;
    }

    private List<String> generateSubStrings(String word) {
        List<String> subStrings = new ArrayList<>();

        generateSubStringsRecursively("", Word.sortLetters(word), 0, subStrings);

        return subStrings;
    }

    private void generateSubStringsRecursively(String prefix, String word, int index, List<String> subStrings) {
        if (index == word.length()) return;

        for (int i = index; i < word.length(); i++) {
            subStrings.add(prefix + word.charAt(i));
            generateSubStringsRecursively(prefix + word.charAt(i), word, i + 1, subStrings);
        }
    }

    private boolean validateAnagram(String diff, LinkedList<String> stack) {
        return (diff.isBlank() && (includeWord.isBlank() || stack.contains(includeWord)));
    }

    private void addAnagramToList(LinkedList<String> anagram, List<String> anagramList) {
        anagramList.add(String.join(" ", anagram));
        count++;
    }

    // TODO: one of the words must have a certain suffix?
    //  also includeWordSet and anagram validation
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Anagram <word>");
            System.exit(0);
        }

        Set<String> toExclude = new HashSet<>();
        toExclude.add(null);
        toExclude.add("msc");
        toExclude.add("acl");
        toExclude.add("tko");
        toExclude.add("acm");
        toExclude.add("mph");
        toExclude.add("acol");
        toExclude.add("acs");
        toExclude.add("HIMS");

        Anagram anagram = new Builder("dictionary-large.txt")
                .setMinWordLength(3)
                .setMaxWordLength(10)
                .setMaxResults(30000)
                .excludeDuplicates(false)
                .excludeWordSet(toExclude)
                .startFrom("pens")
                .includeWord("ali")
                .build();

        String word = String.join(" ",args);

        findAndPrintSubWords(anagram, word);

        findAndPrintAnagrams(anagram, word);
        anagram.startFrom("pep");
        findAndPrintAnagrams(anagram, word);
        anagram.startFrom("ali");
        anagram.setMaxResults(3);
        findAndPrintAnagrams(anagram, word);
    }

    private static void findAndPrintSubWords(Anagram anagram, String word) {
        // TODO: check the tailSet() here later
        Set<String> allSubWords = anagram.findAllValidSubWordsAsSet(word);

        if (allSubWords.isEmpty()) {
            System.out.printf("No sub-words of \"%s\" found\n", word);
        } else {
            System.out.println("Valid sub-words of \"" + word + "\" found: " + allSubWords.size());
            allSubWords.forEach(System.out::println);
        }
    }

    private static void findAndPrintAnagrams(Anagram anagram, String word) {
        long startTime = System.currentTimeMillis();
        List<String> allAnagrams = anagram.findAllAnagramsOf(word);
        long endTime = System.currentTimeMillis();

        if (allAnagrams.isEmpty()) {
            System.out.printf("\nNo anagrams of \"%s\" found\n", word);
        } else {
            System.out.println("\nAll anagrams of " + word + " found: " + allAnagrams.size());
            allAnagrams.forEach(System.out::println);
        }

        System.out.println("Elapsed time: " + (double)(endTime - startTime) / 1000);
    }

}
