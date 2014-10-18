/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;
import edu.buffalo.cse.util.Utility;

/**
 * @author Kaushik
 *
 */
public class DocumentIDFilter {

	IndicesDTO indices;

	public DocumentIDFilter(IndicesDTO indices) {
		this.indices = indices;
	}


	/**
	 * @param type 
	 * @param term
	 * @return Map<Integer, String> ...  Map<DocID, TermFreq:PositionalIndex>
	 */
	public ArrayList<Integer> getDocIDArrayList(IndexType type, String term) {

		ArrayList<Integer> docIDList = null;

		if(null!=term)
		{
			switch (type) {
			case TERM:
				if(null!=indices.termIndex.get(term))
					docIDList = generateDocIDArray(indices.termIndex.get(term));
				break;
			case CATEGORY:
				if(null!=indices.categoryIndex.get(term))
					docIDList = generateDocIDArray(indices.categoryIndex.get(term));
				break;
			case AUTHOR:
				if(null!=indices.authorIndex.get(term))
					docIDList = generateDocIDArray(indices.authorIndex.get(term));
				break;
			case PLACE:
				if(null!=indices.placeIndex.get(term))
					docIDList = generateDocIDArray(indices.placeIndex.get(term));
				break;
			default:
				break;
			}
		}
		return docIDList;
	}

	
	
	
	private ArrayList<Integer> generateDocIDArray(HashMap<Integer, String> postings) {
		ArrayList<Integer> arrLst = new ArrayList<Integer>();
		for (Entry<Integer, String> eItr : postings.entrySet()) {
			arrLst.add(eItr.getKey());
		}
		return arrLst;
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



	public ArrayList<Integer> getDocIDArrayListForPhraseQueries(IndexType type, String query) {

		String[] queryTerms = query.split(" ");

		ArrayList<Integer> filteredDocIDs = new ArrayList<Integer>(); 
		HashMap<Integer, HashMap<String, ArrayList<Integer>>> fwdIndex = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();

		// Get the IndexMap corresponding to the type
		HashMap<String, HashMap<Integer, String>> indexMap = getIndexMap(type);

		for(String term:queryTerms)
		{
			HashMap<Integer, String> postingsMap = null;
			if(indexMap.get(term) != null){
				postingsMap = indexMap.get(term);
			}

			for (Entry<Integer, String> mapItr : postingsMap.entrySet()) {

				Integer docID = mapItr.getKey();

				String[] str = mapItr.getValue().split(":");
				String[] posiStr = str[1].split(",");
				ArrayList<Integer> positionalIndex = new ArrayList<Integer>();

				for(String s:posiStr)
				{
					int p = Integer.parseInt(s);
					positionalIndex.add(p);
				}

				HashMap<String, ArrayList<Integer>> fwdIndexInner = null;

				if(fwdIndex.get(docID)==null)
				{
					fwdIndexInner = new HashMap<String, ArrayList<Integer>>();
					fwdIndexInner.put(term, positionalIndex);
				}
				else
				{
					fwdIndexInner = fwdIndex.get(docID);
					fwdIndexInner.put(term, positionalIndex);
				}

				fwdIndex.put(docID, fwdIndexInner);
			}
		}


		// Iterate through fwdIndex and check for positions of the queryTerms

		for (Entry<Integer, HashMap<String, ArrayList<Integer>>> mapItr : fwdIndex.entrySet()) {

			HashMap<String, ArrayList<Integer>> fwdIndexInner = mapItr.getValue();
			if(fwdIndexInner.size() != queryTerms.length) { continue; }

			// Calculate size of value string by taking maximum position
			int maxPosition = 0;
			for(Entry<String, ArrayList<Integer>> innerItr : fwdIndexInner.entrySet())
			{
				ArrayList<Integer> positionalIndex = innerItr.getValue();
				int lastPosition = positionalIndex.get(positionalIndex.size()-1);
				if(maxPosition<lastPosition) maxPosition = lastPosition;
			}

			String[] value = new String[maxPosition];
			for(String str:value)
				str = "$";

			for(Entry<String, ArrayList<Integer>> innerItr : fwdIndexInner.entrySet())
			{
				String term = innerItr.getKey();
				ArrayList<Integer> positionalIndex = innerItr.getValue();

				while (positionalIndex.iterator().hasNext())
				{
					value[positionalIndex.iterator().next()] = term;
				}
			}

			String str = Utility.join(value, " ");
			if(str.contains(query))
				filteredDocIDs.add(mapItr.getKey());
		}
		return filteredDocIDs;
	}


}
