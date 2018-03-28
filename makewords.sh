#!/bin/bash
# make the input word file from the indicated Linux dictionary
# (you may want to change this)
grep -E '^[a-z]{4,6}$' /usr/share/dict/american-english-huge
