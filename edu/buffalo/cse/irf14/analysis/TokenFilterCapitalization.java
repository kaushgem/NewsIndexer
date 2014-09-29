package edu.buffalo.cse.irf14.analysis;



public class TokenFilterCapitalization extends TokenFilter {

	public TokenFilterCapitalization(TokenStream stream) {
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
			
			if(str.equals(str.toUpperCase()))
				return true;
			
			str = str.toLowerCase();
			token.setTermText(str);
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
