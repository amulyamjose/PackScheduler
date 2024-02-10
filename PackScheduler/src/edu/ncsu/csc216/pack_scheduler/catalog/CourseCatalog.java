/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Holds the courses needed in the catalog, can add, remove,
 * and get courses from the catalog
 * 
 * @author Kavya Vadla
 */
public class CourseCatalog {


	/** A sorted list of courses that make up the catalog */
	private SortedList<Course> catalog;

	/**
	 * Constructs an empty catalog
	 */
	public CourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * constructs a new empty catalog, erasing the first one
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid
	 * Courses are ignored. If the file to read cannot be found or the permissions
	 * are incorrect
	 * a File NotFoundException is caught and an IllegalArgumentException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @throws IllegalArgumentException if the file cannot be found or read
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds specified course to schedule and throws IllegalArgumentException if
	 * course is already in schedule
	 * 
	 * @param name         name of course
	 * @param title        title of course
	 * @param section      section of course
	 * @param credits      credits of course
	 * @param instructorId course's instructorId
	 * @param enrollmentCap course's enrollment capacity
	 * @param meetingDays  meeting days of course
	 * @param startTime    start time of course
	 * @param endTime      end time of course
	 * @return boolean of if the course could be added to the schedule, returns
	 *         false if there is a duplicate course
	 *         already in the schedule
	 * @throws IllegalArgumentException if course could not be constructed
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		try {
			Course add = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
			for (int i = 0; i < catalog.size(); i++) {
				Course catalogCourse = catalog.get(i);
				if (catalogCourse.isDuplicate(add) && catalogCourse.getSection().equals(add.getSection())) {
					return false;
				}
			}
			catalog.add(add);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return true;
	}

	/**
	 * Removes specified course to schedule
	 * 
	 * @param name    name of course
	 * @param section section of course
	 * @return boolean of if the course could be added to the schedule
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			String catalogName = catalog.get(i).getName();
			String catalogSect = catalog.get(i).getSection();
			if (catalogName.equals(name) && catalogSect.equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches course catalog for course and return the course if found.
	 * 
	 * @param name    name of course you are searching for
	 * @param section section of course you are searching for
	 * @return course you are searching for if it exists in the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			String catalogName = catalog.get(i).getName();
			String catalogSect = catalog.get(i).getSection();
			if (catalogName.equals(name) && catalogSect.equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * Gets the String array of the course catalog
	 * 
	 * @return string array of the course catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogs = new String[catalog.size()][4];
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			catalogs[i] = c.getShortDisplayArray();
		}
		return catalogs;
	}

	/**
	 * Saves all courses in the catalog to a file.
	 * 
	 * @param fileName name of file to save students to.
	 * @throws IllegalArgumentException "Unable to write to file " + fileName
	 *                                  if it is unable to write to such file
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

}
