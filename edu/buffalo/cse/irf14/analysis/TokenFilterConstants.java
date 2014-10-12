package edu.buffalo.cse.irf14.analysis;

import java.util.*;

/**
 * @author Kaushik
 *
 */

public class TokenFilterConstants {

	public Map<String, String> contractions = new HashMap<String, String>();
	public Map<String, Boolean> stopWords = new HashMap<String, Boolean>();
	public Map<Character, String> accents = new HashMap<Character, String>();

	private static TokenFilterConstants tokenFilterConstants = null;

	public static TokenFilterConstants getInstance() {
		if (tokenFilterConstants == null) {
			tokenFilterConstants = new TokenFilterConstants();
		}
		return tokenFilterConstants;
	}

	private void addToMap(String str1, String str2) {
		contractions.put(str1, str2);
	}

	private void addToStopWordMap(String str1, Boolean boo) {
		stopWords.put(str1, boo);
	}

	private void addToAccentsMap(char ch, String str1) {
		accents.put(ch, str1);
	}

	private TokenFilterConstants() {
		// Common English Contractions

		// addToMap("ain't", "am not / are not / is not / has not / have not");
		addToMap("ain't", "am not");
		// addToMap("aren't", "are not / am not");
		addToMap("aren't", "are not");
		addToMap("can't", "cannot");
		addToMap("could've", "could have");
		addToMap("couldn't", "could not");
		addToMap("couldn't've", "could not have");
		addToMap("didn't", "did not");
		addToMap("doesn't", "does not");
		addToMap("don't", "do not");
		addToMap("hadn't", "had not");
		addToMap("hadn't've", "had not have");
		addToMap("hasn't", "has not");
		addToMap("haven't", "have not");
		// addToMap("he'd", "he had / he would");
		addToMap("he'd", "he had");
		addToMap("he'd've", "he would have");
		// addToMap("he'll", "he shall / he will");
		addToMap("he'll", "he will");
		// addToMap("he's", "he has / he is");
		addToMap("he's", "he is");
		// addToMap("how'd", "how did / how would");
		addToMap("how'd", "how did");
		addToMap("how'll", "how will");
		// addToMap("how's", "how has / how is / how does");
		addToMap("how's", "how is");
		// addToMap("I'd", "I had / I would");
		addToMap("i'd", "i had");
		addToMap("i'd've", "i would have");
		// addToMap("I'll", "I shall / I will");
		addToMap("i'll", "i will");
		addToMap("i'm", "i am");
		addToMap("i've", "i have");
		addToMap("isn't", "is not");
		// addToMap("it'd", "it had / it would");
		addToMap("it'd", "it had");
		addToMap("it'd've", "it would have");
		// addToMap("it'll", "it shall / it will");
		addToMap("it'll", "it will");
		// addToMap("it's", "it has / it is");
		addToMap("it's", "it is");
		addToMap("let's", "let us");
		addToMap("ma'am", "madam");
		addToMap("mightn't", "might not");
		addToMap("mightn't've", "might not have");
		addToMap("might've", "might have");
		addToMap("mustn't", "must not");
		addToMap("must've", "must have");
		addToMap("needn't", "need not");
		addToMap("not've", "not have");
		addToMap("o'clock", "of the clock");
		addToMap("shan't", "shall not");
		// addToMap("she'd", "she had / she would");
		addToMap("she'd", "she had");
		addToMap("she'd've", "she would have");
		// addToMap("she'll", "she shall / she will");
		addToMap("she'll", "she will");
		// addToMap("she's", "she has / she is");
		addToMap("she's", "she is");
		addToMap("should've", "should have");
		addToMap("shouldn't", "should not");
		addToMap("shouldn't've", "should not have");
		// addToMap("that's", "that has / that is");
		addToMap("that's", "that is");
		// addToMap("there'd", "there had / there would");
		addToMap("there'd", "there had");
		addToMap("there'd've", "there would have");
		addToMap("there're", "there are");
		// addToMap("there's", "there has / there is");
		addToMap("there's", "there is");
		// addToMap("they'd", "they had / they would");
		addToMap("they'd", "they would");
		addToMap("they'd've", "they would have");
		// addToMap("they'll", "they shall / they will");
		addToMap("they'll", "they will");
		addToMap("they're", "they are");
		addToMap("they've", "they have");
		addToMap("wasn't", "was not");
		// addToMap("we'd", "we had / we would");
		addToMap("we'd", "we had");
		addToMap("we'd've", "we would have");
		addToMap("we'll", "we will");
		addToMap("we're", "we are");
		addToMap("we've", "we have");
		addToMap("weren't", "were not");
		// addToMap("what'll", "what shall / what will");
		addToMap("what'll", "what will");
		addToMap("what're", "what are");
		// addToMap("what's", "what has / what is / what does");
		addToMap("what's", "what is");
		addToMap("what've", "what have");
		// addToMap("when's", "when has / when is");
		addToMap("when's", "when is");
		addToMap("where'd", "where did");
		// addToMap("where's", "where has / where is");
		addToMap("where's", "where is");
		addToMap("where've", "where have");
		// addToMap("who'd", "who would / who had");
		addToMap("who'd", "who had");
		// addToMap("who'll", "who shall / who will");
		addToMap("who'll", "who will");
		addToMap("who're", "who are");
		// addToMap("who's", "who has / who is");
		addToMap("who's", "who is");
		addToMap("who've", "who have");
		addToMap("why'll", "why will");
		addToMap("why're", "why are");
		// addToMap("why's", "why has / why is");
		addToMap("why's", "why is");
		addToMap("won't", "will not");
		addToMap("would've", "would have");
		addToMap("wouldn't", "would not");
		addToMap("wouldn't've", "would not have");
		addToMap("y'all", "you all");
		// addToMap("y'all'd've",
		// "you all should have / you all could have / you all would have");
		addToMap("y'all'd've", "you all should have");
		// addToMap("you'd", "you had / you would");
		addToMap("you'd", "you had");
		addToMap("you'd've", "you would have");
		// addToMap("you'll", "you shall / you will");
		addToMap("you'll", "you will");
		addToMap("you're", "you are");
		addToMap("you've", "you have");
		addToMap("'em", "them");

		// Stop Words
		addToStopWordMap("a", true);
		addToStopWordMap("able", true);
		addToStopWordMap("about", true);
		addToStopWordMap("across", true);
		addToStopWordMap("after", true);
		addToStopWordMap("all", true);
		addToStopWordMap("almost", true);
		addToStopWordMap("also", true);
		addToStopWordMap("am", true);
		addToStopWordMap("among", true);
		addToStopWordMap("an", true);
		addToStopWordMap("and", true);
		addToStopWordMap("any", true);
		addToStopWordMap("are", true);
		addToStopWordMap("as", true);
		addToStopWordMap("at", true);
		addToStopWordMap("be", true);
		addToStopWordMap("because", true);
		addToStopWordMap("been", true);
		addToStopWordMap("but", true);
		addToStopWordMap("by", true);
		addToStopWordMap("can", true);
		addToStopWordMap("cannot", true);
		addToStopWordMap("could", true);
		addToStopWordMap("dear", true);
		addToStopWordMap("did", true);
		addToStopWordMap("do", true);
		addToStopWordMap("does", true);
		addToStopWordMap("either", true);
		addToStopWordMap("else", true);
		addToStopWordMap("ever", true);
		addToStopWordMap("every", true);
		addToStopWordMap("for", true);
		addToStopWordMap("from", true);
		addToStopWordMap("get", true);
		addToStopWordMap("got", true);
		addToStopWordMap("had", true);
		addToStopWordMap("has", true);
		addToStopWordMap("have", true);
		addToStopWordMap("he", true);
		addToStopWordMap("her", true);
		addToStopWordMap("hers", true);
		addToStopWordMap("him", true);
		addToStopWordMap("his", true);
		addToStopWordMap("how", true);
		addToStopWordMap("however", true);
		addToStopWordMap("i", true);
		addToStopWordMap("if", true);
		addToStopWordMap("in", true);
		addToStopWordMap("into", true);
		addToStopWordMap("is", true);
		addToStopWordMap("it", true);
		addToStopWordMap("its", true);
		addToStopWordMap("just", true);
		addToStopWordMap("least", true);
		addToStopWordMap("let", true);
		addToStopWordMap("like", true);
		addToStopWordMap("likely", true);
		addToStopWordMap("may", true);
		addToStopWordMap("me", true);
		addToStopWordMap("might", true);
		addToStopWordMap("most", true);
		addToStopWordMap("must", true);
		addToStopWordMap("my", true);
		addToStopWordMap("neither", true);
		addToStopWordMap("no", true);
		addToStopWordMap("nor", true);
		addToStopWordMap("not", true);
		addToStopWordMap("of", true);
		addToStopWordMap("off", true);
		addToStopWordMap("often", true);
		addToStopWordMap("on", true);
		addToStopWordMap("only", true);
		addToStopWordMap("or", true);
		addToStopWordMap("other", true);
		addToStopWordMap("our", true);
		addToStopWordMap("own", true);
		addToStopWordMap("rather", true);
		addToStopWordMap("said", true);
		addToStopWordMap("say", true);
		addToStopWordMap("says", true);
		addToStopWordMap("she", true);
		addToStopWordMap("should", true);
		addToStopWordMap("since", true);
		addToStopWordMap("so", true);
		addToStopWordMap("some", true);
		addToStopWordMap("than", true);
		addToStopWordMap("that", true);
		addToStopWordMap("the", true);
		addToStopWordMap("their", true);
		addToStopWordMap("them", true);
		addToStopWordMap("then", true);
		addToStopWordMap("there", true);
		addToStopWordMap("these", true);
		addToStopWordMap("they", true);
		addToStopWordMap("this", true);
		addToStopWordMap("tis", true);
		addToStopWordMap("to", true);
		addToStopWordMap("too", true);
		addToStopWordMap("twas", true);
		addToStopWordMap("us", true);
		addToStopWordMap("wants", true);
		addToStopWordMap("was", true);
		addToStopWordMap("we", true);
		addToStopWordMap("were", true);
		addToStopWordMap("what", true);
		addToStopWordMap("when", true);
		addToStopWordMap("where", true);
		addToStopWordMap("which", true);
		addToStopWordMap("while", true);
		addToStopWordMap("who", true);
		addToStopWordMap("whom", true);
		addToStopWordMap("why", true);
		addToStopWordMap("will", true);
		addToStopWordMap("with", true);
		addToStopWordMap("would", true);
		addToStopWordMap("yet", true);
		addToStopWordMap("you", true);
		addToStopWordMap("your", true);
		
		// Accents - Reference - https://raw.githubusercontent.com/lmjabreu/solr-conftemplate/master/mapping-ISOLatin1Accent.txt
		addToAccentsMap('\u00C0', "A");
		addToAccentsMap('\u00C1', "A");
		addToAccentsMap('\u00C2', "A");
		addToAccentsMap('\u00C3', "A");
		addToAccentsMap('\u00C4', "A");
		addToAccentsMap('\u00C5', "A");
		addToAccentsMap('\u00C6', "AE");
		addToAccentsMap('\u00C7', "C");
		addToAccentsMap('\u00C8', "E");
		addToAccentsMap('\u00C9', "E");
		addToAccentsMap('\u00CA', "E");
		addToAccentsMap('\u00CB', "E");
		addToAccentsMap('\u00CC', "I");
		addToAccentsMap('\u00CD', "I");
		addToAccentsMap('\u00CE', "I");
		addToAccentsMap('\u00CF', "I");
		addToAccentsMap('\u0132', "IJ");
		addToAccentsMap('\u00D0', "D");
		addToAccentsMap('\u00D1', "N");
		addToAccentsMap('\u00D2', "O");
		addToAccentsMap('\u00D3', "O");
		addToAccentsMap('\u00D4', "O");
		addToAccentsMap('\u00D5', "O");
		addToAccentsMap('\u00D6', "O");
		addToAccentsMap('\u00D8', "O");
		addToAccentsMap('\u0152', "OE");
		addToAccentsMap('\u00DE', "TH");
		addToAccentsMap('\u00D9', "U");
		addToAccentsMap('\u00DA', "U");
		addToAccentsMap('\u00DB', "U");
		addToAccentsMap('\u00DC', "U");
		addToAccentsMap('\u00DD', "Y");
		addToAccentsMap('\u0178', "Y");
		addToAccentsMap('\u00E0', "a");
		addToAccentsMap('\u00E1', "a");
		addToAccentsMap('\u00E2', "a");
		addToAccentsMap('\u00E3', "a");
		addToAccentsMap('\u00E4', "a");
		addToAccentsMap('\u00E5', "a");
		addToAccentsMap('\u00E6', "ae");
		addToAccentsMap('\u00E7', "c");
		addToAccentsMap('\u00E8', "e");
		addToAccentsMap('\u00E9', "e");
		addToAccentsMap('\u00EA', "e");
		addToAccentsMap('\u00EB', "e");
		addToAccentsMap('\u00EC', "i");
		addToAccentsMap('\u00ED', "i");
		addToAccentsMap('\u00EE', "i");
		addToAccentsMap('\u00EF', "i");
		addToAccentsMap('\u0133', "ij");
		addToAccentsMap('\u00F0', "d");
		addToAccentsMap('\u00F1', "n");
		addToAccentsMap('\u00F2', "o");
		addToAccentsMap('\u00F3', "o");
		addToAccentsMap('\u00F4', "o");
		addToAccentsMap('\u00F5', "o");
		addToAccentsMap('\u00F6', "o");
		addToAccentsMap('\u00F8', "o");
		addToAccentsMap('\u0153', "oe");
		addToAccentsMap('\u00DF', "ss");
		addToAccentsMap('\u00FE', "th");
		addToAccentsMap('\u00F9', "u");
		addToAccentsMap('\u00FA', "u");
		addToAccentsMap('\u00FB', "u");
		addToAccentsMap('\u00FC', "u");
		addToAccentsMap('\u00FD', "y");
		addToAccentsMap('\u00FF', "y");
		addToAccentsMap('\uFB00', "ff");
		addToAccentsMap('\uFB01', "fi");
		addToAccentsMap('\uFB02', "fl");
		addToAccentsMap('\uFB03', "ffi");
		addToAccentsMap('\uFB04', "ffl");
		addToAccentsMap('\uFB05', "ft");
		addToAccentsMap('\uFB06', "st");
	}
}