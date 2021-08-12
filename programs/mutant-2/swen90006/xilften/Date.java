package swen90006.xilften;

public class Date
{
    //a mapping from month numbers to month names
    final private static String [] MONTHS =
	new String [] {"January",
		       "February",
		       "March",
		       "April",
		       "May",
		       "June",
		       "July",
		       "August",
		       "September",
		       "October",
		       "November",
		       "December"};
  
    //the day, month, and year of this date
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year)
    {
	this.day = day;
	this.month = month;
	this.year = year;
    }

    public int getDay()
    {
	return day;
    }

    public int getMonth()
    {
	return month;
    }

    public int getYear()
    {
	return year;
    }

    public boolean equals(Object o)
    {
	if (o instanceof Date) {
	    Date date = (Date) o;
	    return day == date.day && 
		month == date.month & 
		year == date.year;
	}
	return false;
    }

    public String toString()
    {
	String result = day + " " + MONTHS[month - 1] + " " + year;
	return result;
    }
}
