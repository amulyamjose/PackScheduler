package edu.ncsu.csc216.pack_scheduler.util;


import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Custom implementation of a linked list that doesn’t allow null elements or duplicate elements as defined by the equals() method.
 * Extends java.util.AbstractSequentialList and implements ListIterator interface.
 *
 * @param <E> the type of elements in the list
 * 
 * @author Nathan-Blaise MIHINDU MI NZAMBE
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/**
	 * The first node in the linked list.
	 */
	private ListNode front;

	/**
	 * The last node in the linked list.
	 */
	private ListNode back;

	/**
	 * The number of elements in the linked list.
	 */
	private int size;

    /**
     * Constructs an empty LinkedList.
     * Initializes the front and back nodes with null data, and sets their references.
     * Initializes the size to 0.
     */
    public LinkedList() {
        front = new ListNode(null);
        back = new ListNode(null);
        front.next = back;
        back.prev = front;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence),
     * starting at the specified position in the list.
     *
     * @param index the index of the first element to be returned from the list iterator
     * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range if index less than 0 or index grater than size
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        LinkedListIterator iterator = new LinkedListIterator(index);
        return iterator;
    }
    
    @Override
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list.");
        }

        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate elements are not allowed.");
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        ListIterator<E> iterator = listIterator(index);
        iterator.add(element);
    }

    /**
     * Inner class that represents a node in the linked list.
     */
    private class ListNode {
    	/**
    	 * The data stored in this node.
    	 */
    	public E data;

    	/**
    	 * The next node in the linked list.
    	 */
    	public ListNode next;

    	/**
    	 * The previous node in the linked list.
    	 */
    	public ListNode prev;


        /**
         * Constructs a ListNode with the given data.
         *
         * @param data the data to store in the node
         */
        public ListNode(E data) {
            this(data, null, null);
        }

        /**
         * Constructs a ListNode with the given data, previous node, and next node.
         *
         * @param data the data to store in the node
         * @param prev the previous node in the list
         * @param next the next node in the list
         */
        public ListNode(E data, ListNode prev, ListNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Inner class that represents an iterator for the linked list.
     */
    private class LinkedListIterator implements ListIterator<E> {
    	/**
    	 * The previous node in the linked list.
    	 */
    	private ListNode previous;

    	/**
    	 * The next node in the linked list.
    	 */
    	private ListNode next;

    	/**
    	 * The index of the previous node in the linked list.
    	 */
    	private int previousIndex;

    	/**
    	 * The index of the next node in the linked list.
    	 */
    	private int nextIndex;

    	/**
    	 * The last retrieved node in the linked list.
    	 */
    	private ListNode lastRetrieved;


        /**
         * Constructs a LinkedListIterator and positions it at the specified index.
         *
         * @param index the index to position the iterator at
         * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
         */
        public LinkedListIterator(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }
            previous = front;
            next = front.next;
            for (int i = 0; i < index; i++) {
                previous = next;
                next = next.next;
            }
            previousIndex = index - 1;
            nextIndex = index;
            lastRetrieved = null;
        }

        /**
         * Returns true if this iterator has more elements when traversing the list in the forward direction.
         *
         * @return true if the iterator has more elements when traversing the list in the forward direction
         */
        @Override
        public boolean hasNext() {
            return next != back;
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         *
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            previous = next;
            next = next.next;
            previousIndex++;
            nextIndex++;
            lastRetrieved = previous;
            return previous.data;
        }

        /**
         * Returns true if this iterator has more elements when traversing the list in the reverse direction.
         *
         * @return true if the iterator has more elements when traversing the list in the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            return previous != front;
        }

        /**
         * Returns the previous element in the list and moves the cursor position backwards.
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous element
         */
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            next = previous;
            previous = previous.prev;
            nextIndex--;
            previousIndex--;
            lastRetrieved = next;
            return next.data;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to next().
         *
         * @return the index of the next element
         */
        @Override
        public int nextIndex() {
            return nextIndex;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to previous().
         *
         * @return the index of the previous element
         */
        @Override
        public int previousIndex() {
            return previousIndex;
        }

        /**
         * Inserts the specified element into the list.
         * The element is inserted immediately before the element that would be returned by next().
         *
         * @param e the element to add
         * @throws NullPointerException if the element to add is null
         * @throws IllegalArgumentException if the element to add is a duplicate of an element already in the list
         */
        @Override
        public void add(E e) {
            if (e == null) {
                throw new NullPointerException();
            }
            if (contains(e)) {
                throw new IllegalArgumentException("Duplicate element");
            }
            ListNode newNode = new ListNode(e, previous, next);
            previous.next = newNode;
            next.prev = newNode;
            previous = newNode;
            previousIndex++;
            nextIndex++;
            size++;
            lastRetrieved = null;
        }

        /**
         * Removes from the list the last element that was returned by next() or previous().
         *
         * @throws IllegalStateException if neither remove() nor add(E) have been called after the last call to next() or previous(),
         *                               or if remove() or add(E) was the last call on the list, or if there has not been a call to next() or previous()
         */
        @Override
        public void remove() {
            if (lastRetrieved == null) {
                throw new IllegalStateException();
            }
            ListNode prevNode = lastRetrieved.prev;
            ListNode nextNode = lastRetrieved.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            if (lastRetrieved == previous) {
                previousIndex--;
                previous = prevNode;
            } else {
                nextIndex--;
                next = nextNode;
            }
            size--;
            lastRetrieved = null;
        }

        /**
         * Replaces the last element returned by next() or previous() with the specified element.
         *
         * @param e the element to set
         * @throws NullPointerException if the element to set is null
         * @throws IllegalStateException if neither remove() nor add(E) have been called after the last call to next() or previous(),
         *                               or if remove() or add(E) was the last call on the list, or if there has not been a call to next() or previous()
         * @throws IllegalArgumentException if the element to set is a duplicate of an element already in the list
         */
        @Override
        public void set(E e) {
            if (e == null) {
                throw new NullPointerException();
            }
            if (lastRetrieved == null) {
                throw new IllegalStateException();
            }
            if (contains(e)) {
                throw new IllegalArgumentException("Duplicate element");
            }
            lastRetrieved.data = e;
        }
    }
}
