package org.kotopka;

import java.util.Arrays;
import java.util.Objects;

public class Word {

    public static String sortLetters(String word) {
        Objects.requireNonNull(word, "String object cannot be null");

        char[] wordChars = word.toUpperCase().toCharArray();

        Arrays.sort(wordChars);

        return String.valueOf(wordChars).trim();
    }

    // assumes letters in each string are sorted before subtracting
    public static String subtract(String original, String subtract) {
        Objects.requireNonNull(original, "String object cannot be null");
        Objects.requireNonNull(subtract, "String object cannot be null");

        if (subtract.length() > original.length())
            throw new IllegalArgumentException("Can't subtract larger string from smaller string");

        StringBuilder difference = getDifferenceOf(original, subtract);

        return difference.toString().trim();
    }

    private static StringBuilder getDifferenceOf(String original, String subtract) {
        StringBuilder difference = new StringBuilder();
        int i = 0, j = 0;

        for (; i < original.length() && j < subtract.length(); i++) {
            if (original.charAt(i) == subtract.charAt(j))
                j++;
            else
                difference.append(original.charAt(i));
        }

        return difference.append(original.substring(i));
    }

    public static void main(String[] args) {
        System.out.println(subtract(sortLetters("mongolian"), sortLetters("omni")));
    }

}
