/**

 * 
 */
package edu.buffalo.cse.irf14.query;
import edu.buffalo.cse.irf14.index.IndexType;




/**
 * @author Sathish
 *
 */
public class QueryEntity {

	/**
	 * 
	 */
	
	public IndexType indexType;
	public String term;
	public Operator operator;
	public boolean isOperator ;
	public boolean isEvaluated;
	public QueryEntity() {
		
		// TODO Auto-generated constructor stub
	
		
	}
	
	public QueryEntity(IndexType indexType, String term, Operator operator, boolean isOperator,boolean isEvaluated) {
		this.indexType = indexType;
		this.term = term;
		this.operator = operator;
		this.isOperator = isOperator;
		this.isEvaluated = isEvaluated;
	}
	
	

}
