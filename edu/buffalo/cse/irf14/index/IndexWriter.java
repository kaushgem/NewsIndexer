/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

	public static HashMap<String, HashMap<String, Integer>> invertedIndex;

	// LookUp
	public static HashMap<Integer, String> fileIDLookup;

	public static HashMap<String, HashMap<String, Integer>> termIndex;
	public static HashMap<String, HashMap<String, Integer>> categoryIndex;
	public static HashMap<String, HashMap<String, Integer>> authorIndex;
	public static HashMap<String, HashMap<String, Integer>> placeIndex;

	// get top K
	// get top K
	public static TrieNode root = new TrieNode(null, '?');
		
	
	public IndexWriter(String indexDir) {
		if (invertedIndex == null)
			invertedIndex = new HashMap<String, HashMap<String, Integer>>();
		if (fileIDLookup == null)
			fileIDLookup = new HashMap<Integer, String>();

		if (termIndex == null)
			termIndex = new HashMap<String, HashMap<String, Integer>>();
		if (categoryIndex == null)
			categoryIndex = new HashMap<String, HashMap<String, Integer>>();
		if (authorIndex == null)
			authorIndex = new HashMap<String, HashMap<String, Integer>>();
		if (placeIndex == null)
			placeIndex = new HashMap<String, HashMap<String, Integer>>();
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

		Tokenizer tokenizer = new Tokenizer();
		AnalyzerFactory analyzerFactoryObj = AnalyzerFactory.getInstance();
		Analyzer analyzerObj = null;
		String[] stringArray = null;
		try {
			// FileID Lookup
			String fileID = d.getField(FieldNames.FILEID)[0];
			fileIDLookup.put(fileIDLookup.size() + 1, fileID);

			// Iterate FieldNames
			for (FieldNames field : FieldNames.values()) {
				stringArray = d.getField(field);
				if (stringArray != null) {
					for (String s : stringArray) {
						if (s != null && !s.isEmpty()) {
							TokenStream tStream = tokenizer.consume(s);
							analyzerObj = analyzerFactoryObj
									.getAnalyzerForField(field, tStream);
							analyzerObj.increment();
							tStream.reset();
							switch(field)
							{
							case TITLE:
							case AUTHORORG:
							case NEWSDATE:
							case CONTENT:
								insertToIndex(tStream, fileID, termIndex);
								break;
							case CATEGORY:
								insertToIndex(tStream, fileID, categoryIndex);
								break;
							case AUTHOR:
								insertToIndex(tStream, fileID, authorIndex);
								break;
							case PLACE:
								insertToIndex(tStream, fileID, placeIndex);
								break;
							default:
								break;
							}
						}
					}// stringArray forLoop
				}
			}// FieldNames forLoop

			// System.out.println("File - "+d.getField(FieldNames.CATEGORY)[0]+" / "+
			// d.getField(FieldNames.FILEID)[0]);

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
		// fileWrite();
		// fileRead();
		 System.out.println("Term Size : "+ termIndex.size());
		 System.out.println("Cate Size : "+ categoryIndex.size());
		 System.out.println("Auth Size : "+ authorIndex.size());
		 System.out.println("Plac Size : "+ placeIndex.size());
		// TODO
	}

	public HashMap<String, HashMap<String, Integer>> getInvertedIndex() {
		if (invertedIndex == null) {
			ComputeInvertedIndex();
		}
		return invertedIndex;
	}

	public void ComputeInvertedIndex() {
		invertedIndex = new HashMap<String, HashMap<String, Integer>>();
	}
	
	public void insertToIndex(TokenStream tStream, String fileID, HashMap<String, HashMap<String, Integer>> indexMap) throws IndexerException {

		HashMap<String, Integer> indexPostings = null;

		try {
			while (tStream.hasNext()) {
				String token = tStream.next().toString();
				if (token == null || token.isEmpty())
					continue;
				root.AddWord(token,0);
				// indexPostings HashMap check
				indexPostings = indexMap.get(token);
				if (indexPostings == null) {
					indexPostings = new HashMap<String, Integer>();
					indexPostings.put(fileID, 1);
					indexMap.put(token, indexPostings);
				} else { // indexPostings map already exists
					if (indexPostings.get(fileID) == null) {
						indexPostings.put(fileID, 1);
					} else {
						indexPostings.put(fileID, indexPostings.get(fileID) + 1);
					}
				}
			}// while
		} catch (Exception e) {
			throw new IndexerException();
		}
	}

	public void fileWrite() {
		// System.out.println("file write");
		try {
			FileOutputStream fos = new FileOutputStream("hashmap.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(invertedIndex);
			oos.close();
			fos.close();
			// System.out.printf("Serialized HashMap data is saved in hashmap.ser");
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	public void fileRead() {
		// System.out.println("\n\n===============================");
		// System.out.println("Deserialize()");
		HashMap<Integer, String> map = null;
		try {
			FileInputStream fis = new FileInputStream("hashmap.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (HashMap) ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return;
		}
		// System.out.println("Deserialized HashMap..");
		// Display content using Iterator
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			// System.out.print("key: "+ mentry.getKey() + " & Value: ");
			// System.out.println(mentry.getValue());
		}
	}

}
