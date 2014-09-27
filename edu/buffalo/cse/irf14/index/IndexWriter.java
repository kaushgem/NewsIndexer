/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.util.HashMap;

import edu.buffalo.cse.irf14.analysis.*;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

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
	
	HashMap<String,String> invertedIndex = null;
	public IndexWriter(String indexDir) {
		// TODO : YOU MUST IMPLEMENT THIS
		invertedIndex = new HashMap<String,String>();
	}
	
	
	public HashMap<String,String> getInvertedIndex()
	{
		if(invertedIndex == null)
		{
			ComputeInvertedIndex();
		}
		
		return invertedIndex;
	}
	
	public void ComputeInvertedIndex()
	{
		invertedIndex = new HashMap<String,String>();
		
	}
	
	public void insertToHashmap(TokenStream tStream,String fileID){

		while(tStream.hasNext())
		{
			String token = tStream.next().toString();
			String existingToken =invertedIndex.get(token); 
			invertedIndex.put(token,existingToken+","+fileID);
		}
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
		String[] stringArray = null;
		
		AnalyzerFactory analyzerFactoryObj = AnalyzerFactory.getInstance();
		Analyzer analyzerObj = null;
		String fileID = d.getField(FieldNames.FILEID)[0];
		try {
			for (FieldNames field : FieldNames.values()) {
				stringArray = d.getField(field);
				for (String s : stringArray) {
					TokenStream	tStream = tokenizer.consume(s);
					analyzerObj = analyzerFactoryObj.getAnalyzerForField(field,tStream);
					analyzerObj.increment();
					//to-do
					
					
					
				}
			}

			/*
			 * String[] stringArray = d.getField(FieldNames.CONTENT); for(String
			 * s:stringArray) { TokenStream tStream = tokenizer.consume(s);
			 * AnalyzerFactory analyzerFactoryObj =
			 * AnalyzerFactory.getInstance(); Analyzer contentAnalyser =
			 * analyzerFactoryObj.getAnalyzerForField(FieldNames.CONTENT,tStream
			 * ); contentAnalyser.increment(); }
			 */

		} catch (Exception ex) {
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
		// TODO
	}
}
