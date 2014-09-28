/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

	public static HashMap<String,HashMap<String,Integer>> invertedIndex;
	public static HashMap<Integer, String> fileIDLookup;

	public IndexWriter(String indexDir) {
		if(invertedIndex ==null)
			invertedIndex = new HashMap<String,HashMap<String,Integer>>();
		if(fileIDLookup == null)
			fileIDLookup = new HashMap<Integer, String>(); 
	}

	public HashMap<String,HashMap<String,Integer>> getInvertedIndex()
	{
		if(invertedIndex == null) { ComputeInvertedIndex(); }
		return invertedIndex;
	}

	public void ComputeInvertedIndex()
	{
		invertedIndex = new HashMap<String,HashMap<String,Integer>>();
	}

	public void insertToHashmap(TokenStream tStream,String fileID){

		while(tStream.hasNext())
		{
			String token = tStream.next().toString();
			HashMap<String,Integer> indexPostings = invertedIndex.get(token);
			if(token == null || token.isEmpty())
				continue;

			//Keyword check
			if(indexPostings == null)
			{
				indexPostings = new HashMap<String,Integer>();
				indexPostings.put(fileID, 1);
				invertedIndex.put(token, indexPostings);
			}
			else
			{
				//fileID check
				if(indexPostings.get(fileID)==null)
				{
					indexPostings.put(fileID, 1);
				}
				else
				{
					indexPostings.put(fileID, indexPostings.get(fileID) +1);
				}
			}
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
		fileIDLookup.put(fileIDLookup.size()+1, fileID);

		try {
			for (FieldNames field : FieldNames.values()) {

				stringArray = d.getField(field);
				if(stringArray==null) continue;

				if(field.equals(FieldNames.FILEID)) continue;
				if(field.equals(FieldNames.NEWSDATE)) continue;

				for (String s : stringArray) {
					if(s!=null && !s.isEmpty())
					{
						try{
							TokenStream	tStream = tokenizer.consume(s);
							analyzerObj = analyzerFactoryObj.getAnalyzerForField(field,tStream);
							analyzerObj.increment();
							//to-do
							tStream.reset();
							insertToHashmap(tStream, fileID);

							// invertedIndex
						}catch(Exception e)
						{
							System.out.println("loop"+fileID);
							e.printStackTrace();
						}
					}
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

			//System.out.println("File - "+d.getField(FieldNames.CATEGORY)[0]+" / "+ 
			//d.getField(FieldNames.FILEID)[0]);
			//fileWrite();
			//fileRead();
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public void fileWrite()
	{
		//System.out.println("file write");
		try{
			FileOutputStream fos = new FileOutputStream("hashmap.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(invertedIndex);
			oos.close();
			fos.close();
			//System.out.printf("Serialized HashMap data is saved in hashmap.ser");
		}catch(Exception ioe)
		{
			ioe.printStackTrace();
		}
	}


	public void fileRead()
	{
		//System.out.println("\n\n===============================");
		//System.out.println("Deserialize()");
		HashMap<Integer, String> map = null;
		try
		{
			FileInputStream fis = new FileInputStream("hashmap.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (HashMap) ois.readObject();
			ois.close();
			fis.close();
		}catch(Exception ioe)
		{
			ioe.printStackTrace();
			return;
		}
		//System.out.println("Deserialized HashMap..");
		// Display content using Iterator
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			//System.out.print("key: "+ mentry.getKey() + " & Value: ");
			//System.out.println(mentry.getValue());
		}
	}

}



