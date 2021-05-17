package org.kotopka.CommandlineParser;

public class MaxWords extends AbstractIntOption {

    public MaxWords( ) {
        this.commandlineSwitch = Switch.MAX_WORDS;
        this.description = "Max words";
        this.value = Integer.MAX_VALUE;
        this.extendedHelpMessage =
                "Max Words -- " +
                "Maximum number of words in anagram." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <max>";
    }

}
