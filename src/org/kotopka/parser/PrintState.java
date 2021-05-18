package org.kotopka.parser;

public class PrintState extends AbstractBooleanOption {

    public PrintState() {
        this.commandlineSwitch = Switch.PRINT_STATE;
        this.description = "Print state";
        this.extendedHelpMessage =
                "Print State -- " +
                "Prints the current state of options collected from the commandline and their respective values. " +
                "This option does not take additional parameters." + NEWLINE +
                "\tUsage: " + commandlineSwitch;
    }

    @Override
    public void execute(Parser parser) { value = true; }

}
