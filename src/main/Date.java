package main;

/**
 * Date interface.
 * Allows the user to get the month, year, and day of month; also the name of the month;
 * also the full date as a text string.
 *
 */
public interface Date {
	/**
	 * @return the date as a String in the format mm/dd/yyyy.
	 */
	public String getDate();
	
	/**
	 * @return the month name, January through December
	 */
	public String getMonthName();
	
	/**
	 * @return the month as a number, 1-12
	 */
	public int getMonthofYear();
	
	/**
	 * @return the year
	 */
	public int getYear();
	
	/**
	 * @return the day as a number, 1-31
	 */
	public int getDayOfMonth();
}
