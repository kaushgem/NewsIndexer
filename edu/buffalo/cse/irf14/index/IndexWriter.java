/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.util.TrieNode;

/**
 * @author nikhillo Class responsible for writing indexes to disk
 */

public class IndexWriter {
	/**
	 * Default constructor
	 * 
	 * @param indexDir
	 *            : The root directory to be sued for indexing
	 */

	public static boolean inMemory = false;
	String indexDirectory = null;

	// LookUp
	public static HashMap<Integer, String> fileIDLookup;

	//HashMap<word, IDFPostingDTO>
	//IDFPostingDTO = {idf, HashMap<docID, TermFreqPositionIndexDTO>}
	//TermFreqPositionIndexDTO = {freq, ArrayList<PositionIndex> }
	public static HashMap<String, IDFPostingDTO> termIndex;
	public static HashMap<String, IDFPostingDTO> categoryIndex;
	public static HashMap<String, IDFPostingDTO> authorIndex;
	public static HashMap<String, IDFPostingDTO> placeIndex;


	// get top K
	public static TrieNode root = new TrieNode(null, '?');

	public IndexWriter(String indexDir) {
		indexDirectory = indexDir;

		if (fileIDLookup == null)
			fileIDLookup = new HashMap<Integer, String>();

		if (termIndex == null)
			termIndex = new HashMap<String, IDFPostingDTO>();
		if (categoryIndex == null)
			categoryIndex = new HashMap<String, IDFPostingDTO>();
		if (authorIndex == null)
			authorIndex = new HashMap<String, IDFPostingDTO>();
		if (placeIndex == null)
			placeIndex = new HashMap<String, IDFPostingDTO>();
	}

	/**
	 * Method to add the given Document to the index This method should take
	 * care of reading the filed values, passing them through corresponding
	 * analyzers and then indexing the results for each indexable field within
	 * the document.
	 * 
	 * @param d
	 *            : The Document to be added
	 * @throws IndexerException
	 *             : In case any error occurs
	 */

	public void addDocument(Document d) throws IndexerException {
		inMemory = true;
		Tokenizer tokenizer = new Tokenizer();
		AnalyzerFactory analyzerFactoryObj = AnalyzerFactory.getInstance();
		Analyzer analyzerObj = null;
		String[] stringArray = null;
		try {
			// FileID Lookup
			String fileID = d.getField(FieldNames.FILEID)[0];
			int fileIDLookupIndex = fileIDLookup.size() + 1; 
			fileIDLookup.put(fileIDLookupIndex, fileID);

			// Iterate FieldNames
			for (FieldNames field : FieldNames.values()) {
				stringArray = d.getField(field);
				if (stringArray != null) {
					for (String s : stringArray) {
						if (s != null && !s.isEmpty()) {
							TokenStream tStream = tokenizer.consume(s);
							analyzerObj = analyzerFactoryObj.getAnalyzerForField(field, tStream);
							analyzerObj.increment();
							tStream.reset();

							switch (field) {
							case TITLE:
							case AUTHORORG:
							case NEWSDATE:
							case CONTENT:
								insertToIndex(tStream, fileIDLookupIndex, termIndex);
								calculateIDF(termIndex);
								break;
							case CATEGORY:
								insertToIndex(tStream, fileIDLookupIndex, categoryIndex);
								calculateIDF(categoryIndex);
								break;
							case AUTHOR:
								insertToIndex(tStream, fileIDLookupIndex, authorIndex);
								calculateIDF(authorIndex);
								break;
							case PLACE:
								insertToIndex(tStream, fileIDLookupIndex, placeIndex);
								calculateIDF(placeIndex);
								break;
							default:
								break;
							}
						}
					}// stringArray forLoop
				}
			}// FieldNames forLoop

		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}

	/**
	 * Method that indicates that all open resources must be closed and cleaned
	 * and that the entire indexing operation has been completed.
	 * 
	 * @throws IndexerException
	 *             : In case any error occurs
	 */
	public void close() throws IndexerException {
		//writeIndex();
		/*System.out.println("Term Size : " + termIndex.size());
		System.out.println("Cate Size : " + categoryIndex.size());
		System.out.println("Auth Size : " + authorIndex.size());
		System.out.println("Plac Size : " + placeIndex.size());
		System.out.println("File Size : " + fileIDLookup.size());*/
	}





	/*public HashMap<String, HashMap<String, Integer>> getInvertedIndex() {
		if (invertedIndex == null) {
			ComputeInvertedIndex();
		}
		return invertedIndex;
	}

	public void ComputeInvertedIndex() {
		invertedIndex = new HashMap<String, HashMap<String, Integer>>();
	}*/

	public void insertToIndex(TokenStream tStream, int fileIDLookupIndex,
			HashMap<String, IDFPostingDTO> indexMap)
					throws IndexerException {

		IDFPostingDTO idfObj;
		Integer posiIndex = new Integer(0);

		//HashMap<String, Integer> indexPostings = null;

		try {
			while (tStream.hasNext()) {
				String token = tStream.next().toString();
				if (token == null || token.isEmpty())
					continue;
				root.AddWord(token, 0);

				// indexPostings HashMap check
				idfObj = indexMap.get(token);

				if (null == idfObj) {
					idfObj = new IDFPostingDTO();
					//idf     idfObj.setIdf(0);
					HashMap<Integer, TermFreqPositionIndexDTO> map = new HashMap<Integer, TermFreqPositionIndexDTO>();
					TermFreqPositionIndexDTO tfObj = new TermFreqPositionIndexDTO();
					tfObj.setTermFreq(1);
					//positional index
					ArrayList<Integer> positionIndex = new ArrayList<Integer>();
					positionIndex.add(posiIndex);
					tfObj.positionIndex = positionIndex;
					map.put(fileIDLookupIndex, tfObj);
					idfObj.setTermFreqPositionIndexDTO(map);
					indexMap.put(token, idfObj);
				}
				else		 // indexPostings map already exists
				{
					TermFreqPositionIndexDTO tfObj = idfObj.getTermFreqPositionIndexDTO().get(fileIDLookupIndex);
					if(null == tfObj){
						//idf     idfObj.setIdf(0);
						tfObj = new TermFreqPositionIndexDTO();
						tfObj.setTermFreq(1);
						// positional index
						ArrayList<Integer> positionIndex = new ArrayList<Integer>();
						positionIndex.add(posiIndex);
						tfObj.positionIndex = positionIndex;
						idfObj.getTermFreqPositionIndexDTO().put(fileIDLookupIndex, tfObj);
					}
					else
					{
						tfObj.positionIndex.add(++posiIndex);
						tfObj.setTermFreq(tfObj.getTermFreq()+1);
						idfObj.getTermFreqPositionIndexDTO().put(fileIDLookupIndex, tfObj);
					}
				}


			}// while
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}




	public void calculateIDF(HashMap<String, IDFPostingDTO> indexMap) {

		int noOfDocsTermOccurs = 0, totalDocs = 190;
		float idf = 0;

		for(Entry<String, IDFPostingDTO> indexMapIte : indexMap.entrySet()){
			noOfDocsTermOccurs = indexMapIte.getValue().getTermFreqPositionIndexDTO().size();
			idf = (float)(Math.log(totalDocs/(noOfDocsTermOccurs+1)) + 1.0);
			indexMapIte.getValue().setIdf(idf);
			System.out.println(indexMapIte.getKey()+" : "+idf);
		}
	}

	

	public void writeIndex() throws IndexerException {
		try {
			String termIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.TERM.toString() + ".txt";
			indexToFile(termIndexFilepath, termIndex);
			String categoryIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.CATEGORY.toString() + ".txt";
			indexToFile(categoryIndexFilepath, categoryIndex);
			String authorIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.AUTHOR.toString() + ".txt";
			indexToFile(authorIndexFilepath, authorIndex);
			String placeIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.PLACE.toString() + ".txt";
			indexToFile(placeIndexFilepath, placeIndex);

			String fileIDLookupFilepath = this.indexDirectory + File.separator
					+ "FILEID" + ".txt";
			lookupToFile(fileIDLookupFilepath, fileIDLookup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}

	public void indexToFile(String path,
			HashMap<String, IDFPostingDTO> indexMap)
					throws IndexerException {
		try {
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(indexMap);
			oout.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}

	public void lookupToFile(String path,
			HashMap<Integer, String> indexMap)
					throws IndexerException {
		try {
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(indexMap);
			oout.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}
}
