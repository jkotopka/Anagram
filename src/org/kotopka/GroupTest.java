package org.kotopka;

public class GroupTest {

    /*
        Example:
        Groups of "abcde": [truncated]
        a
        ab
        abc
        abcd
        abcde
        abce
        abd
        abde
        abe
        ac
        acd
        acde
        ace
        ad
        ade
        ae
        ... [then so on starting from b, c, etc.]
     */

    static int recursions;

    // 2^n recursions to generate an exhaustive collection of groupings
    public static void recur(String prefix, String str, int index) {
        recursions++;
        if (index == str.length()) return;

        for (int i = index; i < str.length(); i++) {
            System.out.println(prefix + str.charAt(i));
            recur(prefix + str.charAt(i), str, i + 1);
        }
    }

    public static void main(String[] args) {
        String str = "abcdefg";
        recursions = 0;

        recur("", str, 0);

        System.out.println("String length: " + str.length());
        System.out.println("Recursions: " + recursions);
    }

}
