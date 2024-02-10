package edu.ncsu.csc216.pack_scheduler.util;

/**
 * The Stack interface that is used for implementing ArrayStack and LinkedStack
 * @param <E> the type of element stored in the stack
 */
public interface Stack<E> {

	/**
	 * Adds an element to the top of the stack. If there is no room (capacity has been reached), 
	 * an IllegalArgumentException is thrown
	 * @param element the element to be added
	 */
	void push (E element);
	
	
	/**
	 * Removes and returns the element at the top of the stack. If the stack is empty, an 
	 * EmptyStackException() is thrown
	 * @return the element at the top of the stack
	 */
	E pop();
	
	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty; otherwise false
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the stack
	 * @return the number of elements in the stack
	 */
	int size();
	
	/**
	 * Sets the stack's capacity. If the actual parameter is negative or if it is less than the 
	 * number of elements in the stack, an IllegalArgumentException is thrown
	 * @param capacity the capacity to be set for the stack
	 * @throws IllegalArgumentException if the parameter is negative or if it is less than the 
	 * 			number of elements in the stack
	 */
	void setCapacity(int capacity);
}
