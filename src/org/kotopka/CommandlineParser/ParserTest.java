package org.kotopka.CommandlineParser;

public class ParserTest {

    public static void main(String[] args) {
        Parser parser = new CommandlineParser(args, Switch.COLLECT_PHRASE);
        parser.addOptions(
                new DictFile(),
                new MinWordLen(),
                new MaxWordLen(),
                new ExcludeFile(),
                new MaxResults(),
                new Timeout(),
                new MaxWords(),
                new StartFrom(),
                new IncludeWord(),
                new ExcludeWord(),
                new IncludeWordWithSuffix(),
                new RestrictPermutations(),
                new ExcludeDuplicates(),
                new HelpMessage(),
                new ExtendedHelpMessage(),
                new CollectPhrase());

        parser.parseArgs();

        parser.printHelp();
//        parser.printValues();
        parser.printState();

//        System.out.println(parser.getOption(Switch.COLLECT_PHRASE).getString());
//        System.out.println(parser.getOption(Switch.MAX_WORD_LENGTH).getInt());
//        System.out.println(parser.getOption(Switch.RESTRICT_PERMUTATIONS).getBool());
//        System.out.println(parser.getOption(Switch.EXCLUDE_DUPLICATES).getBool());
    }
}
