package org.kotopka;

import java.util.Arrays;
import java.util.Objects;

/**
 * {@code Word} - Contains static methods for word-related operations, e.g. sorting letters in the word and
 * subtracting one word from another
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

}
