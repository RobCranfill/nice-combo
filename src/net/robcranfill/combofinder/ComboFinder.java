/**
 * Read a file for input words and check each one for "goodness"; if good, output it.
 * "Good" means that the phone keypad representation of the number has "enough" different digits in it.
 * And "enough" means each digit is distinct; that is, a 5-character word must map to 5 different keys.
 */
package net.robcranfill.combofinder;

import java.io.*;
import java.util.*;

/**
 * @author robcranfill@gmail.com
 * copyright 2018 - all rights reserved
 */
public class ComboFinder {

// 0 and 1 have no letters associated!
public static String[] keypadLetters_ = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};


/**
 * @param args - args[0]: input file; args[1]: output file.
 */
public static void main(String[] args) {

	if (args.length !=2) {
		System.err.printf("Need two input args: {infile} {outfile}");
	}
	processInToOut(args[0], args[1]);
}


/**
 * Read the words, check each one for goodness,
 * and output a Javascript array with good words and digits.

 * @param infile
 * @param outfile
 */
private static void processInToOut(String infile, String outfile) {

	System.out.printf("Processing %s to %s....\n", infile, outfile);

	BufferedReader br = null;
	PrintWriter pw = null;
	int wordsOut = 0;
	int skipped = 0;
	try {
		br = new BufferedReader(new FileReader(new File(infile)));
		pw = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));

		pw.printf("[");

		String w = null;

		while ((w = br.readLine()) != null) {

			// Returns the good string if good, null otherwise.
			//
			String d = goodComboDigits(wordToDigits(w));
			if (d != null) {
	//				System.out.printf("%s -> %s\n", w, d);
					pw.printf("['%s','%s'],", w, d);
					wordsOut++;
					if (wordsOut % 20 == 0) {
						pw.printf("\n");
					}
				}
			}
		}
	catch (Exception ex) {
		ex.printStackTrace();
	}
	finally {
		try {
			br.close();
			pw.printf("];");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	System.out.printf("Wrote %d words, skipped %d input.\n", wordsOut, skipped);
}



/**
 * Is this a good combo? The only critereon we use now:
 * An n-digit number maps to n different keypad keys.
 *  (Since that's all we care about, this method doesn't really need to return
 *   the string of distinct digits, as it does, since that's the same thing as
 * 	input.... so sue me.)
 * @param digits
 * @return The
 */
private static String goodComboDigits(String digits) {

	boolean result = true;

	Set<String> distinct = new HashSet<>();
	String asString = ""; // this will be OK if passes test
	for (char c: digits.toCharArray()) {
		distinct.add(c + ""); // urk
		asString += c;
	}
//	System.out.printf("distinct: %s\n", distinct);
//	System.out.printf("distinct.size = %d\n", distinct.size());

	if (distinct.size() < digits.length()) {
		System.out.printf("\t - fails: only %d (of %d) digits\n", distinct.size(), digits.length());
		return null;
	}
//	System.out.printf("isGoodCombo '%s'? %s\n", digits, result);

  System.out.printf("\t - OK!\n");

	// this is the minimal set of chars;
	// kind of pointless if always of size n.
	return asString;
}


private static String wordToDigits(String word) {
	String digits = "";
	for (char c: word.toCharArray()) {
		c = Character.toLowerCase(c);
		for (int i=2; i<keypadLetters_.length; i++) {
			if (keypadLetters_[i].indexOf(c) > -1) {
				digits += i;
				continue;
			}
		}
	}
	System.out.printf("Word %s -> %s ", word, digits);
	return digits;
}

}
