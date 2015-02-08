/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.TokenFilter;
import edu.buffalo.cse.irf14.analysis.TokenFilterFactory;
import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

/**
 * @author Kaushik
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
		TokenFilter tokenFilterObj;
		try{
			TokenFilterType[] filterOrder = {
					TokenFilterType.CAPITALIZATION,
					TokenFilterType.SYMBOL,
					TokenFilterType.SPECIALCHARS,
					TokenFilterType.STOPWORD,
					TokenFilterType.DATE,
					TokenFilterType.NUMERIC,
					TokenFilterType.ACCENT
					//TokenFilterType.STEMMER
			};
			for (TokenFilterType tokenFilType : filterOrder) {
				tokenFilterObj = factory.getFilterByType(tokenFilType, tStream);
				while (tokenFilterObj.increment()) {}
				if(tStream!=null)
					tStream.reset();

				//System.out.println("\n\n** "+tokenFilType+" : "+tStream.getTokensAsString());

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
		return tStream;
	}
}
