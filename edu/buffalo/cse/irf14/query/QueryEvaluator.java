/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.Stack;

import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndicesDTO;

/**
 * @author Sathish
 *
 */
public class QueryEvaluator {

	ArrayList<QueryEntity> postfixExpression;
	Stack<ArrayList<Integer>> stack ;

	public QueryEvaluator(ArrayList<QueryEntity> postfixExpression)
	{
		this.postfixExpression = postfixExpression;
		stack = new Stack<ArrayList<Integer>>();
	}

	public ArrayList<Integer> evaluateQuery(IndicesDTO indices )
	{
		try
		{
			for (QueryEntity qe:postfixExpression) {

				if(qe.isOperator)
				{
					switch(qe.operator)
					{
					case OR:
					{
						OROperation(qe); break;
					}
					case AND:
					{
						ANDOperation(qe); break;
					}
					case MINUS:
					{
						MINUSOperation(qe); break;
					}
					default:
					{
						break;
					}
					}
				}
				else
				{
					// operand
					ArrayList operandArrList = getDocIdarrayList(qe.term,qe.indexType,indices);
					// System.out.println("Index: "+ qe.indexType+ " Operand: "+ qe.term);
					//  System.out.println("Doc list Size: "+ ((operandArrList==null)?0: operandArrList.size()));
					stack.push(operandArrList);
				}

			}}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return stack.pop();
	}

	private void ANDOperation(QueryEntity topQE)
	{
		ArrayList<Integer> rhs = stack.pop( );
		ArrayList<Integer> lhs =stack.pop( );
		if(lhs!= null && rhs!= null)
		{
			rhs.retainAll(lhs);
			stack.push(rhs);
		}
		else
		{
			stack.push(new ArrayList<Integer>());
		}
	}

	private void OROperation(QueryEntity topQE)
	{
		ArrayList<Integer> rhs = stack.pop( );
		ArrayList<Integer> lhs =stack.pop( );
		if(lhs== null && rhs== null)
		{
			stack.push(new ArrayList<Integer>());
		}
		else if(lhs!=null && rhs==null)
		{
			stack.push(lhs);
		}
		else if(rhs!=null && lhs==null)
		{
			stack.push(rhs);
		}
		else
		{
			rhs.addAll(lhs);
			stack.push(rhs);
		}
	}

	private void MINUSOperation(QueryEntity topQE)
	{
		ArrayList<Integer> rhs = stack.pop( );
		ArrayList<Integer> lhs =stack.pop( );
		
		if(lhs== null && rhs== null)
		{
			stack.push(new ArrayList<Integer>());
		}
		else if(lhs!=null && rhs==null)
		{
			stack.push(lhs);
		}
		else if(rhs!=null && lhs==null)
		{
			stack.push(new ArrayList<Integer>());
		}
		else
		{
			lhs.removeAll(rhs);
			stack.push(rhs);
		}
		
		
	}


	private ArrayList<Integer> getDocIdarrayList(String queryTerm, IndexType indexType, IndicesDTO indices)
	{
		ArrayList<Integer> docIDsList = new ArrayList<Integer>();
		DocumentIDFilter dc =new DocumentIDFilter(indices) ;
		if(isPhraseQuery(queryTerm))
		{
			docIDsList = dc.getDocIDArrayListForPhraseQueries(indexType,queryTerm); // phrase query
		}
		else
		{
			docIDsList = dc.getDocIDArrayList(indexType,queryTerm); // normal query
		}
		return docIDsList;
	}

	private boolean isPhraseQuery(String query)
	{
		return (query!= null 
				&& !query.isEmpty()
				&& query.startsWith("\"")
				&& query.endsWith("\"")
				);

	}



}
