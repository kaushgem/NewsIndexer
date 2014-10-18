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
public abstract class Ranking
{
	public IndicesDTO indices;
	public abstract HashMap<Integer,Float> getRankedDocIDs(HashMap<String,IndexType> queryBagWords, ArrayList<Integer> matchingDocIDs);

}
