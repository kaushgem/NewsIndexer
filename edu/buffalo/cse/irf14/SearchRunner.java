package edu.buffalo.cse.irf14;

import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;
import edu.buffalo.cse.irf14.query.*;
import edu.buffalo.cse.irf14.ranking.Ranking;
import edu.buffalo.cse.irf14.ranking.RankingFactory;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class to run the searcher.
 * As before implement all TODO methods unless marked for bonus
 * @author nikhillo
 *
 */
public class SearchRunner {
	public enum ScoringModel {TFIDF, OKAPI};
	String indexDir; 
	String corpusDir; 
	char mode; 
	PrintStream stream;
	/**
	 * Default (and only public) constuctor
	 * @param indexDir : The directory where the index resides
	 * @param corpusDir : Directory where the (flattened) corpus resides
	 * @param mode : Mode, one of Q or E
	 * @param stream: Stream to write output to
	 */
	public SearchRunner(String indexDir, String corpusDir, 
			char mode, PrintStream stream) {

		this.indexDir = indexDir;
		this.corpusDir = corpusDir;
		this.mode = mode;
		this.stream = stream;


	}

	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {

		Query query = QueryParser.parse(userQuery, "OR");
		String formattedUserQuery =  query.toString();
		InfixExpression infix = new InfixExpression(formattedUserQuery);
		ArrayList<QueryEntity> infixArrayListEntity = infix.getInfixExpression();
		PostfixExpression postfixExpression = new  PostfixExpression(infixArrayListEntity);
		ArrayList<QueryEntity> postfixArrayListEntity = postfixExpression.getPostfixExpression();
		QueryEvaluator qEval = new QueryEvaluator(postfixArrayListEntity);
		IndexReader reader = new IndexReader(indexDir);
		ArrayList<Integer> docIDs = qEval.evaluateQuery(reader);
		IndicesDTO indices = reader.getIndexDTO();
		Ranking ranker = RankingFactory.getRankingInstance(model, indices);
		HashMap<String,IndexType> queryBagWords = infix.getBagOfQueryWords();
		HashMap<Integer,Float> rankedDocuments = ranker.getRankedDocIDs(queryBagWords, docIDs);
		


	}

	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 */
	public void query(File queryFile) {
		//TODO: IMPLEMENT THIS METHOD
	}

	/**
	 * General cleanup method
	 */
	public void close() {
		//TODO : IMPLEMENT THIS METHOD
	}

	/**
	 * Method to indicate if wildcard queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
	}

	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * @return A Map containing the original query term as key and list of
	 * possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		//TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		return null;

	}

	/**
	 * Method to indicate if speel correct queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return false;
	}

	/**
	 * Method to get ordered "full query" substitutions for a given misspelt query
	 * @return : Ordered list of full corrections (null if none present) for the given query
	 */
	public List<String> getCorrections() {
		//TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}
	
	
	
	
}
