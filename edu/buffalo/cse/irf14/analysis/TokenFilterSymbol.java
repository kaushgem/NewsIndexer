/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

/**
 * @author Sathish
 *
 */
public class TokenFilterSymbol extends TokenFilter {

	/**
	 * @param stream
	 */
	public TokenFilterSymbol(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#getStream()
	 */
	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
