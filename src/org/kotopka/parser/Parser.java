package org.kotopka.parser;

/**
 * {@code Parser} - Interface for objects used in command-line parsing.
 */
public interface Parser {

    Parser addOption(Option option);
    Parser addOptions(Option... optionArgs);
    boolean hasNextArg();
    String getNextArg();
    Option getOption(Switch commandlineSwitch);
    void printHelp();
    void printValues();
    void printState();
    void parseArgs();

}
