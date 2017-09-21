package edu.iastate.cs228.hw4;

/**
 *  
 * @author Devin Johnson
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * This class represents an infix expression. It implements infix to postfix conversion using 
 * one stack, and evaluates the postfix equivalent of an infix expression.    
 *
 */

public class InfixExpression 
{
	private String infixExpression;   // the infix expression to convert		
	private String postfixExpression; // the postfix equivalent of infixExpression
	private boolean postfixReady = false; 
	private boolean noLeftParenthesis;
	
	private PureStack<Operator> operatorStack; 	  // stack of operators 
	private HashMap<Character, Integer> varTable; // hash table storing variables in the 
												  // infix expression and their values 

	
	
	/**
	 * Constructor stores the input infix string, and initializes the operand stack and 
	 * the hash table.
	 * @param st  input infix string. 
	 * @param varTbl  hash table storing all variables in the infix expression and their values. 
	 */
	public InfixExpression (String st, HashMap<Character, Integer> varTbl)
	{
		infixExpression = expressionFixer(st);
		varTable = varTbl;
		this.operatorStack = new ArrayBasedStack<>();
		postfixExpression = "";
	}
	

	// Default constructor 
	public InfixExpression ()
	{
		infixExpression = null;
		this.operatorStack = new ArrayBasedStack<>();
		postfixExpression = "";
	}
	

	/**
	 * Outputs the infix expression according to the format in the project description.
	 */
	public String toString()
	{
		String toFix = infixExpression;
		String toReturn = "";
		Scanner scanner = new Scanner(toFix);
		while(scanner.hasNext())
		{
			String value = scanner.next();
			if(toReturn.length() == 0)
			{
				toReturn += value;
			}
			else if(toReturn.charAt(toReturn.length() - 1) == '(')
			{
				toReturn += value;
			}
			else if(value.charAt(0) == ')')
			{
				toReturn += value;
			}
			else
			{
				toReturn += " " + value;
			}
		}
		return toReturn.trim(); 
	}
	
	
	/** 
	 * 
	 * @return the equivalent postfix expression 
	 * Returns a null string if a call to postfix() inside the body (when postfixReady 
	 * == false) throws an exception.
	 */
	public String postfixString() 
	{
		try
		{
			if(postfixReady == false)
			{
				this.postfix();
			}
			postfixExpression.trim();
			return this.postfixExpression;
		}
		catch(Exception e)
		{
			return null; 
		}
	}


	/**
	 * Resets the infix expression. 
	 * @param st
	 */
	public void resetInfix (String st)
	{
		infixExpression = st; 
	}


	/**
	 * Converts infixExpression to an equivalent postfix string stored at postfixExpression.
	 * If postfixReady == false, the method scans the infixExpression, and does the following: 
	 * 
	 *     1. Skips a whitespace character.
	 *     2. Writes a scanned operand to postfixExpression.
	 *     3. If a scanned operator has a higher input precedence than the stack precedence of 
	 *        the operator on the top of operatorStack, push it onto the stack.   
	 *     4. Otherwise, first calls outputHigherOrEqual() before pushing the scanner operator 
	 *        onto the stack. No push if the scanned operator is ). 
     *     5. Keeps track of the cumulative rank of the infix expression. 
     *     
     *  During the conversion, catches errors in the infixExpression by throwing 
     *  ExpressionFormatException with one of the following messages:
     *  
     *      -- "Operator expected" if the cumulative rank goes above 1;
     *      -- "Operand expected" if the rank goes below 0; 
     *      -- "Missing '('" if scanning a ‘)’ results in popping the stack empty with no '(';
     *      -- "Missing ')'" if a '(' is left unmatched on the stack at the end of the scan; 
     *      -- "Invalid character" if a scanned char is neither a digit nor an operator; 
     *      -- "Negative operand not allowed" if the input operand is negative
     *   
     *   If an error is not one of the above types, throw the exception with a message you define.
     *      
     *  Sets postfixReady to true.  
	 */
	public void postfix() throws ExpressionFormatException
	{
		int totalRank = 0;
		String s = infixExpression;
		if(!postfixReady)
		{
			Scanner scanner = new Scanner(s);
			while(scanner.hasNext())
			{
				if(totalRank < 0)
				{
					if(operatorStack.peek().getOp() == '-')
					{
						throw new ExpressionFormatException("Negative operand not allowed");
					}
					throw new ExpressionFormatException("Operand expected");
				}
				if(totalRank > 1)
				{
					throw new ExpressionFormatException("Operator expected");
				}
				String value = scanner.next();
				if(whatIsTheChar(value.charAt(0)) == 0)//if it is an operand
				{
					postfixExpression += value;
					totalRank += 1;
				}
				else if(whatIsTheChar(value.charAt(0)) == 1)//if it is an operator
				{
					if(value.charAt(0) == ')')
					{
						while(operatorStack.peek().operator != '(')
						{
							postfixExpression += operatorStack.pop();
							totalRank += getRank(value.charAt(0));
							if(operatorStack.size() == 0)
							{
								throw new ExpressionFormatException("Missing '('");
							}
						}
						operatorStack.pop();
					}
					else if(operatorStack.size() == 0)//if stack is empty
					{
						operatorStack.push(new Operator(value.charAt(0))); 
					}
					else
					{
						outputHigherOrEqual(new Operator(value.charAt(0)));
					}
				}
				else//if it is neither an operator or operand
				{
					throw new ExpressionFormatException("Invalid character");
				}
			}
			while(operatorStack.size() > 1)
			{
				postfixExpression += operatorStack.pop();
			}
			if(operatorStack.peek().getOp() == '(')
			{
				throw new ExpressionFormatException("Missing ')'");
			}
			postfixReady = true;
		}
	}
	
	
	/**
	 * This function first calls postfix() to convert infixExpression into postfixExpression. Then 
	 * it creates a PostfixExpression object and calls its evaluate() method (which may throw  
	 * an exception).  It passes exceptions thrown by the evaluate() method of the 
	 * PostfixExpression object upward the chain. 
	 * 
	 * @return value of the infix expression 
	 * @throws ExpressionFormatException, UnassignedVariableException
	 */
	public int evaluate() throws ExpressionFormatException, UnassignedVariableException 
    {
    	postfix();
    	PostfixExpression letsEvaluate = new PostfixExpression(postfixExpression, varTable);
		return letsEvaluate.evaluate();
    }


	/**
	 * Pops the operator stack and output as long as the operator on the top of the stack has a 
	 * stack precedence greater than or equal to the input precedence of the current operator op.  
	 * Writes the popped operators to the string postfixExpression. 
	 * 
	 * If op is a ')', and the  top of the stack is a '(', also pops '(' from the stack but does 
	 * not write it to postfixExpression. 
	 * 
	 * @param op  Current operator
	 */
	private void outputHigherOrEqual(Operator op)
	{
		if(operatorStack.peek().compareTo(op) >= 0)
		{
			if(op.getOp()==')' && operatorStack.peek().getOp()=='(')  //changed to ||
			{
				operatorStack.pop();
			}
			else if(op.getOp()==')')
			{
				while(operatorStack.peek().getOp() != '(')
				{
					if(operatorStack.peek().getOp() != '(' && operatorStack.peek().getOp() != ')')
					{
						postfixExpression += operatorStack.pop().getOp() + " ";
					}
					if(operatorStack.isEmpty())
					{
						noLeftParenthesis = true;
						break;
					}
				}
				if(noLeftParenthesis == false && operatorStack.peek().getOp() == '(')
				{
					operatorStack.pop();
				}
			}
			else
			{
				postfixExpression += operatorStack.pop().getOp() + " ";
			}
		}
		else
		{
			operatorStack.push(op);
		}
	}
	
	private static String expressionFixer(String s)
	{
		Scanner scanner = new Scanner(s);
		String fixed = "";
		while(scanner.hasNext())
		{
			String value = scanner.next();
			if(value.length() > 1)
			{
				char[] array = value.toCharArray();
				String temp = "";
				int i = 0;
				while(i<array.length)
				{
					if(whatIsTheChar(array[i]) == 0)//is an operand
					{
						boolean keepGoing = true;
						while(keepGoing)
						{
							temp += array[i];
							if(i + 1 < array.length && whatIsTheChar(array[i + 1]) == 0)
							{
								i++;
							}
							else
							{
								keepGoing = false;
							}
						}
						fixed += (temp + " ");
						temp = "";
					}
					else
					{
						fixed += (array[i] + " ");
					}
					i++;
				}
			}
			else
			{
				fixed += (value + " ");
			}
		}
		//remove any spaces at the end of the expression
		fixed.trim();
		return fixed;
	}
	
	//operators are 1
	//operands are 0
	//invalids are -1
	private static int whatIsTheChar(char c)
	{
		if(Character.isDigit(c))
		{
			return 0;
		}
		else if(Character.isAlphabetic(c))
		{
			return 0;
		}
		else
		{
			switch(c)
			{
			case '+':
				return 1;
			case '-':
				return 1;
			case '*':
				return 1;
			case '/':
				return 1;
			case '%':
				return 1;
			case '^':
				return 1;
			case '(':
				return 1;
			case ')':
				return 1;
			default:
				return -1;
			}
		} 
	}
	
	private static int getRank(char c)
	{
		if(Character.isAlphabetic(c) || Character.isDigit(c))
		{
			return 1;
		}
		else
		{
			switch(c)
			{
			case '+':
				return -1;
			case '-':
				return -1;
			case '*':
				return -1;
			case '/':
				return -1;
			case '%':
				return -1;
			case '^':
				return -1;
			case '(':
				return 0;
			case ')':
				return 0;
			default:
				return 0;
			}
		}
	}
	
	
	
}
