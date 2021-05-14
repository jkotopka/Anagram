package org.kotopka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO: make the word class an instanced object containing an actual word
//  and all of its substrings to allow very fast query of whether another word can be subtracted
/**
 * {@code Word} - Contains static methods for word-related operations.
 *
 * <ul>
 *     <li>{@code sortLetters()} - For sorting letters in the word.</li>
 *     <li>{@code subtract()} - For subtracting the letters in one word from another.</li>
 *     <li>{@code generateSubStrings()} - For generating an exhaustive list of substrings from the word.</li>
 * </ul>
 */
public class Word {

    /**
     * {@code sortLetters()} - Sorts the letters in the word in alphabetical order
     * @param word the word to sort
     * @return a {@code String} of letters of the {@code word} in alphabetical order
     */
    public static String sortLetters(String word) {
        Objects.requireNonNull(word, "String object cannot be null");

        char[] wordChars = word.toLowerCase().toCharArray();

        Arrays.sort(wordChars);

        return String.valueOf(wordChars).trim();
    }

    /**
     * {@code subtract()} - Subtract one substring from another larger string. If the entirety of the
     * smaller string can't be subtracted, returns the original string
     * @param original the original word
     * @param subtract the substring to subtract
     * @return a sorted {@code String} of the set of letters of the difference between "word"
     * minus "subtract", or the original string
     */
    public static String subtract(String original, String subtract) {
        Objects.requireNonNull(original, "String object cannot be null");
        Objects.requireNonNull(subtract, "String object cannot be null");

        // TODO: maybe just return the original word if the original len < subtract len, have to think about this
        if (subtract.length() > original.length())
            throw new IllegalArgumentException("Can't subtract larger string from smaller string");

        return getDifferenceOf(sortLetters(original), sortLetters(subtract));
    }

    private static String getDifferenceOf(String original, String subtract) {
        StringBuilder difference = new StringBuilder();
        int i = 0, j = 0;

        for (; i < original.length() && j < subtract.length(); i++) {
            if (original.charAt(i) == subtract.charAt(j))
                j++;
            else
                difference.append(original.charAt(i));
        }

        if (j < subtract.length()) return original;

        return difference.append(original.substring(i)).toString().trim();
    }

    /**
     * {@code generateSubStrings()} - Generates an exhaustive {@code List<String>} of the subsequences of the input {@code word}.
     * <p>For example, a list of subsequences of the input string "abc" would be:</p>
     * {@code a, ab, abc, ac, b, bc, c}
     * <p>This method runs in {@code O(2^n)} time where {@code n} is {@code word.length()}.</p>
     * @param word {@code String} of the word used to generate subsequences
     * @return {@code List<String>} of the subsequences generated
     */
    public static List<String> generateSubStrings(String word) {
        List<String> subStrings = new ArrayList<>();

        generateSubStringsRecursively("", sortLetters(word), 0, subStrings);

        return subStrings;
    }

    private static void generateSubStringsRecursively(String prefix, String word, int index, List<String> subStrings) {
        if (index == word.length()) return;

        for (int i = index; i < word.length(); i++) {
            subStrings.add(prefix + word.charAt(i));
            generateSubStringsRecursively(prefix + word.charAt(i), word, i + 1, subStrings);
        }
    }

}
