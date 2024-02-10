package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests InvalidTransitionException
 * @author Amulya Jose, Jacob Phillips, William Walton
 */
class InvalidTransitionExceptionTest {
	/**
	 * Tests InvalidTransitionException(message) - parameterized constructor
	 */
	@Test
	void testInvalidTransitionExceptionString() {
		InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Tests InvalidTransitionException() - parameterless constructor
	 */
	@Test
	void testInvalidTransitionException() {
		InvalidTransitionException ce = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ce.getMessage());
	}
}
