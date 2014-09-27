/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.*;

/**
 * @author kaush
 *
 */
public class AnalyzerContent implements Analyzer {

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	TokenStream tStream;
	public AnalyzerContent(TokenStream tStream) 
	{

		this.tStream = tStream;
	}
	
	
	@Override
	public boolean increment() throws TokenizerException {
		
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

	
	@Override
	public void analyze() throws TokenizerException {
		
		TokenFilterFactory factory = TokenFilterFactory.getInstance();
		
		
		//Number 
		TokenFilter numericFilter = factory.getFilterByType(TokenFilterType.NUMERIC, tStream);
		while (numericFilter.increment()) {}
		
		
		//Special char
		TokenFilter specialCharFilter = factory.getFilterByType(TokenFilterType.SPECIALCHARS, tStream);
		while (specialCharFilter.increment()) {}
		//Stemmer
		//Stopwords
		//Symbol
		//Accents
		
		
		
	}

	
	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#getStream()
	 */
	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
