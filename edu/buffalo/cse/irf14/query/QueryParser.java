/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.util.Utility;

/**
 * @author nikhillo
 * Static parser that converts raw text to Query objects
 */
public class QueryParser {
	/**
	 * MEthod to parse the given user query into a Query object
	 * @param userQuery : The query to parse
	 * @param defaultOperator : The default operator to use, one amongst (AND|OR)
	 * @return Query object if successfully parsed, null otherwise
	 */
	public static Query parse(String userQuery, String defaultOperator) {

		//TODO: YOU MUST IMPLEMENT THIS METHOD
		Operator defaultOper = getDefaultOperator(defaultOperator);

		if(!ValidateQuery(userQuery))
			return null;

		String formattedQuery = GetFormatedQueryString(userQuery,defaultOper);
		Query query = new Query(formattedQuery,null,null,null,formattedQuery);
		return query;

	}

	private static boolean ValidateQuery(String userQuery )
	{
		//TODO: 4 types of validation

		// validate brackets (( 

		// validate quotes """

		// validate operators AND OR

		// validate operator followed by closing bracket

		return true;
	}

	private static String GetFormatedQueryString(String userQuery, Operator defaultOper)
	{

		HashMap<String,String> quotedSearchTerms = GetHashMapForQuotedSearchTerms(userQuery);
		userQuery = ReplaceQuotedStringWithHashValues(userQuery,quotedSearchTerms);
		userQuery = AddCategoryToIndividualTermsInsideParanthesis(userQuery);
		userQuery = AddParanthesis(userQuery);
		userQuery = AddDefaultOperators(userQuery,defaultOper);
		userQuery = AddDefaultIndex(userQuery);
		userQuery = AddNotOperator(userQuery);
		userQuery = ReplaceParanthesisWithSquareBrackets(userQuery);
		userQuery = EncloseWithCurlyBrackets(userQuery);
		userQuery = ReplaceHashValuesWithQuotedString(userQuery,quotedSearchTerms);


		return userQuery;
	}



	private static String AddNotOperator(String userQuery) {

		String extractwordWitheRegex = "[\\w\\:]+";
		Pattern p = Pattern.compile(extractwordWitheRegex);
		Matcher m = p.matcher(userQuery);
		while(m.find()) {
			//System.out.println(m.group(0));
			if(m.group(0).equals("NOT"))
			{
				userQuery = userQuery.replaceFirst(m.group(0),"AND");
				m.find();
				String negativeTerm = m.group(0);
				negativeTerm = "<"+ negativeTerm + ">";
				// System.out.println("(m.group(0): "+m.group(0));
				userQuery = userQuery.replaceFirst(Pattern.quote(m.group(0)),Matcher.quoteReplacement(negativeTerm));


			}
		}
		return userQuery;
	}

	private static String ReplaceHashValuesWithQuotedString(String userQuery,
			HashMap<String, String> quotedSearchTerms) {

		for (Map.Entry<String, String> entry : quotedSearchTerms.entrySet())
		{
			userQuery = userQuery.replace(entry.getKey(), entry.getValue() );

		}

		return userQuery;
	}

	private static String ReplaceQuotedStringWithHashValues(String userQuery,
			HashMap<String, String> quotedSearchTerms) {

		for (Map.Entry<String, String> entry : quotedSearchTerms.entrySet())
		{
			userQuery = userQuery.replace(entry.getValue(), entry.getKey());

		}

		return userQuery;
	}

	private static HashMap<String,String> GetHashMapForQuotedSearchTerms(String userQuery)
	{
		// #1 GUID replacement for quoted strings 
		// replace "hi quote" with a random UUID like 38400000-8cf0-11bd-b23e-10b96e4ef00d


		HashMap<String,String> quotedSearchTerms = new HashMap<String,String>(); 

		String RegexQuotesString = "([\"'])(?:\\\\\\1|.)*?\\1";


		Pattern p = Pattern.compile(RegexQuotesString);
		Matcher m = p.matcher(userQuery);
		while(m.find()) {

			String quotedSearchTem = m.group(0);
			String guid = UUID.randomUUID().toString().replace("-", "");
			quotedSearchTerms.put(guid, quotedSearchTem);
			//userQuery = userQuery.replaceFirst(RegexQuotesString, guid);

		}

		return quotedSearchTerms;

	}

	private static String EncloseWithCurlyBrackets(String userQuery) {
		userQuery = "{ "+userQuery+" }";
		return userQuery;
	}

	private static String ReplaceParanthesisWithSquareBrackets(String userQuery) {
		userQuery = userQuery.replace("(", "[ ").replace(")"," ]");
		return userQuery;
	}

	private static String AddDefaultIndex(String userQuery) {

		userQuery = AddIndex(userQuery, "Term");

		return userQuery;
	}

	private static String AddIndex(String userQuery, String Index) {
		String[] tokens = userQuery.split("AND|OR|NOT");
		String extractwordWitheRegex = "[\\w\\:]+";
		Pattern p = Pattern.compile(extractwordWitheRegex);
		HashMap<String,String> hs = new HashMap<String, String>();
		for(String words:tokens)
		{
			Matcher m = p.matcher(words);
			while(m.find()) {
				
				if(!m.group(0).contains(":"))
				{
					// System.out.println("m.group(0): "+m.group(0));
					hs.put(m.group(0),Index+":"+m.group(0));
				}
			}
		}
		for(Entry<String, String> e: hs.entrySet())
		{
			userQuery = userQuery.replace(e.getKey(), e.getValue());
		}

		return userQuery;
	}

	private static String AddParanthesis(String userQuery)
	{
		// AND prisoners detainees rebels
		// will be converted to AND (prisoners detainees rebels)
		String[] tokens = userQuery.split("AND|OR|NOT");
		if(tokens.length >1)
		{
			for(String word:tokens) 
			{
				word = word.trim();
				if(word.split(" ").length >1
						&& !word.contains("(")
						&& !word.contains(")")
						&& !(word.replace(" ", "").isEmpty())
						)
				{
					String paranthesisEnclosedWords = "("+ word + ")";
					userQuery = userQuery.replace(word, paranthesisEnclosedWords);

				}
			}
		}
		return userQuery;

	}

	private static String AddDefaultOperators(String userQuery, Operator defaultOperator)
	{
		try
		{
		String[] tokens = userQuery.split("[AND|OR|NOT]");
		for(String word:tokens) 
		{
			word = word.trim();
			word = word.replace("( ","(").replace(" )",")");
			String[] wordArr =  word.split(" ");
			/*ArrayList<String> arrList = new ArrayList<String>();
			for(String s: wordArr)
			{
				if(s!=null
						&& !s.trim().isEmpty()
						&& !s.trim().equals("(")
						&& !s.trim().equals(")")
						)
				{
					arrList.add(s);
					
				}
			}
			
			String []dsf = new String[arrList.size()];
			arrList.toArray(dsf);*/
			String queryWithDefaultOperator = Utility.join(wordArr," " + defaultOperator.toString()+ " ");
			userQuery = userQuery.replace(word, queryWithDefaultOperator);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return userQuery;

	}

	private static String AddCategoryToIndividualTermsInsideParanthesis(String userQuery)
	{
		// #2 Category Normalization
		// if :( is found in the user query we need to replace
		// the content inside brackets with categories
		// Category:(movies AND crime) will be changed to (Category:movies AND Category:crime)


		//String termsToInsertIndexRegex = ":\\((.*?)\\)";
		String extractIndexTypeRegex = "(Author|Category|Term|Place):\\(((?:[^\\)])+)";
		Pattern p = Pattern.compile(extractIndexTypeRegex);
		Matcher m = p.matcher(userQuery);


		while(m.find()) {

			String searchTerms = m.group(2); // movies AND crime
			// System.out.println("searchTerms:"+searchTerms);		
			String indexType = m.group(1);
			// System.out.println("indexType:"+indexType);		
			searchTerms = AddIndex(searchTerms,  indexType);
			// System.out.println("searchTerms:"+searchTerms);		
			userQuery = userQuery.replaceFirst(indexType+":", "");
			userQuery = userQuery.replaceFirst(Pattern.quote(m.group(2)), Matcher.quoteReplacement(searchTerms));
			// System.out.println("userQuery:"+userQuery);
			//m = p.matcher(userQuery);
		}


		// System.out.println("userQuery:"+userQuery);
		return userQuery;


	}

	private static Operator getDefaultOperator(String defaultOperator)
	{
		Operator defaultOper = Operator.OR;

		if(defaultOperator!=null && defaultOperator.toLowerCase()== "and")
		{
			defaultOper = Operator.AND;
		}

		return defaultOper;
	}
	
	
	
}
