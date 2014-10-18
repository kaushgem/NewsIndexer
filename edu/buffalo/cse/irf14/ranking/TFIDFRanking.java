/**
 * 
 */
package edu.buffalo.cse.irf14.ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;
import edu.buffalo.cse.util.RankCalc;

/**
 * @author Sathish
 *
 */
public class TFIDFRanking extends Ranking {


	public TFIDFRanking(IndicesDTO indices) {
		this.indices =  indices;
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
	public HashMap<Integer, Float> getRankedDocIDs(
			HashMap<String, IndexType> queryBagWords,
			ArrayList<Integer> matchingDocIDs) {

		HashMap<Integer, Float> rankedDocIDs = new HashMap<Integer, Float>();

		// Iterate for queryTerm in the Query

		for (Entry<String, IndexType> queryTerm : queryBagWords.entrySet()) {
			String term = queryTerm.getKey();
			IndexType type = queryTerm.getValue();

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

				String[] str = mapItr.getValue().split(":");
				int tf = Integer.parseInt(str[0]);
				float score = RankCalc.calculateTFIDF(tf, idf);

				if(rankedDocIDs.get(docID)==null){
					rankedDocIDs.put(docID, score);
				}
				else{
					score += rankedDocIDs.get(docID);
					rankedDocIDs.put(docID, score);
				}
			}
		}

		return rankedDocIDs;
	}


	

	

	// term, IndexType

//	public HashMap<Float, Integer> getRankedDocs(HashMap <String, IndexType> query, ArrayList<Integer> docIDs){
//
//		HashMap<Float, Integer> rankedDocs = new HashMap<Float, Integer>();
//		HashMap<Integer, HashMap<String, Float>> fwdIndex = new HashMap<Integer, HashMap<String, Float>>();
//
//		// Iterate for queryTerm in the Query
//
//		for (Entry<String, IndexType> queryTerm : query.entrySet()) {
//			String term = queryTerm.getKey();
//			IndexType type = queryTerm.getValue();
//
//			// Get the IndexMap corresponding to the type
//			HashMap<String, HashMap<Integer, String>> indexMap = getIndexMap(type);
//
//			HashMap<Integer, String> postingsMap = null;
//			if(indexMap.get(term) != null){
//				postingsMap = indexMap.get(term);
//			}
//
//			// Calculate IDF
//			int totalDocs = indices.docIDLookup.size();
//			int noOfDocsTermOccurs = postingsMap.size();
//			float idf = (float)(Math.log(totalDocs/noOfDocsTermOccurs));
//
//			for (Entry<Integer, String> mapItr : postingsMap.entrySet()) {
//
//				Integer docID = mapItr.getKey();
//
//				// Check for only the DocIDs received after Query evaluation
//				if(!docIDs.contains(docID)) { continue; }
//
//				String[] str = mapItr.getValue().split(":");
//				int tf = Integer.parseInt(str[0]);
//				float weight = tf+idf; 
//
//				HashMap<String, Float> fwdIndexInner = null;
//
//				if(fwdIndex.get(docID)==null)
//				{
//					fwdIndexInner = new HashMap<String, Float>();
//					fwdIndexInner.put(term, weight);
//				}
//				else
//				{
//					fwdIndexInner = fwdIndex.get(docID);
//					fwdIndexInner.put(term, weight);
//				}
//
//				fwdIndex.put(docID, fwdIndexInner);
//			}
//		}
//
//
//		//Using fwdIndex calculate total score => rankedDocs
//
//		return rankedDocs;
//	}
	
////////////////////////////////////////////////////////

//	public ArrayList<Integer> docIDSearch(IndexType type, String query){
//		ArrayList<Integer> docIDList = null;
//		if(null!=term)
//		{
//			switch (type) {
//			case TERM:
//				if(null!=indices.termIndex.get(term))
//					docIDList = generateFwdIndex(indices.termIndex.get(term));
//				break;
//			case CATEGORY:
//				if(null!=indices.categoryIndex.get(term))
//					docIDList = generateFwdIndex(indices.categoryIndex.get(term));
//				break;
//			case AUTHOR:
//				if(null!=indices.authorIndex.get(term))
//					docIDList = generateFwdIndex(indices.authorIndex.get(term));
//				break;
//			case PLACE:
//				if(null!=indices.placeIndex.get(term))
//					docIDList = generateFwdIndex(indices.placeIndex.get(term));
//				break;
//			default:
//				break;
//			}
//		}
//
//	}
//
//
//	private ArrayList<Integer> generateFwdIndex(HashMap<Integer, String> postings) {
//
//		ArrayList<Integer> arrLst = new ArrayList<Integer>();
//
//		for (Entry<Integer, String> eItr : postings.entrySet()) {
//			arrLst.add(eItr.getKey());
//		}
//		return arrLst;
//	}

//	{
//		String[] q = query.split(" ");
//		for(String term:q)
//		{
//			if(null!=indices.authorIndex.get(term))
//				docIDList = generateDocIDArray(indices.authorIndex.get(term));
//			HashMap<Integer, String> map = new HashMap<Integer, String>();
//		}
//		return null;
//	}


	
}
