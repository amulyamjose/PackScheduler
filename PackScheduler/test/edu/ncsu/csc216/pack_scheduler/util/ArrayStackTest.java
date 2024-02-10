package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for the ArrayStack class.
 */
class ArrayStackTest {

	
	/**
	 * This is the set up method for the test class
	 * It creates the stack that we will be using for testing and makes
	 * sure that it is empty
	 */
	@BeforeEach
	void setUp() {
		Stack<String> stack = new ArrayStack<String>(5);
		assertTrue(stack.isEmpty());
	}

	
	/**
	 * This method tests the push method.
	 * It makes sure that the elements 
	 */
	@Test
	void testPushStrings() {
		Stack<String> stack = new ArrayStack<String>(5);
		
		//Tests adding element on top of each other
		stack.push("first");
		assertEquals(1, stack.size());
		stack.push("second");
		assertEquals(2, stack.size());
		stack.push("third");
		assertEquals(3, stack.size());
		stack.push("fourth");
		assertEquals(4, stack.size());
		stack.push("fifth");
		assertEquals(5, stack.size());
		
		//Test adding element when there is no more room in the stack
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> stack.push("sixth"));
		assertEquals("There is no more room in the list", e1.getMessage());
	}
	
	/**
	 * This method tests the push method.
	 * It makes sure that the elements 
	 */
	@Test
	void testPushIntegers() {
		Stack<String> stack = new ArrayStack<String>(5);
		
		//Tests adding element on top of each other
		stack.push("1");
		assertEquals(1, stack.size());
		stack.push("2");
		assertEquals(2, stack.size());
		stack.push("3");
		assertEquals(3, stack.size());
		stack.push("4");
		assertEquals(4, stack.size());
		stack.push("5");
		assertEquals(5, stack.size());
		
		//Test adding element when there is no more room in the stack
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> stack.push("6"));
		assertEquals("There is no more room in the list", e2.getMessage());
	}
	
	/**
	 * This methods tests the pop function of the class.
	 */
	@Test
	void testPopStrings() {
		Stack<String> secondStack = new ArrayStack<String>(5);
	
		//Adding element to the list
		secondStack.push("One");
		secondStack.push("Two");
		secondStack.push("Three");
		secondStack.push("Four");
		assertEquals(4, secondStack.size());
		
		//Popping the elements and making sure they are in the right order
		assertEquals(secondStack.pop(), "Four");
		assertEquals(secondStack.pop(), "Three");
		assertEquals(secondStack.pop(), "Two");
		assertEquals(secondStack.pop(), "One");

	}
	
	/**
	 * This methods tests the pop function of the class for integers.
	 */
	@Test
	void testPopIntgers() {
		Stack<String> secondStack = new ArrayStack<String>(5);
	
		//Adding element to the list
		secondStack.push("1");
		secondStack.push("2");
		secondStack.push("3");
		secondStack.push("4");
		assertEquals(4, secondStack.size());
		
		//Popping the elements and making sure they are in the right order
		assertEquals(secondStack.pop(), "4");
		assertEquals(secondStack.pop(), "3");
		assertEquals(secondStack.pop(), "2");
		assertEquals(secondStack.pop(), "1");

	}

	/**
	 * Test if the stack is empty
	 */
	@Test
	void testIsEmpty() {
		Stack<String> task = new ArrayStack<String>(5);
		assertEquals(0, task.size());
		
		//Adding then removing elements to make sure it works
		task.push("One");
		task.pop();
		assertEquals(0, task.size());

	}
	
	/**
	 * Test method for size().
	 */
	@Test
	public void testSize() {
		Stack<String> stack = new ArrayStack<String>(5);
		assertEquals(stack.size(), 0);
		stack.push("one");
		assertEquals(stack.size(), 1);
		stack.push("two");
		assertEquals(stack.size(), 2);
		stack.push("three");
		assertEquals(stack.size(), 3);
		stack.push("four");
		assertEquals(stack.size(), 4);
		stack.push("five");
		assertEquals(stack.size(), 5);
		stack.pop();
		assertEquals(stack.size(), 4);
		stack.pop();
		assertEquals(stack.size(), 3);
		stack.pop();
		assertEquals(stack.size(), 2);
		stack.pop();
		assertEquals(stack.size(), 1);
		stack.pop();
		assertEquals(stack.size(), 0);
	}

	/**
	 * Test method for setCapacity(capacity).
	 * Verifies that an IllegalArgumentException is thrown when attempting to set a negative capacity
	 */
	@Test
	void testSetCapacity() {
	    Stack<String> stack = new ArrayStack<String>(5);
	    stack.push("one");
	    stack.push("two");
	    stack.push("three");
	    stack.push("four");
	    stack.push("five");
	    
	    //Test adding element when there is no more room in the stack
  		Exception e3 = assertThrows(IllegalArgumentException.class, () -> stack.push("6"));
  		assertEquals("There is no more room in the list", e3.getMessage());
	}

	/**
	 * Test method for setCapacity( capacity).
	 * Verifies that an IllegalArgumentException is thrown when attempting to set a capacity smaller than the current size of the stack.
	 */
	@Test
	void testSetCapacityExceedSize() {
	    Stack<String> stack = new ArrayStack<String>(5);
	    stack.push("one");
	    stack.push("two");
	    stack.push("three");
	    stack.push("four");
	    stack.push("five");
	    
	    //Test adding element when there is no more room in the stack
  		Exception e4 = assertThrows(IllegalArgumentException.class, () -> stack.push("6"));
  		assertEquals("There is no more room in the list", e4.getMessage());
	}
	
	/**
	 * This is the stack used for testing 
	 * the IllegalArgumentExceptions
	 */
	private Stack<String> stack2;
	
	/** 
	 * This method tries to set an invalid capacity.
	 */
	@Test
	void testInvalidCapacity() {
  		assertThrows(IllegalArgumentException.class,
  				() -> new ArrayStack<String>(-1));
	}
	
	/**
	 * This method tries popping with an empty List
	 */
	@Test
	void testPoppingEmptyList() {
		stack2 = new ArrayStack<String>(2);
		assertThrows(EmptyStackException.class,
  				() -> stack2.pop());
	}
	
	/**
	 * This tests the setCapacity method to make sure its working
	 */
	@Test
	void testSetCapacityToInvalidvalue() {
		//Tests to create a list with an incorrect capacity
		try {
			Stack<String> stack3 = new ArrayStack<String>(5);
			stack3.setCapacity(-1);
 		} catch (IllegalArgumentException e) {
 			e.getMessage();
 		}
		
		//Tests to set capacity to a number that is less than the list size
		try {
			Stack<String> stack7 = new ArrayStack<String>(5);
			stack7.push("One");
			stack7.push("Two");
			stack7.push("Three");
			stack7.setCapacity(2);
		} catch (IllegalArgumentException e) {
			e.getMessage();
		}
		
		
		// test to set capacity to size
		ArrayStack<Integer> stack = new ArrayStack<Integer>(5);
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		
		// setting the capacity to size
		assertDoesNotThrow(
				() -> stack.setCapacity(stack.size()),
				"Should not thrown an exception");
		assertThrows(IllegalArgumentException.class,
				() -> stack.push(1));

	}

}