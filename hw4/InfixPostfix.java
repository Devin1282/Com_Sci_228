package edu.iastate.cs228.hw4;

/**
 *  
 * @author Devin Johnson
 *
 */

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class InfixPostfix 
{

	/**
	 * Repeatedly evaluates input infix and postfix expressions.  See the project description
	 * for the input description. It constructs a HashMap object for each expression and passes it 
	 * to the created InfixExpression or PostfixExpression object. 
	 *  
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnassignedVariableException 
	 * @throws ExpressionFormatException 
	 **/
	public static void main(String[] args) throws FileNotFoundException, ExpressionFormatException, UnassignedVariableException 
	{   try{
		Scanner scanner = new Scanner(System.in);
		int trialCount = 1;
		System.out.println("Welcome to the Infix and Postfix Calculator!!");
		System.out.println("keys: 1 (standard input) 2 (file input) 3 (exit)");
		System.out.println("(Enter “I” before an infix expression, “P” before a postfix expression”)");
		System.out.println("Trial "+ trialCount +": ");
		boolean done = false;
		while(done == false)
		{
			trialCount++;
			String next = scanner.next();  // 1 or 2 or 3
			
			int type = Integer.parseInt(next);
			if(type == 1)
			{   System.out.println("Enter an expression");
			  //  String expressionType = scanner.next();
				String expression = scanner.next();
				char typeInput = expression.charAt(0);
				expression = scanner.next();
				System.out.println("Expression: " + expression);
				if(typeInput == 'I')
				{
					HashMap<Character, Integer> hashmap = new HashMap();
					InfixExpression infixExpression = new InfixExpression(expression, hashmap);
					Scanner expressionScanner = new Scanner(expression);
					ArrayList<Character> variableArray = new ArrayList<Character>();
					while(expressionScanner.hasNext())
					{
						String value = scanner.next();
						if(Character.isAlphabetic(value.charAt(0)))
						{
							variableArray.add(value.charAt(0));
						}
					}
					if(variableArray.size() > 0)
					{
						System.out.println("Variable Values: ");
						for(int i = 0; i < variableArray.size(); i++)
						{
							char variableName = variableArray.get(i);
							System.out.println(variableArray.get(i) + "=");
							int variableValue = scanner.nextInt();
							hashmap.put(variableArray.get(i), variableValue);
						}
						System.out.println("Infix Expression: "+ infixExpression.toString());
						System.out.println("Postfix Expression: " + infixExpression.postfixString());
						System.out.println("Value: " + infixExpression.evaluate());
					}
					else
					{
						System.out.println("Infix Expression: "+ infixExpression.toString());
						System.out.println("Postfix Expression: " + infixExpression.postfixString());
						System.out.println("Value: " + infixExpression.evaluate());
					}
				}
				else if(typeInput == 'P')
				{
					HashMap<Character, Integer> hashmap = new HashMap();
					PostfixExpression postfixExpression = new PostfixExpression(expression, hashmap);
					Scanner expressionScanner = new Scanner(expression);
					ArrayList<Character> variableArray = new ArrayList<Character>();
					while(expressionScanner.hasNext())
					{
						String value = scanner.next();
						if(Character.isAlphabetic(value.charAt(0)))
						{
							variableArray.add(value.charAt(0));
						}
					}
					if(variableArray.size() > 0)
					{
						System.out.println("Variable Values: ");
						for(int i = 0; i < variableArray.size(); i++)
						{
							char variableName = variableArray.get(i);
							System.out.println(variableArray.get(i) + "=");
							int variableValue = scanner.nextInt();
							hashmap.put(variableArray.get(i), variableValue);
						}
						System.out.println("Postfix Expression: " + postfixExpression.toString());
						System.out.println("Value: " + postfixExpression.evaluate());
					}
					else
					{
						System.out.println("Postfix Expression: " + postfixExpression.toString());
						System.out.println("Value: " + postfixExpression.evaluate());
					}
				}
			}
			else if(type == 2)
			{
				System.out.println("Input from a File");
				System.out.println("Enter File Name: ");
				String expressionFile = scanner.next();
				File expressionFile1 = new File(expressionFile);
				Scanner fileScanner = new Scanner(expressionFile1);
				System.out.println("Infix (I) or Postfix (P)?");
				String typeOfExpression = scanner.next();
				if(typeOfExpression.equals("I")) //infix expression coming
				{
					while(fileScanner.hasNext())
					{
						HashMap<Character, Integer> hashmap = new HashMap();
						InfixExpression infixExpression = new InfixExpression(fileScanner.next(), hashmap);
						Scanner expressionScanner = new Scanner(infixExpression.toString());
						ArrayList<Character> variableArray = new ArrayList<Character>();
						while(expressionScanner.hasNext())
						{
							String value = scanner.next();
							if(Character.isAlphabetic(value.charAt(0)))
							{
								variableArray.add(value.charAt(0));
							}
						}
						if(variableArray.size() > 0)
						{
							for(int i = 0; i < variableArray.size(); i++)
							{
								while(fileScanner.hasNext())
								{
									String variableLine = fileScanner.next();
									boolean digitNotYetFound = true;
									int j = 0;
									while(digitNotYetFound)
									{
										if(Character.isDigit(variableLine.charAt(j)))
										{
											hashmap.put(variableArray.get(i), Integer.parseInt(variableLine.charAt(j)+""));
										}
									}
								}
							}
							System.out.println("Infix Expression: "+ infixExpression.toString());
							System.out.println("Postfix Expression: " + infixExpression.postfixString());
							System.out.println("Value: " + infixExpression.evaluate());
						}
						else
						{
							System.out.println("Infix Expression: "+ infixExpression.toString());
							System.out.println("Postfix Expression: " + infixExpression.postfixString());
							System.out.println("Value: " + infixExpression.evaluate());
						}
					}
				}
				else if(typeOfExpression.equals("P"))//postfix expression coming
				{
					while(fileScanner.hasNext())
					{
						
					}
				}
			}
			else if(type == 3)
			{
				done = true;
				System.out.println("Ended");
			}
			else
			{
				System.out.println("please enter a valid input type");
			}
			
			
		}
	} catch (NumberFormatException n){
		System.out.println(n.getMessage());
	}
	
	
	}
	
}
