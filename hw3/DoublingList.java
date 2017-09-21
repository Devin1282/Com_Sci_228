package edu.iastate.cs228.hw3;


import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list that stores data in Nodes with varying size arrays as
 * the backing store.
 * Important Note: Your index-based methods remove(int pos), add(int pos, E
 * item) and listIterator(int pos) must not traverse every element in order to
 * find the node and offset for a given index pos (see spec for more details)
 */

public class DoublingList<E> extends AbstractSequentialList<E> {

	/**
	 * Node to keep track of the head (beginning of the list)
	 */
	private Node<E> head;

	/**
	 * Node to keep track of the tail (end of the list)
	 */
	private Node<E> tail;
	
	/**
	 * The number of actual nodes in the list
	 */
	private int numNodes;
	
	/**
	 * A count of the number of data elements in the list
	 */
	private int size;
	
	/**
	 * List interator
	 */

	/**
	 * Constructs an empty DoublingList
	 */
	public DoublingList() {
		E[] arr = (E[]) new Object[1];
		
		Node<E> initialNode = new Node<>(tail, head, arr);
		this.head = new Node<E>(initialNode, null, null);
		this.tail = new Node<E>(null, initialNode, null);
		head.setNext(initialNode);
		tail.setPrev(initialNode);
		initialNode.setPrev(head);
		initialNode.setNext(tail);
		numNodes = 1;
		size = 0;
	}
	
	/**
	 * A constructor to be called by the tests when it's necessary to manually create
	 * the internal structure of the list. 
	 * 
	 * NOTE: In real life you would never have this constructor. It is simply used so
	 * we can test your remove methods without relying on the add methods working properly
	 * 
	 * @param head
	 * @param tail
	 * @param numNodes
	 * @param size
	 */
	public DoublingList(Node<E> head, Node<E> tail, int numNodes, int size) {
		this.head = head;
		this.tail = tail;
		this.numNodes = numNodes;
		this.size = size;
		E[] arr = (E[]) new Object[1];

		Node<E> initialNode = new Node(null, null, arr);
	
		head.setNext(initialNode);
		initialNode.setPrev(head);
		initialNode.setNext(tail);
		tail.setPrev(initialNode);
		numNodes +=1;
		
		/*
		 * TODO any additional initialization code you need.
		 * It is not necessarily the case that you need anything here. 
		 */
	}
	
	/**
	 * Returns the head node of the list.
	 * 
	 *  NOTE: Again, in real life you would never have this method. It is just used 
	 *  in the tests so we don't need to rely on your get method.
	 * @return
	 * 		the head node of the list
	 */
	public Node<E> getHeadNode() {
		return head;
	}
	
	/**
	 * Returns the tail node of the list.
	 * 
	 *  NOTE: Again, in real life you would never have this method. It is just used 
	 *  in the tests so we don't need to rely on your get method.
	 * @return
	 * 		the tail node of the list
	 */
	public Node<E> getTailNode() {
		return tail;
	}
	
	/**
	 * Removes the element with the given logical position, following the rules
	 * for removing an element.
	 */
	@Override
	public E remove(int pos) {
		NodeInfo removeSpot = getNodeInfo(pos);
		if(pos < 0 || pos > removeSpot.node.getData().length)
		{
			throw new IllegalStateException();
		}
		E data = removeSpot.node.getData()[removeSpot.offset];
		removeSpot.node.getData()[removeSpot.offset] = null;
		if(size < Math.pow(2, numNodes - 2))
		{
			Node<E> current = head.getNext();
			ArrayList<E> arr = new ArrayList<E>();
			while(current.getNext() != tail)
			{
				for(int i = 0; i < current.getData().length; i++)
				{
					if(current.getData()[i] != null)
					{
						arr.add(current.getData()[i]);
					}
				}
				current = current.getNext();
			}
			current = head.getNext();
			int timesLeftToAdd = arr.size();
			int j = 0;
			while(timesLeftToAdd > 0)
			{
				for(int i = 0; i < current.getData().length; i++)
				{
					current.getData()[i] = arr.get(j);
					j++;
				}
				current = current.getNext();
			}
		}
		return data;
	}

	/**
	 * Adds the given item to have the given logical position. Adds a new Node
	 * if necessary. Follows the rules stated by leftward and rightward shift.
	 */
	@Override
	public void add(int pos, E item) {
		if(pos > size || pos < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		if(size == 0)
		{
			if(head.getNext() == tail)
			{
				E[] arr = (E[]) new Object[1];
				Node<E> createdNew = new Node<>(tail, head, arr);
				tail.setPrev(createdNew);
				head.setNext(createdNew);
			}
			head.getNext().getData()[0] = item;
			size++;
		}
		else if(size == ((int) (Math.pow(2,  numNodes)) - 1))
		{
			int createdSize = ((int) (Math.pow(2, numNodes + 1)) - 1) - ((int) (Math.pow(2, numNodes)) - 1);
			E[] contents = (E[]) new Object[createdSize];
			Node<E> newNode = new Node<>(contents);
			newNode.setPrev(tail.getPrev());
			newNode.setNext(tail);
			tail.setPrev(newNode);
			newNode.getPrev().setNext(newNode);
			numNodes++;
			if( pos == size)
			{
				newNode.getData()[0] = item;
				size++;
			}
		}
		//left shift
		else if(existsEmptiesLeft(pos) != null)
		{
			leftShift(existsEmptiesLeft(pos), pos);
		}
		//right sift
		else if(existsEmptiesRight(pos) != null)
		{
			if(pos == -1)
			{
				tail.getPrev().getData()[tail.getPrev().getData().length-1] = item;
				size++;
			}
			else
			{
			rightShift(existsEmptiesRight(pos), pos);
			}
		}
		NodeInfo whereToPut = getNodeInfo(pos);
		
		whereToPut.node.getData()[whereToPut.offset] = item;
		
		
	}

	/**
	 * Adds the given item to the end of the list. Creates a new Node if
	 * Necessary. Return true if the add was successful, false otherwise.
	 * 
	 * @throws NullPointerException 
	 * 				If the item is null.
	 */
	@Override
	public boolean add(E item) {
		if(item == null)
		{
			throw new NullPointerException();
		}
		add(size, item);
		return true;
	}

	/**
	 * Returns a ListIterator for this DoublingList at the given position (I.E.
	 * a call to next should return the element with the logical index equal to
	 * the index given)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new DoublingListIterator(index);
	}

	/**
	 * Returns a ListIterator for this DoublingList starting from the beginning
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new DoublingListIterator(0);
	}

	/**
	 * Returns an Iterator for this DoublingList starting from the beginning
	 */
	@Override
	public Iterator<E> iterator() {
		return new DoublingIterator();
	}

	/**
	 * Returns the size of the list. It is acceptable to use the size instance
	 * variable and update it during add / remove so you can just return that
	 * variable here.
	 */
	@Override
	public int size() {
		return size;
	}
	
	private NodeInfo existsEmptiesLeft(int position)
	{
		Node<E> nodeCursor = head.getNext();
		NodeInfo foundEmpty = null;
		int indexCursor = 0;
		while(nodeCursor != getNodeInfo(position).node && indexCursor != getNodeInfo(position).offset)
		{
			if(indexCursor > nodeCursor.getData().length)
			{
				indexCursor = 0;
				nodeCursor = nodeCursor.getNext();
			}
			if(nodeCursor.getData()[indexCursor] == null)
			{
				foundEmpty = new NodeInfo(nodeCursor, indexCursor);
			}
			else
			{
				indexCursor++;
			}
		}
		return foundEmpty;
	}
	
	private NodeInfo existsEmptiesRight(int position)
	{
		Node<E> nodeCursor = getNodeInfo(position).node;
		NodeInfo foundEmpty = null;
		int indexCursor = 0;
		while(nodeCursor != tail)
		{
			if(indexCursor >= nodeCursor.getData().length)
			{
				indexCursor = 0;
				nodeCursor = nodeCursor.getNext();
			}
			if(nodeCursor.getData()[indexCursor] == null)
			{
				foundEmpty = new NodeInfo(nodeCursor, indexCursor);
				break;
			}
			else
			{
				indexCursor++;
			}
		}
		return foundEmpty;
	}
	
	private void leftShift(NodeInfo emptySpot, int position)
	{
		Node<E> nodeCursor = emptySpot.node;
		int indexCursor = emptySpot.offset;
		NodeInfo nodeInfoAddPosition = getNodeInfo(position);
		while(!(nodeCursor == nodeInfoAddPosition.node && indexCursor ==nodeInfoAddPosition.offset))
		{
			while(indexCursor + 1 < nodeCursor.getData().length)
			{
				if(nodeCursor == nodeInfoAddPosition.node && indexCursor == nodeInfoAddPosition.offset)
				{
					break;
				}
				nodeCursor.getData()[indexCursor + 1] = nodeCursor.getData()[indexCursor];
				indexCursor = indexCursor + 1;
			}
			nodeCursor.getPrev().getData()[nodeCursor.getPrev().getData().length - 1] = nodeCursor.getData()[0];
			if(nodeCursor == nodeInfoAddPosition.node && indexCursor == nodeInfoAddPosition.offset)
			{
				break;
			}
			nodeCursor = nodeCursor.getNext();
			indexCursor = 0;
		}
	}
	
	private void rightShift(NodeInfo emptySpot, int position)
	{
		
		Node<E> nodeCursor = emptySpot.node;
		int indexCursor = emptySpot.offset;
		NodeInfo nodeInfoAddPosition = getNodeInfo(position);
		while(!(nodeCursor == nodeInfoAddPosition.node && indexCursor ==nodeInfoAddPosition.offset))
		{
			while(indexCursor - 1 > -1)
			{
				if(nodeCursor == nodeInfoAddPosition.node && indexCursor == nodeInfoAddPosition.offset)
				{
					break;
				}
				nodeCursor.getData()[indexCursor] = nodeCursor.getData()[indexCursor - 1];
				indexCursor = indexCursor - 1;
			}
			if(nodeCursor == nodeInfoAddPosition.node && indexCursor == nodeInfoAddPosition.offset)
			{
				break;
			}
			nodeCursor.getData()[0] = nodeCursor.getPrev().getData()[nodeCursor.getPrev().getData().length - 1];
			nodeCursor = nodeCursor.getPrev();
			indexCursor = nodeCursor.getData().length;
			
		
		}
	}

	/**
	 * ListIterator class. Please reference the ListIterator API to see how
	 * methods handle errors (no next element, null arguments, etc.)
	 * 
	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/ListIterator.html
	 */
	private class DoublingListIterator implements ListIterator<E> {
		
		private Node<E> currentNode;
		
		private NodeInfo lastAdded;
		
		private int insideCursor = 0;
		
		private int elementsReturned = -1;
		
		private NodeInfo lastReturned;
		
		private int direction;
		
		public DoublingListIterator(int index)
		{
			elementsReturned = index - 1;
			lastReturned = getNodeInfo(index);
			currentNode = getNodeInfo(index).node;
			insideCursor = getNodeInfo(index).offset-1;
			lastReturned = null;
		}
		
		/**
		 * Adds the given element to the DoublingList following the rules of
		 * add(). DO NOT call the add method you wrote for DoublingList above!
		 * This one needs to run in AMORTIZED O(1) (constant time).
		 */
		@Override
		public void add(E arg0) {
			if(size == 0)
			{
				head.getNext().getData()[0] = arg0;
			    size++;
			    lastAdded = new NodeInfo(head.getNext(), 0);
			    //elementsReturned++;
			}
			else if(size == (int)(Math.pow(2, numNodes) - 1))
			{
				E[] arr = (E[]) new Object[(int) ((Math.pow(2, numNodes + 1) - 1) - (Math.pow(2, numNodes) - 1))];
				Node<E> node = new Node(tail, tail.getPrev(), arr);
				tail.getPrev().setNext(node);
				tail.setPrev(node);
				arr[0] = arg0;
				size++;
				numNodes++;
				lastAdded = new NodeInfo(node, 0);
				//elementsReturned++;
			}
			//left shift
			else if(existsEmptiesLeft(elementsReturned) != null)
			{
				leftShift(existsEmptiesLeft(elementsReturned), elementsReturned);
			}
			//right sift
			else if(existsEmptiesRight(elementsReturned) != null)
			{
				if(elementsReturned==-1){
					tail.getPrev().getData()[tail.getPrev().getData().length-1] = arg0;
					size++;
				}else{
				rightShift(existsEmptiesRight(elementsReturned), elementsReturned);
			}
			}
			if(elementsReturned!=-1){
		if(direction == 1)
			{
				lastAdded.node.getData()[insideCursor - 1] = arg0;
			}
			if(direction == -1)
			{
				lastAdded.node.getData()[insideCursor + 1] = arg0;
			}
			}
			lastReturned = null;
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the forward direction. (In other words, returns true if
		 * next would return an element rather than throwing an exception.)
		 */

		@Override
		public boolean hasNext() {
			return (elementsReturned < size);
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the reverse direction. (In other words, returns true if
		 * previous would return an element rather than throwing an exception.)
		 */

		@Override
		public boolean hasPrevious() {
			return (elementsReturned > 0);
		}

		/**
		 * Returns the next element in the list. This method may be called
		 * repeatedly to iterate through the list, or intermixed with calls to
		 * previous to go back and forth. (Note that alternating calls to next
		 * and previous will return the same element repeatedly.)
		 */
		@Override
		public E next() {
			if(!hasNext())
			{
				throw new NoSuchElementException();
			}
			E element = null;
			while(currentNode != tail)
			{
				insideCursor++;
				while(insideCursor < currentNode.getData().length)
				{
					if(currentNode.getData()[insideCursor] != null)
					{
						element = currentNode.getData()[insideCursor];
						lastReturned = new NodeInfo(currentNode, insideCursor);
						break;
					}
					insideCursor++;
					if(insideCursor > currentNode.getData().length)
					{
						currentNode = currentNode.getNext();
						insideCursor = 0;
					}
				}
				if(element != null)
				{
					break;
				}
				currentNode = currentNode.getNext();
				insideCursor=-1;
			}
			elementsReturned++;
			direction = 1;
			return element;
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to next. (Returns list size if the list iterator is
		 * at the end of the list.)
		 */
		@Override
		public int nextIndex() {
			return elementsReturned + 1;  
		}

		/**
		 * 
		 * Returns the previous element in the list. This method may be called
		 * repeatedly to iterate through the list backwards, or intermixed with
		 * calls to next to go back and forth. (Note that alternating calls to
		 * next and previous will return the same element repeatedly.)
		 */

		@Override
		public E previous() {
			if(!hasPrevious())
			{
				throw new NoSuchElementException();
			}
			E element = null;
			while(currentNode.getNext() != head)
			{
				while(insideCursor > -1)
				{
					if(currentNode.getData()[insideCursor] != null)
					{
						element = currentNode.getData()[insideCursor];
						lastReturned = new NodeInfo(currentNode, insideCursor);
						break;
					}
					insideCursor--;
					if(insideCursor < 0)
					{
						currentNode = currentNode.getNext();
						insideCursor = 0;
					}
				}
				if(element != null)
				{
					break;
				}
			}
			direction = -1;
			elementsReturned--;
			return element;
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to previous. (Returns -1 if the list iterator is at
		 * the beginning of the list.)
		 */

		@Override
		public int previousIndex() {
			return elementsReturned - 1;
		}

		/**
		 * Removes from the list the last element that was returned by next or
		 * previous (optional operation). This call can only be made once per
		 * call to next or previous. It can be made only if ListIterator.add has
		 * not been called after the last call to next or previous. DO NOT call
		 * the remove method you wrote for DoublingList above! This one should
		 * run in AMORTIZED O(1) (constant time)
		 */
		@Override
		public void remove() {
			if(lastReturned == null)
			{
				throw new IllegalStateException();
			}
			currentNode.getData()[insideCursor] = null;
			lastReturned = null;
			if(size < Math.pow(2, numNodes - 2))
			{
				Node<E> current = head.getNext();
				ArrayList<E> arr = new ArrayList<E>();
				while(current.getNext() != tail)
				{
					for(int i = 0; i < current.getData().length; i++)
					{
						if(current.getData()[i] != null)
						{
							arr.add(current.getData()[i]);
						}
					}
					current = current.getNext();
				}
				current = head.getNext();
				int timesLeftToAdd = arr.size();
				int j = 0;
				while(timesLeftToAdd > 0)
				{
					for(int i = 0; i < current.getData().length; i++)
					{
						current.getData()[i] = arr.get(j);
						j++;
					}
					current = current.getNext();
				}
			}
		}

		/**
		 * Replaces the last element returned by next or previous with the
		 * specified element (optional operation). This call can be made only if
		 * neither ListIterator.remove nor ListIterator.add have been called
		 * after the last call to next or previous.
		 */
		@Override
		public void set(E arg0) {
			if(lastReturned == null)
			{
				throw new IllegalStateException();
			}
			lastReturned.node.getData()[lastReturned.offset] = arg0;
		}
	}

	/**
	 * Iterator to be used for traversing a DoublingList. This iterator is
	 * optional if you fully implement the ListIterator but is easier and
	 * partial point will be awarded if the one is correct and your ListIterator
	 * is wrong.

	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/Iterator.html
	 */
	private class DoublingIterator implements Iterator<E> {
		
		
		private Node<E> currentNode;
		
		private int insideCursor;
		
		private int elementsReturned;
		
		/**
		 * Returns true if the iteration has more elements. (In other words,
		 * returns true if next would return an element rather than throwing an
		 * exception.)
		 */
		@Override
		public boolean hasNext() {
			return (elementsReturned < size);
		}

		/**
		 * Returns the next element in the iteration.
		 */
		@Override
		public E next() {
			if(!hasNext())
			{
				throw new NoSuchElementException();
			}
			E element = null;
			while(currentNode.getNext() != tail)
			{
				while(insideCursor < currentNode.getData().length)
				{
					if(insideCursor > currentNode.getData().length)
					{
						currentNode = currentNode.getNext();
						insideCursor = 0;
					}
					
					if(currentNode.getData()[insideCursor] != null)
					{
						element = currentNode.getData()[insideCursor];
					}
					insideCursor++;
					
				}
				insideCursor++;
			}
			
			elementsReturned++;
			return element;
		}

		/**
		 * You do not need to implement this method
		 */

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * NodeInfo class that you may find useful to use. Again, feel free to add
	 * methods / constructors / variables that you find useful in here.
	 */
	private class NodeInfo {
		public Node<E> node;
		public int offset;
	

		public NodeInfo(Node<E> node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}
	
	private NodeInfo getNodeInfo(int index) {

		if (index > size) {
			throw new IllegalArgumentException();
		}
		int indexSoFar = -1;
		Node<E> currentNode = head.getNext();
		while (indexSoFar < index) 
		{ 
			indexSoFar += currentNode.getNodeSize();
			if (currentNode.getNext() == tail) 
			{
				break;
			}
			if (indexSoFar < index) 
			{
				currentNode = currentNode.getNext();
			}
		}
		indexSoFar = indexSoFar - currentNode.getNodeSize(); 
		if (currentNode.getNodeSize() == 0) 
		{
			NodeInfo nodeInfo = new NodeInfo(currentNode, 0);
			return nodeInfo;
		}

		int nodeIndex = 0;
		while (indexSoFar < index) 
		{
			if (currentNode.getData()[nodeIndex] != null) 
			{
				indexSoFar++;
			}
			nodeIndex++;
		}
		NodeInfo nodeInfo = new NodeInfo(currentNode, nodeIndex - 1); 
		return nodeInfo;
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes.
	 */

	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes and the position of the iterator.
	 * @param iter
	 *            an iterator for this list
	 */

	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');

		Node<E> current = head.getNext();
		while (current != tail) {
			sb.append('(');
			E data = current.getData()[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < current.getData().length; ++i) {
				sb.append(", ");
				data = current.getData()[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}

					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size() && count == size()) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.getNext();
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();

	}
	
	public static void main(String[] args) {
		DoublingList<String> list = new DoublingList<>();
		ListIterator<String> iter = list.listIterator();
		
		iter.add("sup");
		System.out.println(DoublingListUtil.toStringInternal(list, iter));
		iter.add("dfhsdfj");
		System.out.println(DoublingListUtil.toStringInternal(list, iter));
		iter.add("hthtnt");
		System.out.println(DoublingListUtil.toStringInternal(list, iter));
		iter.add("fsdfsdfsdfsdf");
	    System.out.println(DoublingListUtil.toStringInternal(list, iter));
		System.out.println(iter.next());
		  System.out.println(DoublingListUtil.toStringInternal(list, iter));
		System.out.println(iter.next());
		  System.out.println(DoublingListUtil.toStringInternal(list, iter));
		  System.out.println(iter.next());
		  System.out.println(DoublingListUtil.toStringInternal(list, iter));
		  System.out.println(iter.next());
		  System.out.println(DoublingListUtil.toStringInternal(list, iter));
		  iter.remove();
		  System.out.println(DoublingListUtil.toStringInternal(list, iter));
		  
	}

}

