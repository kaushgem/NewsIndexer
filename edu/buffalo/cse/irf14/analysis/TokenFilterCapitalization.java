package edu.buffalo.cse.irf14.analysis;
/**
 * @author Kaushik
 *
 */
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

			//System.out.println("=="+tStream.getTokensAsString());

			// Abbrevation Check

			//if (str.matches("[A-Za-z ]+[,-_.\"'?]*"))
			if (str.matches("[A-Z][A-Z]+"))
				if (str.equals(str.toUpperCase())) {
					// No change
					//System.out.println(token.getTermText());
					return true;
				}



			// Camel Case Check
			String strOriginal = str;
			String strCheck = str;
			boolean prevStrCheck = false;
			boolean strAppend = false;
			while (strCheck.matches("[A-Z][a-z]+[,-_.\"'?]*")) {
				if (prevStrCheck) {
					str += " " + strCheck;
					//tStream.next().setTermText("");
					tStream.getNextWithoutChangingPtr().setTermText(tStream.getNextWithoutChangingPtr().getTermText().toLowerCase());
					tStream.next();
					strAppend = true;
				}
				prevStrCheck = true;
				Token nextToken = tStream.getNextWithoutChangingPtr();
				if (nextToken == null) {
					token.setTermText(strOriginal.toLowerCase());
					Token nToken = new Token(str.toLowerCase());
					tStream.insertToken(nToken);
					//System.out.println(token.getTermText());
					return true;
				} else {
					strCheck = nextToken.getTermText();
				}
			}

			if (strAppend) {
				token.setTermText(strOriginal.toLowerCase());
				Token nToken = new Token(str.toLowerCase());
				tStream.insertToken(nToken);
				//System.out.println(token.getTermText());
				return true;
			} else {
				// Check FirstChar
				char[] chArr = str.toCharArray();
				if (Character.isUpperCase(chArr[0])) {
					Token prevToken = tStream.getPreviousWithoutChangingPtr();
					if (prevToken == null) {
						token.setTermText(str.toLowerCase());
						//System.out.println(token.getTermText());
						return true;
					}
					String prevStr = prevToken.getTermText();
					if (prevStr.matches(".*[.]$")) {
						token.setTermText(str.toLowerCase());
						//System.out.println(token.getTermText());
						return true;
					}
					else{
						token.setTermText(str.toLowerCase());
						//System.out.println(token.getTermText());
						return true;
					}
				}
			}
			token.setTermText(str.toLowerCase());
			//System.out.println(token.getTermText());
		} catch (Exception e) {
			//throw new TokenizerException();
			return false;
		}

		return true;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
