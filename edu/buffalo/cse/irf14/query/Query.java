package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;

/**
 * Class that represents a parsed query
 * @author nikhillo 
 *
 */
public class Query {
	/**
	 * Method to convert given parsed query into string
	 */


	String formatedQueryOutput ;
	String userQuery;
	Operator defaultOperator;
	ArrayList<QueryEntity> infixExpression;
	ArrayList<QueryEntity> postfixExpression;

	public Query (String userQuery, Operator defaultOperator,
			ArrayList<QueryEntity> infixExpression,
			ArrayList<QueryEntity> postfixExpression,
			String formatedQueryOutput)
	{
		this.defaultOperator = defaultOperator;
		this.userQuery = userQuery;
		this.formatedQueryOutput = formatedQueryOutput;
		this.infixExpression = infixExpression;
		this.postfixExpression = postfixExpression;
	}

	


	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS
		return formatedQueryOutput;
	}
}
