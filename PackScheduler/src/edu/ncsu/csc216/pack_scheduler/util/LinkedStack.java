/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Constructs a LinkedAbstractList with stack functionality
 * @param <E> the type of element stored in the stack
 */
public class LinkedStack<E> implements Stack<E> {
	
	/** A list to be used as a stack */
	private LinkedAbstractList<E> list;
	
	/**
	 * Constructs a LinkedStack
	 * @param capacity the capacity to set for the LinkedStack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * Adds an element to the top of the stack. If there is no room (capacity has been reached), 
	 * an IllegalArgumentException is thrown
	 * @param element the element to be added
	 */
	@Override
	public void push(E element) {
		list.add(0, element);
	}

	/**
	 * Removes and returns the element at the top of the stack. If the stack is empty, an 
	 * EmptyStackException() is thrown
	 * @return the element at the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		}
		E element = list.remove(0);
		return element;
	}

	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty; otherwise false
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the number of elements in the stack
	 * @return the number of elements in the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the stack's capacity. If the actual parameter is negative or if it is less than the 
	 * number of elements in the stack, an IllegalArgumentException is thrown
	 * @param capacity the capacity to be set for the stack
	 * @throws IllegalArgumentException if the parameter is negative or if it is less than the 
	 * 			number of elements in the stack
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}

}
