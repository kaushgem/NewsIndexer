package edu.buffalo.cse.irf14.analysis;

import java.util.*;


public class TokenFilterConstants {

	static Map<String,String> contractions = new HashMap<String,String>();

	public TokenFilterConstants()
	{
		//Common English Contractions

		//addToMap("ain't", "am not / are not / is not / has not / have not");
		addToMap("ain't", "am not");
		//addToMap("aren't", "are not / am not");
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
		//addToMap("he'd", "he had / he would");
		addToMap("he'd", "he had");
		addToMap("he'd've", "he would have");
		//addToMap("he'll", "he shall / he will");
		addToMap("he'll", "he will");
		//addToMap("he's", "he has / he is");
		addToMap("he's", "he is");
		//addToMap("how'd", "how did / how would");
		addToMap("how'd", "how did");
		addToMap("how'll", "how will");
		//addToMap("how's", "how has / how is / how does");
		addToMap("how's", "how is");
		//addToMap("I'd", "I had / I would");
		addToMap("I'd", "I had");
		addToMap("I'd've", "I would have");
		//addToMap("I'll", "I shall / I will");
		addToMap("I'll", "I will");
		addToMap("I'm", "I am");
		addToMap("I've", "I have");
		addToMap("isn't", "is not");
		//addToMap("it'd", "it had / it would");
		addToMap("it'd", "it had");
		addToMap("it'd've", "it would have");
		//addToMap("it'll", "it shall / it will");
		addToMap("it'll", "it will");
		//addToMap("it's", "it has / it is");
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
		//addToMap("she'd", "she had / she would");
		addToMap("she'd", "she had");
		addToMap("she'd've", "she would have");
		//addToMap("she'll", "she shall / she will");
		addToMap("she'll", "she will");
		//addToMap("she's", "she has / she is");
		addToMap("she's", "she is");
		addToMap("should've", "should have");
		addToMap("shouldn't", "should not");
		addToMap("shouldn't've", "should not have");
		//addToMap("that's", "that has / that is");
		addToMap("that's", "that is");
		//addToMap("there'd", "there had / there would");
		addToMap("there'd", "there had");
		addToMap("there'd've", "there would have");
		addToMap("there're", "there are");
		//addToMap("there's", "there has / there is");
		addToMap("there's", "there is");
		//addToMap("they'd", "they had / they would");
		addToMap("they'd", "they had");
		addToMap("they'd've", "they would have");
		//addToMap("they'll", "they shall / they will");
		addToMap("they'll", "they will");
		addToMap("they're", "they are");
		addToMap("they've", "they have");
		addToMap("wasn't", "was not");
		//addToMap("we'd", "we had / we would");
		addToMap("we'd", "we had");
		addToMap("we'd've", "we would have");
		addToMap("we'll", "we will");
		addToMap("we're", "we are");
		addToMap("we've", "we have");
		addToMap("weren't", "were not");
		//addToMap("what'll", "what shall / what will");
		addToMap("what'll", "what will");
		addToMap("what're", "what are");
		//addToMap("what's", "what has / what is / what does");
		addToMap("what's", "what is");
		addToMap("what've", "what have");
		//addToMap("when's", "when has / when is");
		addToMap("when's", "when is");
		addToMap("where'd", "where did");
		//addToMap("where's", "where has / where is");
		addToMap("where's", "where is");
		addToMap("where've", "where have");
		//addToMap("who'd", "who would / who had");
		addToMap("who'd", "who had");
		//addToMap("who'll", "who shall / who will");
		addToMap("who'll", "who will");
		addToMap("who're", "who are");
		//addToMap("who's", "who has / who is");
		addToMap("who's", "who is");
		addToMap("who've", "who have");
		addToMap("why'll", "why will");
		addToMap("why're", "why are");
		//addToMap("why's", "why has / why is");
		addToMap("why's", "why is");
		addToMap("won't", "will not");
		addToMap("would've", "would have");
		addToMap("wouldn't", "would not");
		addToMap("wouldn't've", "would not have");
		addToMap("y'all", "you all");
		//addToMap("y'all'd've", "you all should have / you all could have / you all would have");
		addToMap("y'all'd've", "you all should have");
		//addToMap("you'd", "you had / you would");
		addToMap("you'd", "you had");
		addToMap("you'd've", "you would have");
		//addToMap("you'll", "you shall / you will");
		addToMap("you'll", "you will");
		addToMap("you're", "you are");
		addToMap("you've", "you have");
		addToMap("'em", "them");
	}
	
	static void addToMap(String str1, String str2)
	{
		contractions.put(str1, str2);
	}
	
}
