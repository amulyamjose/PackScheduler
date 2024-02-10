package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;


/**
 * Tests the faculty object.
 * 
 * @author Sarah Heckman
 * @author William Walton
 * @author Kavya Vadla
 * @author Carter Gentile
 */
public class FacultyTest {

	/** Test Faculty's first name. */
	private static final String FIRST_NAME = "first";
	/** Test Faculty's last name */
	private static final String LAST_NAME = "last";
	/** Test Faculty's id */
	private static final String ID = "flast";
	/** Test Faculty's email */
	private static final String EMAIL = "first_last@ncsu.edu";
	/** Test Faculty's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Test Faculty Max Credits
	 */
	private static final int MAX_COURSES = 3;

	// This is a block of code that is executed when the FacultyTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the FacultyTest
	// object is
	// constructed. By automating the hash of the plaintext password, we are
	// not tied to a specific hash implementation. We can change the algorithm
	// easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}

	/**
	 * tests valid Faculty without given max courses
	 */
	@Test
	public void testValidFacultyWithOutMaxcourses() {
		// tests a valid construction
		Faculty s = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES),
				"should not throw exception");

		assertAll("Faculty",
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s.getId(), "incorrect id"),
				() -> assertEquals(EMAIL, s.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s.getPassword(), "incorrect password"),
				() -> assertEquals(3, s.getMaxCourses(), "incorrect max courses"));
	}

	/**
	 * tests valid Faculty with given max courses
	 * tests the boundary of 1 and 3
	 */
	@Test
	public void testValidFacultyWithMaxcourses() {
		// tests a valid construction
		Faculty s1 = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 3),
				"should not throw exception");

		assertAll("Faculty with 3 courses",
				() -> assertEquals(FIRST_NAME, s1.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s1.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s1.getId(), "incorrect id"),
				() -> assertEquals(EMAIL, s1.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s1.getPassword(), "incorrect password"),
				() -> assertEquals(3, s1.getMaxCourses(), "incorrect max courses"));

		Faculty s2 = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 1),
				"should not throw exception");

		assertAll("Faculty with 1 courses",
				() -> assertEquals(FIRST_NAME, s2.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s2.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s2.getId(), "incorrect id"),
				() -> assertEquals(EMAIL, s2.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s2.getPassword(), "incorrect password"),
				() -> assertEquals(1, s2.getMaxCourses(), "incorrect max courses"));
	}

	/**
	 * tests Faculty constructor for invalid first name
	 * 
	 * @param firstName an invalid first name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testFacultyInvalidFirstName(String firstName) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES));
		assertEquals("Invalid first name", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, LAST_NAME, ID, EMAIL, hashPW, 3));
		assertEquals("Invalid first name", e2.getMessage());
	}

	/**
	 * tests Faculty constructor for invalid last name
	 * 
	 * @param lastName an invalid last name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testFacultyInvalidLastName(String lastName) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, lastName, ID, EMAIL, hashPW, MAX_COURSES));
		assertEquals("Invalid last name", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, lastName, ID, EMAIL, hashPW, 3));
		assertEquals("Invalid last name", e2.getMessage());
	}

	/**
	 * tests Faculty constructor for invalid id
	 * 
	 * @param id an invalid id
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testFacultyInvalidID(String id) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, id, EMAIL, hashPW, MAX_COURSES));
		assertEquals("Invalid id", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, id, EMAIL, hashPW, 3));
		assertEquals("Invalid id", e2.getMessage());
	}

	/**
	 * tests Faculty constructor for invalid email
	 * 
	 * @param email an invalid email
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "hello", "first.last@ncsu", "firstlast@ncsu", ".@", "@", "." })
	public void testFacultyInvalidEmail(String email) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, email, hashPW, MAX_COURSES));
		assertEquals("Invalid email", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, email, hashPW, 3));
		assertEquals("Invalid email", e2.getMessage());
	}

	/**
	 * tests Faculty constructor for valid email
	 * 
	 * @param email a valid email
	 */
	@ParameterizedTest
	@ValueSource(strings = { "hello@google.com", "first.last@ncsu.", "firstlast@nc.su", "@.", "@.@", ".@.", "@.@.@" })
	public void testFacultyValidEmail(String email) {
		Faculty s = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, email, hashPW, MAX_COURSES));
		assertAll("Faculty",
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s.getId(), "incorrect id"),
				() -> assertEquals(email, s.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s.getPassword(), "incorrect password"),
				() -> assertEquals(3, s.getMaxCourses(), "incorrect max courses"));
	}

	/**
	 * tests Faculty constructor for invalid password
	 * 
	 * @param hashPW an invalid password
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testFacultyInvalidPassword(String hashPW) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES));
		assertEquals("Invalid password", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 3));
		assertEquals("Invalid password", e2.getMessage());
	}

	/**
	 * tests Faculty constructor for invalid max courses
	 * 
	 * @param maxCourses the max courses of the Faculty
	 */
	@ParameterizedTest
	@ValueSource(ints = { 22, 19, 0, -3, -3 })
	public void testFacultyInvalidMaxcourses(int maxCourses) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, maxCourses));
		assertEquals("Invalid max courses", e1.getMessage());
	}


	/**
	 * tests the setMaxcourses() method
	 * 
	 * @param maxCourses an invalid max courses to test
	 */
	@ParameterizedTest
	@ValueSource(ints = { 22, 19, 0, -3, -3 })
	public void testSetMaxcoursesInvalid(int maxCourses) {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setMaxCourses(maxCourses));
		assertEquals("Invalid max courses", e1.getMessage());
	}

	/**
	 * tests that .hashCode() is working correctly
	 */
	@Test
	public void testHashCode() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 3);
		// s2 checks that the string comparison uses .equals instead of ==
		User s2 = new Faculty(String.valueOf(FIRST_NAME.toCharArray()),
				String.valueOf(LAST_NAME.toCharArray()),
				String.valueOf(ID.toCharArray()),
				String.valueOf(EMAIL.toCharArray()),
				String.valueOf(hashPW.toCharArray()),
				MAX_COURSES);
		User s3 = new Faculty("Samantha", LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, "Johnson", ID, EMAIL, hashPW, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, "filast", EMAIL, hashPW, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "filast@ncsu.edu", hashPW, MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW.substring(1), MAX_COURSES);
		User s8 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 2);

		assertEquals(s1.hashCode(), s1.hashCode());
		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());
		assertNotEquals(s1.hashCode(), FIRST_NAME.hashCode());
	}

	/**
	 * test equals() method.
	 */
	@Test
	public void testEquals() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES);
		// s2 checks that the string comparison uses .equals instead of ==
		User s2 = new Faculty(String.valueOf(FIRST_NAME.toCharArray()),
				String.valueOf(LAST_NAME.toCharArray()),
				String.valueOf(ID.toCharArray()),
				String.valueOf(EMAIL.toCharArray()),
				String.valueOf(hashPW.toCharArray()),
				3);
		User s3 = new Faculty("Samantha", LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, "Johnson", ID, EMAIL, hashPW, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, "filast", EMAIL, hashPW, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "filast@ncsu.edu", hashPW, MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW.substring(1), MAX_COURSES);
		User s8 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 2);

		assertEquals(s1, s1);
		assertEquals(s1, s2);
		assertNotEquals(s1, s3);
		assertNotEquals(s1, s4);
		assertNotEquals(s1, s5);
		assertNotEquals(s1, s6);
		assertNotEquals(s1, s7);
		assertNotEquals(s1, s8);
		assertNotEquals(s1, null);
		assertNotEquals(s1, FIRST_NAME);

	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, MAX_COURSES);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",3", s1.toString());
	}




	// @CsvFileSource(resources = "test-files/MANYFacultyS.csv", numLinesToSkip = 0)
	// public void testManyValidFacultys(String first, String last, String id,
	// String email, String pass, String courses) {
	// assertDoesNotThrow(() -> new Faculty(first, last, id, email, pass,
	// Integer.parseInt(courses)));
	// }
}