package org.kotopka.CommandlineParser;

import java.util.ArrayList;
import java.util.List;

public class CollectPhrase extends AbstractOption {

    private final List<String> value;

    public CollectPhrase( ) {
        this.commandlineSwitch = Switch.COLLECT_PHRASE;
        this.description = "Collect words in phrase";
        this.value = new ArrayList<>();
    }

    @Override
    public String getState() {
        return description + ": " + value;
    }

    @Override
    public String getString() { return String.join(" ", value); }

    @Override
    public void execute(Parser parser) {
        while (parser.hasNextArg())
            value.add(parser.getNextArg());
    }

    @Override
    public String toString() {
        return commandlineSwitch + " " + value;
    }

}
