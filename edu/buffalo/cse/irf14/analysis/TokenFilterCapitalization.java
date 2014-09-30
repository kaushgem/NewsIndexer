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
			if (str == null || str.isEmpty())
				return true;

			// Abbrevation Check
			//if (str.matches("[A-Za-z ]+[,-_.\"'?]*"))
			if (str.matches("[A-Z][A-Z]+"))
				if (str.equals(str.toUpperCase())) {
					// No change
					return true;
				}

			// Camel Case Check
			String strCheck = str;
			boolean prevStrCheck = false;
			boolean strAppend = false;
			while (strCheck.matches("[A-Z][a-z]+[,-_.\"'?]*")) {
				if (prevStrCheck) {
					str += " " + strCheck;
					tStream.next().setTermText("");
					strAppend = true;
				}
				prevStrCheck = true;
				Token nextToken = tStream.getNextWithoutChangingPtr();
				if (nextToken == null) {
					token.setTermText(str);
					return true;
				} else {
					strCheck = nextToken.getTermText();
				}
			}
			if (strAppend) {
				token.setTermText(str);
				return true;
			} else {
				// Check FirstChar
				char[] chArr = str.toCharArray();
				if (Character.isUpperCase(chArr[0])) {
					Token prevToken = tStream.getPreviousWithoutChangingPtr();
					if (prevToken == null) {
						token.setTermText(str.toLowerCase());
						return true;
					}
					String prevStr = prevToken.getTermText();
					if (prevStr.matches(".*[.]$")) {
						token.setTermText(str.toLowerCase());
						return true;
					}
				}
			}
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
