package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;

/**
 * A class that represents a course at ncsu
 * stores and gets the title, section, credits, instructorId, meeting days,
 * start time, end time
 * can get the name of the course after it's created
 * 
 * @author William Walton
 * @author Amulya Jose
 */
public class Course extends Activity implements Comparable<Course> {

	/** The length of the Course's section */
	private static final int SECTION_LENGTH = 3;
	/** The maximum number of credit hours for a Course */
	private static final int MAX_CREDITS = 5;
	/** The minimum number of credit hours for a Course */
	private static final int MIN_CREDITS = 1;
	/** Holds the overall minimum enrollment ever */
    private static final int MIN_ENROLLMENT = 10;
    /** Holds the overall max enrollment ever */
    private static final int MAX_ENROLLMENT = 250;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** The validator of the course name */
	private CourseNameValidator validator;
	/** A CourseRoll holding a list of students enrolled in a course */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         the Course's name
	 * @param title        the Course's title
	 * @param section      the Course's section
	 * @param credits      the Course's credit hours
	 * @param instructorId the Course's instructor
	 * @param enrollmentCap the cap for enrollment for this specific class
	 * @param meetingDays  the Course's meeting days
	 * @param startTime    the Course's starting time
	 * @param endTime      the Course's ending time
	 * @throws IllegalArgumentException if the enrollment capacity is less than 10 or greater than 250
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Invalid enrollment capacity");
		}
		validator = new CourseNameValidator();
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(enrollmentCap, this);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays
	 * courses that are arranged:
	 * 
	 * @param name         the Course's name
	 * @param title        the Course's title
	 * @param section      the Course's section
	 * @param credits      the Course's credit hours
	 * @param instructorId the Course's instructor
	 * @param enrollmentCap the cap for enrollment for this specific class
	 * @param meetingDays  the Course's meeting days
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
		roll = new CourseRoll(enrollmentCap, this);
	}

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8,
	 * does not contain a space between letter characters and number characters, has
	 * less than 1
	 * or more than 4 letter characters, and not exactly three trailing digit
	 * characters, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		// Old code to determine if name is valid or not - kept for records and refrence
		/*
		 * // throw exception if the name is null
		 * if (name == null)
		 * throw new IllegalArgumentException("Invalid course name.");
		 * 
		 * // throw an exception if the name is an empty string
		 * // throw exception if the name contains less than 5 characters or greater
		 * than 8 characters
		 * if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
		 * throw new IllegalArgumentException("Invalid course name.");
		 * 
		 * // check for pattern of L[LLL] NNN
		 * int letters = 0;
		 * int digits = 0;
		 * boolean foundSpace = false;
		 * char currentNameChar;
		 * for (int i = 0; i < name.length(); i++) {
		 * currentNameChar = name.charAt(i);
		 * if (!foundSpace) {
		 * if (currentNameChar == ' ')
		 * foundSpace = true;
		 * else if (Character.isAlphabetic(currentNameChar))
		 * letters += 1;
		 * else
		 * throw new IllegalArgumentException("Invalid course name.");
		 * } else {
		 * if (!Character.isDigit(currentNameChar))
		 * throw new IllegalArgumentException("Invalid course name.");
		 * digits += 1;
		 * }
		 * 
		 * 
		 * }
		 * if (letters < MIN_LETTER_COUNT || letters > MAX_LETTER_COUNT)
		 * throw new IllegalArgumentException("Invalid course name.");
		 * 
		 * if (digits < DIGIT_COUNT || digits > DIGIT_COUNT)
		 * throw new IllegalArgumentException("Invalid course name.");
		 * 
		 */

		try {
			if(!validator.isValid(name)) {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;

	}

	/**
	 * Returns the Course's section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section. if the section is null or
	 * not exactly 3 digits, throws IllegalArgumentException
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section parameter is invalid
	 */
	public void setSection(String section) {

		// checks if the section is null or the wrong size
		if (section == null || section.length() != SECTION_LENGTH)
			throw new IllegalArgumentException("Invalid section.");

		// checks that all characters are digits
		for (int i = 0; i < SECTION_LENGTH; i++) {
			if (!Character.isDigit(section.charAt(i)))
				throw new IllegalArgumentException("Invalid section.");
		}

		this.section = section;
	}

	/**
	 * Returns the Course's credit hours
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credit hours. if the parameter
	 * less than 1 or greater than 5
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException "Invalid credits." if the credits are less
	 *                                  than the minimum, or more than the maximum
	 */
	public void setCredits(int credits) {

		// checks if credits are not a number or outside the acceptable range
		if (credits < MIN_CREDITS || credits > MAX_CREDITS)
			throw new IllegalArgumentException("Invalid credits.");

		this.credits = credits;
	}

	/**
	 * Returns the Course's instructor.
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor. if the instructorId is an empty string, throws an IllegalArgumentException
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorId is invalid
	 */
	public void setInstructorId(String instructorId) {

		if (instructorId != null && instructorId.length() == 0)
			throw new IllegalArgumentException("Invalid instructor id.");

		this.instructorId = instructorId;
	}

	/**
	 * sets the Course's meetingDays, startTime, and endTime. if the
	 * meetingDays is null or empty, throws IllegalArgumentException
	 * if MeetingDays == "A"
	 * call super.setMeetingDaysAndTimes(...)
	 * end method
	 * otherwise if a character in meetingDays is not M, T, W, H, or F, throw
	 * IllegalArgumentException
	 * otherwise, call super.setMeetingDaysAndTimes(...)
	 * 
	 * See Activity.setMeetingDaysAndTime(...) for possible IllegalArgumentException
	 * due to invalid time
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime   the startTime to set
	 * @param endTime     the endTime to set
	 * @throws IllegalArgumentException "Invalid meeting days and times." if the
	 *                                  meeting days
	 *                                  are invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (!"A".equals(meetingDays)) { // filtering only not arranged

			String weekChart = "MTWHF"; // valid days of the week, not including weekend
			int weekIndex = -1;

			// checks that all meetingDays are days of the week
			for (int i = 0; i < meetingDays.length(); i++) {
				weekIndex = weekChart.indexOf(meetingDays.charAt(i));
				if (weekIndex == -1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}

				weekChart = weekChart.substring(0, weekIndex) + weekChart.substring(1 + weekIndex); // removing the day
																									// from the week (no
																									// repeats)
			}

		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * gives the hash code of the Course
	 * 
	 * @return the hash code of the Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(credits, instructorId, name, section);
		return result;
	}

	/**
	 * tells if this Course is equal to an object
	 * returns false if the object is null
	 * returns false if the object is not the same class as this
	 * returns true if the credits, instructorId, name, and section are equal
	 * 
	 * @param obj the object to be compared to this course
	 * @return boolean representation of whether or not the object is equal to this
	 *         Course
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(name, other.name) && Objects.equals(section, other.section);
	}

	/**
	 * Returns a comma separated value String of all Course fields as follows:
	 * name,title,section,credits,instructorId,meetingDays[,startTime,endTime]
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + 
				roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * gets an array displaying the contents of the course, in order:
	 * name, section, title, meetingString
	 * 
	 * @return an array of strings representing the course
	 */
	@Override
	public String[] getShortDisplayArray() {
		return new String[] {
				name,
				section,
				getTitle(),
				getMeetingString(),
				"" + roll.getOpenSeats()
		};
	}

	/**
	 * gets an array displaying the contents of the course, in order:
	 * name, section, title, credits, instructorId, meetingString, ""
	 * Event uses the 7th spot so for uniformity, we are keeping this as long
	 * 
	 * @return an array of strings representing the course
	 */
	@Override
	public String[] getLongDisplayArray() {
		return new String[] {
				name,
				section,
				getTitle(),
				String.valueOf(credits),
				instructorId,
				getMeetingString(),
				""
		};
	}

	/**
	 * returns false if the activity is null
	 * if the activity is a different class than this, returns false
	 * if the activity and this have the same name, return true
	 * 
	 * @param activity the Activity to be compared to this
	 * @return boolean representation of whether or not the activity is a duplicate
	 *         of this
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity == null) {
			return false;
		}
		if (getClass() == activity.getClass()) {
			Course course = (Course) activity;
			return getName().equals(course.getName());
		}
		return false;
	}

	/**
	 * Compares fields of this to another course by name, then section
	 * returning whether this is before/copy/after parameter
	 * 
	 * @param course the course to compare this to
	 * @return One of the following:
	 *         -1: this comes before the parameter
	 *         0: this is put at the same spot as the parameter (copy)
	 *         1: this comes after the parameter
	 */
	@Override
	public int compareTo(Course course) {
		if (name.compareTo(course.getName()) != 0) {
			return name.compareTo(course.getName());
		}
		if (section.compareTo(course.getSection()) != 0) {
			return section.compareTo(course.getSection());
		}
		return 0;
	}
	
	/**
	 * Returns the CourseRoll holding a list of students enrolled in a course
	 * @return the CourseRoll holding a list of students enrolled in a course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}
