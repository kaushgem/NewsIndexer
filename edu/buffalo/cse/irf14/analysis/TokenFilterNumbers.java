package edu.buffalo.cse.irf14.analysis;


public class TokenFilterNumbers extends TokenFilter {

	public TokenFilterNumbers(TokenStream stream) {
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
			
			str = str.replaceAll("^[0-9]+[.,][0-9]+", "");
			if (str.matches("^[0-9]+[/][0-9]+$"))
				str = str.replaceAll("[0-9]+", "");
			str = str.replaceAll("[0-9]+", "");
			//System.out.println(str);
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
