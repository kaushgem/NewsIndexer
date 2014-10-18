/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.buffalo.cse.util.SortMapUsingValueCompare;

/**
 * @author kaush
 *
 */
public class RankingHelper {

	public static Map<Integer, Float> sortUsingRank(HashMap<Integer, Float> rankedDocIDs)
	{
		SortMapUsingValueCompare sort =  new SortMapUsingValueCompare(rankedDocIDs);
		TreeMap<Integer, Float> sortedRankedDocIDs = new TreeMap<Integer, Float>(sort);
		sortedRankedDocIDs.putAll(rankedDocIDs);
		return sortedRankedDocIDs;
	}
}

