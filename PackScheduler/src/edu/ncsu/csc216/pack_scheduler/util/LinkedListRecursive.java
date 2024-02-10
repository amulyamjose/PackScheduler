/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Custom implementation of a recursive linked list that doesn’t allow for null elements or duplicate elements
 * @param <E> the type of elements in the list
 * @author Amulya Jose
 */
public class LinkedListRecursive<E> {
	/** The size of the list */
	private int size;
	
	/** The first node in the list */
	private ListNode front;
	
	/**
	 * Constructs a recursive linked list
	 */
	public LinkedListRecursive() {
		this.size = 0;
		this.front = null;
	}
	
	/**
	 * Returns true if the list is empty and false if it is not
	 * @return true if the list is empty and false if it is not
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the size of the list
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds the given element to the end of the list. Returns true if this happens, and false if it doesn't.
	 * If the element already exists, an IllegalArgumentException is thrown.
     * @param element the element to add
	 * @return true if the given element is added to the end of the list, and otherwise false
	 * @throws IllegalArgumentException if the the element already exists
	 */
	public boolean add(E element) {
		if (contains(element)) {
			throw new IllegalArgumentException("Duplicate element");
		}
		
		add(size, element);
		
		return true;
	}
	
	/**
	 * Adds the given element at the given index. All the elements in front of that index are shifted
	 * to the right.
	 * @param index the index to add the element at
	 * @param element the element to be added to the list at the given index
	 * @throws IllegalArgumentException if the the element already exists
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater than the list size
	 * @throws NullPointerException if the element is null
	 */
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Element is null");
		}
			
		if (contains(element)) {
			throw new IllegalArgumentException("Duplicate element");
		}
		
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		
		if (index == 0) {
			front = new ListNode(element, front);
			size++;
		} else if (front == null) {
			throw new IndexOutOfBoundsException();
		} else {
			front.add(index - 1, element);
			size++;
		}
 	}
	
	/**
	 * Returns the element at the given index
	 * @param index the index of the element to be returned
	 * @return the element at the given index
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater than the list size
	 */
	public E get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		} else {
			return front.get(index);
		}
	}
	
	/**
	 * Removes the given element from the list. Returns true if this happens, and false if it doens't.
	 * @param element the element to be removed
	 * @return true if the element is removed, and false if it isn't
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		} else if (isEmpty()) {
			return false;
		} else if (front.data.equals(element)) {
			if (front.next != null) {
				front = front.next;
				size--;
				return true;
			} else {
				front = null;
				size--;
				return true;
			}
		} else {
			return front.remove(element);
		}
	}
	
	/**
	 * Removes the element at the given index and returns that element
	 * @param index the index of the element to be removed
	 * @return the element that is removed
	 */
	public E remove (int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		} else if (index == 0) {
			E value = front.data;
			front = front.next;
			size--;
			return value;
		} else {
			return front.remove(index - 1);
		}
	}
	
	/**
	 * Sets the element at the given index to the given element
	 * @param index the index of the element to be changed
	 * @param element the element that the element at the given index is to be changed to
	 * @return the element replaced by the new element
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater than the list size
	 */
	public E set(int index, E element) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		
		if (element == null) {
			throw new NullPointerException("Element is null");
		} 
		
		if (contains(element)) {
			throw new IllegalArgumentException("Duplicate element");
		}
		
		return front.set(index, element);
	}
	
	/**
	 * Returns true if the element is contained inside the list and false if it isn't. If the list is not empty,
	 * the flow of control is transferred to the ListNode.contains(E element) method.
	 * @param element the element to be seen if it is inside the list
	 * @return true if the element is contained inside the list and false if it isn't
	 */
	public boolean contains(E element) {
		if (isEmpty()) {
			return false;
		} else {
			return front.contains(element);
		}
	}
	
	/**
     * Inner class that represents a node in the linked list.
     */
    private class ListNode {
    	/** The data stored in this node. */
    	public E data;

    	/** The next node in the linked list. */
    	public ListNode next;
    	
    	/**
    	 * Adds the given element at the given index.
    	 * @param index the index at which to add the given element
    	 * @param element the element to be added at the given index
    	 * @throws IndexOutOfBoundsException if the index is less than zero or if next is null and you
    	 * 			haven't reached the index yet
    	 */
    	public void add(int index, E element) {
    		if (index == 0) {
    			next = new ListNode(element, next);
    		} else if (next == null) {
    			throw new IndexOutOfBoundsException();
    		} else {
    			next.add(index - 1, element);
    		}
    	}
    	
    	/**
    	 * Gets the element at the given index
    	 * @param index the index at which to retrieve the element from
    	 * @return the element at the given index
    	 * @throws IndexOutOfBoundsException if next is null and you haven't reached the index yet
    	 */
    	public E get(int index) {
    		if (index == 0) {
    			return this.data;
    		} else if (next == null) {
    			throw new IndexOutOfBoundsException();
    		} else {
    			return next.get(index - 1);
    		}
    	}
    	
    	/**
    	 * Removes the element at the given index
    	 * @param index the index at which to remove the element
    	 * @return the element at the given index
    	 * @throws IndexOutOfBoundsException if next is null and you haven't reached the index yet
    	 */
    	public E remove(int index) {
    		if (index == 0) {
    			E value = next.data;
    			next = next.next;
    			size--;
    			return value;
    		} else if (next == null) {
    			throw new IndexOutOfBoundsException();
    		} else {
    			return next.remove(index - 1);
    		}
    	}
    	
    	/**
    	 * Removes the node with the given index
    	 * @param element the element to be removed from the list
    	 * @return true if the element is removed and false if it isn't
    	 */
    	public boolean remove(E element) {
    		if (this.data == null) {
    			return false;
    		}
    		
    		if (this.next == null) {
    			return false;
    		}
    		
    		if (next.data.equals(element)) {
    			next = next.next;
    			size--;
    			return true;
    		}
    		
    		return this.next.remove(element);	
    	}
    	
    	/**
    	 * Sets an element at a given index.
    	 * @param index the index at which to set the given element
    	 * @param element the given element to set at the given index
    	 * @return the old data at the given index
    	 */
    	public E set(int index, E element) {
    		if (index == 0) {
    			E value = this.data;
    			this.data = element;
    			return value;
    		} else if (next == null) {
    			throw new IndexOutOfBoundsException();
    		} else {
    			return next.set(index - 1, element);
    		}
    	}
    	
    	/**
    	 * Recursively checks the elements in the list to see if the element is contained inside the list
    	 * @param element the element to be checked if it is inside the list
    	 * @return true if the element is contained inside the list and otherwise false
    	 */
    	public boolean contains(E element) {
    		if (this.data == null) {
    			return false;
    		}
    		
    		if (this.data.equals(element)) {
    			return true;
    		}
    		
    		if (this.next == null) {
    			return false;
    		}
    		
    		return this.next.contains(element);
    	}

        /**
         * Constructs a ListNode with the given data and next node.
         *
         * @param data the data to store in the node
         * @param next the next node in the list
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }
	
}
