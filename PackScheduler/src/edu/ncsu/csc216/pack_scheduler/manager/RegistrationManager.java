package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Handles the current user of the program
 * allows logging in/out
 * Allows user to do specific things according to
 * the type of user
 * @author William Walton
 * @author Amulya Jose
 */
public class RegistrationManager {

	/** the current and only instance of RegistrationManager */
	private static RegistrationManager instance;
	/** the catalog of all the available courses */
	private CourseCatalog courseCatalog;
	/** the directory of all the students */
	private StudentDirectory studentDirectory;
    /** the directory of all the faculty */
    private FacultyDirectory facultyDirectory;
	/** the registrar of the program */
	private User registrar;
	/** the currentUser of the program */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** the file where the registrar's info is */
	private static final String PROP_FILE = "registrar.properties";

	
	/**
	 * constructs a new registration manager
	 * with an empty course catalog and student directory
	 * there is no current user
	 */
	private RegistrationManager() {
		createRegistrar();
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
        this.facultyDirectory = new FacultyDirectory();
		this.currentUser = null;
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if the currentUser isn't a Student
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

    /**
     * Gets the faculty directory in the registration manager
     * @return The faculty directory
     */
    public FacultyDirectory getFacultyDirectory() {
        return facultyDirectory;
    }
	
	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if the currentUser isn't a Student
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        int num = s.getSchedule().getScheduledCourses().length;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().getScheduledCourses().length == num - 1;
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 * @throws IllegalArgumentException if the currentUser isn't a Student
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}

	/**
	 * creates the registrar of the manager
	 * according to the Properties file
	 * @throws IllegalArgumentException "Cannot create registrar." if file
	 * 		cannot be read or found
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * hashes the password to prevent it from being directly accessible
	 * @param pw the unhashed password
	 * @return a hashed passwrod
	 * @throws IllegalArgumentException "Cannot hash password" if the hash algorithm
	 * 		is invalid
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Returns an instance of the RegistrationManager
	 * @return an instance of the RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Returns the course catalog
	 * @return the course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Returns the student directory
	 * @return the student directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Logs in the current user
	 * @param id the id of the current user
	 * @param password the password of the current user
	 * @return true if the user logged in and false if they didn't
	 * @throws IllegalArgumentException if the user doesn't exist
	 */
	public boolean login(String id, String password) {
		if (currentUser != null) {
			return false;
		}
		
		boolean isRegistrar = false;

		String localHashPW = hashPW(password);
		if (registrar.getId().equals(id)) {
			isRegistrar = true;
			if (registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			}
		}
		
	    if (!isRegistrar) {
	        Faculty f = facultyDirectory.getFacultyById(id);
	        if (f != null && f.getPassword().equals(localHashPW)) {
	            currentUser = f;
	            return true;
	        }

	        Student s = studentDirectory.getStudentById(id);
	        if (s != null && s.getPassword().equals(localHashPW)) {
	            currentUser = s;
	            return true;
	        }

	        if (f == null && s == null) {
		        throw new IllegalArgumentException("User doesn't exist.");
	        }
	    }
	    
	    return false;
	}
	
	/**
	 * Adds a faculty object to a course
	 * @param c the course being added to the faculty's schedule
	 * @param f the faculty being manipulated
	 * @return whether the course can be added to the faculty's schedule
	 * @throws IllegalArgumentException if the currentUser isn't a Registrar
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (!(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
		
		return f.getSchedule().addCourseToSchedule(c);
	}
	
	/**
	 * Removes a faculty object from a course
	 * @param c the course being removed from the faculty's schedule
	 * @param f the faculty being manipulated
	 * @return whether the course can be removed from the faculty's schedule
	 * @throws IllegalArgumentException if the currentUser isn't a Registrar
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (!(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
		
		return f.getSchedule().removeCourseFromSchedule(c);
	}
	
	/**
	 * Resets a faculty's schedule
	 * @param f the faculty being manipulated
	 * @throws IllegalArgumentException if the currentUser isn't a Registrar
	 */
	public void resetFacultySchedule(Faculty f) {
		if (!(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
		
		f.getSchedule().resetSchedule();
	}

	/**
	 * Logs out the current user
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * getter method for the current user
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * clears all data including the
	 * course catalog, student directory, and current user
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
		this.currentUser = null;
	}

	/**
	 * A Registrar is a special type of user
	 * that has the ability to edit many of the aspects of the system
	 */
	private static class Registrar extends User {
		/**
		 * Creates a registrar user.
		 * @param firstName the first name of the registrar
		 * @param lastName the last name of the registrar
		 * @param id the unique id of the registrar
		 * @param email the email of the registrar
		 * @param hashPW the hashed password of the registrar
		 * 
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}

	}
}