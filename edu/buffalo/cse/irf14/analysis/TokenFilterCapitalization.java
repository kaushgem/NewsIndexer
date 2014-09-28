package edu.buffalo.cse.irf14.analysis;


public class TokenFilterCapitalization extends TokenFilter {

	public TokenFilterCapitalization(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
