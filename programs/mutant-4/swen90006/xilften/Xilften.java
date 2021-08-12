package swen90006.xilften;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Xilften is a (fictional) video streaming service. Users can create
 * accounts, rent movies, and watch movies they have rented.
 * 
 * A user has an account with Xilften. This account is protected by a
 * password.
 * 
 * A user can rent a movie for up to 28 days. The cost depends on the
 * number of days.
 * 
 */
public class Xilften 
{
    /** The minimum length of a username */
    public final static int MINIMUM_USERNAME_LENGTH = 4;

    /** The cost for standard and high definition movies */
    public final static double SD_COST = 4.0;
    public final static double HD_COST = 5.0;
    public final static double ADDITIONAL_DAILY_COST = 0.1;

    /** The minumum and maximum rental time in week days */
    public final static int MINIMUM_RENTAL_TIME = 5;
    public final static int MAXIMUM_RENTAL_TIME = 20;

    /** The stream type of a movie: standard definition or high definition */
    public enum StreamType {SD, HD};

    //The passwords for all users (non encrypted!!!)
    private Map<String, String> passwords;

    /**
     * Constructs a new Xilften server with no users and no rented movies
     */
    public Xilften()
    {
	passwords = new HashMap<String, String>();
    }

    /**
     * Registers a new user to Xilften.
     *
     * The Xilften username must conform to the following requirements:
     * Must be at least four characters long and contain only letters
     * (a-z and A-Z) and digits (0-9). Passwords have no requirements.
     *
     * @param username   the username for the user to be added
     * @param password   the password for the user
     * @throws DuplicateUserException   if the username is already registered
     * @throws InvalidUsernameException  if the username does not fit
     *          the requirements (see above)
     *
     * Assumption: username and password are non-null
     */
    public void register(String username, String password)
	throws DuplicateUserException, InvalidUsernameException
    {
	//Check if this user exists
	if (passwords.containsKey(username)) {
	    throw new DuplicateUserException(username);
	}
	else if (username.length() < MINIMUM_USERNAME_LENGTH) {
	    throw new InvalidUsernameException(username);
	}
	else {
	    //check the username contains only letters and digits
	    for(char c : username.toCharArray()) {
		if (!(('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9'))) {
		    throw new InvalidUsernameException(username);
		}

	    }
	}

	passwords.put(username, password);
    }

    /**
     * Checks if a user exists
     * @param username  the username
     * @return  true if and only if this user is registered
     *
     * Assumption: username is non-null
     */
    public boolean isUser(String username)
    {
	return passwords.containsKey(username);
    }

    /**
     * Calculate the cost of a user renting a movie between two dates.
     *
     * A standard definition movie costs $4 to rent for 5 weekdays or less.
     * A high definition movie costs $5 to rent for 5 weekdays or less.
     * 
     * The cost of a movie is an additional 10c per day after 5 weekdays, up to 20 weekdays.
     * After 20 weekdays, the cost does not increase.
     *
     * Weekdays are Monday to Friday.
     *
     * 29 February must be counted in a leap year. A leap year is:
     * (a) divisble by 400; or (4) divisible by 4 but not divisible by 100.
     *
     * @param username  the username for the user
     * @param password  the password for the user
     * @param currentDate the current date
     * @param returnDate  the return date
     * @param streamType the stream type for the movie
     * @returns the cost (in dollars) to rent the movie
     *
     * @throws  NoSuchUserException if the user does not have a xilften account
     * @throws  IncorrectPasswordException if the password is incorrect for this user
     *
     * Before calling this method, the web interface ensures the following assumptions hold.
     *
     * Assumption: the username and password are non-null
     * Assumption: the return date is not before the current date
     * Assumption: both dates are valid dates; i.e. 
     *             (a) month in 1..12
     *             (b) day in 1..28 for Feb, of 29 for leap years
     *             (c) day in 1..30 for Apr, Jun, Sep, and Nov
     *             (d) day in 1..31 for all other months.
     */
    public double rent(String username, String password, Date currentDate, Date returnDate, StreamType streamType)
	throws NoSuchUserException, IncorrectPasswordException
    {
	//check that the user exists
	if (!passwords.containsKey(username)) {
	    throw new NoSuchUserException(username);
	}
	else if (!passwords.get(username).equals(password)) {
	    throw new IncorrectPasswordException(username, password);
	}

	int period = getWeekDays(currentDate, returnDate);
	
	//calculate the base cost
	double cost = SD_COST;
	if (streamType == StreamType.HD) {
	    cost = HD_COST;
	}

	//calculate additional costs
	if (period > MINIMUM_RENTAL_TIME) {
	    cost += (period - MINIMUM_RENTAL_TIME) * ADDITIONAL_DAILY_COST;
	}

	return cost;
    }


    /**
     * Calculates the number of weekdays between two dates
     */
    private static int getWeekDays(Date startDate, Date endDate)
    {
	int day = startDate.getDay();
	int month = startDate.getMonth();
	int year = startDate.getYear();

	int dayOfWeek = dayOfWeek(day, month, year);
	int numberOfWeekDays = 0;
	Date iterantDate = new Date(day, month, year);
	while (numberOfWeekDays < MAXIMUM_RENTAL_TIME && !iterantDate.equals(endDate)) {

	    //increment numberOfWeekDays only for weekdays
	    if (dayOfWeek != 0 && dayOfWeek != 6) {
		numberOfWeekDays++;
	    }

	    if (day < monthDuration(month, year)) {
		day++;
	    }
	    else if (month < 12) {
		day = 1;
		month++;
	    }
	    else {
		day = 1;
		month = 1;
		year++;
	    }

	    iterantDate = new Date(day, month, year);
	    dayOfWeek = dayOfWeek(day, month, year);
	}

	return numberOfWeekDays;
    }

    /**
     * Calculates the month duration.
     */
    private static int monthDuration(int month, int year)
    {
	//Adjust February for leap years
	if (month == 2  && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
	    return 29;
	}
	else if (month == 2) {
	    return 28;
	}
	else if (month != 4 && month == 6 || month == 9 || month == 11) {
	    return 30;
	}
	return 31;
    }

    /* 
     * Convert a day, month and year to the day of the week, returning
     * an integer between 0 and 6, inclusive, with 0 indicating Sunday.
     * Precondition: The date must be in the range September 14,
     * 1752 - December 31, 9999.
     */
    private static int dayOfWeek(int day, int month, int year)
    {
	final int [] MONTH_KEYS = new int [] {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};

	if (month < 3) {
	    year -= 1;
	}

	return (year + year/4 - year/100 + year/400 + MONTH_KEYS[month - 1] + day) % 7;
    }
}
