/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
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
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String ipDir = "C:\\Users\\Sathish\\Dropbox\\UB\\Fall\\535 - IR\\news_training\\training";//args[0];
		String indexDir = "";
		//more? idk!
		
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
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				
				if (files == null)
					continue;
				
				for (String f : files) {
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						// Date fileTime = new Date();
						// System.out.println(fileTime);
						writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
			
			writer.close();
			Date endTime = new Date();
			System.out.println(startTime);
			System.out.println(endTime);
			System.out.println("Terminated TotalTime = "+ endTime.compareTo(startTime));
			  
			
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
