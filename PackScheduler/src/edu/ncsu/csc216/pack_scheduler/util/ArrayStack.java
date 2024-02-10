package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * This is the ArrayStack class
 * It implements the interface Stack methods.
 * 
 * @param <E> for the element that is being used
 */
public class ArrayStack<E> implements Stack<E> {
	
	/** This is the capacity of the list*/
	private int capacity;
	
	/** This is the list of the ArrayStack class */
	private ArrayList<E> list;
	
	/**
	 * This is the constructor of the ArrayStack class
	 * It creates the list and sets the size 
	 * of that list to being capacity.
	 * 
	 * @param capacity for the capacity of the list.
	 * @throws IllegalArgumentException if the capacity is less than 0
	 */
	public ArrayStack(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		list = new ArrayList<E>();
		setCapacity(capacity);
	}
	
	/**
	 * This is the push method of the class
	 * It adds elements to the top of the stack.
	 * If the limit has been reached, then it throws an exception
	 * 
	 * @param element for the element that is being used
	 * @throws IllegalArgumentException if the limit has been reached.
	 */
	public void push (E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException("There is no more room in the list");
		}
		list.add(element);
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
		return list.remove(list.size() - 1);
	}

	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty; otherwise false
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
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
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity.");		
		}
		this.capacity = capacity;
	}
}
