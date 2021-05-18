package org.kotopka;

import org.kotopka.CommandlineParser.*;

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
                    new CollectPhrase()
                );

        parser.parseArgs();

        return parser;
    }

}
