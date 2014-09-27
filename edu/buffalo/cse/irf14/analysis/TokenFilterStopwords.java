package edu.buffalo.cse.irf14.analysis;


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
			System.out.println(str);
			token.setTermText(str);
		} catch (Exception e) {
			throw new TokenizerException();
		}

		return true;
	}

	@Override
	public TokenStream getStream() {
		return tStream;
	}

}
