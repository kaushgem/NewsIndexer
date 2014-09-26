package edu.buffalo.cse.irf14.analysis;

public class TokenFilterAccent extends TokenFilter {

	public TokenFilterAccent(TokenStream stream) {
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
			StringBuilder strB = new StringBuilder("");
			
			if (!str.matches("^\\w*$")) {
				char[] chArray = str.toCharArray();
				TokenFilterConstants tConst = TokenFilterConstants.getInstance();
				for (char ch : chArray) {
					if (tConst.accents.get(ch) != null) {
						strB.append(tConst.accents.get(ch));
					}else{
						strB.append(ch);
					}
				}
				str = strB.toString();
			}
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
