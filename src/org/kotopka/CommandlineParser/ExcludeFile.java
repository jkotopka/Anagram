package org.kotopka.CommandlineParser;

public class ExcludeFile extends AbstractStringOption {

    public ExcludeFile( ) {
        this.commandlineSwitch = Switch.EXCLUDE_FROM_DICT_FILE;
        this.description = "Exclude words file";
        this.extendedHelpMessage =
                "Exclude words in File -- " +
                "Name of file which contains words to be excluded from the dictionary." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <filename>";
    }

}
