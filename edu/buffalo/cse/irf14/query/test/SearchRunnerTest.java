package edu.buffalo.cse.irf14.query.test;

import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buffalo.cse.irf14.SearchRunner;
import edu.buffalo.cse.irf14.SearchRunner.ScoringModel;

public class SearchRunnerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		String indexDir = "C:\\Users\\Sathish\\Dropbox\\UB\\Fall\\535 - IR\\index";
		String corpusDir = "C:\\Users\\Sathish\\Dropbox\\UB\\Fall\\535 - IR\\news_training\\training";
		char mode = 'E';
		PrintStream stream = System.out;
		
		SearchRunner search = new SearchRunner(indexDir,  corpusDir, mode,  stream);

		String q = "Category:oil AND place:Dubai AND ( price OR cost )";// regulatory";
		q = "lubricating AND marine AND petrochemical";  //CoFAB
		// q = "NATO AND NATO";
		File f = new File("/Users/kaush/Coding/Dataset/IR/q.txt");
		//search.query(f);
		search.query(q,ScoringModel.TFIDF);
		//search.query(q,ScoringModel.OKAPI);

		

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}

}
