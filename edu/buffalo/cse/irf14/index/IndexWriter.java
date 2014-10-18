/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
	IndicesDTO indices;
	
	public IndexWriter(String indexDir) {
		indexDirectory = indexDir;
		indices = new IndicesDTO();
				
		
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
			int docIDLookupIndex = indices.docIDLookup.size() + 1; 
			indices.docIDLookup.put(docIDLookupIndex, fileID);
//			System.out.println(docIDLookupIndex+" :: "+fileID);

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
								insertToIndex(tStream, docIDLookupIndex, indices.termIndex);
								break;
							case CATEGORY:
								insertToIndex(tStream, docIDLookupIndex, indices.categoryIndex);
								break;
							case AUTHOR:
								insertToIndex(tStream, docIDLookupIndex, indices.authorIndex);
								break;
							case PLACE:
								insertToIndex(tStream, docIDLookupIndex, indices.placeIndex);
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
		writeIndex();
	}



	public void insertToIndex(TokenStream tStream, int docIDLookupIndex,
			HashMap<String, HashMap<Integer, String>> indexMap)
					throws IndexerException {

		int posiIndex = 1;

		HashMap<Integer, String> indexPostings = null;

		try {
			while (tStream.hasNext()) {
				String token = tStream.next().toString();
				if (token == null || token.isEmpty())
					continue;
				indices.root.AddWord(token, 0);

				// indexPostings HashMap doesn't exist
				indexPostings = indexMap.get(token);
				if (indexPostings == null) {
					indexPostings = new HashMap<Integer, String>();
					indexPostings.put(docIDLookupIndex, 1  +":"+  posiIndex++);
					indexMap.put(token, indexPostings);
				}
				else		// indexPostings map already exists 
				{ 			// docID doesn't exist
					if (indexPostings.get(docIDLookupIndex) == null)
					{
						indexPostings.put(docIDLookupIndex, 1  +":"+  posiIndex++);
						posiIndex++;
					}
					else	// docID doesn't exist - update term frequency and positional index
					{
						String[] str = indexPostings.get(docIDLookupIndex).split(":");
						Integer tf = Integer.parseInt(str[0]) +1;		// inc term freq

						String tfposiIndex = tf.toString() +":"+ str[1] +","+ posiIndex++ ;		// append positional index

						indexPostings.put(docIDLookupIndex, tfposiIndex);
					}
				}
			}// while
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}


	public void writeIndex() throws IndexerException {
		try {
			String termIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.TERM.toString() + ".txt";
			indexToFile(termIndexFilepath, indices.termIndex);
			String categoryIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.CATEGORY.toString() + ".txt";
			indexToFile(categoryIndexFilepath, indices.categoryIndex);
			String authorIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.AUTHOR.toString() + ".txt";
			indexToFile(authorIndexFilepath, indices.authorIndex);
			String placeIndexFilepath = this.indexDirectory + File.separator
					+ IndexType.PLACE.toString() + ".txt";
			indexToFile(placeIndexFilepath, indices.placeIndex);

			String docIDLookupFilepath = this.indexDirectory + File.separator
					+ "FILEID" + ".txt";
			lookupToFile(docIDLookupFilepath, indices.docIDLookup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IndexerException();
		}
	}

	public void indexToFile(String path,
			HashMap<String, HashMap<Integer, String>> indexMap)
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
