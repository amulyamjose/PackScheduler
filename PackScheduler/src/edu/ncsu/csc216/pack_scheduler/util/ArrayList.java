package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Stores data in an array
 * allows for variable sizes
 * @param <E> the type of ArrayList
 * @author Amulya Jose
 * @author William Walton
 * @author Jacob Phillips
 */
public class ArrayList<E> extends AbstractList<E> {
	/** A constant value for initializing the list size */
	private static final int INIT_SIZE = 10;
	/** An array of type E for the list */
	private E [] list;
	/** The size of the list */
	private int size;
	
	/**
	 * Constructs an ArrayList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		size = 0;
		list = (E[]) new Object[INIT_SIZE];
	}
	
	/**
	 * Adds an element to the specified index
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if 
	 * 		index greater than size
	 * 		index less than 0
	 * 	@throws IllegalArgumentException if there is another element in the list equal to this
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (size() == list.length) {
			growArray();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (E otherElement : this) {
			if (element.equals(otherElement)) {
				throw new IllegalArgumentException();
			}
		}
		for (int i = size(); i > index; i--) {
			list[i] = list[i - 1];
		}
		list[index] = element;
		size++;
	}
	
	/**
	 * Doubles the length of the array. Has to have an unchecked warning because of construction
	 * of new array
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] newList = (E[]) new Object[size * 2];
		for (int i = 0; i < size(); i++) {
			newList[i] = list[i];
		}
		list = newList;
	}
	
	/**
	 * Removes the element at the index
	 * @param index the index of the element to remove
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if
	 * 		index less than 0 or
	 * 		index greater than or equal to size
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E element = list[index];
		for (int i = index; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size() - 1] = null;
		size--;
		return element;
	}
	
	/**
	 * Sets the element at the index
	 * @param index the index of the element to set
	 * @param element the element the index should be set to
	 * @return the element previously at the specified position
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if 
	 * 		index greater than or equal to size or
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
		E otherElement = list[index];
		list[index] = element;
		return otherElement;
	}

	/**
	 * Gets the size of the array
	 * @return size the size of the array
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Gets the element at the specified index
	 * @param index the index the element is at
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException if
	 * 		index less than 0
	 * 		index greater than or equal to size
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
}
