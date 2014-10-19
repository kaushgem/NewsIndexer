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

	public ArrayList<Integer> evaluateQuery(IndexReader reader )
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
				stack.push(getDocIdarrayList(qe.term,qe.indexType,reader));
			}
		}
		return stack.pop();
	}

	private void ANDOperation(QueryEntity topQE)
	{
		ArrayList<Integer> rhs = stack.pop( );
		ArrayList<Integer> lhs =stack.pop( );
		rhs.retainAll(lhs);
		stack.push(rhs);
	}

	private void OROperation(QueryEntity topQE)
	{
		ArrayList<Integer> rhs = stack.pop( );
		ArrayList<Integer> lhs =stack.pop( );
		rhs.addAll(lhs);
		stack.push(rhs);
	}

	private void MINUSOperation(QueryEntity topQE)
	{
		ArrayList<Integer> rhs = stack.pop( );
		ArrayList<Integer> lhs =stack.pop( );
		lhs.removeAll(rhs);
		stack.push(rhs);
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
