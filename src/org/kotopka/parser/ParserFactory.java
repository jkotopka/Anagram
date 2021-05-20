package org.kotopka.parser;

/**
 * {@code ParserFactory} - Creates a {@code Parser} object with various commandline {@code Option} objects as parameters.
 * The {@code Option} objects are used to collect the values associated with the commandline switches, to be passed on to
 * the {@code Dictionary} and {@code Anagram} objects as anagram generation constraints.
 */
public class ParserFactory {

    public static Parser getParser(String[] args) {
        Parser parser = new CommandlineParser(args, Switch.COLLECT_PHRASE)
                .addOptions(
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
                    new PrintState(),
                    new CollectPhrase()
                );

        parser.parseArgs();

        return parser;
    }

}
