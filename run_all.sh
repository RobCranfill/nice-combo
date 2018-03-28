#!/bin/bash
#
# Do all the things:
# Prepare the input file from a dictionary file (provided as input arg)
# process it to find "good" combinations, and output the final HTML file.

# Input param for this script: the dictionary file to use,
#  such as /usr/share/dict/american-english-huge (ok, but lots of garbage),
#  or something from http://wordlist.aspell.net/ (better quality words)
#  such as "American/2of12inf.txt".

if [ $# -eq 0 ]
  then
    echo "No arguments supplied; please point to a dictionary file."
    exit 1
fi

DICT_FILE=$1

WORDS_IN=words.in.text
WORDS_OUT=words.out.text
HTML_OUT=combo.html

# Find all 4-to-6-letter words - could filter more here?
echo Searching dictionary for suitable-length words....
grep -E '^[a-z]{4,6}$' $DICT_FILE >$WORDS_IN

# Compile the Java - not really needed every time, but hey.
javac -d bin src/net/robcranfill/combofinder/ComboFinder.java

echo Finding combinations with good mnemonics....
java -classpath .:bin net.robcranfill.combofinder.ComboFinder $WORDS_IN $WORDS_OUT

# Create the HTML
cat combo.html.1  > $HTML_OUT
cat $WORDS_OUT   >> $HTML_OUT
cat combo.html.2 >> $HTML_OUT
