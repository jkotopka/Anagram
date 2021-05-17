package org.kotopka.CommandlineParser;

public class ExcludeDuplicates extends AbstractBooleanOption {

    public ExcludeDuplicates( ) {
        this.commandlineSwitch = Switch.EXCLUDE_DUPLICATES;
        this.description = "Exclude duplicates";
        this.extendedHelpMessage =
                "Exclude Duplicates -- " +
                "Attempts to exclude duplicate words in the anagram. " +
                "This option does not take additional parameters." + NEWLINE +
                "\tUsage: " + commandlineSwitch;
    }

}
