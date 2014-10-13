package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

import edu.buffalo.cse.util.TrieNode;

/**
 * @author nikhillo Class that emulates reading data back from a written index
 */
public class IndexReader {
	/**
	 * Default constructor
	 * 
	 * @param indexDir
	 *            : The root directory from which the index is to be read. This
	 *            will be exactly the same directory as passed on IndexWriter.
	 *            In case you make sub directories etc., you will have to handle
	 *            it accordingly.
	 * @param type
	 *            The {@link IndexType} to read from
	 */

	IndexType type = null;
	String indexDirectory = null;

	public IndexReader(String indexDir, IndexType type) {
		this.type = type;
		this.indexDirectory = indexDir;
		boolean isInmemory = IndexWriter.fileIDLookup.size() > 0;
		//isInmemory = false;
		if (!isInmemory) {
			try {
				readIndex();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get total number of terms from the "key" dictionary associated with this
	 * index. A postings list is always created against the "key" dictionary
	 * 
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		int size = 0;
		switch (type) {
		case TERM:
			size = IndexWriter.termIndex.size();
			break;
		case CATEGORY:
			size = IndexWriter.categoryIndex.size();
			break;
		case AUTHOR:
			size = IndexWriter.authorIndex.size();
			break;
		case PLACE:
			size = IndexWriter.placeIndex.size();
			break;
		default:
			break;
		}
		return size;
	}

	/**
	 * Get total number of terms from the "value" dictionary associated with
	 * this index. A postings list is always created with the "value" dictionary
	 * 
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		return IndexWriter.fileIDLookup.size();
	}

	/**
	 * Method to get the postings for a given term. You can assume that the raw
	 * string that is used to query would be passed through the same Analyzer as
	 * the original field would have been.
	 * 
	 * @param term
	 *            : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the
	 *         number of occurrences as values if the given term was found, null
	 *         otherwise.
	 * @throws IndexerException 
	 */
	public Map<String, Integer> getPostings(String term) {
		
		HashMap<String, Integer> indexPostings = null;

		if(null!=term)
		{
			switch (type) {
			case TERM:
				//indexPostings = IndexWriter.termIndex.get(term);
				if(null!=IndexWriter.termIndex.get(term))
					indexPostings = generatePostings(IndexWriter.termIndex.get(term).getTermFreqPositionIndexDTO());
				break;
			case CATEGORY:
				if(null!=IndexWriter.categoryIndex.get(term))
					indexPostings = generatePostings(IndexWriter.categoryIndex.get(term).getTermFreqPositionIndexDTO());
				break;
			case AUTHOR:
				if(null!=IndexWriter.authorIndex.get(term))
					indexPostings = generatePostings(IndexWriter.authorIndex.get(term).getTermFreqPositionIndexDTO());
				break;
			case PLACE:
				if(null!=IndexWriter.placeIndex.get(term))
					indexPostings = generatePostings(IndexWriter.placeIndex.get(term).getTermFreqPositionIndexDTO());
				break;
			default:
				break;
			}
		}

		return indexPostings;
	}


	private HashMap<String, Integer> generatePostings(HashMap<Integer, TermFreqPositionIndexDTO> postings) {

		HashMap<String, Integer> indexPostings = new HashMap<String, Integer>();
		for (Entry<Integer, TermFreqPositionIndexDTO> etr : postings.entrySet()) {
			String docID = IndexWriter.fileIDLookup.get(etr.getKey());
			int termFreq = etr.getValue().getTermFreq();
			indexPostings.put(docID, termFreq);
		}
		return indexPostings;
	}



	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * 
	 * @param k
	 *            : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values null
	 *         for invalid k values
	 */
	public List<String> getTopK(int k) {

		if (k <= 0)
			return null;
		List<TrieNode> topk_nodes = new ArrayList<TrieNode>();
		List<String> topKwords = new ArrayList<String>();

		int distinct_word_count = 0;
		int total_word_count = 0;

		for (int p = 0; p < k; p++) {
			topk_nodes.add(IndexWriter.root);
		}

		IndexWriter.root.GetTopCounts(topk_nodes, distinct_word_count,
				total_word_count);
		Collections.sort(topk_nodes);

		for (TrieNode t : topk_nodes) {
			topKwords.add(t.toString());
		}

		Collections.reverse(topKwords);
		return topKwords;
	}

	/**
	 * Method to implement a simple boolean AND query on the given index
	 * 
	 * @param terms
	 *            The ordered set of terms to AND, similar to getPostings() the
	 *            terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key and
	 *         number of occurrences as the value, the number of occurrences
	 *         would be the sum of occurrences for each participating term.
	 *         return null if the given term list returns no results BONUS ONLY
	 * @throws IndexerException 
	 */
	public Map<String, Integer> query(String... terms) {
		// TODO : BONUS ONLY

		List<Map<String, Integer>> hashMaps = new ArrayList<Map<String, Integer>>();
		for (String term : terms) {
			Map<String, Integer> map = getPostings(term);
			if (map != null) {
				hashMaps.add(getPostings(term));
			}

		}
		HashMap<String, Integer> containter = new HashMap<String, Integer>(
				hashMaps.get(0));

		int size = hashMaps.size();
		String key;
		int value;
		for (int i = 1; i < size; i++) {
			containter.keySet().retainAll(hashMaps.get(i).keySet());
		}

		for (int i = 1; i < size; i++) {
			for (Entry<String, Integer> indexEntry : hashMaps.get(i).entrySet()) {
				key = indexEntry.getKey();
				value = indexEntry.getValue();

				if (containter.containsKey(key)) {
					containter.put(key, containter.get(key) + value);
				}
			}
		}

		if (containter.isEmpty()) {
			return null;
		}
		//for (Entry<String, Integer> entry : containter.entrySet()) 
		//	System.out.println(entry.getKey() + " " + entry.getValue());
		return containter;
	}



	private void readIndex() throws IndexerException {
		try {
			String termIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.TERM.toString() + ".txt";
			IndexWriter.termIndex = fileToIndex(termIndexFilepath);
			String categoryIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.CATEGORY.toString() + ".txt";
			IndexWriter.categoryIndex = fileToIndex(categoryIndexFilepath);
			String authorIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.AUTHOR.toString() + ".txt";
			IndexWriter.authorIndex = fileToIndex(authorIndexFilepath);
			String placeIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.PLACE.toString() + ".txt";
			IndexWriter.placeIndex = fileToIndex(placeIndexFilepath);
			String fileIDLookupFilepath = this.indexDirectory + File.separator
					+ "FILEID" + ".txt";
			IndexWriter.fileIDLookup = fileToLookup(fileIDLookupFilepath);

			/*
			System.out.println("Term Size : " + IndexWriter.termIndex.size());
			System.out.println("Cate Size : " + IndexWriter.categoryIndex.size());
			System.out.println("Auth Size : " + IndexWriter.authorIndex.size());
			System.out.println("Plac Size : " + IndexWriter.placeIndex.size());
			System.out.println("File Size : " + IndexWriter.fileIDLookup.size());
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, IDFPostingDTO> fileToIndex(
			String path) throws IndexerException {

		HashMap<String, IDFPostingDTO> map = null;

		try {
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(fin);
			map = (HashMap<String, IDFPostingDTO>) oin.readObject();
			//System.out.println("a " + map.size());
			oin.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static HashMap<Integer, String> fileToLookup(
			String path) throws IndexerException {

		HashMap<Integer, String> map = null;

		try {
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(fin);
			map = (HashMap<Integer, String>) oin.readObject();
			//System.out.println("a " + map.size());
			oin.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
		return map;
	}

}
