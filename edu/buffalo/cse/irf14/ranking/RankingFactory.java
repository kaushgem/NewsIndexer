/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import edu.buffalo.cse.irf14.SearchRunner.ScoringModel;
import edu.buffalo.cse.irf14.index.IndicesDTO;

/**
 * @author Sathish
 *
 */
public class RankingFactory 
{
	
	public static Ranking getRankingInstance(ScoringModel scoringModel, IndicesDTO indices)
	{
		switch(scoringModel)
		{
		case OKAPI:
		{
			return new OKAPIRanking(indices);
		}
		case TFIDF:
		{
			return new TFIDFRanking(indices);
		}
		default:
		{
			return new TFIDFRanking(indices);
		}
		}
	}

}
