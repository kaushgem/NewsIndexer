package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;

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
						qE.operator = Operator.NOT; 
						infixList.add(qE);

						QueryEntity qE2 = new QueryEntity();

						String operand = s.replace("<", "").replace(">", "");
						qE2.isOperator = false;
						qE2.term = (operand.split(":").length ==2)? operand.split(":")[1]: "";
						qE2.indexType = operand.split(":")[0];
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
						qE2.indexType = s.split(":")[0];
						infixList.add(qE2);
					}
				}
			}
		}

		return infixList;

	}




}
