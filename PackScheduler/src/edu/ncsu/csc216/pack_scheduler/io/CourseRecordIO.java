package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 * @author William Walton
 */
public class CourseRecordIO {



    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
    public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {

    	Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file

    	SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects

        while (fileReader.hasNextLine()) { //While we have more lines in the file

        	try { //Attempt to do the following
                //Read the line, process it in readCourse, and get the object
                //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
                Course course = readCourse(fileReader.nextLine()); 

                //Create a flag to see if the newly created Course is a duplicate of something already in the list  
                boolean duplicate = false;
                //Look at all the courses in our list
                for (int i = 0; i < courses.size(); i++) {
                    //Get the course at index i
                    Course current = courses.get(i);
                    //Check if the name and section are the same
                    if (course.getName().equals(current.getName()) 
                    		&& course.getSection().equals(current.getSection())) {
                        //It's a duplicate!
                        duplicate = true;
                        break; //We can break out of the loop, no need to continue searching
                    }
                }
                //If the course is NOT a duplicate
                if (!duplicate) {
                    courses.add(course); //Add to the ArrayList!
                } //Otherwise ignore
            } catch (IllegalArgumentException e) {
                //The line is invalid b/c we couldn't create a course, skip it!
            }
        }
        //Close the Scanner b/c we're responsible with our file handles
        fileReader.close();
        //Return the ArrayList with all the courses we read!
        return courses;
    }


    /**
     * Converts a text representation of course to a Course object
     * @param fileLine a string of the line that represents a course
     * @return a course object that fileLine represents
     * @throws IllegalArgumentException if there is more data on the line than 
     * expected, less, or the formatting is wrong
     */
    private static Course readCourse(String fileLine) {
		Scanner coursePartScanner = new Scanner(fileLine);
		coursePartScanner.useDelimiter(",");

		try {

			String name = coursePartScanner.next();
			String title = coursePartScanner.next();
			String section = coursePartScanner.next();
			int credits = coursePartScanner.nextInt();
			String instructorId = coursePartScanner.next();
			int enrollmentCap = coursePartScanner.nextInt();
			String meetingDays = coursePartScanner.next();

			if ("A".equals(meetingDays)) {

				// checks if there is another token
				if (coursePartScanner.hasNext()) {
					coursePartScanner.close();
					throw new IllegalArgumentException("Line formatted unexpectedly");
				}
				coursePartScanner.close();
				Course newCourse = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
				
				// checks if there is a Faculty with the given instructorId and if so, the Course is added
				// to the Faculty's FacultySchedule, which updates the Course object
				RegistrationManager manager = RegistrationManager.getInstance();
				FacultyDirectory directory = manager.getFacultyDirectory();
				if (directory.getFacultyById(instructorId) != null) {
					Faculty faculty = directory.getFacultyById(instructorId);
					faculty.getSchedule().addCourseToSchedule(newCourse);
				}
				
				return newCourse;
			} else {

				int startTime = coursePartScanner.nextInt();
				int endTime = coursePartScanner.nextInt();

				// checks if there is another token
				if (coursePartScanner.hasNext()) {
					coursePartScanner.close();
					throw new IllegalArgumentException("Line formatted unexpectedly");
				}

				coursePartScanner.close();
				Course newCourse = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
				
				// checks if there is a Faculty with the given instructorId and if so, the Course is added
				// to the Faculty's FacultySchedule, which updates the Course object
				RegistrationManager manager = RegistrationManager.getInstance();
				FacultyDirectory directory = manager.getFacultyDirectory();
				if (directory.getFacultyById(instructorId) != null) {
					Faculty faculty = directory.getFacultyById(instructorId);
					faculty.getSchedule().addCourseToSchedule(newCourse);
				}
				
				return newCourse;			}

		} catch (NoSuchElementException e) { // catches if there are not enough tokens
			coursePartScanner.close();
			throw new IllegalArgumentException("Line formatted unexpectedly");
		}
	}

	/**
     * Writes the given list of Courses to a file.
     * @param fileName file to write schedule of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
    public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
    	PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < courses.size(); i++) {
    	    fileWriter.println(courses.get(i).toString());
    	}

    	fileWriter.close();

    }
}