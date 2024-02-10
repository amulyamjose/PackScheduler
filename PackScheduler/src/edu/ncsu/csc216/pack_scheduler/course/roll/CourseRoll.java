package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Holds a list of students enrolled in a course
 * @author Jacob Phillips
 * @author William Walton
 */
public class CourseRoll {
    /** Holds the overall minimum enrollment ever */
    private static final int MIN_ENROLLMENT = 10;
    /** Holds the overall max enrollment ever */
    private static final int MAX_ENROLLMENT = 250;
    /** The size of a waitlist */
    private static final int WAITLIST_SIZE = 10;
    /** Holds the list of students */
    private LinkedAbstractList<Student> roll;
    /** Holds the cap for enrollment for this specific class */
    private int enrollmentCap;
    /** A waitlist for the course */
    private ArrayQueue<Student> waitlist;
    /** The course for students to enroll into*/
    private Course course;

    /**
     * Creates a new course enrollment counter with a waitlist for a specific Course
     * 
     * @param cap set capacity for enrollment
     * @param course the Course the CourseRoll is associated with
     * @throws IllegalArgumentException if the course parameter is null
     */
    public CourseRoll(int cap, Course course) {
    	if (course == null) {
    		throw new IllegalArgumentException();
    	}
        setEnrollmentCap(cap);
        roll = new LinkedAbstractList<Student>(MAX_ENROLLMENT);
        this.waitlist = new ArrayQueue<Student>(WAITLIST_SIZE);
        this.course = course;
    }

    /**
     * Returns the current cap for enrollment
     * 
     * @return the current cap for enrollment
     */
    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    /**
     * Sets the current enrollment capacity
     * 
     * @param cap the cap to set
     * @throws IllegalArgumentException "Invalid enrollment capacity." if
     * 		parameter cap is less than the MIN_ENROLLMENT
     * 		parameter cap is more than the MAX_ENROLLMENT
     * @throws IllegalArgumentException "Cannot decrease enrollment capacity below the current number of students in the class."
     * 		if the roll exists and is bigger than the new capacity
     */
    public void setEnrollmentCap(int cap) {
        if (cap < MIN_ENROLLMENT || cap > MAX_ENROLLMENT) {
            throw new IllegalArgumentException("Invalid enrollment capacity.");
        }

        if (roll != null && cap < roll.size()) {
            throw new IllegalArgumentException(
                    "Cannot decrease enrollment capacity below the current number of students in the class.");
        }
        
        if (roll != null) {
        	roll.setCapacity(cap);
        }
        
        enrollmentCap = cap;
        
    }

    /**
     * Enrolls a new student in the course. If the CourseRoll has reached capacity, the Student is added to the 
     * waitlist. If the size of the roll is the same as the enrollmentCap, the student is added to the waitlist.
     * If the waitlist if full, then the Student can't enroll and an IllegalArgumentException is thrown.
     * 
     * @param student student to enroll
     * @throws IllegalArgumentException if
     * 		parameter student is null or
     * 		the roll is at capacity
     * @throws IllegalArgumentException "Error adding the student."
     * 		if the LinkedList roll encounters an error when trying to add a student or if the waitlist is full
     */
    public void enroll(Student student) {
        if (student == null || getNumberOnWaitlist() == WAITLIST_SIZE) {
            throw new IllegalArgumentException();
        }
        
        if (enrollmentCap == roll.size()) {
        	waitlist.enqueue(student);
        	student.getSchedule().addCourseToSchedule(course);
        } else {
        	try {
                roll.add(roll.size(), student);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error adding the student.");
            }
        }
    }

    /**
     * Drops a student from the course. If the student is dropped from the course, the first person on the 
     * waitlist is added to the main roll. If the student is in the waitlist, they are removed from the
     * waitlist.
     * 
     * @param student student to drop
     * @throws IllegalArgumentException "Error removing the student." if student is not in the roll or null
     */
    public void drop(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Error removing the student.");
        }
        
        for (int i = 0; i < roll.size(); i++) {
            if (student.equals(roll.get(i))) {
            	try {
                    Student stdt = roll.remove(i);
                    stdt.getSchedule().removeCourseFromSchedule(course);
                    if (getNumberOnWaitlist() != 0) {
                    	Student s = waitlist.dequeue();
                        roll.add(roll.size(), s);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error removing the student.");
                }
            }
        }  
        
        ArrayQueue<Student> waitlistCopy = new ArrayQueue<Student>(WAITLIST_SIZE);
        int size = waitlist.size();
    	for (int i = 0; i < size; i++) {
    		Student studentCompare = waitlist.dequeue();
    		if (!student.equals(studentCompare)) {
    			waitlistCopy.enqueue(studentCompare);
    		}
    	}
    	waitlist = waitlistCopy;
    	
    }

    /**
     * Returns the number of open seats in the course
     * 
     * @return the number of open seats in the course
     */
    public int getOpenSeats() {
        return enrollmentCap - roll.size();
    }

    /**
     * Returns whether the student can enroll in the course or be added to the waitlist or not
     * 
     * @param student student to enroll
     * @return whether the student can enroll in the course or be added to the waitlist or not
     */
    public boolean canEnroll(Student student) {
        if (getOpenSeats() == 0 || waitlist.size() == WAITLIST_SIZE)
            return false;

        for (int i = 0; i < roll.size(); i++) {
            if (roll.get(i).equals(student)) {
                return false;
            }
        }
        
//        if (waitlist.contains(student)) {
//        	return false;
//        }
//
//        return true;
        
        return !waitlist.contains(student);
    }
    
    /**
     * Returns the number of Students on the waitlist
     * @return the number of Students on the waitlist
     */
    public int getNumberOnWaitlist() {
    	return waitlist.size();
    }
}