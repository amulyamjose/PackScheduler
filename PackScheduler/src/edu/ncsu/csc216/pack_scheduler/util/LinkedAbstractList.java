package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Stores data in a list of elements that consist of the actual list data and the location of 
 * the next element in the list
 * @param <E> the type of element stored in the list
 * @author William Walton
 * @author Amulya Jose
 * @author Jacob Phillips
 * @author Carter Gentile
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/** A ListNode of type E */
	private ListNode front;
	
	/** The size of the list */
	private int size;
	
	/** The capacity of the list */
	private int capacity;
	
    /** The last node in the list */
    private ListNode back;
    
	
	

	/**
	 * Constructs a LinkedAbstractList
	 * @param capacity the capacity of the list
	 * @throws IllegalArgumentException if the capacity parameter is less than 0 or if the capacity
	 * 			is less than the size
	 */
	public LinkedAbstractList(int capacity) {
		this.front = null;
		this.size = 0;
		if (capacity >= 0) {
			this.capacity = capacity;
		} else {
			throw new IllegalArgumentException();
		}
		
		if (this.capacity < this.size) {
			throw new IllegalArgumentException();
		}	
	}
	
	/**
	 * Adds elements to the list until the capacity is reached
	 * @param index the index that the element should be added to
	 * @param element the element to be added in the index
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if 
	 * 		index greater than size
	 * 		index less than 0
	 * @throws IllegalArgumentException if there is another element in the list equal to this or if
	 * 			the size is equal to the capacity
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (E otherElement : this) {
			if (element.equals(otherElement)) {
				throw new IllegalArgumentException();
			}
		}
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		
		if (index == 0) {
			front = new ListNode(element, front);
	        if (back == null) {
	            back = front; 
	        }
	    } else if (index == size) {
	        if (back == null) {
	            front = new ListNode(element, front);
	            back = front;
	        } else {
	            back.next = new ListNode(element);
	            back = back.next;
	        }
		} else {
			ListNode current = front;
			
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			
			current.next = new ListNode(element, current.next);
	        if (current.next.next == null) {
	            back = current.next;
	        }
		}
		size++;
	}
	
	/**
	 * Removes an element in the list at a specific index
	 * @param index the index of the element to be removed
	 * @return the element that is removed at the specific index
	 * @throws IndexOutOfBoundsException if the index if less than 0 or greater than or equals to the size
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E value;
		
		if (index == 0) {
			value = front.data;
			front = front.next;
	        if (front == null) {
	            back = null;
	        }
		} else {
			ListNode current = front;
			
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			
			value = current.next.data;
			
			current.next = current.next.next;
			
	        if (current.next == null) {
	            back = current;
	        }
		}
		size--;
		return value;
	}
	
	/**
	 * Sets so that the element at the specified index is replaced with the given element
	 * @param index the index at which to set the new element
	 * @param element the element to be set at the index
	 * @return the element previously at the specified position
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if 
	 * 		index greater than or equal to size
	 * 		index less than 0
	 * @throws IllegalArgumentException if there is another element in the list equal to this
	 */
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		for (E otherElement : this) {
			if (element.equals(otherElement)) {
				throw new IllegalArgumentException();
			}
		}
		
		ListNode current = front;
		
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		E value = current.data;
		current.data = element;
		return value;
	}
	
	
	/**
	 * Returns the element at the given index
	 * @param index the index at which the element should be retrieved from
	 * @return the element at the given index
	 * @throws IndexOutOfBoundsException if 
	 * 		index greater than or equal to size
	 * 		index less than 0
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}
	
	/**
	 * Returns the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Sets the capacity if the parameter is valid
	 * @param capacity the capacity to be set
	 * @throws IllegalArgumentException if the parameter capacity is less than zero or less 
	 * 			than the current size of the LinkedAbstractList
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
	}

	/**
	 * The ListNode of the LinkedAbstractList
	 */
	private class ListNode {
		/** The data in the node */
		private E data;
		
		/** The next node in the list */
		private ListNode next;
		
		/**
		 * Constructs a ListNode using just the data
		 * @param data the data to be assigned to the data field
		 */
		public ListNode(E data) {
			this.data = data;
		}
		
		/**
		 * Constructs a ListNode using both the data and a next node
		 * @param data the data to be assigned to the data field
		 * @param next the next node in the list 
		 */
		public ListNode(E data, ListNode next) {
			this(data);
			this.next = next;
		}
		
	}

}
