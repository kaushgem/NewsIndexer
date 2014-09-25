package edu.buffalo.cse.irf14.analysis;

public class TokenFilterDates extends TokenFilter {

	public TokenFilterDates(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		Token token =  tStream.next();
		if(token ==null)
			return false;
		else
		{
			String str = token.getTermText();
			
			
			return true;
		}
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
