package org.kotopka.CommandlineParser;

public class AbstractStringOption extends AbstractOption {

    String value = "";

    @Override
    public String getState() {
        return description + ": " + value;
    }

    @Override
    public String getString() { return value; }

    @Override
    public void execute(Parser parser) {
        try {
            value = parser.getNextArg();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.err.println(commandlineSwitch + ": Cannot parse argument! Please ensure the correct parameter was provided.");
            System.exit(-1);
        }
    }

    @Override
    public String toString() {
        return commandlineSwitch + " " + value;
    }

}
