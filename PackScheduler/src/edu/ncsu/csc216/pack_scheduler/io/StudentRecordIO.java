package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc217.collections.list.SortedList;
/**
 * Reads in student records from a file and writes student records to a file,
 * @author Kavya Vadla
 */

public class StudentRecordIO {

	/**
	 * Reads in student information from file to be processed
	 * @param fileName file that is being read
	 * @return Array list of student records
	 * @throws FileNotFoundException if file cannot be found or read
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));  
	    SortedList<Student> students = new SortedList<Student>(); 
	    while (fileReader.hasNextLine()) {
	        try {
	            Student student = processStudent(fileReader.nextLine()); 
	            // students.add(student); - removed to make the copy detection actually work.
	            boolean copy = false;
	            for (int i = 0; i < students.size(); i++) {
	            	User curStudent = students.get(i);
	            	if (student.getId().equals(curStudent.getId())) {
	            		copy = true;
	            		break;
	            	}
	            }
	            if (!copy) {
	            	students.add(student);
	            }
	        } catch (IllegalArgumentException e) {
	        	// pass (we ignore invalid lines of data)
	        }
	    }
	    fileReader.close();
	    return students;
	}
	
	/**
	 * Processes a single line from the file that was read and assigns the information
	 * to fields.
	 * @param line one line of the file being processed
	 * @return Student object constructed by the information found on file
	 * @throws IllegalArgumentException if line cannot be read or found
	 */
	private static Student processStudent(String line) {
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");
		try {
			String firstName = lineReader.next();
			String lastName = lineReader.next();
			String id = lineReader.next();
			String email = lineReader.next();
			String hashPW = lineReader.next();
			int maxCredits = lineReader.nextInt();
			lineReader.close();
			return new Student(firstName, lastName, id, email, hashPW, maxCredits);
					
	   } catch (Exception ex) {
		   throw new IllegalArgumentException();
	   }
	}

	/**
	 * Writes student records to file
	 * @param fileName file being written to
	 * @param studentDirectory Array list of student records
	 * @throws IOException if file cannot be written to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < studentDirectory.size(); i++) {
    	    fileWriter.println(studentDirectory.get(i).toString());
    	}

    	fileWriter.close();
	}

}
