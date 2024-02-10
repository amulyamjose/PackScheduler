/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * the exception that is thrown when activities conflict in schedule
 */
public class ConflictException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructs the exception without a string
	 */
	public ConflictException() { 
		super("Schedule conflict.");
	}
	
	/**
	 * constructs an exception with a string
	 * @param message the message to be displayed
	 */
	public ConflictException(String message) {
		super(message);
	}
	


}
