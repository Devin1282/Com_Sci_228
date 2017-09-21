package edu.iastate.cs228.hw4;

/**
 *  
 * @author Devin Johnson
 *
 */

/**
 * 
 * This class represents operators '+', '-', '*', '/', '%', '^', '(', and ')'.  
 * Every operator has an input precedence, a stack precedence, and a rank, as specified 
 * in the table below. 
 * 
 *   operator       input precedence        stack precedence       rank
 *   ___________________________________________________________________ 
 *   +  -                   1                        1              -1
 *   *  /  %                2                        2              -1
 *   ^                      4                        3              -1
 *   (                      5                       -1               0
 *   )                      0                        0               0 
 *
 */

import java.lang.Comparable;

public class Operator implements Comparable<Operator> {
	public char operator; // operator

	private int inputPrecedence; // input precedence of operator in the range
									// [0, 5]
	private int stackPrecedence; // stack precedence of operator in the range
									// [-1, 3]

	/**
	 * Constructor
	 * 
	 * @param ch
	 */
	public Operator(char ch) {
		operator = ch;
	}

	/**
	 * Returns 1, 0, -1 if the stackPrecedence of this operator is greater than,
	 * equal to, or less than the inputPrecedence of the parameter operator op.
	 * Determines whether this operator on the stack should be output before
	 * pushing op onto the stack.
	 */
	@Override
	public int compareTo(Operator op) {
		if(getStackPrecedence(op.getOp()) > getInputPrecedence(op.getOp())) 
		{
			return 1;
		} 
		else if(getStackPrecedence(op.getOp()) == getInputPrecedence(op.getOp()))
		{
			return 0;
		} 
		else 
		{
			return -1;
		}
	}

	/**
	 * 
	 * @return char Returns the operator character.
	 */
	public char getOp() {
		return operator;
	}

	private static int getInputPrecedence(char c) {
		switch (c) {
		case '+':
			return 1;
		case '-':
			return 1;
		case '*':
			return 2;
		case '/':
			return 2;
		case '%':
			return 2;
		case '^':
			return 4;
		case '(':
			return 5;
		case ')':
			return 0;
		default:
			return 0;
		}
	}

	private static int getStackPrecedence(char c) {
		switch (c) {
		case '+':
			return 1;
		case '-':
			return 1;
		case '*':
			return 2;
		case '/':
			return 2;
		case '%':
			return 2;
		case '^':
			return 3;
		case '(':
			return -1;
		case ')':
			return 0;
		default:
			return 0;
		}
	}
	// delete before turning in
	@Override
	public String toString(){
		return this.operator+"";
	}
	
}
