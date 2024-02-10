package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Represents the Faculty and their appropiate info
 * @author Carter Gentile
 */
public class Faculty extends User {
	/** The least courses a faculty can take in a semester */
	public static final int MIN_COURSES = 1;
	/** The most courses a faculty can take in a semester */
	public static final int MAX_COURSES = 3;
	/** The max courses this faculty can take per semester */
	private int maxCourses = MAX_COURSES;
	/** The field of type FacultySchedule to Faculty */
	private FacultySchedule schedule;
	
	/**
	 * constructor for Faculty with maxCourses 
	 *  
	 * @param firstName  the first name of the faculty
	 * @param lastName   the last name of the faculty
	 * @param id         the id of the faculty
	 * @param email      the email of the faculty
	 * @param hashPW     the hashed password of the faculty
	 * @param maxCourses the max courses the faculty can take per semester
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		this.schedule = new FacultySchedule(id); 
	}
	
	/**
	 * Getter method for schedule
	 * @return FacultySchedule of the faculty
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	
	/**
	 * Method that gives back true or false based on if schedule is overloaded (which is when the number
	 * of courses is greater than the Faculty's maxCourses)
	 * @return boolean value if scheduled courses is greater than maxCourses
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}
	
	
	/**
	 * getter method for maxCourses
	 * 
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * setter method for maxCourses
	 * 
	 * @param maxCourses the maxCourses to set
	 * 
	 * @throws IllegalArgumentException "Invalid max courses" if
	 *                                  maxCourses parameter is less than 1 or more
	 *                                  than 2
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses > 3 || maxCourses < 1) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	
	/**
	 * Overrides for hashCode() method. Generate a hash code.
	 *
	 * @return the hash code of the `Faculty` object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCourses);
		return result;
	}

	/**
	 * tells if an object is equal to this
	 * returns false if:
	 * the object is not a Faculty
	 * the superclass is not equal
	 * if maxCourses are not equal
	 * otherwise, returns true
	 * 
	 * @param obj the object to be compared to this
	 * @return boolean representation of whether or not this equals an object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}

	/**
	 * gives a string representation of the Faculty's fields as follows:
	 * firstName,lastName,id,email,password,maxCourses
	 * 
	 * @return a string representation of this faculty
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail()
				+ "," + getPassword() + "," + getMaxCourses();
	}
}
