/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.*;

import edu.buffalo.cse.irf14.index.IndexesDTO;

/**
 * @author Sathish
 *
 */
public abstract class Ranking
{
	public IndexesDTO indexes;
	public abstract HashMap<Integer,Float> getRankedDocIDs(HashMap<String,String> queryBagWords, ArrayList<Integer> matchingDocIDs);
}
