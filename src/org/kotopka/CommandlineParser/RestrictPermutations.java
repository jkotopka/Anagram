package org.kotopka.CommandlineParser;

public class RestrictPermutations extends AbstractBooleanOption{

    public RestrictPermutations( ) {
        this.commandlineSwitch = Switch.RESTRICT_PERMUTATIONS;
        this.description = "Restrict permutations";
        this.extendedHelpMessage =
                "Restrict permutations -- " +
                "This option attempts to restrict the number of permutations " +
                "the algorithm uses to build anagrams. This option does not take additional parameters. " + NEWLINE +
                "\tUsage: " + commandlineSwitch;
    }

}
