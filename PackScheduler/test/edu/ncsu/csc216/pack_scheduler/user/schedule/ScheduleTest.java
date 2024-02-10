package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Ensures Schedule.java works correctly
 * 
 * @author Jacob Phillips
 * @author Amulya Jose
 * @author William Walton
 */
public class ScheduleTest {
        /** Schedule to be reused between tests */
        private Schedule schedule;

        /** List of courses to be reused between tests */
        private Course[] courses;

        /**
         * Cleans the schedule between each test
         */
        @BeforeEach
        public void setUp() {
                schedule = new Schedule();
                courses = new Course[14];
                courses[0] = new Course("CSC116",
                                "Intro to Programming - Java",
                                "001",
                                3,
                                "jdyoung2",
                                10,
                                "MW",
                                910,
                                1100);
                courses[1] = new Course("CSC116",
                                "Intro to Programming - Java",
                                "002",
                                3,
                                "spbalik",
                                10,
                                "MW",
                                1120,
                                1310);
                courses[2] = new Course("CSC116",
                                "Intro to Programming - Java",
                                "003",
                                3,
                                "tbdimitr",
                                10,
                                "TH",
                                1120,
                                1310);
                courses[3] = new Course("CSC116",
                                "Intro to Programming - Java",
                                "002",
                                3,
                                "jtking",
                                10,
                                "TH",
                                910,
                                1100);
                courses[4] = new Course("CSC216",
                                "Software Development Fundamentals",
                                "001",
                                3,
                                "sesmith5",
                                10,
                                "TH",
                                1330,
                                1445);
                courses[5] = new Course("CSC216",
                                "Software Development Fundamentals",
                                "002",
                                3,
                                "ixdoming",
                                10,
                                "MW",
                                1330,
                                1445);
                courses[6] = new Course("CSC216",
                                "Software Development Fundamentals",
                                "601",
                                3,
                                "jctetter",
                                10,
                                "A");
                courses[7] = new Course("CSC217",
                                "Software Development Fundamentals Lab",
                                "202",
                                1,
                                "sesmith5",
                                10,
                                "M",
                                140,
                                141);
                courses[8] = new Course("CSC217",
                                "Software Development Fundamentals Lab",
                                "211",
                                1,
                                "sesmith5",
                                10,
                                "T",
                                830,

                                1020);
                courses[9] = new Course("CSC217",
                                "Software Development Fundamentals Lab",
                                "223",
                                1,
                                "sesmith5",
                                10,
                                "W",
                                1500,

                                1650);
                courses[10] = new Course("CSC217",
                                "Software Development Fundamentals Lab",
                                "601",
                                1,
                                "sesmith5",
                                10,
                                "A");
                courses[11] = new Course("CSC226",
                                "Discrete Mathematics for Computer Scientists",
                                "001",
                                3,
                                "tmbarnes",
                                10,
                                "MWF",

                                501,
                                502);
                courses[12] = new Course("CSC230",
                                "C and Software Tools",
                                "001",
                                3,
                                "dbsturgi",
                                10,
                                "MW",
                                1145,
                                1300);
                courses[13] = new Course("CSC316",
                                "Data Structures and Algorithms",
                                "001",
                                3,
                                "jtking",
                                10,
                                "MW",
                                830,
                                945);
        }

        /**
         * Ensures the schedule.java constructor works correctly.
         */
        @Test
        public void testConstructor() {
                assertEquals("My Schedule",
                                schedule.getTitle());
                assertEquals(0,
                                schedule.getScheduledCourses().length);
        }

        /**
         * Ensures functionality of getting and setting title
         */
        @Test
        public void testGetAndSetTitle() {
                assertEquals("My Schedule",
                                schedule.getTitle());
                schedule.setTitle("test 1");

                assertEquals("test 1",
                                schedule.getTitle());
                schedule.setTitle("2ndTest");

                assertEquals("2ndTest",
                                schedule.getTitle());
                schedule.setTitle("$$$LAAst___Test");

                assertEquals("$$$LAAst___Test",
                                schedule.getTitle());

                assertEquals("Title cannot be null.",

                                assertThrows(IllegalArgumentException.class,

                                                () -> schedule.setTitle(null)).getMessage());

        }

        /**
         * Tests the addCourseToSchedule method
         */
        @Test
        public void testAddCoursesAndReset() {
                assertTrue(schedule.addCourseToSchedule(courses[0]));
                assertArrayEquals(courses[0].getShortDisplayArray(), schedule.getScheduledCourses()[0]);
                assertTrue(schedule.addCourseToSchedule(courses[4]));
                assertArrayEquals(courses[4].getShortDisplayArray(), schedule.getScheduledCourses()[1]);
                assertTrue(schedule.addCourseToSchedule(courses[7]));
                assertArrayEquals(courses[7].getShortDisplayArray(), schedule.getScheduledCourses()[2]);
                assertTrue(schedule.addCourseToSchedule(courses[11]));
                assertArrayEquals(courses[11].getShortDisplayArray(), schedule.getScheduledCourses()[3]);
                assertTrue(schedule.addCourseToSchedule(courses[12]));
                assertArrayEquals(courses[12].getShortDisplayArray(), schedule.getScheduledCourses()[4]);

                assertEquals("You are already enrolled in CSC116",
                                assertThrows(IllegalArgumentException.class,
                                                () -> schedule.addCourseToSchedule(courses[1]))
                                                .getMessage());
                assertEquals("You are already enrolled in CSC116",
                                assertThrows(IllegalArgumentException.class,
                                                () -> schedule.addCourseToSchedule(courses[2]))
                                                .getMessage());
                assertEquals("You are already enrolled in CSC116",
                                assertThrows(IllegalArgumentException.class,
                                                () -> schedule.addCourseToSchedule(courses[3]))
                                                .getMessage());
                assertEquals("You are already enrolled in CSC216",
                                assertThrows(IllegalArgumentException.class,
                                                () -> schedule.addCourseToSchedule(courses[5]))
                                                .getMessage());

                assertEquals("The course cannot be added due to a conflict.",
                                assertThrows(IllegalArgumentException.class,
                                                () -> schedule.addCourseToSchedule(courses[13]))
                                                .getMessage());

                assertEquals(
                                "Cannot invoke \"edu.ncsu.csc216.pack_scheduler.course.Activity.getMeetingDays()\" because \"possibleConflictingActivity\" is null",
                                assertThrows(NullPointerException.class, () -> schedule.addCourseToSchedule(null))
                                                .getMessage());

                schedule.setTitle("test 1");
                schedule.resetSchedule();

                assertEquals("My Schedule",
                                schedule.getTitle());
                assertEquals(0,
                                schedule.getScheduledCourses().length);
        }

        /**
         * Test the removeCourseFromSchedule method
         */
        @Test
        public void testRemoveCourses() {
                assertFalse(schedule.removeCourseFromSchedule(courses[0]));
                assertFalse(schedule.removeCourseFromSchedule(null));

                assertTrue(schedule.addCourseToSchedule(courses[0]));
                assertTrue(schedule.addCourseToSchedule(courses[4]));
                assertTrue(schedule.addCourseToSchedule(courses[7]));
                assertTrue(schedule.addCourseToSchedule(courses[11]));
                assertTrue(schedule.addCourseToSchedule(courses[12]));

                assertTrue(schedule.removeCourseFromSchedule(courses[0]));
                assertTrue(schedule.removeCourseFromSchedule(courses[12]));
                assertTrue(schedule.removeCourseFromSchedule(courses[7]));

                assertFalse(schedule.removeCourseFromSchedule(courses[0]));

        }

        /**
         * Test the getScheduledCourses method
         */
        @Test
        public void testGetScheduledCourses() {

                assertTrue(schedule.addCourseToSchedule(courses[0]));
                assertTrue(schedule.addCourseToSchedule(courses[4]));
                assertTrue(schedule.addCourseToSchedule(courses[7]));
                assertTrue(schedule.addCourseToSchedule(courses[11]));
                assertTrue(schedule.addCourseToSchedule(courses[12]));

                String[][] expected = { courses[0].getShortDisplayArray(), courses[4].getShortDisplayArray(),
                                courses[7].getShortDisplayArray(), courses[11].getShortDisplayArray(),
                                courses[12].getShortDisplayArray() };
                assertArrayEquals(expected, schedule.getScheduledCourses());
        }

        /**
         * tests Schedule.getScheduleCredits()
         */
        @Test
        public void testGetScheduleCredits() {

                assertTrue(schedule.addCourseToSchedule(courses[0]));
                assertTrue(schedule.addCourseToSchedule(courses[4]));
                assertTrue(schedule.addCourseToSchedule(courses[7]));
                assertTrue(schedule.addCourseToSchedule(courses[11]));
                assertTrue(schedule.addCourseToSchedule(courses[12]));

                assertEquals(13, schedule.getScheduleCredits());

                assertEquals(0, new Schedule().getScheduleCredits());
        }

        /**
         * tests Schedule.canAdd()
         */
        @Test
        public void testCanAdd() {
                assertTrue(schedule.canAdd(courses[0]));
                assertFalse(schedule.canAdd(null));
                assertTrue(schedule.addCourseToSchedule(courses[0]));
                assertTrue(schedule.canAdd(courses[4]));
                assertTrue(schedule.addCourseToSchedule(courses[4]));
                assertTrue(schedule.canAdd(courses[7]));
                assertTrue(schedule.addCourseToSchedule(courses[7]));
                assertTrue(schedule.canAdd(courses[11]));
                assertTrue(schedule.addCourseToSchedule(courses[11]));
                assertTrue(schedule.canAdd(courses[12]));
                assertTrue(schedule.addCourseToSchedule(courses[12]));

                assertFalse(schedule.canAdd(courses[1]));
                assertFalse(schedule.canAdd(courses[2]));
                assertFalse(schedule.canAdd(courses[3]));
                assertFalse(schedule.canAdd(courses[5]));
                assertFalse(schedule.canAdd(courses[13]));
        }

}
