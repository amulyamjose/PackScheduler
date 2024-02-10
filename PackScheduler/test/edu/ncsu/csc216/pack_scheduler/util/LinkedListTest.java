package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Test class for LinkedList implementation.
 * 
 * @author Nathan-Blaise MIHINDU MI NZAMBE
 */
public class LinkedListTest {
    
    /**
     * Test method for NoSuchElementException when calling next or previous on an empty list.
     */
    @Test
    public void testNoSuchElementException() {
        LinkedList<String> list = new LinkedList<String>();
        
        ListIterator<String> iterator = list.listIterator();
        
        try {
            iterator.next();
            fail("Should have thrown a NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Exception successfully thrown
        }
        
        try {
            iterator.previous();
            fail("Should have thrown a NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Exception successfully thrown
        }
    }

    /**
     * Test method for listIterator method.
     */
    @Test
    public void testListIterator() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        // Test forward traversal
        ListIterator<String> iterator = list.listIterator();
        assertTrue(iterator.hasNext());
        assertEquals("apple", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("banana", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("cherry", iterator.next());
        assertFalse(iterator.hasNext());
        
        // Test backward traversal
        assertTrue(iterator.hasPrevious());
        assertEquals("cherry", iterator.previous());
        assertTrue(iterator.hasPrevious());
        assertEquals("banana", iterator.previous());
        assertTrue(iterator.hasPrevious());
        assertEquals("apple", iterator.previous());
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Test method for add and remove methods.
     */
    @Test
    public void testAddAndRemove() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        // Test removing the first element
        assertEquals("apple", list.remove(0));
        assertEquals(2, list.size());
        
        // Test removing the last element
        assertEquals("cherry", list.remove(1));
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
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        // Test setting the first element
        assertEquals("apple", list.set(0, "apricot"));
        assertEquals("apricot", list.get(0));
        
        // Test setting the last element
        assertEquals("cherry", list.set(2, "coconut"));
        assertEquals("coconut", list.get(2));
        
        // Test setting the middle element
        assertEquals("banana", list.set(1, "blueberry"));
        assertEquals("blueberry", list.get(1));
    }

    /**
     * Test method for add(index, element) method.
     */
    @Test
    public void testAddAtIndex() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
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
     * Test method for clear method.
     */
    @Test
    public void testClear() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertFalse(list.isEmpty());
        
        list.clear();
        
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    /**
     * Test method for contains method.
     */
    @Test
    public void testContains() {
        LinkedList<String> list = new LinkedList<String>();
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
        LinkedList<String> list = new LinkedList<String>();
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
     * Test method for indexOf method.
     */
    @Test
    public void testIndexOf() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertEquals(0, list.indexOf("apple"));
        assertEquals(1, list.indexOf("banana"));
        assertEquals(2, list.indexOf("cherry"));
        assertEquals(-1, list.indexOf("date"));
    }

    /**
     * Test method for isEmpty method.
     */
    @Test
    public void testIsEmpty() {
        LinkedList<String> list = new LinkedList<String>();
        assertTrue(list.isEmpty());
        
        list.add("apple");
        assertFalse(list.isEmpty());
    }

    /**
     * Test method for lastIndexOf method.
     */
    @Test
    public void testLastIndexOf() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertEquals(0, list.lastIndexOf("apple"));
        assertEquals(1, list.lastIndexOf("banana"));
        assertEquals(2, list.lastIndexOf("cherry"));
    }

    /**
     * Test method for remove(element) method.
     */
    @Test
    public void testRemoveElement() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        assertTrue(list.remove("banana"));
        assertEquals(2, list.size());
        assertFalse(list.contains("banana"));
        
        assertFalse(list.remove("date"));
        assertEquals(2, list.size());
    }

    /**
     * Test method for toArray method.
     */
    @Test
    public void testToArray() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        Object[] array = list.toArray();
        assertEquals("apple", array[0]);
        assertEquals("banana", array[1]);
        assertEquals("cherry", array[2]);
    }

    /**
     * Test method for iterator.
     */
    @Test
    public void testIterator() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        
        StringBuilder sb = new StringBuilder();
        for (String fruit : list) {
            sb.append(fruit);
        }
        assertEquals("applebananacherry", sb.toString());
    }
}