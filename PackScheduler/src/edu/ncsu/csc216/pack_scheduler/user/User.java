/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;


/**
 * Represents a User for Pack scheduler
 * 
 * @author William Walton
 * @author Amulya Jose
 */
public abstract class User {

	/** the first name of the user */
	private String firstName;
	/** the last name of the user */
	private String lastName;
	/** the user's unique id */
	private String id;
	/** the email address of the user */
	private String email;
	/** the user's password */
	private String password;

	/**
	 * creates a user with the given parameters
	 * 
	 * @param firstName the first name of the user
	 * @param lastName  the last name of the user
	 * @param id        the id of the user
	 * @param email     the email of the user
	 * @param hashPW    the hashed password of the user
	 */
	public User(String firstName, String lastName, String id, String email, String hashPW) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
	}

	/**
	 * getter method for first name
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * setter method for first name
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException "Invalid first name" if
	 *                                  firstName is null or empty
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * getter method for last name
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * setter method for last name
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException "Invalid Last Name" if
	 *                                  lastName is null or empty
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * getter method for user id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * setter method for user id
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException "Invalid id" if
	 *                                  id is null or empty
	 */
	protected void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * getter method for email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setter method for email
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException "Invalid email" if,
	 *                                  the email parameter is null or empty
	 *                                  The email doesn't contain an '@' character
	 *                                  The email doesn't contain a '.' character
	 *                                  the index of the last '.' character in the
	 *                                  email string is earlier than the index of
	 *                                  the first '@' character
	 */
	public void setEmail(String email) {

		// checking null and empty emails
		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException("Invalid email");
		}

		// checking that @ exists in the email
		int atIndex = email.indexOf('@');
		if (atIndex == -1) {
			throw new IllegalArgumentException("Invalid email");
		}

		// this checks for the first dot after the @, which if it would exist if and
		// only if a final dot exists after the @ because email is of finite length.
		// this means that this means that this statement both satisfies "email doesn't
		// contain '.' character'" and
		// "the index of the last '.' character in the email string is earlier than the
		// index of the first '@' character"
		if (email.substring(atIndex).indexOf('.') == -1) {
			throw new IllegalArgumentException("Invalid email");
		}

		this.email = email;
	}

	/**
	 * getter method for the password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * setter method for the password
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException "Invalid password" if
	 *                                  password parameter is null or empty
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * creates a hash code for User
	 * 
	 * @return the hash code of the user
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}

	/**
	 * checks if this is equal to an object
	 * returns false if:
	 * obj is another class
	 * any field is different
	 * otherwise, returns true
	 * 
	 * @param obj the object to be compared to this
	 * @return whether or not this is equal to the parameter object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass() || !obj.getClass().isAssignableFrom(getClass()))
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}

}