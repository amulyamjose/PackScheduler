package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Manages the list of courses
 * 
 * @author Jacob Phillips
 */
public class Schedule {
    /** Holds the name of the schedule */
    private String title;
    /** The list of courses */
    private ArrayList<Course> schedule;

    /**
     * Creates a new schedule
     */
    public Schedule() {
        setTitle("My Schedule");
        schedule = new ArrayList<Course>();
    }

    /**
     * Adds a course to the schedule
     * 
     * @param course a Course to add
     * @return whether the course was added or not; will always return true if it
     *         was added
     * @throws IllegalArgumentException if any of the following:
     *                                  The course's timeslot is already filled
     *                                  The course has already been added
     * @throws NullPointerException     if the course is null
     */
    public boolean addCourseToSchedule(Course course) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isDuplicate(course))
                throw new IllegalArgumentException("You are already enrolled in " + course.getName());
            try {
                schedule.get(i).checkConflict(course);
            } catch (ConflictException e) {
                throw new IllegalArgumentException("The course cannot be added due to a conflict.");
            }
        }

        schedule.add(course);
        return true;
    }

    /**
     * Removes a course from the schedule
     * 
     * @param course Course to remove
     * @return whether the course was removed. If not found, returns false. If
     *         found, returns true.
     */
    public boolean removeCourseFromSchedule(Course course) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).equals(course)) {
                schedule.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the total amount of credits being taken by all of this student's
     * classes
     * 
     * @return the total amount of credits being taken by all of this student's
     *         classes
     */
    public int getScheduleCredits() {
        int count = 0;
        for (int i = 0; i < schedule.size(); i++) {
            count += schedule.get(i).getCredits();
        }
        return count;
    }

    /**
     * Returns whether the specified course can be added or not
     * returns false if:
     * course is null
     * it is a duplicate of a pre-existing course
     * if it conflicts with the time of another course
     * otherwise returns true
     * 
     * @param course course to test schedule compatibility with
     * @return whether the specified course can be added or not
     */
    public boolean canAdd(Course course) {
        if (course == null) {
            return false;
        }
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isDuplicate(course))
                return false;
            try {
                schedule.get(i).checkConflict(course);
            } catch (ConflictException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Erases all information about the schedule
     */
    public void resetSchedule() {
        setTitle("My Schedule");
        schedule = new ArrayList<Course>();
    }

    /**
     * Returns a 2d string array representation of the schedule
     * 
     * @return a 2d string array representation of the schedule
     */
    public String[][] getScheduledCourses() {
        String[][] ret = new String[schedule.size()][5];
        for (int i = 0; i < schedule.size(); i++) {
            ret[i] = schedule.get(i).getShortDisplayArray();
        }

        return ret;
    }

    /**
     * Sets the title of the schedule
     * 
     * @param title title to set
     * @throws IllegalArgumentException "Title cannot be null." if title is null
     */
    public void setTitle(String title) {
        if (title == null)
            throw new IllegalArgumentException("Title cannot be null.");

        this.title = title;
    }

    /**
     * Returns the title of the schedule
     * 
     * @return the title of the schedule
     */
    public String getTitle() {
        return title;
    }
}
