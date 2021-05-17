package org.kotopka.CommandlineParser;

public class MaxWordLen extends AbstractIntOption {

    public MaxWordLen( ) {
        this.commandlineSwitch = Switch.MAX_WORD_LENGTH;
        this.description = "Maximum word length";
        this.value = Integer.MAX_VALUE;
        this.extendedHelpMessage =
                "Max Word Length -- " +
                "Maximum length of words to be inserted into the dictionary." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <length>";
    }

}
