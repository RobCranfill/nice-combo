#!/bin/bash
# Run the Java on the indicated input file, outputting Javascript to 2nd file.

java -classpath .:bin net.robcranfill.combofinder.ComboFinder $1 $2
