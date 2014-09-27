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

	public AnalyzerContent(TokenStream stream) {
		tStream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {

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
		return tStream;
	}
}
