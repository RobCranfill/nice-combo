# nice-combo
Find a nice mnemonic-friendly 5-digit combination for a Kidde "KeySafe" 10-digit lockbox or the like.

This program finds 5-digit combinations (5 digits chosen for [these reasons](https://boards.straightdope.com/sdmb/showthread.php?t=791728) that have a common English word mnemonic, using one of the Linux dictionaries and some Java code to create a list of all found combinations; this is then stored in a very stupid fashion in a dictionary that the Javascript on the web page picks a random pair out of.


The important thing to note about these lock-boxes is that the combination is *not* order-dependent; that is, "123" is the same as "321" and "312" and all other permutations of those three digits. :-/ And repeats don't count, either, so "121232111" is effectively the same as that three-digit combo.

If all goes as planned, you can run this code here: <a href="http://robcranfill.net/combo" target="_blank">http://robcranfill.net/combo</a>
