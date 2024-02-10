/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * tests the StudentRecordIO class
 * 
 * @author William Walton
 * @author Kavya Vadla
 */
class StudentRecordIOTest {

	/** the first line in the the student records */
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** the second line in the the student records */
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** the third line in the the student records */
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** the fourth line in the the student records */
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** the fifth line in the the student records */
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** the sixth line in the the student records */
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** the seventh line in the the student records */
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** the eighth line in the the student records */
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/** the ninth line in the the student records */
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** the tenth line in the the student records */
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/**
	 * All the lines in the student records test-files where the index is the line
	 * number
	 */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5,
			validStudent6, validStudent7, validStudent8, validStudent9 };

	/** the hashed password */
	private String hashPW;
	/** the algorithm to encode the hash */
	private static final String HASH_ALGORITHM = "SHA-256";

	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * tests that StudentRecordIO() constructor exists?
	 * Exists solely for coverage
	 */
	@Test
	public void testCheckConstruction() {
		assertDoesNotThrow(() -> new StudentRecordIO(), "should not throw exception");
	}

	/**
	 * Tests StudendRecordIO.readStudentRecords() for a file full of valid students
	 */
	@Test
	public void testReadStudentRecordsValidStudents() {

		SortedList<Student> students = assertDoesNotThrow(
				() -> StudentRecordIO.readStudentRecords("test-files/student_records.txt"),
				"should not throw exception");
		assertEquals(10, students.size());
		for (int i = 0; i < 10; i++) {
			assertEquals(validStudents[i], students.get(i).toString());
		}
	}

	/**
	 * Tests StudendRecordIO.readStudentRecords() for a file full of valid students
	 * except for one copy
	 */
	@Test
	public void testReadStudentRecordsValidStudentsWithCopy() {

		SortedList<Student> students = assertDoesNotThrow(
				() -> StudentRecordIO.readStudentRecords("test-files/student_records_with_copy.txt"),
				"should not throw exception");
		assertEquals(10, students.size());
		for (int i = 0; i < 10; i++) {
			assertEquals(validStudents[i], students.get(i).toString());
		}
	}

	/**
	 * Tests StudendRecordIO.readStudentRecords() for a file full of invalid
	 * students
	 */
	@Test
	public void testReadStudentRecordsInvalidStudents() {

		SortedList<Student> students = assertDoesNotThrow(
				() -> StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt"),
				"should not throw exception");
		assertEquals(0, students.size());
	}

	/**
	 * Tests StudendRecordIO.readStudentRecords() for a non-existent file
	 */
	@Test
	public void testReadStudentRecordsNonExistant() {
		assertThrows(FileNotFoundException.class,
				() -> StudentRecordIO.readStudentRecords("test_files/szdioflu.txt"));

	}

	/**
	 * Tests method writeStudentRecords() for a single line
	 */
	@Test
	public void testWriteStudentRecordsOneLine() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		assertDoesNotThrow(
				() -> StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students));
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}

	/**
	 * Tests method writeStudentRecords() for multiple students
	 */
	@Test
	public void testWriteStudentRecordsMultipleStudents() {
		SortedList<Student> students = new SortedList<Student>();
		String[] ps; // ps - primitive student
		for (int i = 0; i < 10; i++) {
			ps = validStudents[i].split(",");
			students.add(new Student(ps[0], ps[1], ps[2], ps[3], ps[4], Integer.valueOf(ps[5])));
		}
		assertDoesNotThrow(
				() -> StudentRecordIO.writeStudentRecords("test-files/actual_full_student_records.txt", students));
		checkFiles("test-files/expected_full_student_records.txt", "test-files/actual_full_student_records.txt");
	}

	/**
	 * Tests method writeStudentRecords() for no permissions
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

		Exception exception = assertThrows(IOException.class,
				() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
	}
}
