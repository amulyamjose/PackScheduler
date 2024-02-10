package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseNameValidatorFSMTest {
	
	@Test
	void testNonAlphanumericCharacters() {
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> fsm.isValid(",.)"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage(), 
				"Incorrect exception thrown with course name having non alphanumeric characters");
	}

	@Test
	void testTransitionInitialState() throws InvalidTransitionException {
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		
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
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();

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
