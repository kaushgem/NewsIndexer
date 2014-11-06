/**

 * 
 */

package edu.buffalo.cse.irf14.query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.index.IndicesDTO;
/**
 * @author Sathish
 *
 */

// Reference: http://raelcunha.com/spell-correct.php
// Reference: http://norvig.com/spell-correct.html

public class SpellChecker {

	private final HashMap<String, Integer> nWords = new HashMap<String, Integer>();

	public SpellChecker(IndicesDTO indices) {
		fillWords(indices.termIndex);
		fillWords(indices.authorIndex);
		fillWords(indices.categoryIndex);
		fillWords(indices.placeIndex);


	}

	private void fillWords(HashMap<String,HashMap<Integer,String>> index)
	{
		for(Entry<String,HashMap<Integer,String>> eSet:index.entrySet() )
		{
			String word = eSet.getKey();
			int count = 0;
			for ( Entry<Integer,String> docDetail : eSet.getValue().entrySet())
			{
				String postingsList = docDetail.getValue().split(":")[0];
				count +=Integer.parseInt(postingsList);
			}
			nWords.put(word, count);
		}
	}

	private final ArrayList<String> edits(String word) {
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0; i < word.length(); ++i) result.add(word.substring(0, i) + word.substring(i+1));
		for(int i=0; i < word.length()-1; ++i) result.add(word.substring(0, i) + word.substring(i+1, i+2) + word.substring(i, i+1) + word.substring(i+2));
		for(int i=0; i < word.length(); ++i) for(char c='a'; c <= 'z'; ++c) result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i+1));
		for(int i=0; i <= word.length(); ++i) for(char c='a'; c <= 'z'; ++c) result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
		return result;
	}

	public final List<String> correct(String word) {

		ArrayList<String> suggestionsList = new ArrayList<String>();
		if(nWords.containsKey(word))
		{
			suggestionsList.add(word);
			return suggestionsList;

		}
		ArrayList<String> list = edits(word);
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();

		// check for all edited words
		for(String s : list) if(nWords.containsKey(s)) candidates.put(nWords.get(s),s);

		if(candidates.size() > 0) 
		{
			// tree map for sorting
			Map<Integer, String> map = new TreeMap<Integer, String>(candidates);
			for(Entry<Integer,String> suggestion :map.entrySet())
			{
				//System.out.println(suggestion.getValue() + " :" + suggestion.getKey());
				suggestionsList.add(suggestion.getValue());
			}
			
			// change the sorting order ascending - > descending
			Collections.reverse(suggestionsList);
			return suggestionsList;
		}

		// if none of the edited word is present in the index
		// go one level down.
		// check all the edited words for each edit word => O(n^2)
		for(String s : list) for(String w : edits(s)) if(nWords.containsKey(w)) candidates.put(nWords.get(w),w);

		if(candidates.size() > 0) 

		{
			Map<Integer, String> map = new TreeMap<Integer, String>(candidates);
			for(Entry<Integer,String> suggestion :map.entrySet())
			{
				//System.out.println(suggestion.getValue() + " :" + suggestion.getKey());
				suggestionsList.add(suggestion.getValue());
			}
			Collections.reverse(suggestionsList);
			return suggestionsList;
		}

		suggestionsList.add(word);
		return suggestionsList;
	}
}
