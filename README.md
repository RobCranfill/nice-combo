# nice-combo
Find a nice mnemonic-friendly combination to use with a Kidde "KeySafe" 10-digit lockbox or the like.

This program finds 4-to-6-digit combinations (that length was chosen for [these reasons](https://boards.straightdope.com/sdmb/showthread.php?t=791728)) that have a common English word mnemonic, using one of the Linux dictionaries and some Java code to create a list of all found combinations; this is then stored in a simplistic fashion in a dictionary that the Javascript on the web page picks a random pair out of.

We use a telephone keypad as the way to map numbers to letters. One disadvantage of using the phone keypad for mapping is the combination will never have any zeros or ones in it, so the number of possible combos is smaller - and if the Bad Guy knows this, the search area for a brute-force attack is smaller too. (But they probably don't know this, so we're probably OK.)

The important thing to note about these lock-boxes is that the combination is *not* order-dependent; that is, "123" is the same as "321" and "312" and all other permutations of those three digits. :-/ And repeats don't matter, either, so "12131213111" is effectively the same as that simple three-digit combo.

The upshot of all this is there are only a few hundred combination of 4-6 digits, making a brute-force attack more feasible; but that's still a shit-ton of tries, and I think the Bad Guy would give up before then.


If all goes as planned, you can run this code here:
<a href="http://robcranfill.net/combo" target="_blank">http://robcranfill.net/combo</a>
