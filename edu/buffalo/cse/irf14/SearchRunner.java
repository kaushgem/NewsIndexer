package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import edu.buffalo.cse.irf14.DTO.QueryInfoDTO;
import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.analyzer.AnalyzerAuthor;
import edu.buffalo.cse.irf14.analysis.analyzer.AnalyzerCategory;
import edu.buffalo.cse.irf14.analysis.analyzer.AnalyzerContent;
import edu.buffalo.cse.irf14.analysis.analyzer.AnalyzerPlace;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;
import edu.buffalo.cse.irf14.index.IndicesDTO;
import edu.buffalo.cse.irf14.query.DocumentIDFilter;
import edu.buffalo.cse.irf14.query.InfixExpression;
import edu.buffalo.cse.irf14.query.PostfixExpression;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryEntity;
import edu.buffalo.cse.irf14.query.QueryEvaluator;
import edu.buffalo.cse.irf14.query.QueryModeOutput;
import edu.buffalo.cse.irf14.query.QueryParser;
import edu.buffalo.cse.irf14.ranking.Ranking;
import edu.buffalo.cse.irf14.ranking.RankingFactory;
import edu.buffalo.cse.util.Utility;
import static java.nio.file.StandardCopyOption.*;
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
	IndicesDTO indices;
	ArrayList<QueryInfoDTO> queryBagWords;
	String flattenedCorpusDir;
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
		this.flattenedCorpusDir = corpusDir+ File.separator+"75d6e08f656e45febb167d2564a14548";
		// WriteToIndex();
		reader = new IndexReader(indexDir);
		this.indices = reader.getIndexDTO();

	}


	private void WriteToIndex()
	{
		File ipDirectory = new File(corpusDir);
		String[] catDirectories = ipDirectory.list();

		String[] files;
		File dir;
		Utility.createEmptyDir(this.flattenedCorpusDir);
		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);

		Date startTime = new Date();
		System.out.println(startTime);

		try {
			for (String cat : catDirectories) {
				dir = new File(corpusDir + File.separator + cat);
				files = dir.list();

				if (files == null)
					continue;

				for (String f : files) {
					try {
						String filePath = dir.getAbsolutePath() + File.separator + f;
						try {
							d = Parser.parse(filePath);
							Path source = Paths.get(filePath);
							Path destination = Paths.get(this.flattenedCorpusDir+File.separator + f);
							Files.copy(source,destination,StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			writer.close();

		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void  query(String userQuery, ScoringModel model) {


		long start_time = System.nanoTime();


		Map<Integer,Float> rankedDocuments = getRankedDocuments(userQuery,model);

		long end_time = System.nanoTime();
		double difference = (end_time - start_time)/1e6;

		try {
			stream.println("Query: "+userQuery);
			stream.println("Time taken: "+ difference+ "msec");
			//System.out.println("Query: "+userQuery);
			//System.out.println("Time taken: "+ difference+ "msec");
			ArrayList<QueryModeOutput> qmList = getQueryModeOutput(rankedDocuments);
			printResultsQmode(qmList);

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void printResultsQmode(ArrayList<QueryModeOutput> qmList)
	{
		for(QueryModeOutput qm:qmList)
		{
			System.out.println("Result Rank: " +qm.resultRank);
			System.out.println("Result Title: " +qm.resultTitle);
			System.out.println("Result Relavancy: " +qm.resultRelevancy);
			System.out.println("Context: " +qm.snippet);

			//	System.out.println("Result Rank: " +qm.resultRank);
			//	System.out.println("Result Title: " +qm.resultTitle);
			//	System.out.println("Result Relavancy: " +qm.resultRelevancy);
			//	System.out.println("Context: " +qm.snippet);
		}
	}


	public ArrayList<QueryModeOutput> getQueryModeOutput( Map<Integer,Float> rankedDocuments) 
			throws ParserException, IOException
			{
		ArrayList<QueryModeOutput> queryModeList = new ArrayList<QueryModeOutput>();
		int i = 0;
		for(Entry<Integer,Float> entry : rankedDocuments.entrySet())
		{
			QueryModeOutput qmo = new QueryModeOutput();
			int fileID = entry.getKey();
			float rank = entry.getValue();
			String FileName = indices.docIDLookup.get(fileID);
			String finalFilePath = this.flattenedCorpusDir + File.separator + FileName;

			qmo.resultRelevancy = rank;
			qmo.resultRank = ++i;
			File fleobj = new File(finalFilePath);
			if(fleobj.exists())
			{
				Document d = Parser.parse(finalFilePath);
				qmo.resultTitle = d.getField(FieldNames.TITLE)[0];
				qmo.snippet =   findSnippet(fileID,indices);
			}
			queryModeList.add(qmo);
		}

		return queryModeList;
			}

	// reference http://stackoverflow.com/questions/16387989/get-words-around-a-position-in-a-string
	private String findSnippet( int fileID,  IndicesDTO indices) throws IOException
	{


		String snippet = "";
		for(QueryInfoDTO queryWord: queryBagWords )
		{
			if(queryWord.getType() == IndexType.CATEGORY)
			{
				continue;
			}

			DocumentIDFilter docIDFilter = new DocumentIDFilter(indices);
			ArrayList<Integer> matchingDocIds = docIDFilter.getDocIDArrayList(queryWord.getType(), queryWord.getQueryTerm());
			if(matchingDocIds.contains(fileID))
			{
				String keyWord = queryWord.getQueryTerm();
				String documentContent = Utility.readStream(this.flattenedCorpusDir + File.separator+indices.docIDLookup.get(fileID));
				String[] sp = documentContent.split(" +"); // "+" for multiple spaces
				String word = null;

				for (int i = 2; i < sp.length; i++) {
					if (sp[i].contains(keyWord)) {
						// have to check for ArrayIndexOutOfBoundsException
						String surr = (i-2 > 0 ? sp[i-2]+" " : "") +
								(i-1 > 0 ? sp[i-1]+" " : "") +
								sp[i] +
								(i+1 < sp.length ? " "+sp[i+1] : "") +
								(i+2 < sp.length ? " "+sp[i+2] : "");
						snippet +="..."+surr;
						word = sp[i];
					}
				}
				if(snippet!=null)
				{
					snippet= snippet.replace(word, "<B>"+word + "</B>");
				}

				if(snippet!=null && snippet.length() >150)
				{
					break;
				}
			}
		}
		return snippet;
	}
	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 * @throws IOException 
	 */
	public void query(File queryFile) throws IOException {
		String queriesStr = Utility.readStreamFileObj(queryFile);
		String[] lines = queriesStr.split("\\r?\\n");
		HashMap<String,String> resultSet = new HashMap<String, String>();
		if(lines.length >=2)
		{
			int queriesCount = Integer.parseInt(lines[0].split("=")[1]);

			for(int i=1; i <= queriesCount; i++)
			{
				String queryID = lines[i].split(":")[0];
				String query = lines[i].replace(queryID+":","");
				query = query.replace("{", "").replace("}", "");
				Map<Integer,Float> rankedDocuments = getRankedDocuments(query,ScoringModel.TFIDF);
				String result = getStringFromRankedDocuments(rankedDocuments,10);
				System.out.println("\n===="+queryID+":"+result);
				resultSet.put(queryID, result);
			}
		}
		//System.out.println(resultSet);
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

	private Map<Integer,Float> getRankedDocuments(String userQuery, ScoringModel model)
	{
		String defaultOperator = "OR";
		// parse query
		//System.out.println("user query");
		//System.out.println(userQuery);
		Query query = QueryParser.parse(userQuery, defaultOperator);
		String formattedUserQuery =  query.toString();
		//System.out.println("Formatted user query");
		//System.out.println(formattedUserQuery);

		//convert to infix
		InfixExpression infix = new InfixExpression(formattedUserQuery);
		ArrayList<QueryEntity> infixArrayListEntity = infix.getInfixExpression();
		//printFix(infixArrayListEntity);
		infixArrayListEntity = getAnalysedQueryTerms(infixArrayListEntity);
		//printFix(infixArrayListEntity);

		// convert to postfix
		//System.out.println("Converting to postfix");
		PostfixExpression postfixExpression = new  PostfixExpression(infixArrayListEntity);
		ArrayList<QueryEntity> postfixArrayListEntity = postfixExpression.getPostfixExpression();
		//printFix(postfixArrayListEntity);
		//System.out.println(" postfix converted");
		// evaluate postfix
		QueryEvaluator qEval = new QueryEvaluator(postfixArrayListEntity);
		ArrayList<Integer> docIDs = qEval.evaluateQuery(reader.getIndexDTO());
		IndicesDTO indices = reader.getIndexDTO();

		//		System.out.println("out");
		//		System.out.println(docIDs.toString());

		// rank documents
		Ranking ranker = RankingFactory.getRankingInstance(model, indices);
		queryBagWords = infix.getBagOfQueryWords();

		Map<Integer,Float> rankedDocuments = ranker.getRankedDocIDs(queryBagWords, docIDs);
		return rankedDocuments;
	}

	private void printFix(ArrayList<QueryEntity> postfixArrayListEntity )
	{
		for(QueryEntity qe: postfixArrayListEntity)
		{
			if(qe.isOperator)
			{
				System.out.println(qe.operator);
			}
			else
			{
				System.out.println(qe.term);
			}
		}
		System.out.println("***************");

	}

	private ArrayList<QueryEntity> getAnalysedQueryTerms(ArrayList<QueryEntity> queryExpression)
	{
		for(QueryEntity qe:queryExpression)
		{
			if(!qe.isOperator)
			{
				Analyzer analyzerObj = null;
				Tokenizer tokenizer = new Tokenizer();
				try {
					TokenStream tStream = null;
					String queryTerm = qe.term;
					if(isPhraseQuery(queryTerm))
					{
						queryTerm = removeQuorations(queryTerm);

					}
					System.out.println("operand: "+qe.term);
					tStream = tokenizer.consume(queryTerm);

					analyzerObj = getAnalyzerforIndexType(qe.indexType,tStream);
					analyzerObj.increment();
					qe.term =tStream.getTokensAsString();


				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return queryExpression;

	}

	private boolean isPhraseQuery(String query)
	{
		return (query!=null
				&& query.isEmpty()
				&& query.startsWith("\"")
				&& query.endsWith("\"")
				);



	}
	private String removeQuorations(String query)
	{
		return (query!=null)?query.replace("\"", ""):query;
	}

	private Analyzer getAnalyzerforIndexType(IndexType indexType, TokenStream tStream)
	{
		Analyzer analyzerObj = null;
		switch(indexType)
		{
		case TERM:
		{
			analyzerObj = new AnalyzerContent(tStream);
			break;
		}
		case CATEGORY:
		{
			analyzerObj = new AnalyzerCategory(tStream);
			break;
		}
		case AUTHOR:
		{
			analyzerObj = new AnalyzerAuthor(tStream);
			break;
		}
		case PLACE:
		{
			analyzerObj = new AnalyzerPlace(tStream);
			break;
		}

		}

		return analyzerObj;
	}
	private String getStringFromRankedDocuments(Map<Integer,Float> rankedDocuments, int limit)
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
			// queryResult.append(rank);
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

		System.out.println("out "+evalModeOutput.toString());
		stream.append(evalModeOutput.toString());
	}

}
