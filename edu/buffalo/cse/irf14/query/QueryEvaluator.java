/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Sathish
 *
 */
public class QueryEvaluator {

	ArrayList<QueryEntity> postfixExpression;
	Stack<HashMap<Integer,String>> stack ;

	public QueryEvaluator(ArrayList<QueryEntity> postfixExpression)
	{
		this.postfixExpression = postfixExpression;
		
		stack = new Stack<HashMap<Integer,String>>();
	}

	private void ANDOperation(QueryEntity topQE)
	{
		HashMap<Integer,String> rhs = stack.pop( );
		HashMap<Integer,String> lhs =stack.pop( );

		

	}

	private 

	public HashMap<Integer,String> evaluateQuery()
	{



		for (QueryEntity qe:postfixExpression) {

			if(qe.isOperator)
			{
				switch(qe.operator)
				{

				case OR:
				{

					break;
				}

				case AND:
				{
					break;
				}

				case MINUS:
				{
					break;
				}


				}
			}

			else
				
			{
				//stack.push(qe);
			}
		}
		System.out.println(stack.pop());


	}



}
