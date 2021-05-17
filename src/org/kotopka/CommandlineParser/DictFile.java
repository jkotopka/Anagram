package org.kotopka.CommandlineParser;

public class DictFile extends AbstractStringOption {

    public DictFile( ) {
        this.commandlineSwitch = Switch.DICT_FILE;
        this.description = "Dictionary file";
        this.extendedHelpMessage =
                "Dictionary file -- " +
                "Name of file which contains the words to be inserted into the dictionary." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <filename>";
    }

}
