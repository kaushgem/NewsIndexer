package edu.buffalo.cse.irf14.analysis;


public class TokenFilterStemmer extends TokenFilter {

	public TokenFilterStemmer(TokenStream stream) {
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
			char[] ch = str.toCharArray();

			if (str.matches("^[A-Za-z]+")) {
				TokenFilterStemmerHelper stem = new TokenFilterStemmerHelper();
				stem.add(ch, ch.length);
				stem.stem();
				str = stem.toString();
			}
			//System.out.println(str);
			token.setTermText(str);
		} catch (Exception e) {
			return false;
			// throw new TokenizerException();
		}
		return true;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
