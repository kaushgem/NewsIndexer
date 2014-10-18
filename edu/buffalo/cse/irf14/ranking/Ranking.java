/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.*;

import edu.buffalo.cse.irf14.index.IndicesDTO;

/**
 * @author Sathish
 *
 */
public abstract class Ranking
{
	public IndicesDTO indexes;
	public abstract HashMap<Integer,Float> getRankedDocIDs(HashMap<String,String> queryBagWords, ArrayList<Integer> matchingDocIDs);
}
