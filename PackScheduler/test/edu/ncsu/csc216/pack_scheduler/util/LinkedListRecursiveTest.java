package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedListRecursive class
 */
public class LinkedListRecursiveTest {
	
    /**
     * Test method for add and remove methods.
     */
    @Test
    public void testAddAndRemove() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertThrows(IllegalArgumentException.class,
        		() -> list.add("cherry"));
        
     // Test removing the last element
        assertEquals("cherry", list.remove(2));
        assertEquals(2, list.size());
        
        // Test removing the first element
        assertEquals("apple", list.remove(0));
        assertEquals(1, list.size());
        
        // Test adding elements
        list.add("date");
        list.add("elderberry");
        assertEquals(3, list.size());
        
        // Test removing the middle element
        assertEquals("date", list.remove(1));
        assertEquals(2, list.size());
    }

    /**
     * Test method for set method.
     */
    @Test
    public void testSet() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        // Test setting the first element
        assertDoesNotThrow(
        		() -> list.set(0, "apricot"));
        assertEquals("apricot", list.get(0));
        
        // Test setting the last element
        assertDoesNotThrow(
        		() -> list.set(2, "coconut"));
        assertEquals("coconut", list.get(2));
        
        // Test setting the middle element
        assertDoesNotThrow(
        		() -> list.set(1, "blueberry"));
        assertEquals("blueberry", list.get(1));
    }

    /**
     * Test method for add(index, element) method.
     */
    @Test
    public void testAddAtIndex() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertThrows(IndexOutOfBoundsException.class,
        		() -> list.add(-1, "coconut"));
        assertThrows(NullPointerException.class,
        		() -> list.add(0, null));
        assertThrows(IllegalArgumentException.class,
        		() -> list.add(0, "cherry"));
        
        // Test adding at the beginning
        list.add(0, "apricot");
        assertEquals("apricot", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("banana", list.get(2));
        assertEquals("cherry", list.get(3));
        
        // Test adding in the middle
        list.add(2, "blueberry");
        assertEquals("apricot", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("blueberry", list.get(2));
        assertEquals("banana", list.get(3));
        assertEquals("cherry", list.get(4));
        
        // Test adding at the end
        list.add(5, "coconut");
        assertEquals("apricot", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("blueberry", list.get(2));
        assertEquals("banana", list.get(3));
        assertEquals("cherry", list.get(4));
        assertEquals("coconut", list.get(5));
    }

    /**
     * Test method for contains method.
     */
    @Test
    public void testContains() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertTrue(list.contains("apple"));
        assertTrue(list.contains("banana"));
        assertTrue(list.contains("cherry"));
        assertFalse(list.contains("date"));
    }

    /**
     * Test method for get method.
     */
    @Test
    public void testGet() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("cherry", list.get(2));
        
        try {
            list.get(-1);
            fail("Should have thrown an IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Exception successfully thrown
        }
        
        try {
            list.get(3);
            fail("Should have thrown an IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Exception successfully thrown
        }
    }

    /**
     * Test method for isEmpty method.
     */
    @Test
    public void testIsEmpty() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        assertTrue(list.isEmpty());
        
        list.add("apple");
        assertFalse(list.isEmpty());
    }

    /**
     * Test method for remove(element) method.
     */
    @Test
    public void testRemoveElement() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("berry");
        
        assertTrue(list.remove("berry"));
        assertEquals(3, list.size());
        assertFalse(list.contains("berry"));
        
        assertTrue(list.remove("banana"));
        assertEquals(2, list.size());
        assertFalse(list.contains("banana"));
        
        assertFalse(list.remove("date"));
        assertEquals(2, list.size());
    }
    
    /**
     * Test method for remove(int) method
     */
    @Test
    public void testRemoveAtIndex() {
    	LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("berry");
        
        assertEquals("berry", list.remove(3));
        assertEquals(3, list.size());
        assertFalse(list.contains("berry"));
        
        assertThrows(IndexOutOfBoundsException.class,
        		() -> list.remove(-5));
        assertThrows(IndexOutOfBoundsException.class,
        		() -> list.remove(10));
    }
    
    /**
     * Testing removing based off TS test
     */
    @Test
    public void testRemove() {
        LinkedListRecursive<String> list = new LinkedListRecursive<String>();
        list.add("orange");
        list.add("banana");
        list.add("apple");
        list.add("kiwi");
        
        assertEquals("orange", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("apple", list.get(2));
        assertEquals("kiwi", list.get(3));

        assertEquals("banana", list.remove(1));
        
        assertEquals("orange", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("kiwi", list.get(2));
        
        assertEquals("orange", list.remove(0));
        
        assertFalse(list.contains("orange"));
        assertEquals("apple", list.get(0));
        assertEquals("kiwi", list.get(1));
    }

}
