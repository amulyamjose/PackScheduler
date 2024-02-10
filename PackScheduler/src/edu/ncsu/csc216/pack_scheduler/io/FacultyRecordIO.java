/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads in faculty records from a file and writes faculty records to a file.
 * @author Amulya Jose
 */
public class FacultyRecordIO {
	/**
	 * Reads in faculty information from file to be processed
	 * @param fileName file that is being read
	 * @return Custom LinkedList of faculty records
	 * @throws FileNotFoundException if file cannot be found or read
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileScanner = new Scanner(new File(fileName));
		LinkedList<Faculty> faculty = new LinkedList<>();
		while (fileScanner.hasNextLine()) {
			try {
				Faculty facultyPerson = processFaculty(fileScanner.nextLine());
				boolean copy = false;
	            for (int i = 0; i < faculty.size(); i++) {
	            	User curStudent = faculty.get(i);
	            	if (facultyPerson.getId().equals(curStudent.getId())) {
	            		copy = true;
	            		break;
	            	}
	            }
	            if (!copy) {
	            	faculty.add(facultyPerson);
	            }
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a faculty, skip it!
			}
		}
		fileScanner.close();
		return faculty;
	}
	
	/**
	 * Processes a single line from the file that was read and assigns the information to fields.
	 * @param line one line of the file being processed
	 * @return Faculty object constructed by the information found on file
	 * @throws IllegalArgumentException if line cannot be read or found
	 */
	private static Faculty processFaculty(String line) {
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");

		try {
			String firstName = lineReader.next();
			String lastName = lineReader.next();
			String id = lineReader.next();
			String email = lineReader.next();
			String hashedPassword = lineReader.next();
			int maxCourses = lineReader.nextInt();
			Faculty facultyPerson = new Faculty(firstName, lastName, id, email, hashedPassword, maxCourses);
			lineReader.close();
			return facultyPerson;
		} catch (Exception e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Writes faculty records to file
	 * @param fileName file being written to
	 * @param facultyDirectory custom LinkedList of student records
	 * @throws IOException if file cannot be written to
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}

		fileWriter.close();
	}
}
