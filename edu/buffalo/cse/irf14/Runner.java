/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;
import edu.buffalo.cse.util.Utility;

/**
 * @author nikhillo
 *
 */
public class Runner {

	/**
	 * 
	 */
	public Runner() {
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {

		String ipDir = args[0];
		String indexDir = args[1];
		String flattenedCorpusDir = ipDir + File.separator+ "75d6e08f656e45febb167d2564a14548";
		Utility.createEmptyDir(flattenedCorpusDir);
		// more? idk!

		System.out.println(ipDir);
		System.out.println(indexDir);
		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();

		String[] files;
		File dir;

		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);

		Date startTime = new Date();
		System.out.println(startTime);

		try {
			for (String cat : catDirectories) {
				dir = new File(ipDir + File.separator + cat);
				files = dir.list();

				if (files == null)
					continue;

				for (String f : files) {
					try {
						String filePath = dir.getAbsolutePath() + File.separator + f;
						
						
				
						d = Parser.parse(filePath);
						writer.addDocument(d);
						
						File sourceFile= new File(filePath);
						File destinationfile = new File(flattenedCorpusDir+File.separator + f);
						sourceFile.renameTo(destinationfile);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			writer.close();

			System.out.println(startTime);
			Date endTime = new Date();
			System.out.println(endTime);
			
			IndexReader r = new IndexReader(indexDir);
			List<String> a = r.getTopK(10);

		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
