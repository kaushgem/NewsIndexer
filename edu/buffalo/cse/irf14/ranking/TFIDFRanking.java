/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.ArrayList;
import java.util.HashMap;

import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;

/**
 * @author Sathish
 *
 */
public class TFIDFRanking extends Ranking {

	/**
	 * 
	 */
	public TFIDFRanking(IndicesDTO indexes) {
		this.indexes =  indexes;
	}

	@Override
	public HashMap<Integer, Float> getRankedDocIDs(
			HashMap<String, IndexType> queryBagWords,
			ArrayList<Integer> matchingDocIDs) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.ranking.Ranking#getRankedDocIDs(java.util.HashMap, java.util.ArrayList)
	 */
	

}
