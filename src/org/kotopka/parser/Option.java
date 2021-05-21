package org.kotopka.parser;

/**
 * {@code Option} - Interface for {@code Option} objects used in classes that implement the {@code Parser} interface.
 */
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
