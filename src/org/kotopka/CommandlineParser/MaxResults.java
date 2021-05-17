package org.kotopka.CommandlineParser;

public class MaxResults extends AbstractIntOption {

    public MaxResults( ) {
        this.commandlineSwitch = Switch.MAX_RESULTS;
        this.description = "Max results";
        this.value = Integer.MAX_VALUE;
        this.extendedHelpMessage =
                "Max Results -- " +
                "Maximum number of anagrams to be generated." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <max>";
    }

}
