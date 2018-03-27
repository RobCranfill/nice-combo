/**
 * 
 */
package net.robcranfill.combofinder;

import java.io.*;
import java.util.*;


/**
 * @author robcranfill@gmail.com
 *
 */
public class ComboFinder {


//0 and 1 have no letters associated!
public static String[] keypadLetters_ = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

private static Random randomGenerator_ = new Random();


private String randomWord(List<String> words) {
	return words.get(randomGenerator_.nextInt(words.size()));
	}


/**
 * @param args
 */
public static void main(String[] args) {

	if (args.length > 0) {

		String infile  = args[0];
		processInToOut(infile);
	}
	else { // args == 0; just fart out a good one
		System.out.printf("Let me find you a combination....\n");
		
		ComboFinder cf = new ComboFinder();
		List<String> words = cf.initDict();
		
	//	System.out.printf("Random word: %s\n", cf.randomWord(words));
	//	System.out.printf("Random word: %s\n", cf.randomWord(words));
	//	System.out.printf("Random word: %s\n", cf.randomWord(words));
		
		String digits = null;
		String word = null;
		int tries = 0;
		do {
			tries++;
			word = cf.randomWord(words);
			digits = cf.wordToDigits(word);
			System.out.printf("*** digits of '%s': %s\n", word, digits);
		} while (!isGoodCombo(digits));
		
		System.out.printf("\nCombination: %s; mnemonic '%s'\n", digits, word);
		System.out.printf(" (found in %d %s)\n", tries, tries>1?"tries":"try");
	}
}


/**
 * Read the file, output another one with good words and digits.
 * @param infile
 * @param outfile
 */
private static void processInToOut(String infile) {
	
	System.err.printf("Processing %s....\n", infile);
	
	BufferedReader br = null;
	int lines = 0;
	int skipped = 0;
	try {
		br = new BufferedReader(new FileReader(new File(infile)));
		
		System.out.printf("[");

		String w = null;
		while ((w = br.readLine()) != null) {
			if (w.contains("\'")) {
				skipped++;
				continue;
			}
			
			String d = goodComboDigits(wordToDigits(w));
			if (d != null) {
//				System.out.printf("%s %s\n", w, d);
				System.out.printf("['%s','%s'],", w, d);
			}
			lines++;
		}
	}
	catch (Exception ex) {
		ex.printStackTrace();
	}
	finally {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	System.out.printf("];");
	System.err.printf("Wrote %d lines, skipped %d input.\n", lines, skipped);
}



/**
 * The crux: what's a good combo?
 * <ul>
 * <li>has <i>MIN_DIGITS</i> distinct digits.
 * </ul>
 * @param digits
 * @return
 */
private static String goodComboDigits(String digits) {
	final int MIN_DIGITS = 5;
	boolean result = true;

	Set<String> distinct = new HashSet<>();
	String asString = ""; // this will be OK if passes test
	for (char c: digits.toCharArray()) {
		distinct.add(c + ""); // urk
		asString += c;
	}
//	System.out.printf("distinct: %s\n", distinct);
//	System.out.printf("distinct.size = %d\n", distinct.size());

	if (distinct.size() < MIN_DIGITS) {
		System.err.printf(" '%s' fails: too few digits (%d)\n", digits, distinct.size());
		return null;
	}
//	System.out.printf("isGoodCombo '%s'? %s\n", digits, result);

	return asString;
}


/**
 * The crux: what's a good combo?
 * <ul>
 * <li>has <i>MIN_DIGITS</i> distinct digits.
 * </ul>
 * @param digits
 * @return
 */
private static boolean isGoodCombo(String digits) {
	final int MIN_DIGITS = 5;
	boolean result = true;

	Set<String> distinct = new HashSet<>();
	for (char c: digits.toCharArray()) {
		distinct.add(c + ""); // urk
	}
//	System.out.printf("distinct: %s\n", distinct);
//	System.out.printf("distinct.size = %d\n", distinct.size());

	if (distinct.size() < MIN_DIGITS) {
		System.err.printf(" '%s' fails: too few digits (%d)\n", digits, distinct.size());
		result = false;
	}
//	System.out.printf("isGoodCombo '%s'? %s\n", digits, result);

	return result;
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
	System.err.printf("Word %s -> %s\n", word, digits);
	return digits;
}


public ComboFinder() {
}


private List<String> initDict() {
	
	List<String> words = new ArrayList<String>();
	int echo = 0;
	BufferedReader br = null;
	String lastS = null;
	int lines = 0;
	int skipped = 0;
	try {
		
		File ff = new File(".\\words-5-letters.text");
		br = new BufferedReader(new FileReader(ff));
		String s = null;
		while ((s = br.readLine()) != null) {
			if (s.contains("\'")) {
				skipped++;
				continue;
			}
			words.add(s);
			if (echo-- > 0) {
				System.out.printf(" -- %s \n", s);
			}
			lastS = s;
			lines++;
		}
	}
	catch (Exception ex) {
		ex.printStackTrace();
	}
	finally {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	System.out.printf("Read %d words from dictionary.\n", words.size());
	boolean debug = false;
	if (debug) {
		System.out.printf("  %d lines\n", lines);	
		System.out.printf("  %d skipped (%d total)\n", skipped, skipped+lines);
		System.out.printf(" -- %s \n", words);
		
		System.out.printf(" --	lastS %s \n", lastS);
		
		System.out.printf(" contains '%s'? %s \n", "diode", words.contains("diode"));
		System.out.printf(" contains '%s'? %s \n", "wreck", words.contains("wreck"));
		System.out.printf(" contains '%s'? %s \n", "smeggdorf", words.contains("smeggdorf"));
		}
	
	return words;
	}

}
	
