/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * the exception that is thrown when activities conflict in schedule
 */
public class InvalidTransitionException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructs the exception without a string
	 */
	public InvalidTransitionException() { 
		this("Invalid FSM Transition.");
	}
	
	/**
	 * constructs an exception with a string
	 * @param message the message to be displayed
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	


}
