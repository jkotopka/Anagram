package org.kotopka.CommandlineParser;

public interface Option {

    Switch getSwitch();
    String getHelp();
    String getExtendedHelp();
    String getState();
    void execute(Parser parser);
    default int getInt() { return  0; }
    default boolean getBool() { return false; }
    default String getString() { return ""; }

}
