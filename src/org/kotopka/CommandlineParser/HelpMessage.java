package org.kotopka.CommandlineParser;

public class HelpMessage extends AbstractBooleanOption {

    public HelpMessage() {
        this.commandlineSwitch = Switch.HELP;
        this.description = "Get help";
        this.extendedHelpMessage =
                "Help -- " +
                "Prints a list of options with a simplified description of each. " +
                "This option does not take additional parameters." + NEWLINE +
                "\tUsage: " + commandlineSwitch;
    }

    @Override
    public void execute(Parser parser) {
        value = true;

        parser.printHelp();
    }

}
