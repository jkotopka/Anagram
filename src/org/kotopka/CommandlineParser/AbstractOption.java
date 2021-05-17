package org.kotopka.CommandlineParser;

public abstract class AbstractOption implements Option {

    static final String NEWLINE = System.lineSeparator();

    Switch commandlineSwitch;
    String description;
    String extendedHelpMessage = "No extended help available";

    @Override
    public String getHelp() {
        return String.format("\t%-5s\t%s", commandlineSwitch, description);
    }

    @Override
    public String getExtendedHelp() {
        return extendedHelpMessage;
    }

    @Override
    public Switch getSwitch() {
        return commandlineSwitch;
    }

}
