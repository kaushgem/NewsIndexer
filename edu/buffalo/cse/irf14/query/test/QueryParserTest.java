/**
 * 
 */
package edu.buffalo.cse.irf14.query.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buffalo.cse.irf14.query.Operator;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;

/**
 * @author Sathish
 *
 */
public class QueryParserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.query.Query#toString()}.
	 */
	@Test
	public void singleTerm() {
		
		String userQuery = "hello";
		String rep = "{ Term:hello }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	}
	@Test
	public void multipleTerms() {
		
		String userQuery = "hello world";
		String rep = "{ Term:hello OR Term:world }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	} 
	@Test
	public void phraseQuery() {
		
		String userQuery = "\"hello world\"";
		String rep = "{ Term:\"hello world\" }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	}
	@Test
	public void queryWithOperators() {
		
		String userQuery = "orange AND yellow";
		String rep = "{ Term:orange AND Term:yellow }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	}
	
	@Test
	public void queryWithBrackets() {
		
		String userQuery = "(black OR blue) AND bruises";
		String rep = "{ [ Term:black OR Term:blue ] AND Term:bruises }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	}
	
	@Test
	public void queryWithUserDefinedIndex() {
		
		String userQuery = "Author:rushdie NOT jihad";
		String rep = "{ Author:rushdie AND <Term:jihad> }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	}
	
	@Test
	public void queryWithUserNoProperBrackets() {
		
		String userQuery = "Category:War AND Author:Dutt AND Place:Baghdad AND prisoners detainees rebels";
		String rep = "{ Category:War AND Author:Dutt AND Place:Baghdad AND [ Term:prisoners OR Term:detainees OR Term:rebels ] }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
		
	}
	
	@Test
	public void queryWithUserGroupedCategory() {
		
		String userQuery = "(Love NOT War) AND Category:(movies NOT crime)";
		String rep = "{ [ Term:Love AND <Term:War> ] AND [ Category:movies AND <Category:crime> ] }";
		Operator defaultOp = Operator.OR;
		Query q =QueryParser.parse(userQuery, defaultOp.toString());
		assertEquals(rep, q.toString());
		
	}

}
