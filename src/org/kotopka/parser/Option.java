package org.kotopka.parser;

public interface Option {

    Switch getSwitch();
    String getHelp();
    String getExtendedHelp();
    String getState();
    void execute(Parser parser);

    default int getInt() { throw new UnsupportedOperationException("Operation not supported"); }
    default boolean getBool() { throw new UnsupportedOperationException("Operation not supported"); }
    default String getString() { throw new UnsupportedOperationException("Operation not supported"); }
}
