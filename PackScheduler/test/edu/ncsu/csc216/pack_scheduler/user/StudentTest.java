package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Student object.
 * 
 * @author Sarah Heckman
 * @author William Walton
 * @author Kavya Vadla
 */
public class StudentTest {

	/** Test Student's first name. */
	private static final String FIRST_NAME = "first";
	/** Test Student's last name */
	private static final String LAST_NAME = "last";
	/** Test Student's id */
	private static final String ID = "flast";
	/** Test Student's email */
	private static final String EMAIL = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	// This is a block of code that is executed when the StudentTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the StudentTest
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
	 * tests valid student without given max credits
	 */
	@Test
	public void testValidStudentWithOutMaxCredits() {
		// tests a valid construction
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW),
				"should not throw exception");

		assertAll("student",
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s.getId(), "incorrect id"),
				() -> assertEquals(EMAIL, s.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s.getPassword(), "incorrect password"),
				() -> assertEquals(18, s.getMaxCredits(), "incorrect max credits"));
	}

	/**
	 * tests valid student with given max credits
	 * tests the boundary of 3 and 18
	 */
	@Test
	public void testValidStudentWithMaxCredits() {
		// tests a valid construction
		Student s1 = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 18),
				"should not throw exception");

		assertAll("student with 18 credits",
				() -> assertEquals(FIRST_NAME, s1.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s1.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s1.getId(), "incorrect id"),
				() -> assertEquals(EMAIL, s1.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s1.getPassword(), "incorrect password"),
				() -> assertEquals(18, s1.getMaxCredits(), "incorrect max credits"));

		Student s2 = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 3),
				"should not throw exception");

		assertAll("student with 3 credits",
				() -> assertEquals(FIRST_NAME, s2.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s2.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s2.getId(), "incorrect id"),
				() -> assertEquals(EMAIL, s2.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s2.getPassword(), "incorrect password"),
				() -> assertEquals(3, s2.getMaxCredits(), "incorrect max credits"));
	}

	/**
	 * tests Student constructor for invalid first name
	 * 
	 * @param firstName an invalid first name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testStudentInvalidFirstName(String firstName) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, LAST_NAME, ID, EMAIL, hashPW));
		assertEquals("Invalid first name", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, LAST_NAME, ID, EMAIL, hashPW, 18));
		assertEquals("Invalid first name", e2.getMessage());
	}

	/**
	 * tests Student constructor for invalid last name
	 * 
	 * @param lastName an invalid last name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testStudentInvalidLastName(String lastName) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, lastName, ID, EMAIL, hashPW));
		assertEquals("Invalid last name", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, lastName, ID, EMAIL, hashPW, 18));
		assertEquals("Invalid last name", e2.getMessage());
	}

	/**
	 * tests Student constructor for invalid id
	 * 
	 * @param id an invalid id
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testStudentInvalidID(String id) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, id, EMAIL, hashPW));
		assertEquals("Invalid id", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, id, EMAIL, hashPW, 18));
		assertEquals("Invalid id", e2.getMessage());
	}

	/**
	 * tests Student constructor for invalid email
	 * 
	 * @param email an invalid email
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "hello", "first.last@ncsu", "firstlast@ncsu", ".@", "@", "." })
	public void testStudentInvalidEmail(String email) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, email, hashPW));
		assertEquals("Invalid email", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, email, hashPW, 18));
		assertEquals("Invalid email", e2.getMessage());
	}

	/**
	 * tests Student constructor for valid email
	 * 
	 * @param email a valid email
	 */
	@ParameterizedTest
	@ValueSource(strings = { "hello@google.com", "first.last@ncsu.", "firstlast@nc.su", "@.", "@.@", ".@.", "@.@.@" })
	public void testStudentValidEmail(String email) {
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, ID, email, hashPW));
		assertAll("student",
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(ID, s.getId(), "incorrect id"),
				() -> assertEquals(email, s.getEmail(), "incorrect email"),
				() -> assertEquals(hashPW, s.getPassword(), "incorrect password"),
				() -> assertEquals(18, s.getMaxCredits(), "incorrect max credits"));
	}

	/**
	 * tests Student constructor for invalid password
	 * 
	 * @param hashPW an invalid password
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testStudentInvalidPassword(String hashPW) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW));
		assertEquals("Invalid password", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 18));
		assertEquals("Invalid password", e2.getMessage());
	}

	/**
	 * tests Student constructor for invalid max credits
	 * 
	 * @param maxCredits the max credits of the student
	 */
	@ParameterizedTest
	@ValueSource(ints = { 2, 19, 0, -3, -18 })
	public void testStudentInvalidMaxCredits(int maxCredits) {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, maxCredits));
		assertEquals("Invalid max credits", e1.getMessage());
	}

	/**
	 * tests the setFirstName() method
	 * 
	 * @param firstName an invalid FirstName to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetFirstNameInvalid(String firstName) {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(firstName));
		assertEquals("Invalid first name", e1.getMessage());
	}

	/**
	 * tests the setLastName() method
	 * 
	 * @param lastName an invalid last name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetLastNameInvalid(String lastName) {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(lastName));
		assertEquals("Invalid last name", e1.getMessage());
	}

	/**
	 * tests the setEmail() method
	 * 
	 * @param email an invalid email to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "hello", "first.last@ncsu", "firstlast@ncsu", ".@", "@", "." })
	public void testSetEmailInvalid(String email) {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail(email));
		assertEquals("Invalid email", e1.getMessage());
	}

	/**
	 * tests the setPassword() method
	 * 
	 * @param password an invalid password to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetPasswordInvalid(String password) {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(password));
		assertEquals("Invalid password", e1.getMessage());
	}

	/**
	 * tests the setMaxCredits() method
	 * 
	 * @param maxCredits an invalid max credits to test
	 */
	@ParameterizedTest
	@ValueSource(ints = { 2, 19, 0, -3, -18 })
	public void testSetMaxCreditsInvalid(int maxCredits) {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setMaxCredits(maxCredits));
		assertEquals("Invalid max credits", e1.getMessage());
	}

	/**
	 * tests that .hashCode() is working correctly
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		// s2 checks that the string comparison uses .equals instead of ==
		User s2 = new Student(String.valueOf(FIRST_NAME.toCharArray()),
				String.valueOf(LAST_NAME.toCharArray()),
				String.valueOf(ID.toCharArray()),
				String.valueOf(EMAIL.toCharArray()),
				String.valueOf(hashPW.toCharArray()),
				18);
		User s3 = new Student("Samantha", LAST_NAME, ID, EMAIL, hashPW);
		User s4 = new Student(FIRST_NAME, "Johnson", ID, EMAIL, hashPW);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "filast", EMAIL, hashPW);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, "filast@ncsu.edu", hashPW);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW.substring(1));
		User s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 17);

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
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		// s2 checks that the string comparison uses .equals instead of ==
		User s2 = new Student(String.valueOf(FIRST_NAME.toCharArray()),
				String.valueOf(LAST_NAME.toCharArray()),
				String.valueOf(ID.toCharArray()),
				String.valueOf(EMAIL.toCharArray()),
				String.valueOf(hashPW.toCharArray()),
				18);
		User s3 = new Student("Samantha", LAST_NAME, ID, EMAIL, hashPW);
		User s4 = new Student(FIRST_NAME, "Johnson", ID, EMAIL, hashPW);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "filast", EMAIL, hashPW);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, "filast@ncsu.edu", hashPW);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW.substring(1));
		User s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 17);

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
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}

	/**
	 * Test compareTo() method.
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		Student s2 = new Student("Samantha", "Johnson", "sjohn", "sjohn@ncsu.edu", hashPW);
		Student s3 = new Student("Aiden", LAST_NAME, "alast", "alast@ncsu.edu", hashPW);
		Student s4 = new Student(FIRST_NAME, LAST_NAME, "flast1", "flast1@ncsu.edu", hashPW);

		assertEquals(1, Integer.signum(s1.compareTo(s2)));
		assertEquals(-1, Integer.signum(s2.compareTo(s1)));
		assertEquals(0, s1.compareTo(s1));
		assertEquals(-1, Integer.signum(s3.compareTo(s1)));
		assertEquals(1, Integer.signum(s1.compareTo(s3)));
		assertEquals(1, Integer.signum(s4.compareTo(s1)));
		assertEquals(-1, Integer.signum(s1.compareTo(s4)));

	}

	/**
	 * tests canAdd
	 */
	@Test
	public void testCanAdd() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW, 8);
		Course c1 = new Course("CSC116", "TEST", "001", 4, "testID", 100, "MWF", 900, 1000);
		Course c2 = new Course("CSC216", "TEST 2", "001", 3, "testID", 100, "MWF", 900, 1000);
		Course c3 = new Course("CSC316", "TEST 3", "001", 3, "testID", 100, "MWF", 1100, 1200);
		Course c4 = new Course("CSC416", "TEST 4", "001", 2, "testID", 100, "MWF", 1300, 1400);

		assertFalse(s1.canAdd(null));
		assertTrue(s1.canAdd(c1));
		s1.getSchedule().addCourseToSchedule(c1);
		assertFalse(s1.canAdd(c1));
		assertFalse(s1.canAdd(c2));

		assertTrue(s1.canAdd(c3));
		s1.getSchedule().addCourseToSchedule(c3);

		assertFalse(s1.canAdd(c4));
	}

	// @CsvFileSource(resources = "test-files/MANYSTUDENTS.csv", numLinesToSkip = 0)
	// public void testManyValidStudents(String first, String last, String id,
	// String email, String pass, String credits) {
	// assertDoesNotThrow(() -> new Student(first, last, id, email, pass,
	// Integer.parseInt(credits)));
	// }
}
