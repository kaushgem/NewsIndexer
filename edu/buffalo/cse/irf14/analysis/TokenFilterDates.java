package edu.buffalo.cse.irf14.analysis;

import java.util.HashMap;
import java.util.*;

public class TokenFilterDates extends TokenFilter {

	public TokenFilterDates(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		Token token =  tStream.next();
		Date date1 = new Date();
		
		if(token ==null)
			return false;
		else
		{
			String str = token.getTermText();
			str = str.toLowerCase();
			
			if(Character.isDigit(str.charAt(0)))
			{
				List<Token> surroundingTokens = tStream.GetSurroundingTokens(2);
				String[] strArray = str.split("-");
				
				if(strArray.length == 1)
				{
					int number = Integer.parseInt( str.replaceAll("[^0-9]", ""));
				}
				
				
				int month = isMonth(str);
				if(month>=1 && month <=12)
				{
					


				}  

			}

			
			return true;
		}
	}

	private int isMonth(String str)
	{
		HashMap hm = new HashMap();
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

		return (hm.get(str) != null)?(Integer)hm.get(str):0;



	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
