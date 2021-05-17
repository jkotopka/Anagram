package org.kotopka.CommandlineParser;

public class MinWordLen extends AbstractIntOption {

    public MinWordLen( ) {
        this.commandlineSwitch = Switch.MIN_WORD_LENGTH;
        this.description = "Minimum word length";
        this.extendedHelpMessage =
                "Min Word Length -- " +
                "Minimum length of words to be inserted into the dictionary." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <length>";
    }

}
