package edu.buffalo.cse.irf14.analysis;

/**
 * @author Kaushik
 *
 */

public class TokenFilterStopwords extends TokenFilter {

	public TokenFilterStopwords(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		try {
			if (!tStream.hasNext())
				return false;
			Token token = tStream.next();
			String str = token.getTermText();
			if(str == null || str.isEmpty())
				return true;
			
			TokenFilterConstants tConst = TokenFilterConstants.getInstance();
			if (tConst.stopWords.get(str.toLowerCase()) != null)
				if (tConst.stopWords.get(str.toLowerCase()))
					str = "";
			token.setTermText(str);
		} catch (Exception e) {
			return false;
			//throw new TokenizerException();
		}

		return true;
	}

	@Override
	public TokenStream getStream() {
		return tStream;
	}

}
