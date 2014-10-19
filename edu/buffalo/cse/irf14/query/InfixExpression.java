package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.HashMap;

import edu.buffalo.cse.irf14.DTO.QueryInfoDTO;
import edu.buffalo.cse.irf14.index.IndexType;

public class InfixExpression {

	ArrayList<QueryEntity> infixList;
	String formattedUserQuery;

	public InfixExpression(String formattedUserQuery)
	{
		this.formattedUserQuery = formattedUserQuery;
		infixList = new ArrayList<QueryEntity>();
	}

	public ArrayList<QueryEntity> getInfixExpression()
	{

		String[] strArray = formattedUserQuery.split(" ");

		for(String s: strArray)
		{
			if(s!=null && !s.isEmpty())
			{
				
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
					else if( s.equals("[") || s.equals("]") )
					{
						QueryEntity qE2 = new QueryEntity();
						qE2.isOperator = true;
						qE2.operator = (s.equals("[")? Operator.OPEN_PARANTHESIS : Operator.CLOSE_PARANTHESIS);
						infixList.add(qE2);
						
					}
					else if( !s.equals("{") && !s.equals("}") && !s.trim().isEmpty() )
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
	
	public ArrayList<QueryInfoDTO> getBagOfQueryWords()
	{
		ArrayList<QueryInfoDTO> bagOfWords = new ArrayList<QueryInfoDTO>();
		
		for(int i=0; i<infixList.size(); i++)
		{
			QueryEntity qe = infixList.get(i);
			
			if(qe.isOperator && qe.operator == Operator.NOT)
			{
				i=i+2;
				
			}
			else if(!qe.isOperator)
			{
				QueryInfoDTO qI = retrieveIndexTypeTermCombinationPresent(qe.term,qe.indexType,bagOfWords);
				if(null == qI )
				{
				bagOfWords.add(new QueryInfoDTO(qe.term, qe.indexType,1));
				}
				else
				{
					qI.setFreq(qI.getFreq()+1);
				}
			}
			
		}
		return bagOfWords;
		
	}
	
	private QueryInfoDTO retrieveIndexTypeTermCombinationPresent(String term, IndexType indexType,ArrayList<QueryInfoDTO> qBag)
	{
		for(QueryInfoDTO qi: qBag)
		{
			if(qi.getQueryTerm().equals(term)
				&& qi.getType() == indexType)
				{
					return qi;
				}
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
