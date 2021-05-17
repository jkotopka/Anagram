package org.kotopka.CommandlineParser;

import java.util.List;

public class ExtendedHelpMessage extends AbstractBooleanOption {

    public ExtendedHelpMessage() {
        this.commandlineSwitch = Switch.EXTENDED_HELP;
        this.description = "Get extended help for option";
        this.extendedHelpMessage =
                "Extended Help -- " +
                "Get extended help on commandline switch options." + NEWLINE +
                "\tUsage: " + commandlineSwitch + " <option>";
    }

    @Override
    public void execute(Parser parser) {
        String arg = getArg(parser);

        value = true;

        printExtendedHelpMessage(arg, parser);
        System.exit(0);
    }

    private String getArg(Parser parser) {
        String arg;

        if (parser.hasNextArg())
            arg = parser.getNextArg();
        else
            arg = commandlineSwitch.getLabel(); // if called without an <option> parameter

        return arg;
    }

    private void printExtendedHelpMessage(String arg, Parser parser) {
        try {
            System.out.println(arg + ": " + parser.getOption(Switch.get(arg)).getExtendedHelp());
        } catch (Exception e) {
            System.err.println("Unknown option: " + arg);
        }
    }

}
