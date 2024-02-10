
/*
 * Tests every single possible valid outcome for the method
 * 
 * Commented to not be a test as this takes too long to run all of them
 * EDIT: DO NOT RUN THIS TEST. LIKE EVER AT ALL. DO NOT UPLOAD IT TO JENKINS
 * EITHER.
 * I CALCULATED IT AND ON MY LOCAL MACHINE IT WOULD TAKE LIKE 40 YEARS TO RUN.
 * 
 * I just think it would be funny to leave it here though, so that's what ill do
 * 
 * TAs let me know what you think of this testing method - is there any way to
 * optimize it, other than pre-generating all the strings to a large file and
 * running the code off of each line in that file (ofc using a stream &
 * preloading just a few into memory rather than loading the entire file into
 * memory)
 */
// @Test
// public void testValid() {
// // test 1 letter
// for (char c : alphablet) {
// for (char d1 : digits) {
// for (char d2 : digits) {
// for (char d3 : digits) {
// for (char s : alphablet) {
// assertDoesNotThrow(() -> CourseNameValidator.isValid("" + c + d1 + d2 + d3 +
// s));
// }
// assertDoesNotThrow(() -> CourseNameValidator.isValid("" + c + d1 + d2 + d3));
// }
// }
// }
// }
// // test 2 letter
// for (char c1 : alphablet) {
// for (char c2 : alphablet) {
// for (char d1 : digits) {
// for (char d2 : digits) {
// for (char d3 : digits) {
// for (char s : alphablet) {
// assertDoesNotThrow(() -> CourseNameValidator.isValid("" + c1 + c2 + d1 + d2 +
// d3 + s));
// }
// assertDoesNotThrow(() -> CourseNameValidator.isValid("" + c1 + c2 + d1 + d2 +
// d3));
// }
// }
// }
// }
// }
// // test 3 letter
// for (char c1 : alphablet) {
// for (char c2 : alphablet) {
// for (char c3 : alphablet) {
// for (char d1 : digits) {
// for (char d2 : digits) {
// for (char d3 : digits) {
// for (char s : alphablet) {
// assertDoesNotThrow(
// () -> CourseNameValidator.isValid("" + c1 + c2 + c3 + d1 + d2 + d3 + s));
// }
// assertDoesNotThrow(() -> CourseNameValidator.isValid("" + c1 + c2 + c3 + d1 +
// d2 + d3));
// }
// }
// }
// }
// }
// }
// // test 4 letter
// for (char c1 : alphablet) {
// for (char c2 : alphablet) {
// for (char c3 : alphablet) {
// for (char c4 : alphablet) {
// for (char d1 : digits) {
// for (char d2 : digits) {
// for (char d3 : digits) {
// for (char s : alphablet) {
// assertDoesNotThrow(
// () -> CourseNameValidator
// .isValid("" + c1 + c2 + c3 + c4 + d1 + d2 + d3 + s));
// }
// assertDoesNotThrow(
// () -> CourseNameValidator.isValid("" + c1 + c2 + c3 + c4 + d1 + d2 + d3));
// }
// }
// }
// }
// }
// }
// }

// }
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class CourseNameValidatorTest {

	/** the validator to be checked */
	private CourseNameValidator fsm = new CourseNameValidator();

	@Test
	void testNonAlphanumericCharacters() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid(",.)"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage(),
				"Incorrect exception thrown with course name having non alphanumeric characters");

	}

	@Test
	void testTransitionInitialState() throws InvalidTransitionException {

		// if the first character is a letter
		assertDoesNotThrow(
				() -> fsm.isValid("C216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("C216"));

		// if the first character is a digit
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("1CSC"));
		assertEquals("Course name must start with a letter.", e1.getMessage(),
				"Incorrect exception thrown with course name not starting with a letter");
	}

	@Test
	void testTransitionLState() throws InvalidTransitionException {

		// if the second character is a letter
		assertDoesNotThrow(
				() -> fsm.isValid("CS216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CS216"));

		// if the second character is a digit
		assertDoesNotThrow(
				() -> fsm.isValid("C216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("C216"));
	}

	@Test
	void testTransitionLLState() throws InvalidTransitionException {

		// if the third character is a letter
		assertDoesNotThrow(
				() -> fsm.isValid("CSC216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSC216"));

		// if the third character is a digit
		assertDoesNotThrow(
				() -> fsm.isValid("CS216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CS216"));
	}

	@Test
	void testTransitionLLLState() throws InvalidTransitionException {

		// if the fourth character is a letter
		assertDoesNotThrow(
				() -> fsm.isValid("CSCC216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSCC216"));

		// if the fourth character is a digit
		assertDoesNotThrow(
				() -> fsm.isValid("CSC216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSC216"));
	}

	@Test
	void testTransitionLLLLState() throws InvalidTransitionException {

		// if the fifth character is a letter
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("CSCCS2"));
		assertEquals("Course name cannot start with more than 4 letters.", e1.getMessage(),
				"Incorrect exception thrown with course name having more than 4 letters");

		// if the fifth character is a digit
		assertDoesNotThrow(
				() -> fsm.isValid("CSCC216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSCC216"));
	}

	@Test
	void testTransitionDState() throws InvalidTransitionException {

		// if the character after the first digit is a letter
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("C2C"));
		assertEquals("Course name must have 3 digits.", e1.getMessage(),
				"Incorrect exception thrown with course name having a letter after the first digit");

		// if the character after the first digit is a digit
		assertDoesNotThrow(
				() -> fsm.isValid("CSC216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSC216"));
	}

	@Test
	void testTransitionDDState() throws InvalidTransitionException {

		// if the character after the second digit is a letter
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("C21C"));
		assertEquals("Course name must have 3 digits.", e1.getMessage(),
				"Incorrect exception thrown with course name having a letter after the second digit");

		// if the character after the second digit is a digit
		assertDoesNotThrow(
				() -> fsm.isValid("CSC216"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSC216"));
	}

	@Test
	void testTransitionDDDState() throws InvalidTransitionException {

		// if the character after the third digit is a letter
		assertDoesNotThrow(
				() -> fsm.isValid("CSC216S"),
				"Should not throw an exception");
		assertTrue(fsm.isValid("CSC216S"));

		// if the character after the third digit is a digit
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("C2166"));
		assertEquals("Course name can only have 3 digits.", e1.getMessage(),
				"Incorrect exception thrown with course name having more than 3 digits");
	}

	@Test
	void testTransitionSuffixState() {

		// if there is a letter after the suffix
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("C216SS"));
		assertEquals("Course name can only have a 1 letter suffix.", e1.getMessage(),
				"Incorrect exception thrown with course name having more than one letter for the suffix");

		// if there is a digit after the suffix
		Exception e2 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid("C216S1"));
		assertEquals("Course name cannot contain digits after the suffix.", e2.getMessage(),
				"Incorrect exception thrown with course name having digits after the suffix");
	}

}
