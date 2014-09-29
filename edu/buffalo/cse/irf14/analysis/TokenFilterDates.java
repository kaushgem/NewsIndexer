package edu.buffalo.cse.irf14.analysis;

import java.util.*;

public class TokenFilterDates extends TokenFilter {

	public TokenFilterDates(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		return false;
		/*
		if (!tStream.hasNext())
			return false;
		Token token = tStream.next();
		Date date1 = new Date();

		if (token == null)
			return false;
		else {
			String str = token.getTermText();
			if (str == null || str.isEmpty())
				return true;
			str = str.toLowerCase();

			if (Character.isDigit(str.charAt(0))) {

				// regex for ^[0-9]{2,4}(BC|AD)$ 78AD or BC
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, 1900);
				List<Token> afterTokens = tStream.GetAfterTokens(2);
				List<Token> beforeTokens = tStream.GetBeforeTokens(2);

				String[] strArray = str.split("-");

				if (strArray.length == 1) {
					int number = Integer.parseInt(str.replaceAll("[^0-9]", ""));
					String NextWord1 = null;
					if (afterTokens.size() != 0) {
						afterTokens.get(0).getTermText()
								.replaceAll("[^a-zA-Z]", " ");
					}

					if (NextWord1 != null
							&& (NextWord1.toLowerCase() == "bc" || NextWord1
									.toLowerCase() == "ad")) {
						if (NextWord1.toLowerCase() == "bc") // 800 BC
							cal.set(Calendar.YEAR, -number);
						else
							cal.set(Calendar.YEAR, number); // 80 AD

						// set token & return

					}

					if (getMonth(NextWord1) > 0) {
						cal.set(Calendar.MONTH, getMonth(NextWord1));
					}
				}
				int month = getMonth(str);
			}
		}
		return true;
		*/
	}

	private int getMonth(String str) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("january", 1);
		hm.put("february", 2);
		hm.put("march", 3);
		hm.put("april", 4);
		hm.put("may", 5);
		hm.put("june", 6);
		hm.put("july", 7);
		hm.put("august", 8);
		hm.put("september", 9);
		hm.put("october", 10);
		hm.put("november", 11);
		hm.put("december", 12);
		hm.put("jan", 1);
		hm.put("feb", 2);
		hm.put("mar", 3);
		hm.put("apr", 4);
		hm.put("may", 5);
		hm.put("jun", 6);
		hm.put("jul", 7);
		hm.put("aug", 8);
		hm.put("sep", 9);
		hm.put("oct", 10);
		hm.put("nov", 11);
		hm.put("dec", 12);

		return (hm.get(str) != null) ? (Integer) hm.get(str) : 0;

	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tStream;
	}

}
