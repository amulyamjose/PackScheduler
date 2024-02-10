package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * represents a student and their associated data
 * 
 * @author William Walton
 * @author Amulya Jose
 * @author Kavya Vadla
 */
public class Student extends User implements Comparable<Student> {
	/** The most credits a student can take in a semester */
	public static final int MAX_CREDITS = 18;

	/** The max credits this student can take per semester */
	private int maxCredits = MAX_CREDITS;

	/** The student's schedule */
	private Schedule schedule;

	/**
	 * constructor for Student with maxCredits
	 * 
	 * @param firstName  the first name of the student
	 * @param lastName   the last name of the student
	 * @param id         the id of the student
	 * @param email      the email of the student
	 * @param hashPW     the hashed password of the student
	 * @param maxCredits the max credits the student can take per semester
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * constructor for Student without maxCredits
	 * 
	 * @param firstName the first name of the student
	 * @param lastName  the last name of the student
	 * @param id        the id of the student
	 * @param email     the email of the student
	 * @param hashPW    the hashed password of the student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * getter method for maxCredits
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * setter method for maxCredits
	 * 
	 * @param maxCredits the maxCredits to set
	 * 
	 * @throws IllegalArgumentException "Invalid max credits" if
	 *                                  maxCredits parameter is less than 3 or more
	 *                                  than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits > MAX_CREDITS || maxCredits < 3) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Overrides for hashCode() method. Generate a hash code.
	 *
	 * @return the hash code of the `Student` object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}

	/**
	 * tells if an object is equal to this
	 * returns false if:
	 * the object is not a Student
	 * the superclass is not equal
	 * if maxCredits are not equal
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
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}

	/**
	 * gives a string representation of the class's fields as follows:
	 * firstName,lastName,id,email,password,maxCredits
	 * 
	 * @return a string representation of this class
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail()
				+ "," + getPassword() + "," + getMaxCredits();
	}

	/**
	 * compares student to students in the directory in order to be sorted
	 * in the directory
	 * 
	 * @param o Student that is being compared to students in the directory
	 * @return One of the following:
	 *         -1: this comes before the parameter
	 *         0: this is put at the same spot as the parameter (copy)
	 *         1: this comes after the parameter
	 */
	@Override
	public int compareTo(Student o) {
		if (this.getLastName().compareTo(o.getLastName()) != 0) {
			return this.getLastName().compareTo(o.getLastName());
		} else if (this.getFirstName().compareTo(o.getFirstName()) != 0) {
			return this.getFirstName().compareTo(o.getFirstName());
		} else if (this.getId().compareTo(o.getId()) != 0) {
			return this.getId().compareTo(o.getId());
		}
		return 0;
	}

	/**
	 * Getter method for the schedule
	 * 
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * checks if this student can add a course to their schedule
	 * 
	 * @param c the course we're checking if it can be added
	 * @return whether the course can be added or not
	 */
	public boolean canAdd(Course c) {
		return schedule.canAdd(c) && c.getCredits() + schedule.getScheduleCredits() <= getMaxCredits();
	}

}
