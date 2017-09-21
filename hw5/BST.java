package edu.iastate.cs228.hw5;

/**
 *  
 * @author
 *
 */


import java.util.AbstractSet;
import java.util.Iterator;
import java.util.ArrayList; 
import java.lang.IllegalArgumentException; 



/**
 * Binary search tree implementation
 */
public class BST<E extends Comparable<? super E>> extends AbstractSet<E>
{
	private Node<E> root;
	private int size;      
  
	private ArrayList<E>  preorderArr;	// stores the key values from a preorder traversal
	private ArrayList<E>  inorderArr;	// stores the key values from an inorder traversal
	private ArrayList<E>  postorderArr;	// stores the key values from a postorder traversal
	private String preorderString;
	private String inorderString;
	private String postorderString;
  
  
	/*
	 * These tags will be set to false respectively at the ends of calls to traversePreorder(), 
	 * traverseInorder(), and traversePostorder(). They must be set back to true whenever
	 * the binary search tree is modified by add(), remove(), leftRotate(), and rightRotate(). 
	 */
	private boolean redoPreorder = true; 	
	private boolean redoInorder = true; 
	private boolean redoPostorder = true; 
  
	

	// ------------
	// Constructors
	// ------------
  
	/**
	 * Default constructor builds an empty tree. 
	 */
	public BST()
	{
		root = null;
		size = 0;
		preorderArr = null;
		inorderArr = null;
		postorderArr = null;
	}
	
	
	/**
	 * Constructor from an existing tree (manually set up for testing) 
	 * @param root
	 * @param size
	 */
	public BST(Node<E> root, int size) 
	{
		this.root = root;
		this.size = size;
		this.preorderArr = new ArrayList<E>();
		this.inorderArr = new ArrayList<E>();
		this.postorderArr = new ArrayList<E>();
	 }
  

	/**
	 * Constructor over an element array.  Elements must be inserted sequentially in order of 
	 * increasing index from the array.  
	 * 
	 * @param eleArray
	 */
	public BST(E[] eleArray)
	{
		
	}
 
  
	/**
	 * Copy constructor.  It takes a binary tree with a given root as input, and calls isBST() to check 
	 * if it is indeed a binary search tree. If not, throws a TreeStructureException with the message 
	 * "Copying a non-BST tree".  If so, makes a deep copy of the input tree such that the resulting BST
	 * assumes the same structure and has the same key stored at every corresponding node.  
	 * 
	 * @param rt  root of an existing binary tree 
	 */
	public BST(Node<E> root) throws TreeStructureException
	{
		// TODO 
	}
  
	

	// -------
	// Getters
	// -------
  
	/**
	 * This function is here for grading purpose not as a good programming practice.
	 * @return root of the BST
	 */
	public Node<E> getRoot()
	{
		return root; 
	}

	
	public int size()
	{
		return size; 
	}
	
	
	/**
	 * 
	 * @return tree height 
	 */
	public int height()
	{
		// TODO 
		
		return 0; 
	}

	/**
	 * This method must be implemented by operating over the tree without using either of 
	 * the array lists preorderArr, inorderArr, and postorderArr. 
	 * 
	 * @return	the minimum element in the tree or null in the case of an empty tree 
	 */
	public E min()
	{
		Node<E> minimum = root;
		while(minimum.getLeft() != null)
		{
			minimum = minimum.getLeft();
		}
		return minimum.getData(); 
	}
	
	
	/**
	 * This method must be implemented by operating over the tree without using either of 
	 * the array lists preorderArr, inorderArr, and postorderArr. 
	 * 
	 * @return	the maximum element in the tree or null in the case of an empty tree 
	 */
	public E max()
	{
		Node<E> maximum = root;
		while(maximum.getRight() != null)
		{
			maximum = maximum.getRight();
		}
		return maximum.getData(); 
	}
	
	
	/**
	 * Calls traversePreorder() and copy the content of preorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getPreorderSequence(ArrayList<E> arr)
	{
		if(redoPreorder == true)
		{
			traversePreorder();
		}
		for(int i = 0; i < preorderArr.size(); i++)
		{
			arr.add(i, preorderArr.get(i));
		}
	}
	

	/**
	 * Calls traverseInorder() and copy the content of inorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getInorderSequence(ArrayList<E> arr)
	{
		if(redoInorder == true)
		{
			traverseInorder();
		}
		for(int i = 0; i < inorderArr.size(); i++)
		{
			arr.add(i, inorderArr.get(i));
		}
	}
	
	
	/**
	 * Calls traversePostorder() and copy the content of postorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getPostorderSequence(ArrayList<E> arr)
	{
		if(redoPostorder == true)
		{
			traversePostorder();
		}
		for(int i = 0; i < postorderArr.size(); i++)
		{
			arr.add(i, postorderArr.get(i));
		}
	}	
	
		
	
	// -----------
	// Comparators 
	// -----------
	
	/**
	 * Returns true if the tree and a second tree o have exactly the same structure, and equal 
	 * elements stored at every pair of corresponding nodes.  
	 */
	@Override
	public boolean equals(Object o) 
	{
		if(o == null)
		{
			return false;
		}
		if(o.getClass() != this.getClass())
		{
			return false;
		}
		BST<E> newTree = (BST<E>) o;
		
		this.traverseInorder();
		this.traversePostorder();
		this.traversePreorder();
		newTree.traverseInorder();
		newTree.traversePostorder();
		newTree.traversePreorder();
		
		if(!this.inorderString.equals(newTree.inorderString))
		{
			return false;
		}
		if(!this.postorderString.equals(newTree.postorderString))
		{
			return false;
		}
		if(!this.preorderString.equals(newTree.preorderString))
		{
			return false;
		}
		 
		return true; 
	}
	
	
	
	/** 
	 * Returns true if two binary search trees store the same set of elements, and false otherwise.   
	 * The tree rooted at tree is also a binary search tree.   
	 *    
	 * @param rt
	 * @return
	 */
	public boolean setEquals(BST<E> tree)
	{
		String first = this.traverseInorder();
		String second = tree.traverseInorder();
		return first.equals(second); 
	}

	
	
	// ----------
	// Traversals
	// ----------
	
	/**
	 * Performs a preorder traversal of the tree, stores the result in the array list preOrderArr, and also 
	 * write the key values to a string in which they are separated by blanks (exactly one blank  
	 * between two adjacent key values). 
	 *  
	 * No need to perform the traversal if redoPreorder == false. 
	 */
	public String traversePreorder()
	{
		if(redoPreorder == false)
		{
			return preorderString;
		}
		else
		{
			this.preorderRecusiveMethod(root);
		}
		redoPreorder = false;
		return preorderString; 
	}
  
	private void preorderRecusiveMethod(Node<E> node)
	{
		if(node == null)
		{
			return;
		}
		preorderString = node.getData().toString() + " ";
		preorderArr.add(node.getData());
		preorderRecusiveMethod(node.getLeft());
		preorderRecusiveMethod(node.getRight());
	}
  
	/**
	 * Performs an inorder traversal of the tree, and stores the result in the array list inOrderArr. 
	 * No need to perform the traversal if redoInorder == false. 
	 */
	public String traverseInorder()
	{
		if(redoInorder == false)
		{
			return inorderString;
		}
		else
		{
			this.inorderRecusiveMethod(root);
		}
		redoInorder = false;
		return inorderString; 
	}  
  
	private void inorderRecusiveMethod(Node<E> node)
	{
		if(node == null)
		{
			return;
		}
		inorderRecusiveMethod(node.getLeft());
		inorderString = node.getData().toString() + " ";
		inorderArr.add(node.getData());
		inorderRecusiveMethod(node.getRight());
	}
  
	/**
	 * Performs a postorder traversal of the tree, and stores the result in the array list preOrderArr. 
	 * No need to perform the traversal if redoPostorder == false. 
	 */   
	public String traversePostorder()
	{
		if(redoPostorder == false)
		{
			return preorderString;
		}
		else
		{
			this.postorderRecusiveMethod(root);
		} 
		redoPostorder = false;
		return postorderString; 
	}

	private void postorderRecusiveMethod(Node<E> node)
	{
		if(node == null)
		{
			return;
		}
		postorderRecusiveMethod(node.getLeft());
		postorderRecusiveMethod(node.getRight());
		postorderString = node.getData().toString() + " ";
		postorderArr.add(node.getData());
	}
	
  
	// -------------
	// Query Methods
	// -------------
	
	/**
	 * Returns the number of keys with values >= minValue and <= maxValue, and stores these key values 
	 * in the array eleArray[] in the increasing order.  Note that minValue and maxValue may not be any
	 * of the key values stored in the tree. 
	 * 
	 * Exception is thrown if minValue > maxValue. 
	 *  
	 * @param minValue	lower bound for query values 
	 * @param maxValue  upper bound for query values 
	 * @param eleArray	stores elements >= minValue and <= maxValue 
	 * @return			number of elements in the interval [minValue, maxValue]
	 */
	public int rangeQuery(E minValue, E maxValue, E[] eleArray) throws IllegalArgumentException 
	{
		// TODO
		
		return 0; 
	}
	
	
	/**
	 * Get the keys that are between the imin-th and the imax-th positions from an inorder traversal. 
	 * The first visited node is at position 0.  Store these keys in eleArray[] in their original order. 
	 * 
	 * Exception is thrown if 1) imax < imin, 2) imin < 0, or 3) imax >= size. 
	 * 
	 * @param imin			minimum index of the keys to be searched for according to inorder
	 * @param imax			maximum index of the keys to be searched for according to inorder
	 * @param eleArray		stores the found keys 
	 * @return
	 */
	public void orderQuery(int imin, int imax, E[] eleArray) throws IllegalArgumentException 
	{
		// TODO 
	}

	
    
	// --------------------------
	// Operations related to Keys
	// --------------------------
  
	@Override
	public boolean contains(Object obj)
	{
		// from BSTSet.java 
		
		return false; 
	}
  

	@Override
	public boolean add(E key)
	{
		boolean canAdd = false;
		if(key == null)
		{
			throw new NullPointerException();
		}
		if(root == null)
		{
			root = new Node<E>(key);
			redoInorder = true;
			redoPostorder = true;
			redoPreorder = true;
			size++;
			return true;
		}
		if(this.findEntry(key) == null)
		{
			canAdd = true;
		}
		if(canAdd == true)
		{
			Node<E> current = root;
			while(true)
			{
				int num = current.getData().compareTo(key);
				if(num > 0)
				{
					if(current.getLeft() != null)
					{
						current = current.getLeft();
					}
					else
					{
						current.setLeft(new Node<E>(key));
						size++;
						redoInorder = true;
						redoPostorder = true;
						redoPreorder = true;
						return true;
					}
				}
				else
				{
					if(current.getRight() != null)
					{
						current = current.getRight();
					}
					else
					{
						current.setRight(new Node<E>(key));
						size++;
						redoInorder = true;
						redoPostorder = true;
						redoPreorder = true;
						return true;
					}
				}
			}
		}
		else
		{
			return false;
		} 
	}
  
	@Override
	public boolean remove(Object obj)
	{
		E key = (E) obj;
	    Node<E> n = findEntry(key);
	    if (n == null)
	    {
	      return false;
	    }
	    unlinkNode(n);
		redoInorder = true;
		redoPostorder = true;
		redoPreorder = true;
		size--; 
	    return true;

	}
  
  
	/**
	 * Returns the node containing key, or null if the key is not
	 * found in the tree.
	 * @param key
	 * @return the node containing key, or null if not found
	 */
	protected Node<E> findEntry(E key)
	{
		Node<E> current = root;
	    while (current != null)
	    {
	      int value = current.getData().compareTo(key);
	      if (value == 0)
	      {
	        return current;
	      }
	      else if (value > 0)
	      {
	        current = current.getLeft();
	      }
	      else
	      {
	        current = current.getRight();
	      }
	    }   
		return null; 
	}
  
  
	
	// -------------------
	// Operations on Nodes
	// -------------------

	/**
	 * Returns the successor of the given node.
	 * @param n
	 * @return the successor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	protected Node<E> successor(Node<E> n)
	{
		if (n == null)
	    {
	      return null;
	    }
	    else if (n.getRight() != null)
	    {
	      Node<E> current = n.getRight();
	      while (current.getLeft() != null)
	      {
	        current = current.getLeft();
	      }
	      return current;
	    }
	    else 
	    {
	      Node<E> current = n.getParent();
	      Node<E> child = n;
	      while (current != null && current.getRight() == child)
	      {
	        child = current;
	        current = current.getParent();
	      }
	      return current;
	    } 
	}
  

	/**
	 * Returns the predecessor of a node.
	 * @param n
	 * @return the predecessor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	public Node<E> predecessor(Node<E> n)
	{
		if(n == null)
		{
			return null;
		}
		
	}

	
	/**
	 * Performs left rotation on a node 
	 * Reset tags redoPreorder, redoInorder, redoPostorder 
	 * 
	 * @param n
	 */
	public void leftRotate(Node<E> n)
	{
		// TODO 
	}
  
	/**
	 * Performs right rotation on a node 
	 * Reset tags redoPreorder, redoInorder, redoPostorder 
	 * 
	 * @param n
	 */
	public void rightRotate(Node<E> n)
	{
		// TODO 
	}
  
		
  
	/**
	 * Removes the given node, preserving the binary search
	 * tree property of the tree.
	 * @param n node to be removed
	 */
	protected void unlinkNode(Node<E> n)
	{
		if(n.getLeft() != null && n.getParent() != null)
		{
			Node<E> s = successor(n);
			s.setData(n.getData());
			n = s;
		}
		
		Node<E> replacement = null;
		if(n.getLeft() != null)
		{
			replacement = n.getLeft();
		}
		else if(n.getRight() != null)
		{
			replacement = n.getRight();
		}
		
		if(n.getParent() == null)
		{
			root = replacement;
		}
		else
		{
			if(n == n.getParent().getLeft())
			{
				n.getParent().setLeft(replacement);
			}
			else
			{
				n.getParent().setRight(replacement);
			}
		}
		if(replacement != null)
		{
			n.setParent(replacement.getParent());
		}
		redoInorder = true;
		redoPostorder = true;
		redoPreorder = true;
		size--;
	}
	

	
	// -------------
	// String output
	// -------------
 
	/**
	 * Returns a representation of this tree as a multi-line string.
	 * The tree is drawn with the root at the left and children are
	 * shown top-to-bottom.  Leaves are marked with a "-" and non-leaves
	 * are marked with a "+".
	 */
	@Override
	public String toString()
	{
	    StringBuilder sb = new StringBuilder();
	    toStringRec(root, sb, 0);
	    return sb.toString();
	}
	
	 /**
	   * Preorder traversal of the tree that builds a string representation
	   * in the given StringBuilder.
	   * @param n root of subtree to be traversed
	   * @param sb StringBuilder in which to create a string representation
	   * @param depth depth of the given node in the tree
	   */
	private void toStringRec(Node<E> n, StringBuilder sb, int depth)
	  {
	    for (int i = 0; i < depth; ++i)
	    {
	      sb.append("  ");
	    }
	    
	    if (n == null)
	    {
	      sb.append("-\n");
	      return;
	    }
	    
	    if (n.getLeft() != null || n.getRight() != null)
	    {
	      sb.append("+ ");
	    }
	    else
	    {
	      sb.append("- ");
	    }
	    sb.append(n.getData().toString());
	    sb.append("\n");
	    if (n.getLeft() != null || n.getRight() != null)
	    {
	      toStringRec(n.getLeft(), sb, depth + 1);   
	      toStringRec(n.getRight(), sb, depth + 1);
	    }
	  }
 
	/**
	 * Iterator implementation is from BSTSet.java and thus not required here. 
	 */	
	@Override
	public Iterator<E> iterator()
	{
	    return null;
	}

  
	/**
	 * Checks if the tree with a given root is a binary search tree. 
	 * @param rt
	 */
	private boolean isBST(Node<E> root)
	{
		// TODO
		
		return false; 
	}  

}
