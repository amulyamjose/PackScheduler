package edu.ncsu.csc216.pack_scheduler.directory;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 * @author William Walton
 * @author Amulya Jose
 */
public class StudentDirectoryTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	
	/**
	 * Resets student_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		//Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();
		
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		
		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();
				
		//Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
	}
	
	/**
	 * Tests StudentDirectory.loadStudentsFromFile() for a file That doesn't exist
	 */
	@Test
	public void testLoadStudentsNonExistantFrom() {
		StudentDirectory sd = new StudentDirectory();
		
		// test invalid file
		Exception exception = assertThrows(IllegalArgumentException.class, 
				() -> sd.loadStudentsFromFile("test-files/asdf.txt"));
		assertEquals("Unable to read file test-files/asdf.txt", exception.getMessage());
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();
		
		//Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
	}
	
	/**
	 * tests StudentDirectory.addStudent() for a null or empty password
	 * @param password the first password entered
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddStudentInvalidPassword(String password) {
		StudentDirectory sd = new StudentDirectory();
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, password, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid password", exception.getMessage());
	}
	
	/**
	 * tests StudentDirectory.addStudent() for a null or empty repeated password
	 * @param password the second password entered
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddStudentRepeatedPassword(String password) {
		StudentDirectory sd = new StudentDirectory();
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, password, MAX_CREDITS));
		assertEquals("Invalid password", exception.getMessage());
	}
	
	/**
	 * tests StudentDirectory.addStudent() for a copy of an already existing person
	 */
	@Test
	public void testAddStudentCopy() {
		StudentDirectory sd = new StudentDirectory();
		sd.loadStudentsFromFile(validTestFile);
		assertFalse(sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15));
		assertEquals(10, sd.getStudentDirectory().length);
	}
	
	/**
	 * tests StudentDirectory.addStudent() for not a "copy" of an existing person
	 * 		The only thing that should make a person unique is their ID
	 */
	@Test
	public void testAddStudentNotCopy() {
		StudentDirectory sd = new StudentDirectory();
		sd.loadStudentsFromFile(validTestFile);
		assertTrue(sd.addStudent("Zahir", "King", ID, "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15));
		assertEquals(11, sd.getStudentDirectory().length);
	}
	
	/**
	 * tests StudentDirectory.addStudent() for invalid maxCredits
	 * 		because there is no way to retrieve maxCredits, we can't make sure it records the right value
	 * @param maxCredits an invalid maxCredits
	 */
	@ParameterizedTest
	@ValueSource(ints = {-18, -3, 0, 2, 19, 32})
	public void testAddStudentInvalidMaxCredits(int maxCredits) {
		StudentDirectory sd = new StudentDirectory();
		assertTrue(assertDoesNotThrow(
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, maxCredits),
				"Should not throw exception"));
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertAll("Student",
				() -> assertEquals(studentDirectory[0][0], FIRST_NAME, "incorrect first name"),
				() -> assertEquals(studentDirectory[0][1], LAST_NAME, "incorrect last name"),
				() -> assertEquals(studentDirectory[0][2], ID, "Incorrect id")
		);
		
	}
	
	/**
	 * tests studentDirectory.addStudent() for a null or empty repeated password
	 * @param password the first password entered by the user
	 * @param repeatPassword the second password entered by the user
	 */
	@ParameterizedTest(name = "{index} => password={0}, repeatPassword={1}")
	@CsvSource({
		"1,one",
		"hello,Hello"
	})
	public void testAddStudentDifferentPasswords(String password, String repeatPassword ) {
		StudentDirectory sd = new StudentDirectory();
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, password, repeatPassword, MAX_CREDITS));
		assertEquals("Passwords do not match", exception.getMessage());
	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();
				
		//Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Shannon", studentDirectory[3][0]);
		assertEquals("Hansen", studentDirectory[3][1]);
		assertEquals("shansen", studentDirectory[3][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();
		
		//Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * tests StudentDirectory.saveStudentDirectory() for an IO exception (no permission for file)
	 */
	@Test
	public void testSaveStudentNoPermission() {
		StudentDirectory sd = new StudentDirectory();
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		
		Exception exception = assertThrows(IllegalArgumentException.class, 
				() -> sd.saveStudentDirectory("/home/sesmith5/actual_student_records.txt"));
		assertEquals("Unable to write to file /home/sesmith5/actual_student_records.txt", exception.getMessage());
	}
	
	/**
	 * Tests the getStudentById() method
	 */
	@Test
	public void testGetStudentById() {
		StudentDirectory sd = new StudentDirectory();
		
		// Add students to student directory
		sd.loadStudentsFromFile(validTestFile);
		
		// Tests if the correct student is returned by id
		assertDoesNotThrow(
				() -> sd.getStudentById("efrost"),
				"Should not throw an exception");
		Student student = new Student("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3);
		assertEquals(student, sd.getStudentById("efrost"), "incorrect student");
		
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
