/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;

/**
 * @author Sathish
 *
 */
public class QueryEvaluator {
	
	ArrayList<QueryEntity> postfixExpression;
	
	public QueryEvaluator(ArrayList<QueryEntity> postfixExpression)
	{
		this.postfixExpression = postfixExpression;
	}
	

}
