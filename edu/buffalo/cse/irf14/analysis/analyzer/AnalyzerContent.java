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

	TokenStream tStream;

	public AnalyzerContent(TokenStream tStream) {

		this.tStream = tStream;
	}

	@Override
	public boolean increment() throws TokenizerException {

		// TODO Auto-generated method stub

		TokenFilterFactory factory = TokenFilterFactory.getInstance();

		// Number
		TokenFilter numericFilter = factory.getFilterByType(TokenFilterType.NUMERIC, tStream);
		while (numericFilter.increment()) {
		}

		// Special char
		TokenFilter specialCharFilter = factory.getFilterByType(TokenFilterType.SPECIALCHARS, tStream);
		while (specialCharFilter.increment()) {
		}
		// Stemmer
		// Stopwords
		// Symbol
		// Accents

		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
