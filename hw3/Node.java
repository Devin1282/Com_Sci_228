package edu.iastate.cs228.hw3;

/**
 * Node class that makes up a DoublingList. Feel free to add methods /
 * constructors / variables you might find useful in here.
 */
public class Node<E> {

	/**
	 * The node that comes after this one in the list
	 */
	private Node<E> next;
	
	/**
	 * The node that comes before this one in the list
	 */
	private Node<E> prev;
	
	/**
	 * The data held within this node
	 */
	private E[] data;

	/**
	 * The number of non-null elements in a node
	 */
	private int size;
	
	/**
	 * Constructs a new unlinked node with the given data
	 * @param data
	 */
	public Node(E[] data) {
		this(null, null, data);
		size = 0;
	}
	
	/**
	 * Constructs a new Node with the given information
	 * @param next
	 * @param prev
	 * @param data
	 */
	public Node(Node<E> next, Node<E> prev, E[] data) {
		this.next = next;
		this.prev = prev;
		this.data = data;
		size = 0;
	}
	
	/**
	 * Returns the node that comes after this node
	 * @return
	 */
	public Node<E> getNext() {
		return next;
	}

	/**
	 * Sets this node's next node to the given value
	 * @param next
	 */
	public void setNext(Node<E> next) {
		this.next = next;
	}

	/**
	 * Returns the node that comes before this node
	 * @return
	 */
	public Node<E> getPrev() {
		return prev;
	}

	/**
	 * Sets this node's previous node to the given value 
	 * @param prev
	 */
	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}

	/**
	 * Returns the data held within this node
	 * @return
	 */
	public E[] getData() {
		return data;
	}

	/**
	 * Sets the data held within this node to the given value
	 * @param data
	 */
	public void setData(E[] data) {
		this.data = data;
	}
	
	/**
	 * Returns the number of elements in the node that are not null
	 */
	public int getNodeSize()
	{
		int nodeSize =0;
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] != null)
			{
				nodeSize++;
			}
		}
		return nodeSize;
	}
}