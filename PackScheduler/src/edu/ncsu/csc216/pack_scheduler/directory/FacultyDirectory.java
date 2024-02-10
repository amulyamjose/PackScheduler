/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all faculty in the system
 * @author Amulya Jose 
 */
public class FacultyDirectory {
	/** List of faculty in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates an empty faculty directory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	
	/**
	 * Creates an empty faculty directory. All faculty in the previous list are lost unless saved
	 * by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Constructs the faculty directory by reading in faculty information from the given file. 
	 * Throws an IllegalArgumentException if the file cannot be found.
	 * 
	 * @param fileName file containing list of faculty
	 * @throws IllegalArgumentException "Unable to read file" if file is unable to be read
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * Adds a Faculty to the directory. Returns true if the faculty is added and false if
	 * the faculty is unable to be added because their id matches another faculty's id.
	 * 
	 * This method also hashes the faculty's password for internal storage.
	 * 
	 * @param firstName      faculty's first name
	 * @param lastName       faculty's last name
	 * @param id             faculty's id
	 * @param email          faculty's email
	 * @param password       faculty's password
	 * @param repeatPassword faculty's repeated password
	 * @param maxCourses     faculty's max courses
	 * @return true if added
	 * @throws IllegalArgumentException if the passwords do not match or are empty
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
								String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";

		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		// If an IllegalArgumentException is thrown, it's passed up from Faculty to the GUI
		Faculty facultyPerson = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			if (s.getId().equals(facultyPerson.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(facultyPerson);
	}
	
	/**
	 * Removes the faculty with the given id from the list of faculty with the given id.
	 * Returns true if the faculty is removed and false if the faculty is not in the list.
	 * 
	 * @param facultyId faculty's id
	 * @return true if removed
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			if (s.getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all faculty in the directory with a column for first name, last name, and id.
	 * 
	 * @return String array containing faculty's first name, last name, and id.
	 */
	public String[][] getFacultyDirectory() {
		String[][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;
	}
	
	/**
	 * Saves all faculty in the directory to a file.
	 * 
	 * @param fileName name of file to save faculty to.
	 * @throws IllegalArgumentException "Unable to write to file " + fileName if it is unable to write to such file
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
	
	/**
	 * Gets a faculty from the directory by their id
	 * 
	 * @param id the id of the faculty to get
	 * @return the faculty with the given id
	 */
	public Faculty getFacultyById(String id) {
		Faculty faculty;
		for (int i = 0; i < facultyDirectory.size(); i++) {
			faculty = facultyDirectory.get(i);
			if (id.equals(faculty.getId())) {
				return faculty;
			}
		}
		return null;
	}
	
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException "Invalid password" if the toHash is null, empty, or
	 *                                  there is trouble with the encoder
	 */
	private static String hashString(String toHash) {
		try {
			if (toHash == null || "".equals(toHash)) {
				throw new IllegalArgumentException("Invalid password");
			}
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid password");
		}
	}
	
}
