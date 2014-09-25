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
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
		Token token = tStream.next();
		String str = token.getTermText();
		
		

		// 2 - 'hv 'nt
		//String str = "ain't";
		TokenFilterConstants tk = new TokenFilterConstants();
		System.out.println("Contractions - " + tk.contractions.get(str));

		// 1 & 2
		// apostrophe
		//Stringstr = "0dfdf's'";
		//str= "dfd$ffd??...--??";
		Pattern p = Pattern.compile("\\S[^?!.'][?!.']+$");
		Matcher m = p.matcher(str);
		if (m.find()) {
			str=str.replaceAll("[?!.']+$", "");
			System.out.println(str);
		}
		str=str.replaceAll("'s$", "");
		System.out.println(str);

		//str= "df$dff d??.. .--??";
		p = Pattern.compile("\\S[^?!.'][^a-zA-Z0-9]+$");
		m = p.matcher(str);
		if (m.find()) {
			str=str.replaceAll("[^a-zA-Z0-9]+$", "");
			System.out.println(str);
		}
		str=str.replaceAll("'s$", "");
		System.out.println(str);

		// 3
		str= "b-v";
		if (str.matches("[0-9]+-[A-Za-z]+") ||str.matches("[A-Za-z]+-[0-9]")) {
			System.out.println("B-46");
		}

		if (str.matches("[0-9]+-")) {
			System.out.println("fdfssf");
		}

		if (str.matches("[A-Za-z]+-[A-Za-z]+")) {
			str=str.replace("-", " ");
			System.out.println("both alphabet");
			System.out.println(str);
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
		// TODO Auto-generated method stub
		return null;
	}

}
