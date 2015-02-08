package edu.buffalo.cse.irf14.query.test;

import java.io.File;
import java.io.IOException;
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
	public void test() throws IOException {
		String indexDir = "/Users/kaush/Coding/Dataset/IR/files";
		String corpusDir = "/Users/kaush/Coding/Dataset/IR/training";

		
		String q = "PARIS AND government";// regulatory";
		q = "\"lusdsbricating OR marine OR petrochemical\"";  //CoFAB
		q = "lusdsbricating OR marrine OR petrochemical";  //CoFAB
		q = "\"san fransisco\"";
		q = "NATOK";
		//		q = "march";
		//		q = "laser";
		//		q = "controlling interest";
		//		q = "category:coffee beans";
//				q = "Author:torday AND (debt OR currency)";
		//		q = "Author:minkwoski OR disney";
		//		q = "place:tokyo NOT bank";
		//		q = "author:torday AND (debt OR currency)";
//				q = "Place:PARIS AND government";
//				q=  "govefrnment AND regulagtory";
		//		q = "Category:oil AND place:Dubai AND ( price OR cost )";// regulatory";
		//		q = "NATO AND NATO";
				q = "place:tokyo NOT bank";
		//q = "\"photovoltaic cells\"";
		
		
		File f = new File("/Users/kaush/Coding/Dataset/IR/q.txt");
		PrintStream stream = System.out;
		
		char mode = 'E';
		SearchRunner search = new SearchRunner(indexDir,  corpusDir, mode,  stream);
		
		search.query(f);
		//search.query(q,ScoringModel.TFIDF);
		//System.out.println("\n\n===============================\n\n");
		search.query(q,ScoringModel.OKAPI);
		
		for ( String s: search.getCorrections())
		{
			System.out.println(s);
		}
	}
}
