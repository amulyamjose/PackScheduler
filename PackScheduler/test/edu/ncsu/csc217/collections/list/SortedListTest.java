package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * Tests the SortedListTest class
 * @author Kavya Vadla
 * @author William Walton
 */
public class SortedListTest {

	/**
	 * tests that SortedList object is constructed correctly
	 * tests adding more than the initial capacity
	 * Tests use of a different class as a parameter
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		for (int i = 0; i < 11; i++) {
			list.add(String.valueOf(i));
			
		}
		
	}


	/**
	 * tests the add() method of SortedList
	 * 		tests adding an element to the front
	 *		tests adding an element to the end
	 *		tests adding an element to the middle
	 *		tests adding a null element
	 *		Tests adding a duplicate element
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		
		list.add("zebra");
		assertEquals(3, list.size());
		assertEquals("zebra", list.get(2));		
		
		list.add("monkey");
		assertEquals(4, list.size());
		assertEquals("monkey", list.get(2));
				
		assertThrows(NullPointerException.class, () -> list.add(null));
			
		assertThrows(IllegalArgumentException.class, () -> list.add("monkey"));
	}
	
	/**
	 * tests the get(int) method of Sorted list
	 * 		getting an element from an empty list
	 * 		adding some elements to the list, and getting an element at an index less than 0
	 * 		Getting an element at index size()
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		 
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		
		list.add("banana");
		list.add("apple");
		list.add("monkey");
		list.add("zebra");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
		
	}
	
	/**
	 * tests SortedList.Remove(int) for
	 * 		Removing an element from an empty list
	 * 		adding at least four elements and removing an element at an index less than 0
	 * 		Removing an element at index size()
	 * 		Removing a middle element()
	 * 		Removing the element at the end of the list
	 * 		Removing the first element of the list
	 * 		Removing the last element left in the list
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		
		list.add("banana");
		list.add("apple");
		list.add("monkey");
		list.add("zebra");
		list.add("carrot");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));
		
		//Test removing a middle element
		assertEquals("monkey", list.remove(3));
		assertEquals(4, list.size());
		
		//Test removing the last element
		assertEquals("zebra", list.remove(3));
		assertEquals(3, list.size());
		
		//Test removing the first element
		assertEquals("apple", list.remove(0));
		assertEquals(2, list.size());
		
		//Test removing the last element
		assertEquals("carrot", list.remove(1));
		assertEquals(1, list.size());
		
		assertEquals("banana", list.remove(0));
		assertEquals(0, list.size());
	}
	
	/**
	 * tests the SortedList.indexOf(E) method for:
	 * 		Getting the index of an element in an empty list
	 * 		Adding some elements and getting the index of elements in various positions in the list
	 * 		getting the index of elements not in the list
	 * 		getting the index of a null element
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		//Test indexOf on an empty list
		assertEquals(-1, list.indexOf(""));
		
		//Add some elements
		
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("monkey");
		list.add("zebra");
		
		// Test various calls to indexOf for elements in the list
		//and not in the list
		
		assertEquals(2, list.indexOf("carrot"));
		assertEquals(4, list.indexOf("zebra"));
		assertEquals(-1, list.indexOf("Apple"));
		assertEquals(-1, list.indexOf("milk"));
		assertEquals(1, list.indexOf("banana"));
		
		//Test checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
		
	}
	
	/**
	 * tests SortedList.clear() for:
	 * 		add at least one element
	 * 		clear the list
	 * 		check it is empty
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("monkey");
		list.add("zebra");
		
		// Clear the list
		list.clear();
		
		// Test that the list is empty
		assertEquals(0, list.size());
	}

	/**
	 * tests SortedList.isEmpty() method for:
	 * 		Checking a new list is empty
	 * 		adding an element
	 * 		checking that the list is now not empty
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		// Test that the list starts empty
		assertTrue(list.isEmpty());
		
		// Add at least one element
		list.add("apple");
		
		
		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}

	/**
	 * tests SortedList.isEmpty() method for:
	 * 		Checking if an empty list contains an element
	 * 		Adding elements and checking if the list contains an element in the list
	 * 		Checking if a list contains an element not in the list
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		// Test the empty list case
		assertFalse(list.contains("apple"));
		
		// Add some elements
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("monkey");
		list.add("zebra");
		
		// Test some true and false cases
		assertFalse(list.contains("Apple"));
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("zebra"));
		assertFalse(list.contains("milk"));
		assertTrue(list.contains("carrot"));
	}
	
	/**
	 * tests the .equals() method
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		// Make two lists the same and one list different
		list1.add("apple");
		list1.add("banana");
		list1.add("carrot");
		list1.add("monkey");
		list1.add("zebra");
		
		list2.add("apple");
		list2.add("banana");
		list2.add("carrot");
		list2.add("monkey");
		list2.add("zebra");
		
		list3.add("apple");
		list3.add("banana");
		list3.add("carrot");
		list3.add("monkey");
		list3.add("zebra");
		list3.add("milk");
		
		// Test for equality and non-equality
		assertEquals(list1, list2);
		assertNotEquals(list1, list3);
		assertEquals(list2, list1);
		assertNotEquals(list2, list3);
		assertNotEquals(list3, list1);
		assertNotEquals(list3, list2);
	}
	
	/**
	 * tests the .hashCode() method
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		

		
		// Make two lists the same and one list different
		
		list1.add("apple");
		list1.add("banana");
		list1.add("carrot");
		list1.add("monkey");
		list1.add("zebra");
		
		list2.add("apple");
		list2.add("banana");
		list2.add("carrot");
		list2.add("monkey");
		list2.add("zebra");
		
		list3.add("apple");
		list3.add("banana");
		list3.add("carrot");
		list3.add("monkey");
		list3.add("zebra");
		list3.add("milk");
		
		// Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list2.hashCode(), list3.hashCode());
	}

}
 