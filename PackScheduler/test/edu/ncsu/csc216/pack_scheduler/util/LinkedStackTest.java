package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;



/**
 * Tests the LinkedStack class
 */
class LinkedStackTest {

	/**
	 * Tests the push method
	 */
	@Test
	public void testAddElement() {
		LinkedStack<String> stack = assertDoesNotThrow(
				() -> new LinkedStack<String>(5),
				"Should not thrown an exception");
		
		// adding one element into the stack
		assertDoesNotThrow(
				() -> stack.push("hello"),
				"Should not thrown an exception");
		assertEquals(1, stack.size());
		
		// adding multiple elements into the stack
		assertDoesNotThrow(
				() -> stack.push("bye"),
				"Should not thrown an exception");
		assertDoesNotThrow(
				() -> stack.push("wow"),
				"Should not thrown an exception");
		assertDoesNotThrow(
				() -> stack.push("dog"),
				"Should not thrown an exception");
		assertEquals(4, stack.size());

	}
	
	/**
	 * Tests the pop method
	 */
	@Test
	public void testRemoveElement() {
		LinkedStack<Integer> stack = new LinkedStack<Integer>(5);
		
		// removing an element from an empty stack
		assertThrows(EmptyStackException.class,
				() -> stack.pop());
		
		// removing one element from the stack
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);

		Integer int1 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(4, stack.size());
		assertEquals(5, int1);
		
		// removing multiple elements from the stack
		Integer int2 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(4, int2);
		Integer int3 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(3, int3);
		Integer int4 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(2, int4);
		assertEquals(1, stack.size());
		
		// removing the last element from the stack
		Integer int5 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(1, int5);
		assertEquals(0, stack.size());

	}
	
	/**
	 * Tests push and pop methods with interleaved inserts and removes
	 */
	@Test
	public void testAddAndRemove() {
		// interleaved inserts and removes
		LinkedStack<Integer> stack = new LinkedStack<Integer>(5);

		// pushing one element
		assertDoesNotThrow(
				() -> stack.push(1),
				"Should not thrown an exception");
		assertEquals(1, stack.size());
		
		// popping one element
		Integer int1 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(1, int1);
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
		
		// pushing two elements
		assertDoesNotThrow(
				() -> stack.push(1),
				"Should not thrown an exception");
		assertEquals(1, stack.size());
		assertDoesNotThrow(
				() -> stack.push(2),
				"Should not thrown an exception");
		assertEquals(2, stack.size());
		
		// popping one element
		Integer int2 = assertDoesNotThrow(
				() -> stack.pop(),
				"Should not thrown an exception");
		assertEquals(2, int2);
		assertEquals(1, stack.size());
		assertFalse(stack.isEmpty());
		
	}
	
	/**
	 * Tests the setCapacity method
	 */
	@Test
	public void testSetCapacity() {
		LinkedStack<Integer> stack = new LinkedStack<Integer>(5);
	
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
		
		// attempting to set the capacity to less than size
		assertEquals(4, stack.size());
		assertThrows(IllegalArgumentException.class,
				() -> stack.setCapacity(2));
	}

}
