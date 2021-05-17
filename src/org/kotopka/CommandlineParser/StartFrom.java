package org.kotopka.CommandlineParser;

public class StartFrom extends AbstractStringOption {

    public StartFrom( ) {
        this.commandlineSwitch = Switch.START_FROM;
        this.description = "Start from";
        this.extendedHelpMessage =
                "Start From -- " +
                "Start anagram search from the word supplied as an option." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <word>";
    }

}
