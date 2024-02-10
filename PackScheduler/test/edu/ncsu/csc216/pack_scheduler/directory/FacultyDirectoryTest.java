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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests FacultyDirectory
 * @author Amulya Jose
 */
public class FacultyDirectoryTest {
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
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
	private static final int MAX_COURSES = 2;

	/**
	 * Resets faculty_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset faculty_records.txt so that it's fine for other needed tests
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
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		//Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are faculty in the directory, they 
		//are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.loadFacultyFromFile().
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory fd = new FacultyDirectory();
				
		//Test valid file
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.loadFacultyFromFile() for a file that doesn't exist
	 */
	@Test
	public void testLoadFacultyNonExistantFrom() {
		FacultyDirectory fd = new FacultyDirectory();
		
		// test invalid file
		Exception exception = assertThrows(IllegalArgumentException.class, 
				() -> fd.loadFacultyFromFile("test-files/asdf.txt"));
		assertEquals("Unable to read file test-files/asdf.txt", exception.getMessage());
	}

	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Test valid Student
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String [][] studentDirectory = fd.getFacultyDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty() for a null or empty password
	 * @param password the first password entered
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddFacultyInvalidPassword(String password) {
		FacultyDirectory fd = new FacultyDirectory();
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, password, PASSWORD, MAX_COURSES));
		assertEquals("Invalid password", exception.getMessage());
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty() for a null or empty repeated password
	 * @param password the second password entered
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddFacultyRepeatedPassword(String password) {
		FacultyDirectory fd = new FacultyDirectory();
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, password, MAX_COURSES));
		assertEquals("Invalid password", exception.getMessage());
	}
	
	/**
	 * tests FacultyDirectory.addFaculty() for a copy of an already existing person
	 */
	@Test
	public void testAddFacultyCopy() {
		FacultyDirectory fd = new FacultyDirectory();
		fd.loadFacultyFromFile(validTestFile);
		assertFalse(fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2));
		assertEquals(8, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty() for not a "copy" of an existing person
	 * The only thing that should make a person unique is their ID
	 */
	@Test
	public void testAddFacultyNotCopy() {
		FacultyDirectory fd = new FacultyDirectory();
		fd.loadFacultyFromFile(validTestFile);
		assertTrue(fd.addFaculty("Ashely", "Witt", ID, "mollis@Fuscealiquetmagna.net", "pw", "pw", 2));
		assertEquals(9, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty() for invalid maxCourses
	 * @param maxCourses an invalid maxCourses
	 */
	@ParameterizedTest
	@ValueSource(ints = {-3, 0, 4, 19})
	public void testAddFacultyInvalidMaxCourses(int maxCourses) {
		FacultyDirectory fd = new FacultyDirectory();
		assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, maxCourses));
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty() for a null or empty repeated password
	 * @param password the first password entered by the user
	 * @param repeatPassword the second password entered by the user
	 */
	@ParameterizedTest(name = "{index} => password={0}, repeatPassword={1}")
	@CsvSource({ "1,one", "hello,Hello" })
	public void testAddFacultyDifferentPasswords(String password, String repeatPassword ) {
		FacultyDirectory fd = new FacultyDirectory();
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, password, repeatPassword, MAX_COURSES));
		assertEquals("Passwords do not match", exception.getMessage());
	}

	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
				
		//Add faculty and remove
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		assertTrue(fd.removeFaculty("awitt"));
		String [][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Halla", facultyDirectory[2][0]);
		assertEquals("Aguirre", facultyDirectory[2][1]);
		assertEquals("haguirr", facultyDirectory[2][2]);
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Add faculty
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		fd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		fd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		assertEquals(3, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}
	
	/**
	 * Tests FacultyDirectory.saveFacultyDirectory() for an IO Exception (no permission for file)
	 */
	@Test
	public void testSaveFacultyNoPermission() {
		FacultyDirectory fd = new FacultyDirectory();
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		assertEquals(1, fd.getFacultyDirectory().length);
		
		Exception exception = assertThrows(IllegalArgumentException.class, 
				() -> fd.saveFacultyDirectory("/home/sesmith5/actual_faculty_records.txt"));
		assertEquals("Unable to write to file /home/sesmith5/actual_faculty_records.txt", exception.getMessage());
	}
	
	/**
	 * Tests the getFacultyById() method
	 */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory fd = new FacultyDirectory();
		
		// Add faculty to faculty directory
		fd.loadFacultyFromFile(validTestFile);
		
		// Tests if the correct faculty is returned by id
		assertDoesNotThrow(
				() -> fd.getFacultyById("awitt"),
				"Should not throw an exception");
		Faculty facultyPerson = new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 2);
		assertEquals(facultyPerson, fd.getFacultyById("awitt"), "incorrect faculty");
		
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
