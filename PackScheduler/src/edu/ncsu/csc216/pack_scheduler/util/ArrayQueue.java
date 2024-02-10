package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayQueue should have a field of type ArrayList. 
 * ArrayQueue will construct an ArrayList and delegate to the ArrayList.
 * @param <E> the type of element being stored in the queue
 * @author Carter Gentile
 */

public class ArrayQueue<E> implements Queue<E> {
	/** A list to be used as a queue */
	private ArrayList<E> list;
	
	/** This is the capacity of the list*/
	private int capacity;
	
	/**
	 * This is the constructor of the ArrayQueue class
	 * It creates the list and sets the size 
	 * of that list to being capacity.
	 * 
	 * @param capacity for the capacity of the list.
	 */
	public ArrayQueue(int capacity) {
	    list = new ArrayList<E>();
	    setCapacity(capacity);
	}

	/**
	 * Adds the element to the back of the Queue
	 * @param element The element to be added to the queue
	 * @throws IllegalArgumentException if the size is equal to or greater than the capacity
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException("There is no more room in the list");
		}
		list.add(list.size(), element);
		
	}
	
	/**
	 * Removes and returns the element at the front of the Queue
	 * @return returns the element at the front
	 * @throws NoSuchElementException if the list is empty
	 */
	@Override
	public E dequeue() {
		if (list.isEmpty()) {
			throw new NoSuchElementException();
		}
		E element = list.remove(0);
		return element;
	}

	/**
	 * Returns true if the Queue is empty
	 * @return true or false based on if the Queue is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the number of elements in the Queue
	 * @return the size of the queue
	 */
	@Override
	public int size() {
        return list.size();
	}


	/**
	 * Sets the queues capacity
	 * If the actual parameter is negative or if it is less than the number
	 *  of elements in the Queue, an 1IllegalArgumentException is thrown
	 * @param capacity The capacity that the queue is at 
	 * @throws IllegalArgumentException if the actual parameter is negative or if it
	 */
	@Override
	public void setCapacity(int capacity) {
	    if (capacity < 0 || capacity < list.size()) {
	        throw new IllegalArgumentException("Invalid capacity");
	    }
	    this.capacity = capacity;
	}
	
	/**
	 * Returns true if the element is in the list and otherwise false
	 * @param element the element to check if it is in the list
	 * @return true if the element is in the list and otherwise false
	 */
	public boolean contains(E element) {
		for (int i = 0; i < list.size(); i++) {
			if (element.equals(list.get(i))) {
				return true;
			}
		}
		return false;
	}


}
