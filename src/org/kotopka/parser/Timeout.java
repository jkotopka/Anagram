package org.kotopka.parser;

public class Timeout extends AbstractIntOption {
    
    public Timeout() {
        this.commandlineSwitch = Switch.TIMEOUT;
        this.description = "Timeout";
        this.value = Integer.MAX_VALUE;
        this.extendedHelpMessage =
                "Timeout -- " +
                "Time in seconds to attempt anagram generation. " +
                "Note: this is a best attempt effort and the actual running time may be longer or shorter " + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <timeout in seconds>";
    }

}
