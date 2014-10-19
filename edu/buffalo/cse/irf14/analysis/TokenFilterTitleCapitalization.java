package edu.buffalo.cse.irf14.analysis;

public class TokenFilterTitleCapitalization extends TokenFilter {

	public TokenFilterTitleCapitalization(TokenStream stream) {
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
			if (str == null || str.isEmpty())
				return true;
			token.setTermText(str.toLowerCase());
		} catch (Exception e) {
			throw new TokenizerException();
		}
		return true;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
