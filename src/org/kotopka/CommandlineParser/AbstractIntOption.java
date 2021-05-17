package org.kotopka.CommandlineParser;

public class AbstractIntOption extends AbstractOption {

    int value;

    @Override
    public String getState() {
        return description + ": " + value;
    }

    @Override
    public int getInt() { return value; }

    @Override
    public void execute(Parser parser) {
        String arg = "";

        try {
            arg = parser.getNextArg();
            value = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid number format: " + arg);
            System.exit(-1);
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
