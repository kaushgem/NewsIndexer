package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.HashMap;

import edu.buffalo.cse.irf14.index.IndexType;

public class InfixExpression {

	ArrayList<QueryEntity> infixList;
	String formattedUserQuery;

	public InfixExpression(String formattedUserQuery)
	{
		this.formattedUserQuery = formattedUserQuery;

	}

	public ArrayList<QueryEntity> InfixExpress()
	{

		String[] strArray = formattedUserQuery.split(" ");

		for(String s: strArray)
		{
			if(s!=null && !s.isEmpty())
			{
				//
				QueryEntity qE = new QueryEntity();
				if(s.equals("AND")|| s.equals("OR"))
				{
					// operator
					qE.isOperator = true;
					qE.operator = Operator.valueOf(s); 
					infixList.add(qE);
				}
				else
				{
					// operand
					if(s.contains("<") || s.contains(">"))
					{
						qE.isOperator = true;
						qE.operator = Operator.MINUS; 
						
						infixList.remove(infixList.size() - 1); // removing last item AND operator 
						// A AND NOT B is changed to A MINUS B
						infixList.add(qE);

						QueryEntity qE2 = new QueryEntity();

						String operand = s.replace("<", "").replace(">", "");
						qE2.isOperator = false;
						qE2.term = (operand.split(":").length ==2)? operand.split(":")[1]: "";
						qE2.indexType = getIndexType(operand.split(":")[0]);
						infixList.add(qE2);
					}
					else if( s.equals("[") && s.equals("]") )
					{
						QueryEntity qE2 = new QueryEntity();
						qE2.isOperator = true;
						qE2.operator = (s.equals("[")? Operator.OPEN_PARANTHESIS : Operator.CLOSE_PARANTHESIS);
						infixList.add(qE2);
						
					}
					else 
					{
						QueryEntity qE2 = new QueryEntity();
						qE2.isOperator = false;
						qE2.term = (s.split(":").length ==2)? s.split(":")[1]: "";
						qE2.indexType = getIndexType(s.split(":")[0]);
						infixList.add(qE2);
					}
				}
			}
		}

		return infixList;

	}
	
	public HashMap<String,String> getBagOfQueryWords()
	{
		HashMap<String,String> bagOfWords = new HashMap<String, String>();
		
		for(int i=0; i<infixList.size(); i++)
		{
			QueryEntity qe = infixList.get(i);
			//if(qe.isOperator && qe.)
			
		}
		return null;
		
	}
	
	private IndexType getIndexType(String indexTypeStr)
	{
		IndexType indexType = IndexType.TERM;
		
		if( indexTypeStr.equalsIgnoreCase("Category"))
		{
			indexType = IndexType.CATEGORY;
		}
		else if( indexTypeStr.equalsIgnoreCase("Author"))
		{
			indexType = IndexType.AUTHOR;
		}

		else if (indexTypeStr.equalsIgnoreCase("Place"))
		{
			indexType = IndexType.PLACE;
		}
		
		return indexType;

	}






}
