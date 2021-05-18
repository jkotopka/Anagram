# OOPCommandlineParser
*"Overengineering problems ...to solutions ...to other problems"*

A basic object-oriented commandline option parser.

A little Sunday afternoon project that spilled over into Monday morning. The idea was to practice OOP concepts and build an extensible commandline parser for an anagram-generator project where each commandline option is encapsulated in its own class and is invoked polymorphically rather than through e.g. a `switch` statement. Since each option is encapsulated in a class, the basic parser-driver can be reused for other types of programs where commandline parsing would be needed, with some stipulations.

Each `Option` in this repo extends either a basic `AbstractOption` class, or an abstract class suitable for the type of value it collects. `AbstractIntOption`, `AbstractStringOption` and `AbstractBooleanOption` can be used to collect the respective data types, and methods contained therein overridden as necessary. It's not necessary to extend these classes as long as the `Option` interface is otherwise implemented.

Each `Option` has a dependency for a `Switch` enum which informs the `Parser` of the relevant commandline string token so it can call the correct object when the token is read. The `Option` classes in this repo have the `Switch` hard-coded in the class but feasibly could be added through dependency injection. The actual parsing is done by retrieving the `Option` object from a `Map<Switch, Option>` instance field and sending the `Parser` `this` reference to the `Option` object so the data can be read and processed.

`Option` objects are passed to the `Parser` through a vararg `addOptions()` method, or through a chained-invocation `addOption()` method. After collecting the requisite options the `parseArgs()` method is called to actually parse the `String[] args` array passed to the `Parser` constructor. Then the client program can get each option and the associated value parsed from the commandline.

To use the options read from the commandline `parser.getOption()` is called with the respective `Switch` enum passed as an argument, then the relevant `getInt()`, `getString()` or `getBool()` method is called to retrieve the value to be passed on to the client.

This project is probably not useful to anybody else. But it was a fun little exercise and does indeed function as intended for my purposes.