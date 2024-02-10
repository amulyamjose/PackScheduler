package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.Test;
/**
 * Tests the CourseRoll class
 * @author William Walton
 * @author Amulya Jose
 * @author Jacob Phillips
 */
public class CourseRollTest {
	
	/**
	 * creates a new CourseRoll for testing
	 */
    private SortedList<Student> studentList;
    {
        try {
            studentList = StudentRecordIO.readStudentRecords("test-files/lotsOfStudents.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * test CourseRoll constructor
     */
    @Test
    public void testConstructor() {
    	Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 20, "A");
    	CourseRoll roll = c.getCourseRoll();
        // assertNotNull(roll);
        assertEquals(0, roll.getOpenSeats() - 20);
        assertEquals(20, roll.getEnrollmentCap());

        assertThrows(IllegalArgumentException.class, () -> new CourseRoll(9, c));
        assertThrows(IllegalArgumentException.class, () -> new CourseRoll(251, c));
        assertThrows(IllegalArgumentException.class, () -> new CourseRoll(8, c));
        assertThrows(IllegalArgumentException.class, () -> new CourseRoll(252, c));
        assertDoesNotThrow(() -> new CourseRoll(10, c));
        assertDoesNotThrow(() -> new CourseRoll(250, c));
        assertDoesNotThrow(() -> new CourseRoll(11, c));
        assertDoesNotThrow(() -> new CourseRoll(249, c));
        
        assertEquals(0, roll.getNumberOnWaitlist());
    }

    /**
     * test CourseRoll getter and setter methods
     */
    @Test
    public void testEnrollmentCapGetSet() {
    	Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 20, "A");
    	CourseRoll courseRoll = c.getCourseRoll();

        assertEquals(20, courseRoll.getEnrollmentCap());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(10));
        assertEquals(10, courseRoll.getEnrollmentCap());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(250));
        assertEquals(250, courseRoll.getEnrollmentCap());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(11));
        assertEquals(11, courseRoll.getEnrollmentCap());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(249));
        assertEquals(249, courseRoll.getEnrollmentCap());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(50));
        assertEquals(50, courseRoll.getEnrollmentCap());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(155));
        assertEquals(155, courseRoll.getEnrollmentCap());

        assertThrows(IllegalArgumentException.class, () -> courseRoll.setEnrollmentCap(9));
        assertThrows(IllegalArgumentException.class, () -> courseRoll.setEnrollmentCap(251));
        assertThrows(IllegalArgumentException.class, () -> courseRoll.setEnrollmentCap(8));
        assertThrows(IllegalArgumentException.class, () -> courseRoll.setEnrollmentCap(252));
    }

    /**
     * tests CourseRoll.enroll()
     */
    @Test
    public void testEnrollStudent() {
    	Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
    	CourseRoll courseRoll = c.getCourseRoll();
    	
    	assertDoesNotThrow (
    			() -> courseRoll.enroll(studentList.get(0)),
    			"Should not thrown exception");
    	
        for (int i = 1; i < 10; i++) {
            courseRoll.enroll(studentList.get(i));
        }
        
        assertDoesNotThrow (
    			() -> courseRoll.enroll(studentList.get(10)),
    			"Should not thrown exception");
        assertEquals(0, courseRoll.getOpenSeats());
        assertEquals(1, courseRoll.getNumberOnWaitlist());
        
        for (int i = 11; i < 20; i++) {
            courseRoll.enroll(studentList.get(i));
        }

        assertThrows(IllegalArgumentException.class, () -> courseRoll.enroll(studentList.get(20)));

        assertThrows(IllegalArgumentException.class, () -> courseRoll.enroll(null));

        assertThrows(IllegalArgumentException.class,
                () -> courseRoll.enroll(new Student(null, null, null, null, null)));

        assertThrows(IllegalArgumentException.class,
                () -> courseRoll.setEnrollmentCap(5));
    }

    /**
     * tests CourseRoll.drop()
     */
    @Test
    public void testDropStudent() {
    	Course c = new Course("CSC217", "Software Development Fundamentals Lab", "601", 1, "sesmith5", 10, "A");
    	CourseRoll courseRoll = c.getCourseRoll();
    	
    	// fill up roll (empty waitlist)
    	for (int i = 0; i < 10; i++) {
            courseRoll.enroll(studentList.get(i));
        }
        assertEquals(0, courseRoll.getNumberOnWaitlist());

    	
    	// one open seat after dropping one student
    	assertEquals(0, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.drop(studentList.get(9)));
        assertEquals(1, courseRoll.getOpenSeats());
        
        // attempt to drop null Student (invalid)
        assertThrows(IllegalArgumentException.class, () -> courseRoll.drop(null));
        
        // fill up roll and waitlist
        for (int i = 14; i < 25; i++) {
            courseRoll.enroll(studentList.get(i));
        }
        assertEquals(10, courseRoll.getNumberOnWaitlist());

        // drop a student (first student in waitlist gets added to the roll)
        assertEquals(0, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.drop(studentList.get(0)));
        assertEquals(0, courseRoll.getOpenSeats());
        assertEquals(9, courseRoll.getNumberOnWaitlist());
    }

    /**
     * tests CourseRoll.canEnroll()
     */
    @Test
    public void testCanEnrollStudent() {
    	Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 20, "A");
    	CourseRoll courseRoll = c.getCourseRoll();
        for (int i = 0; i < 16; i++) {
            courseRoll.enroll(studentList.get(i));
        }

        assertTrue(() -> courseRoll.canEnroll(studentList.get(16)));
        assertFalse(() -> courseRoll.canEnroll(studentList.get(15)));

        courseRoll.setEnrollmentCap(16);
        assertFalse(() -> courseRoll.canEnroll(studentList.get(16)));
    }

    /**
     * tests CourseRoll.getOpenSeats()
     */
    @Test
    public void testGetOpenSeats() {
    	Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 20, "A");
    	CourseRoll courseRoll = c.getCourseRoll();
        assertEquals(20, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(10));
        assertEquals(10, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(250));
        assertEquals(250, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(11));
        assertEquals(11, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(249));
        assertEquals(249, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(50));
        assertEquals(50, courseRoll.getOpenSeats());
        assertDoesNotThrow(() -> courseRoll.setEnrollmentCap(155));
        assertEquals(155, courseRoll.getOpenSeats());

        for (int i = 0; i < 5; i++) {
            courseRoll.enroll(studentList.get(i));
        }

        assertEquals(150, courseRoll.getOpenSeats());
    }
}
