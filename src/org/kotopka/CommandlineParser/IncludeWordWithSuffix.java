package org.kotopka.CommandlineParser;

public class IncludeWordWithSuffix extends AbstractStringOption {

    public IncludeWordWithSuffix( ) {
        this.commandlineSwitch = Switch.INCLUDE_WORD_WITH_SUFFIX;
        this.description = "Include word with suffix";
        this.extendedHelpMessage =
                "Include Word with Suffix -- " +
                "Include the word having the suffix supplied as an option in the anagram." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <suffix>";
    }

}
