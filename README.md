Anagram
----
Basic anagram generator and driver applications.

Contained in this repository are several components to the anagram generator
project, each contained within a package.

The packages are as follows:

* `anagram` - Contains the main `Anagram` class as well as a `AnagramFactory`
class, which uses a simple static factory method to generate `Anagram` objects
according to user-supplied options.
  

* `deprecated` - Contains deprecated features such as the old commandline parsing
class. This package remains in the repo for comparison to newer ideas.
  

* `dictionary` - Contains the `Dictionary` class used to build the `Anagram`
generator as well as a `DictionaryFactory` class which uses a static factory
method to generate the `Dictionary` object according to user-supplied options.


* `gui` - Contains classes used for the GUI-based anagram-generator application.


* `parser` - Contains classes needed for the commandline parsing utility, which
is used by the `AnagramFactory` and `DictionaryFactory` classes.
  

* `word` - Contains the `Word` class, which has a few static methods useful
for word/letter-related operations used by the `Dictionary` and `Anagram` classes.