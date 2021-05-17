package org.kotopka.CommandlineParser;

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
