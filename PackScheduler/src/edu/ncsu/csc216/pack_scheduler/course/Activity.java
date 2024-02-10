/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

/**
 * A representation of an activity for use int the
 * WolfpackScheduler project
 * @author William Walton
 */
public abstract class Activity implements Conflict {
	/** The number of hours in a day */
	private static final int UPPER_HOUR = 24;
	/** The number of minutes in an hour */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * constructor for Activity class
	 * @param title the title of the activity
	 * @param meetingDays the meeting days of the activity
	 * @param startTime the time that the activity starts
	 * @param endTime the time that the activity ends
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Return the Course's title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title. If the title is null or length 0,
	 * an IllegalArgumentException is thrown
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title parameter is invalid
	 */
	public void setTitle(String title) {
		
		// checks if the title is null or length 0
		if (title == null || title.length() == 0)
			throw new IllegalArgumentException("Invalid title.");
		
		
		this.title = title;
	}

	/**
	 * Returns the Course's meeting days.
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Course's starting time.
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's ending time.
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Converts the integer military time to
	 * regular time
	 * @param time the time to be converted
	 * @return A string of the time in normal time
	 */
	private String getTimeString(int time) {
		String timeString = "";
		boolean afternoon = false;
		int hours = time / 100;
		int minutes = time % 100;
		if (hours == 0) {
			timeString += "12";
		} else if (hours < 12) {
			timeString += String.valueOf(hours);
		} else if (hours == 12) {
			timeString += String.valueOf(hours);
			afternoon = true;
		} else {
			timeString += String.valueOf(hours - 12);
			afternoon = true;
		}
		
		timeString += ":";
		
		if (minutes < 10)
			timeString += "0";
		
		timeString += String.valueOf(minutes);
		
		if (afternoon)
			timeString += "PM";
		else
			timeString += "AM";
		
		return timeString;
	}

	/**
	 * checks if the time of an activity conflicts with another activity
	 * an activity conflicts with another if:
	 * 		they are not arranged
	 * 		they have at least one of the same meeting days
	 *  	and the times overlap, including on the same minute (an event ends at 9:00 and another starts at 9:00)
	 * @param possibleConflictingActivity the activity to be compared to the activity implementing this
	 * @throws ConflictException if the activities overlap in time
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		if (!"A".equals(meetingDays)) {
			for (char a : meetingDays.toCharArray()) {
				if (possibleConflictingActivity.getMeetingDays().contains(String.valueOf(a))) {
					
					// thrown out alternative solution:
					//  iterate inclusively from startTime to endTime and throw an exception
					//  if the number is contained inclusively between the other activities times
					// reason: too much iteration when it could be simplified
					if (startTime <= possibleConflictingActivity.getEndTime() && endTime >= possibleConflictingActivity.getStartTime()) {
						throw new ConflictException();
					}
					
					// because the timing is the same on different days, we can be sure that startTime/endTime won't be different
					// for another day so we are done
					break;
				}
			} 
		}
	}

	/**
	 * gives a string starting with meetingDays, space, regular time startTime, dash,
	 * regular time endTime
	 * @return a string representing the days of the meeting and time
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays))
			return "Arranged";
		
		String meetingString = meetingDays + " ";
		
		meetingString += getTimeString(startTime) + "-" + getTimeString(endTime);
		
		return meetingString;
	}

	/**
	 * checks if the time is invalid
	 * @param time the time to be checked
	 * @return boolean representation of whether or not the time is invalid
	 */
	private boolean isInvalidTime(int time) {
	
		return time < 0 || time / 100 >= UPPER_HOUR 
				|| time % 100 >= UPPER_MINUTE;
	}

	/**
	 * sets the Course's meetingDays, startTime, and endTime. if the
	 * meetingDays is null or empty, throws IllegalArgumentException
	 * if MeetingDays == "A"
	 * 		if startTime and endTime are not both 0, throw IllegalArgumentException
	 * otherwise:
	 * 		if a character in meetingDays is not M, T, W, H, or F, startTime or
	 * 		endTime are not valid times, or endTime is before startTime throw
	 * 		IllegalArgumentException
	 * @param meetingDays the meetingDays to set
	 * @param startTime the startTime to set
	 * @param endTime the endTime to set
	 * @throws IllegalArgumentException "Invalid meeting days and times." if the meeting days and/or times
	 * 		are invalid
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		// checks if meetingDays is null or empty
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if ("A".equals(meetingDays)) { // arranged
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else { // not arranged
			
			String weekChart = "MTWHFUS"; // valid days of the week, including weekend
			int weekIndex = -1;
			
			// checks that all meetingDays are days of the week
			for (int i = 0; i < meetingDays.length(); i++) {
				weekIndex = weekChart.indexOf(meetingDays.charAt(i));
				if (weekIndex == -1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				
				weekChart = weekChart.substring(0, weekIndex) + weekChart.substring(1 + weekIndex); //removing the day from the week (no repeats)
			}
			
			// checks if the times are valid
			if (isInvalidTime(startTime) || isInvalidTime(endTime)) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			//checks if the endTime is before the startTime
			if (endTime < startTime) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * gets a 4 long string array of the activity's attributes
	 * @return an array representation of the Activity
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * gets a 7 long string array of the activity's attributes
	 * @return an array representation of the Activity
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * A less strict equals() like method
	 * @param activity the Activity to be compared to this
	 * @return boolean representation of whether or not another activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * Gives the hash code of the Activity
	 * @return the hash code of the Activity
	 */
	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}

	/**
	 * checks if this activity is equal to another
	 * if the object is null, returns falls
	 * if the object is not the same class, returns false
	 * checks if the endTime, meetingDays, startTime, and title are equal
	 * @param obj the object to be compared to this Activity
	 * @return boolean representation of whether the Activity is equal to the object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}

}