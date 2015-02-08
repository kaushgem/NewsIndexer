package edu.buffalo.cse.irf14.query.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.query.InfixExpression;
import edu.buffalo.cse.irf14.query.Operator;
import edu.buffalo.cse.irf14.query.QueryEntity;

public class InfixExpressionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGetInfixExpression() {
		String rep = "{ Term:orange AND Term:yellow }";
		InfixExpression infixEx = new InfixExpression(rep);
		ArrayList<QueryEntity> qEListActual = infixEx.getInfixExpression();
		QueryEntity[] qEListExpected = { new  QueryEntity( IndexType.TERM,
																	"orange",
																	null,
																	false,
																	false),
																	new  QueryEntity( IndexType.TERM,
																			null,
																			Operator.AND,
																			true,
																			false),
																			new  QueryEntity( IndexType.TERM,
																					"yellow",
																					null,
																					false,
			 																		false),
																			
		};
				
		int i=0;
		for(QueryEntity qeExpected:qEListExpected )
		{
			System.out.println("qeExpected.indexType: "+ qeExpected.indexType+ "qEListActual.get(i).indexType: "+qEListActual.get(i).indexType);
			 assertEquals(qeExpected.indexType,qEListActual.get(i).indexType);
			 
			 System.out.println("qeExpected.indexType: "+ qeExpected.indexType+ "qEListActual.get(i).indexType: "+qEListActual.get(i).indexType);
			 assertEquals(qeExpected.term,qEListActual.get(i).term);
			 
			 System.out.println("qeExpected.indexType: "+ qeExpected.indexType+ "qEListActual.get(i).indexType: "+qEListActual.get(i).indexType);
			 assertEquals(qeExpected.isOperator,qEListActual.get(i).isOperator);
			 
			 System.out.println("qeExpected.indexType: "+ qeExpected.indexType+ "qEListActual.get(i).indexType: "+qEListActual.get(i).indexType);
			 assertEquals(qeExpected.operator,qEListActual.get(i).operator);
			 
			 System.out.println("qeExpected.indexType: "+ qeExpected.indexType+ "qEListActual.get(i).indexType: "+qEListActual.get(i).indexType);
			 assertEquals(qeExpected.isEvaluated,qEListActual.get(i).isEvaluated);
			
		}
	}

	@Test
	public void testGetBagOfQueryWords() {
		// fail("Not yet implemented");
	}

}
