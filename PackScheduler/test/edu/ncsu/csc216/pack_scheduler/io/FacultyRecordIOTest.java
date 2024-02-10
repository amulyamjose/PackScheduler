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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests the FacultyRecordIO class
 * @author Amulya Jose
 */
public class FacultyRecordIOTest {
	/** The first line in the faculty records */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
	/** The second line in the faculty records */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** The third line in the faculty records */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** The fourth line in the faculty records */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** The fifth line in the faculty records */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** The sixth line in the faculty records */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** The seventh line in the faculty records */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** The eighth line in the faculty records */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";

	/** All the lines in the faculty records test-files where the index is the line number */
	private String[] validFaculty = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
										validFaculty5, validFaculty6, validFaculty7 };
	
	/** The hashed password */
	private String hashPW;
	/** The algorithm to encode the hash */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * The set up for hashing passwords
	 */
	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
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
	 * Tests that StudentRecordIO() constructor exists.
	 */
	@Test
	public void testCheckConstruction() {
		assertDoesNotThrow(() -> new FacultyRecordIO(), "should not throw exception");
	}
	
	/**
	 * Tests FacultyRecordIO.readFacultyRecords() for a file full of valid faculty
	 */
	@Test
	public void testReadFacultyRecordsValidFaculty() {
		LinkedList<Faculty> faculty = assertDoesNotThrow(
				() -> FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt"),
				"should not throw exception");
		assertEquals(8, faculty.size());
		for (int i = 0; i < 8; i++) {
			assertEquals(validFaculty[i], faculty.get(i).toString());
		}
	}
	
	/**
	 * Tests FacultyRecordIO.readFacultyRecords() for a file full of valid faculty except for one copy
	 */
	@Test
	public void testReadFacultyRecordsValidFacultyWithCopy() {
		LinkedList<Faculty> faculty = assertDoesNotThrow(
				() -> FacultyRecordIO.readFacultyRecords("test-files/faculty_records_with_copy.txt"),
				"should not throw exception");
		assertEquals(8, faculty.size());
		for (int i = 0; i < 8; i++) {
			assertEquals(validFaculty[i], faculty.get(i).toString());
		}
	}
	
	/**
	 * Tests FacultyRecordIO.readFacultyRecords() for a file full of invalid faculty
	 */
	@Test
	public void testReadFacultyRecordsInvalidFaculty() {
		LinkedList<Faculty> faculty = assertDoesNotThrow(
				() -> FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt"),
				"should not throw exception");
		assertEquals(0, faculty.size());
	}
	
	/**
	 *  Tests FacultyRecordIO.readFacultyRecords() for a non-existent file
	 */
	@Test
	public void testReadFacultyRecordsNonExistant() {
		assertThrows(FileNotFoundException.class,
				() -> FacultyRecordIO.readFacultyRecords("test_files/szdioflu.txt"));
	}

	/**
	 * Tests method writeFacultyRecords() for multiple faculty
	 */
	@Test
	public void testWriteFacultyRecordsMultipleFaculty() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		String[] pf; // ps - primitive faculty
		for (int i = 0; i < 8; i++) {
			pf = validFaculty[i].split(",");
			faculty.add(new Faculty(pf[0], pf[1], pf[2], pf[3], pf[4], Integer.valueOf(pf[5])));
		}
		assertDoesNotThrow(
				() -> FacultyRecordIO.writeFacultyRecords("test-files/actual_full_faculty_records.txt", faculty));
		checkFiles("test-files/expected_full_faculty_records.txt", "test-files/actual_full_faculty_records.txt");
	}

	/**
	 * Tests method writeFacultyRecords() for no permissions
	 */
	@Test
	public void testWriteFacultyRecordsNoPermissions() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));

		Exception exception = assertThrows(IOException.class,
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", faculty));
		assertEquals("/home/sesmith5/actual_faculty_records.txt (No such file or directory)", exception.getMessage());
	}

}
