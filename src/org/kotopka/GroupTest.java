package org.kotopka;

public class GroupTest {

    /*
        Groups: [truncated]
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
        abde
        acd
        ace
        acde
        ade
        ...

        Actual: [truncated]
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
        ...
     */

    public static void main(String[] args) {
        String str = "abcde";

        recur("", str, 0);

    }

    public static void recur(String prefix, String str, int index) {

        if (index == str.length()) return;

        for (int i = index; i < str.length(); i++) {
            System.out.println(prefix + str.charAt(i));
            recur(prefix + str.charAt(i), str, i + 1);
        }
    }

}
