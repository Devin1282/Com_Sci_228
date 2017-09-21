package edu.iastate.cs228.hw5;

/**
 * 
 * @author 
 *
 */


/**
 * 
 * This class represents a tree node.  The class is public but in the BST class 
 * the root node will be protected. 
 *
 */

// also okay to use the following. 
public class Node<E extends Comparable<? super E>>
{
	private E data; 
	private Node<E> parent; 
	private Node<E> left; 
	private Node<E> right; 
	
	
	public Node(E dat)
	{
		parent = null;
		left = null;
		right = null;
		data = dat;
	}

	public Node(E dat, Node<E> left, Node<E> right)
	{
		data = dat;
		this.left = left;
		this.right = right;
		parent = null;
	}
	
	/**
	 * Write the value of the instance variable named data.
	 */
	public String toString()
	{
		return data.toString(); 
	}

	public E getData()
	{
		return data; 
	}
	
	public void setData(E dat)
	{
		data = dat;
	}
	
	public Node<E> getParent()
	{
		return parent; 
	}
	
	public void setParent(Node<E> parent)
	{
		this.parent = parent; 
	}
	
	public Node<E> getLeft()
	{
		return left; 
	}
	
	public void setLeft(Node<E> left)
	{
		this.left = left;
	}
	
	public Node<E> getRight()
	{
		return right; 
	}
	
	public void setRight(Node<E> right)
	{
		this.right = right;
	}
	
	private int nodeComparator(Node<E> e)
	{
		return this.data.compareTo(e.data);
	}
}
