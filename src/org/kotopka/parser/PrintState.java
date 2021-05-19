package org.kotopka.parser;

public class PrintState extends AbstractBooleanOption {

    // XXX: this class doesn't work as expected. rather than print the state it only sets the boolean flag
    // to inform the client whether the option was selected. it is the client's responsibility to actually
    // print the state information. the reason for this is because the parser is only able to get the full
    // commandline state after all input has been entered and attempting to print the state from here would
    // be premature and not reflective of all commandline input
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
