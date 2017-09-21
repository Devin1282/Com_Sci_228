package edu.iastate.cs228.hw4;


/**
 * @author Kelsey Hrubes
 */
import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;


public class InfixPostfixTests {
InfixExpression i1;
HashMap<Character, Integer> hM;
String infixExpression;


	@Test
	public void testInfixToString1() throws ExpressionFormatException{
		hM = new HashMap<>();
		infixExpression = "a  + b *  c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
	
		assertEquals("a + b * c", i1.toString());
	}
	
	
	@Test
	public void testInfixPostFix() throws ExpressionFormatException{
		hM = new HashMap<>();
		infixExpression = "a  + b *  c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
	
		assertEquals("a b c * +", i1.postfixString());
	}
	
	
	
	
	@Test (expected = ExpressionFormatException.class)
	public void testInvalidCharacterException() throws ExpressionFormatException{
		hM = new HashMap<>();
		infixExpression = "a  + b * # c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
	
	
	}
	
	@Test (expected = ExpressionFormatException.class)
	public void testExtraOperatorsException() throws ExpressionFormatException{
		hM = new HashMap<>();
		infixExpression = "a  + b * - c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
	
	
	}
	

	@Test (expected = ExpressionFormatException.class)
	public void testExtraOperandsException() throws ExpressionFormatException{
		hM = new HashMap<>();
		infixExpression = "a  + b * f c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
	
	
	}
	
	@Test
	public void testInfixEvaluate() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		hM.put('a', 3);
		hM.put('b', 5);
		hM.put('c', 4);
		infixExpression = "a  + b *  c";
		i1 = new InfixExpression(infixExpression, hM);
		
	
		assertEquals(23, i1.evaluate());
	}
	
	@Test (expected = UnassignedVariableException.class)
	public void testInfixUnassignedVariableEvaluate() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		hM.put('a', 3);
		hM.put('b', 5);
		infixExpression = "a  + b *  c";
		i1 = new InfixExpression(infixExpression, hM);
	    int i = i1.evaluate();
	}
	
	@Test
	public void testInfixToString2() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		infixExpression = "a * b    / c + d";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
		assertEquals("a * b / c + d", i1.toString());
	}
	
	@Test
	public void testInfixPostifx2() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		infixExpression = "a * b    / c + d";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
		assertEquals("a b * c / d +", i1.postfixString());
	}
	
	@Test
	public void testInfixEvaluate2() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		hM.put('a', 5);
		hM.put('b', 2);
		hM.put('c', 6);
		hM.put('d', 8);
		infixExpression = "a * b    / c + d";
		i1 = new InfixExpression(infixExpression, hM);
		
		assertEquals(9, i1.evaluate());
	}
	
	
	@Test
	public void testInfixToString3() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		infixExpression = "a  ^    b  ^   c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
		assertEquals("a ^ b ^ c", i1.toString());
	}
	
	@Test
	public void testInfixPostfix3() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		
		infixExpression = "a  ^    b  ^   c";
		i1 = new InfixExpression(infixExpression, hM);
		i1.postfix();
		assertEquals("a b c ^ ^", i1.postfixString());
	}
	

	@Test
	public void testInfixEvaluate3() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		hM.put('a', 7);
		hM.put('b', 2);
		hM.put('c', 3);
		infixExpression = "a  ^    b  ^   c";
		i1 = new InfixExpression(infixExpression, hM);

		assertEquals(5764801, i1.evaluate());
	}
	
	
	@Test (expected = ExpressionFormatException.class)
	public void testInfixExtraOperators2() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		infixExpression = "a  ^    b  ^ -  c";
		i1 = new InfixExpression(infixExpression, hM);
        i1.evaluate();
	}
	
	@Test
	public void testInfixPostfix4() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();

		infixExpression = "3 * (4 - 2 ^ 5) + 6";
		i1 = new InfixExpression(infixExpression, hM);
        String msg = "I'm just trolling you :) change the test to be '3 4 2 5 ^ - * 6 +'  ";
		assertEquals(msg, "3 4 2 5 * ^ - 6 +", i1.postfixString());
	}
	
	
	@Test (expected = ExpressionFormatException.class)
	public void testMissingLeftParenthesis() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		infixExpression = "a * ( b + c ))";
		i1 = new InfixExpression(infixExpression, hM);
        i1.evaluate();
	}
	
	@Test (expected = ExpressionFormatException.class)
	public void testMissingRightParenthesis() throws ExpressionFormatException, UnassignedVariableException{
		hM = new HashMap<>();
		infixExpression = "a * (( b + c )";
		i1 = new InfixExpression(infixExpression, hM);
        i1.evaluate();
	}
}
	