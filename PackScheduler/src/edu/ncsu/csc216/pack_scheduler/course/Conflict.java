/**
 * The package of this class
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * checks if the time of an activity conflicts with another activity
 * @author William Walton
 */
public interface Conflict {

	/**
	 * checks if the time of an activity conflicts with another activity
	 * an activity conflicts with another if:
	 * 		they are not arranged
	 * 		they have at least one of the same meeting days
	 *  	and the times overlap, including on the same minute (an event ends at 9:00 and another starts at 9:00)
	 * @param possibleConflictingActivity the activity to be compared to the activity implementing this
	 * @throws ConflictException if the activities overlap in time
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
