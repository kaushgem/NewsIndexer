package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;
import edu.buffalo.cse.irf14.query.InfixExpression;
import edu.buffalo.cse.irf14.query.PostfixExpression;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryEntity;
import edu.buffalo.cse.irf14.query.QueryEvaluator;
import edu.buffalo.cse.irf14.query.QueryParser;
import edu.buffalo.cse.irf14.ranking.Ranking;
import edu.buffalo.cse.irf14.ranking.RankingFactory;
import edu.buffalo.cse.util.Utility;

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
	IndexReader reader;
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
		reader = new IndexReader(indexDir);

	}

	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {

		
		HashMap<Integer,Float> rankedDocuments =getRankedDocuments(userQuery,model);
		

	}

	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 * @throws IOException 
	 */
	public void query(File queryFile) throws IOException {
		String queriesStr = Utility.readStream(queryFile.getAbsolutePath());
		String[] lines = queriesStr.split("\\r?\\n");
		HashMap<String,String> resultSet = new HashMap<String, String>();
		if(lines.length >=2)
		{
			int queriesCount = Integer.parseInt(lines[0].split("=")[1]);
			
			for(int i=1; i < queriesCount; i++)
			{
				String queryID = lines[i].split(":")[0];
				String query = lines[i].split(":")[1];
				
				HashMap<Integer,Float> rankedDocuments = getRankedDocuments(query,ScoringModel.TFIDF);
				String result = getStringFromRankedDocuments(rankedDocuments,10);
				resultSet.put(queryID, result);
			}
		}
		writeToPrintStreamEvalMode(resultSet);
		
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
	
	private HashMap<Integer,Float> getRankedDocuments(String userQuery, ScoringModel model)
	{
		String defaultOperator = "OR";
		// parse query
		Query query = QueryParser.parse(userQuery, defaultOperator);
		String formattedUserQuery =  query.toString();
		
		//convert to infix
		InfixExpression infix = new InfixExpression(formattedUserQuery);
		ArrayList<QueryEntity> infixArrayListEntity = infix.getInfixExpression();
		
		// convert to postfix
		PostfixExpression postfixExpression = new  PostfixExpression(infixArrayListEntity);
		ArrayList<QueryEntity> postfixArrayListEntity = postfixExpression.getPostfixExpression();
		
		// evaluate postfix
		QueryEvaluator qEval = new QueryEvaluator(postfixArrayListEntity);
		
		ArrayList<Integer> docIDs = qEval.evaluateQuery(reader);
		IndicesDTO indices = reader.getIndexDTO();
		
		// rank documents
		Ranking ranker = RankingFactory.getRankingInstance(model, indices);
		HashMap<String,IndexType> queryBagWords = infix.getBagOfQueryWords();
		HashMap<Integer,Float> rankedDocuments = ranker.getRankedDocIDs(queryBagWords, docIDs);
		return rankedDocuments;
	}
	
	private String getStringFromRankedDocuments(HashMap<Integer,Float> rankedDocuments, int limit)
	{
		IndicesDTO indices = reader.getIndexDTO();
		StringBuilder queryResult = new StringBuilder();
		limit = (limit <rankedDocuments.size())?limit:rankedDocuments.size();
		String[] queryResultArr = new String[limit];
		int i=0;
		queryResult.append("{");
		for(Map.Entry<Integer,Float> entry:rankedDocuments.entrySet())
		{
			int fileID = entry.getKey();
			float rank = entry.getValue();
			String FileName = indices.docIDLookup.get(fileID);
			queryResultArr[i] = FileName;
			queryResultArr[i]+="#";
			queryResultArr[i]+=rank;
			queryResult.append(rank);
			i++;
			
			if(i>=limit)
			{
				break;
			}
		}
		queryResult.append(Utility.join(queryResultArr, ", "));
		queryResult.append("}");
		return queryResult.toString();
	}
	
	private void writeToPrintStreamEvalMode(HashMap<String,String> resultSet)
	{
		StringBuilder evalModeOutput = new StringBuilder();
		evalModeOutput.append("numResults=");
		evalModeOutput.append(resultSet.size());
		
		for(Map.Entry<String, String> result:resultSet.entrySet())
		{
			String queryID = result.getKey();
			String queryResult = result.getValue();
			evalModeOutput.append(queryID);
			evalModeOutput.append(queryResult);
			evalModeOutput.append("\n");
		}
		
		stream.append(evalModeOutput.toString());
		
	}
	
	
	
}
