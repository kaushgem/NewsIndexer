/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

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
						d = Parser.parse(dir.getAbsolutePath() + File.separator + f);
						writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			writer.close();
			
//			IndexReader r = new IndexReader(indexDir, IndexType.TERM);
//			ArrayList<String> l = (ArrayList<String>) r.getTopK(10);
//			for(String s : l)
//				System.out.println(s);
			
			System.out.println(startTime);
			Date endTime = new Date();
			System.out.println(endTime);

		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
