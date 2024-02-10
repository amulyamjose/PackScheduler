package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue should have a field of type LinkedAbstractList.
 * LinkedQueue will construct a LinkedAbstractList and delegate to the LinkedAbstractList.
 * @param <E> the type of element being stored in the queue
 */
public class LinkedQueue<E> implements Queue<E> {
	
	/** A list to be used as a queue */
	private LinkedAbstractList<E> list;
	
	/**
	 * Constructs a LinkedQueue
	 * @param capacity the capacity to set for the LinkedStack
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * Adds the element to the back of the Queue
	 * @param element The element to be added to the queue
	 * @throws IllegalArgumentException If there is no room (capacity has been reached)
	 */
	@Override
	public void enqueue(E element) {
		list.add(list.size(), element);
	}

	/**
	 * Removes and returns the element at the front of the Queue
	 * @return returns the element at the front
	 * @throws NoSuchElementException if the Queue is empty
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
	 * @param capacity The capcaity that the queue is at 
	 * @throws IllegalArgumentException if the actual paramter is negative or if it
	 * is less than the number of elements in the queue
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
		
	}

}
