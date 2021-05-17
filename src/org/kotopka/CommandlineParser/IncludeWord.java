package org.kotopka.CommandlineParser;

public class IncludeWord extends AbstractStringOption {

    public IncludeWord( ) {
        this.commandlineSwitch = Switch.INCLUDE_WORD;
        this.description = "Include word";
        this.extendedHelpMessage =
                "Include Word -- " +
                "Include the word supplied as an option in the anagram." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <word>";
    }

}
