package org.kotopka.CommandlineParser;

public class ExcludeWord extends AbstractStringOption {

    public ExcludeWord( ) {
        this.commandlineSwitch = Switch.EXCLUDE_WORD;
        this.description = "Exclude word";
        this.extendedHelpMessage =
                "Exclude Word -- " +
                "Exclude the word supplied as an option from appearing in the anagram." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <word>";
    }

}
