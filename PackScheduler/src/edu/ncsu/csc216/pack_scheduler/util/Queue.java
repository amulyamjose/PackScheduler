package edu.ncsu.csc216.pack_scheduler.util;

/**
 * The Queue interface is used to implement the ArrayQueue and Linked Queue
 * @param <E> the type of element being stored in the queue
 */
public interface Queue<E> {
	
	
	/**
	 * Adds the element to the back of the Queue
	 * @param element The element to be added to the queue
	 */
	void enqueue(E element);
	
	/**
	 * Removes and returns the element at the front of the Queue
	 * @return returns the element at the front
	 */
	E dequeue();
	
	/**
	 * Returns true if the Queue is empty
	 * @return true or false based on if the Queue is empty
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the Queue
	 * @return the size of the queue
	 */
	int size();
	
	/**
	 * Sets the queues capacity
	 * If the actual parameter is negative or if it is less than the number
	 *  of elements in the Queue, an 1IllegalArgumentException is thrown
	 * @param capacity The capcaity that the queue is at 
	 * @throws IllegalArgumentException if the actual paramter is negative or if it
	 * is less than the number of elements in the queue
	 */
	void setCapacity(int capacity);
	
		
	

	

}
