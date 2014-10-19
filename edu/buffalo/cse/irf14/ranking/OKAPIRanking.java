/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.buffalo.cse.irf14.DTO.QueryInfoDTO;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;
import edu.buffalo.cse.util.RankCalc;

/**
 * @author Sathish
 *
 */
public class OKAPIRanking extends Ranking {

	public OKAPIRanking(IndicesDTO indices) {
		this.indices = indices;
	}

	private HashMap<String, HashMap<Integer, String>> getIndexMap(IndexType type)
	{
		switch (type) {
		case TERM:
			return indices.termIndex;
		case CATEGORY:
			return indices.categoryIndex;
		case AUTHOR:
			return indices.authorIndex;
		case PLACE:
			return indices.placeIndex;
		default:
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.ranking.Ranking#getRankedDocIDs(java.util.HashMap, java.util.ArrayList)
	 */
	@Override
	public Map<Integer, Float> getRankedDocIDs(ArrayList<QueryInfoDTO> queryBagWords,
			ArrayList<Integer> matchingDocIDs) {

		HashMap<Integer, Float> rankedDocIDs = new HashMap<Integer, Float>();
		float maxWeight = 0;

		// Iterate for queryTerm in the queryBagWords

		for(QueryInfoDTO queryDTOObj : queryBagWords)
		{
			String term = queryDTOObj.getQueryTerm();
			IndexType type = queryDTOObj.getType();
			int tfQ = queryDTOObj.getFreq();

			// Get the IndexMap corresponding to the type
			HashMap<String, HashMap<Integer, String>> indexMap = getIndexMap(type);

			HashMap<Integer, String> postingsMap = null;
			if(indexMap.get(term) != null){
				postingsMap = indexMap.get(term);
			}

			// Calculate IDF
			int totalDocs = indices.docIDLookup.size();
			int noOfDocsTermOccurs = postingsMap.size();
			float idf = RankCalc.calculateIDF(totalDocs, noOfDocsTermOccurs);

			for (Entry<Integer, String> mapItr : postingsMap.entrySet()) {

				Integer docID = mapItr.getKey();

				// Check for only the DocIDs received after Query evaluation
				if(!matchingDocIDs.contains(docID)) { continue; }

				// Calculate score
				String[] str = mapItr.getValue().split(":");
				int tf = Integer.parseInt(str[0]);
				int docLen = indices.docLength.get(docID);
				float aveDocLen = indices.getAverageDocLength();
				float score = RankCalc.calculateOkapi(tf, idf, docLen, aveDocLen, tfQ);

				//score = score/aveDocLen;
				//System.out.println("Weight = "+docID+":"+score);
				
				if(rankedDocIDs.get(docID)==null){
					rankedDocIDs.put(docID, score);
				}
				else{
					score += rankedDocIDs.get(docID);
					rankedDocIDs.put(docID, score);
				}
				
				if(maxWeight < score)
					maxWeight = score;
				
				System.out.println("Score = "+docID+":"+score);
			}
		}
		
		// Normalization:
		maxWeight++;
		for(Entry<Integer, Float> wt:rankedDocIDs.entrySet())
		{
			//System.out.println(wt.getValue());
			wt.setValue(wt.getValue()/maxWeight);
			//System.out.println(wt.getValue());
		}
		
		return RankingHelper.sortUsingRank(rankedDocIDs);
	}

}




//
//
//for (Entry<String, IndexType> queryTerm : queryBagWords.entrySet()) {
//	String term = queryTerm.getKey();
//	IndexType type = queryTerm.getValue();
//
//	// Get the IndexMap corresponding to the type
//	HashMap<String, HashMap<Integer, String>> indexMap = getIndexMap(type);
//
//	HashMap<Integer, String> postingsMap = null;
//	if(indexMap.get(term) != null){
//		postingsMap = indexMap.get(term);
//	}
//
//	// Calculate IDF
//	int totalDocs = indices.docIDLookup.size();
//	int noOfDocsTermOccurs = postingsMap.size();
//	float idf = RankCalc.calculateIDF(totalDocs, noOfDocsTermOccurs);
//
//	for (Entry<Integer, String> mapItr : postingsMap.entrySet()) {
//
//		Integer docID = mapItr.getKey();
//
//		// Check for only the DocIDs received after Query evaluation
//		if(!matchingDocIDs.contains(docID)) { continue; }
//
//		// Calculate score
//		String[] str = mapItr.getValue().split(":");
//		int tf = Integer.parseInt(str[0]);
//		int docLen = indices.docLength.get(docID);
//		float aveDocLen = indices.getAverageDocLength();
//		int tfQ = 0;
//
//		float score = RankCalc.calculateOkapi(tf, idf, docLen, aveDocLen, tfQ);
//
//		if(rankedDocIDs.get(docID)==null){
//			rankedDocIDs.put(docID, score);
//		}
//		else{
//			score += rankedDocIDs.get(docID);
//			rankedDocIDs.put(docID, score);
//		}
//	}
//}
//return rankedDocIDs;
//}
