package edu.ncsu.csc216.pack_scheduler.util;



import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * The class that is testing the arrayQueue
 */
public class ArrayQueueTest {

	/**
	 * Inserting a single element into the Queue
	 */
	@Test
	public void testInsertingElements() {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>(5);
		
		//adding a single element
		queue.enqueue(1);
		assertEquals(1, queue.size());
		
		//adding multiple elements
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		assertEquals(4, queue.size());


}

	/**
	 * Removing a single element from the Queue
	 */
	@Test
	public void testRemoveElements() {
		ArrayQueue<String> queue = new ArrayQueue<String>(4);

		// Adding four elements to the queue
		queue.enqueue("Apple");
		queue.enqueue("Strawberry");
		queue.enqueue("Bread");
		queue.enqueue("Corn");

		// removing a single element
		queue.dequeue();
		assertEquals(3, queue.size());
		
		// removing multiple elements
		queue.dequeue();
		queue.dequeue();
		assertEquals(1, queue.size());

		// removing the last element from the queue
		queue.dequeue();
		assertEquals(0, queue.size());

		
	}

	/**
	 * Interleaved inserts and removes
	 */
	@Test
	public void testInterleavedActions() {
		ArrayQueue<String> queue = new ArrayQueue<String>(4);

		// Adding two elements to the queue
		queue.enqueue("Apple");
		queue.enqueue("Strawberry");
		
		// removing one element
		queue.dequeue();
		assertEquals(1, queue.size());
		
		// Adding two elements then removing one
		queue.enqueue("Bread");
		queue.enqueue("Corn");
		queue.dequeue();
		assertEquals(2, queue.size());
		
		// Removing two then adding three
		queue.dequeue();
		queue.dequeue();
		queue.enqueue("Wheat");
		queue.enqueue("Bagel");
		queue.enqueue("Pizza");
		assertEquals(3, queue.size());

		

}

	/**
	 * Attempting to remove an element from an empty Queue
	 */
	@Test
	public void testRemoveFromEmpty() {
		ArrayQueue<String> queue = new ArrayQueue<String>(1);

		assertThrows(NoSuchElementException.class, () -> queue.dequeue(), "Should throw NoSuchElementException");

}
	/**
	 * Setting the capacity to size
	 */
	@Test
	public void testSettingCapacityToSize() {
		ArrayQueue<String> queue = new ArrayQueue<String>(5);

		// Adding four elements to the queue
		queue.enqueue("Apple");
		queue.enqueue("Strawberry");
		queue.enqueue("Bread");
		queue.enqueue("Corn");

		// setting the capacity to size
		assertDoesNotThrow(
				() -> queue.setCapacity(queue.size()),
				"Should not thrown an exception");
		assertThrows(IllegalArgumentException.class,
				() -> queue.enqueue("cake"));
		
		// Attempting to set the capacity less than size
		assertEquals(4, queue.size());
		assertThrows(IllegalArgumentException.class,
				() -> queue.setCapacity(2));
	}
	
	/**
	 * Tests the isEmpty() method
	 */
	@Test
	public void testIsEmpty() {
		ArrayQueue<String> queue = new ArrayQueue<String>(5);
		assertTrue(queue.isEmpty());
	}
	
}
	
