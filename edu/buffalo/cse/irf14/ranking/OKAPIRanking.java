/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.ArrayList;
import java.util.HashMap;

import edu.buffalo.cse.irf14.index.IndicesDTO;

/**
 * @author Sathish
 *
 */
public class OKAPIRanking extends Ranking {

	/**
	 * 
	 */
	public OKAPIRanking(IndicesDTO indexes) {

		this.indexes =  indexes;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.ranking.Ranking#getRankedDocIDs(java.util.HashMap, java.util.ArrayList)
	 */
	@Override
	public HashMap<Integer, Float> getRankedDocIDs(HashMap<String, String> queryBagWords,
			ArrayList<Integer> matchingDocIDs) {
		// TODO Auto-generated method stub
		return null;
	}

}
