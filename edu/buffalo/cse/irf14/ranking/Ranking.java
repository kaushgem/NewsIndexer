/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.ArrayList;
import java.util.Map;

import edu.buffalo.cse.irf14.DTO.QueryInfoDTO;
import edu.buffalo.cse.irf14.index.IndicesDTO;

/**
 * @author Sathish
 *
 */
public abstract class Ranking
{
	public IndicesDTO indices;
	public abstract Map<Integer,Float> getRankedDocIDs(ArrayList<QueryInfoDTO> queryBagWords, ArrayList<Integer> matchingDocIDs);
}
