package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.TokenFilter;
import edu.buffalo.cse.irf14.analysis.TokenFilterFactory;
import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

/**
 * @author kaush
 *
 */
public class AnalyzerTitle implements Analyzer {

	TokenStream tStream;

	public AnalyzerTitle(TokenStream stream) {
		tStream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		TokenFilterFactory factory = TokenFilterFactory.getInstance();
		TokenFilter tokenFilterObj;
		try{
			TokenFilterType[] filterOrder = {
					TokenFilterType.TITTLECAPITALIZATION,
					TokenFilterType.SYMBOL,
					TokenFilterType.SPECIALCHARS,
					TokenFilterType.STOPWORD,
					TokenFilterType.DATE,
					TokenFilterType.NUMERIC,
					TokenFilterType.ACCENT,
					TokenFilterType.STEMMER};
			for (TokenFilterType tokenFilType : filterOrder) {
				tokenFilterObj = factory.getFilterByType(tokenFilType, tStream);
				while (tokenFilterObj.increment()) {}
				tStream.reset();
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
			// throw new TokenizerException();
		}
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
