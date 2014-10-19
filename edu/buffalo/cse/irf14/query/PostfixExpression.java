package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.Stack;

public class PostfixExpression {


	ArrayList<QueryEntity> infixList;
	ArrayList<QueryEntity> postFixList;

	public PostfixExpression(ArrayList<QueryEntity> infixList)
	{
		this.infixList = infixList;
		postFixList = new ArrayList<QueryEntity>();
	}

	// reference: http://people.cs.clemson.edu/~turner/courses/cs102/spring98/section2/assignments/asg4/InfixToPostfix.java
	public ArrayList<QueryEntity> getPostfixExpression()
	{
		try
		{
		Stack operatorStack = new Stack();  
		for(QueryEntity qe:infixList)
		{     
			if (qe.isOperator ) {    
				while (!operatorStack.empty() &&
						!islowerPrecedence(((QueryEntity)operatorStack.peek()).operator, qe.operator))
					postFixList.add((QueryEntity)operatorStack.pop());
				if (qe.operator==Operator.CLOSE_PARANTHESIS) {
					QueryEntity operator = (QueryEntity)operatorStack.pop();
					while (operator.operator!=Operator.CLOSE_PARANTHESIS) {
						postFixList.add(operator);
						operator = (QueryEntity)operatorStack.pop();  
					}
				}
				else
					operatorStack.push(qe);
			}
			else {  
				postFixList.add(qe) ;
			}
		}
		while (!operatorStack.empty())
			postFixList.add((QueryEntity)operatorStack.pop());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return postFixList;
	}




	private boolean islowerPrecedence(Operator op1, Operator op2) {
		// verify if operator op1 is of lower precendence than operator op2
		switch (op1) {
		case OR:
			return !(op2==Operator.OR ) ;
		case AND:
			return op2== Operator.NOT || op2== Operator.OPEN_PARANTHESIS;
		case NOT:
			return op2==Operator.OPEN_PARANTHESIS;
		case OPEN_PARANTHESIS: return true;
		default:  
			return false;
		}

	} 




}
