package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * tests ArrayList class
 * @author William Walton
 * @author Amulya
 * @author Jacob
 */
class ArrayListTest {

	/**
	 * Tests the ArrayList constructor
	 */
	@Test
	public void testArrayList() {
		ArrayList<Object> list = new ArrayList<Object>();
		assertEquals(0, list.size());
	}

	/**
	 * tests adding an element
	 */
	@Test
	public void testAddElement() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		// adding to an empty list
		assertDoesNotThrow (
				() -> list.add(0, 1),
				"Should not throw an exception");
		assertEquals(1, list.get(0));
		
		// adding to the front of the list
		list.add(0, 2);
		
		assertEquals(2, list.get(0));
		assertEquals(1, list.get(1));
		assertEquals(2, list.size());
		
		
		// adding to the end of the list
		list.add(2, 3);
		
		assertEquals(2, list.get(0));
		assertEquals(1, list.get(1));
		assertEquals(3, list.get(2));
		assertEquals(3, list.size());
		
		// adding to the middle of the list
		list.add(1, 4);
		
		assertEquals(2, list.get(0));
		assertEquals(4, list.get(1));
		assertEquals(1, list.get(2));
		assertEquals(3, list.get(3));
		assertEquals(4, list.size());
		
		// added to a list that is at least 10
		for(int i = 0; i < 7; i++) {
			list.add(i, i + 5);
			assertEquals(i + 5, list.size());
		}
		for(int i = 0; i < 7; i++) {
			assertEquals(i + 5, list.get(i));
		}
		assertEquals(2, list.get(7));
		assertEquals(4, list.get(8));
		assertEquals(1, list.get(9));
		assertEquals(3, list.get(10));
		
		// testing adding a null argument
		assertThrows(NullPointerException.class, () -> list.add(0, null), "Should throw NullPointerException");
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 12), "Should throw IndexOutOfBoundsException");
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(12, 12), "Should throw IndexOutOfBoundsException");
		ArrayList<String> strList = new ArrayList<String>();
		strList.add("hello");
		assertThrows(IllegalArgumentException.class, () -> strList.add(0, "hello"), "Should throw IllegalArgumentException");
		
	}
	
	/**
	 * tests ArrayList.remove(int)
	 */
	@Test
	public void testRemove() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Goodbye");
		list.add("World");
		list.add("!");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		
		// removing from middle
		assertEquals("Goodbye", list.remove(1));
		assertEquals("Hello", list.get(0));
		assertEquals("World", list.get(1));
		assertEquals("!", list.get(2));
		assertEquals(3, list.size());
		

		
		// removing from start
		assertEquals("Hello", list.remove(0));
		assertEquals("World", list.get(0));
		assertEquals("!", list.get(1));
		assertEquals(2, list.size());
		
		// removing from end
		assertEquals("!", list.remove(1));
		assertEquals("World", list.get(0));
		assertEquals(1, list.size());
		
		// removing last element
		assertEquals("World", list.remove(0));
		assertEquals(0, list.size());
		
		// removing from an integer list
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(3);
		intList.remove(0);
		assertEquals(0, intList.size());
	}
	
	/**
	 * tests ArrayList.set(int,E)
	 */
	@Test
	public void testSet() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Goodbye");
		list.add("World");
		list.add("!");
		
		assertEquals("Goodbye", list.set(1, " "));
		assertEquals(" ", list.get(1));
		
		assertThrows(NullPointerException.class, () -> list.set(0, null));
		assertThrows(IllegalArgumentException.class, () -> list.set(0, " "));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "bit"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(4, "bit"));
	}
	
	/**
	 * tests out of bounds index ArrayList.get(int)
	 */
	@Test
	public void testGet() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Goodbye");
		list.add("World");
		list.add("!");
		
		assertEquals("Hello", list.get(0));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
	}
}
