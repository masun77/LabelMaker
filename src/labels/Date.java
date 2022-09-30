/**
 * Date
 * An interface for a date, providing methods to access the day, month, and year,
 * to get the date in various string formats, to compare dates,
 * and to add days to the date. 
 */

package labels;

/**
 * Date interface.
 * Allows the user to get the month, year, and day of month; also the name of the month;
 * also the full date as a text string.
 *
 */
public interface Date {
	
	/**
	 * @return the day as a number, 1-31
	 */
	public int getDayOfMonth();
	/**
	 * @return the month as a number, 1-12
	 */
	public int getMonthofYear();
	
	/**
	 * @return the year
	 */
	public int getYear();
	
	/**
	 * @return the month name, January through December
	 */
	public String getMonthName();
	
	
	/**
	 * @return the date as a String in the format mm/dd/yyyy.
	 */
	public String getDateMMDDYYYY();
	
	/**
	 * @return the data in the form YYMMDD, no slashes
	 */
	public String getDateYYMMDD();
	
	/**
	 * @return the month and day, eg. 05/14
	 */
	public String getMMDD();
	
	/**
	 * @return the month name as a three-letter abbreviation, plus the day of the month
	 */
	public String getAsLabelDate();
	
	/**
	 * @param d the Date to compare to
	 * @return true if this date is earlier than the input date, false otherwise
	 */
	public boolean dateEarlierThan(Date d);
	
	/**
	 * @param d the Date to compare to
	 * @return true if this date is later than the input date, false otherwise
	 */
	public boolean dateLaterThan(Date d);
	
	/**
	 * @param d the Date to compare to
	 * @return true if the two dates have the same year, month, and day, false otherwise
	 */
	public boolean dateEquals(Date d);
	
	/**
	 * Add the given number of days to this date
	 * @param numDays the number of days to add
	 */
	public void addDays(int numDays);
	
}
