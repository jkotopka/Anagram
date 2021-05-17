package org.kotopka.CommandlineParser;

public class AbstractBooleanOption extends AbstractOption {

    boolean value;

    @Override
    public String getState() {
        return description + ": " + value;
    }

    @Override
    public boolean getBool() { return value; }

    @Override
    public void execute(Parser parser) {
        value = true;
    }

    @Override
    public String toString() {
        return commandlineSwitch + " " + value;
    }

}
