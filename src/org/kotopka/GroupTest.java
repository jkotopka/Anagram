package org.kotopka;

public class GroupTest {

    /*
        a
        ab
        ac
        ad
        ae
        abc
        abd
        abe
        abcd
        abce
        abcde
        acd
        ace
        acde
        ade
     */

    public static void main(String[] args) {
        String str = "abcde";

//        recur("", str, 0);
        for (int i = 0; i < str.length(); i++) {
            recur("", str, i);
        }

    }

    public static void recur(String prefix, String str, int index) {

        if (index == str.length()) return;

        for (int i = index; i < str.length(); i++) {
            System.out.println(prefix + str.charAt(i));
            recur(prefix + str.charAt(i), str, i + 1);
        }
    }

}
