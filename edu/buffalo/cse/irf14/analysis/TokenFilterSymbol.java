/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sathish
 *
 */
public class TokenFilterSymbol extends TokenFilter {

	/**
	 * @param stream
	 */
	public TokenFilterSymbol(TokenStream stream) {
		super(stream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {

		try {
			if (!tStream.hasNext())
				return false;
			Token token = tStream.next();
			String str = token.getTermText();
			if(str == null || str.isEmpty())
				return true;
			
			// 2 - 'hv 'nt
			TokenFilterConstants tConst = TokenFilterConstants.getInstance();
			String str1 = str.toLowerCase();
			if (tConst.contractions.get(str1) != null) {
				str1 = tConst.contractions.get(str1);
				if (!str.substring(0, 1).equals("'"))
					str = str.substring(0, 1) + str1.substring(1);
				else
					str = str1;
			}

			str = str.replaceAll("'s$", "");
			str = str.replaceAll("'", "");

			Pattern p = Pattern.compile("\\S[^?!.']*[?!.'-]+$");
			Matcher m = p.matcher(str);
			if (m.find()) {
				str = str.replaceAll("[?!.'-]+$", "");
			}

			/*
			 * p = Pattern.compile("\\S[^?!.'][^a-zA-Z0-9]+$"); m =
			 * p.matcher(str); if (m.find()) { str =
			 * str.replaceAll("[^a-zA-Z0-9]+$", ""); System.out.println(str); }
			 */

			/*
			 * if (str.matches("[0-9]+-[A-Za-z]+") ||
			 * str.matches("[A-Za-z]+-[0-9]")) { str = str; }
			 */

			if (str.matches("[0-9]+-")) {
				str = str.replaceAll("[-]+", "");
			}

			if (str.matches("[\n]*-+[\n]*") || str.matches("^-+\\S")) {
				str = str.replaceAll("[-+]+", "");
			}

			if (str.matches("[A-Za-z]+-[A-Za-z]+")) {
				str = str.replace("-", " ");
			}

			token.setTermText(str);

		} catch (Exception e) {
			throw new TokenizerException();
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#getStream()
	 */
	@Override
	public TokenStream getStream() {
		return tStream;
	}

}
