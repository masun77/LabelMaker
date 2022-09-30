/**
 * DateImp
 * A date implementation with getters 
 * for month, day, and year. 
 */

package labels;

import java.util.HashMap;

public class DateImp implements Date {
	private int dayOfMonth;
	private int monthOfYear;
	private int year;
	
	private static HashMap<Integer, String> monthToMonthName = 
			new HashMap<Integer,String>();
	private static HashMap<String, Integer> monthNameToMonth = 
			new HashMap<>();
	private static HashMap<Integer, Integer> daysInMonths = new HashMap<>();
	private static boolean initialized = false;

	public DateImp(int m, int d, int y) {
		dayOfMonth = d;
		monthOfYear = m;
		year = y;
		if (year < 100) {
			year += 2000;
		}
		initMonths();
	}
	
	public DateImp() {
		dayOfMonth = 1;
		monthOfYear = 1;
		year = 2000;
		initMonths();
	}
	
	/**
	 * Initialized month numbers and names, short names, and days in each month.  
	 */
	private void initMonths() {
		if (!initialized) {
			monthToMonthName.put(1, "January");
			monthToMonthName.put(2, "February");
			monthToMonthName.put(3, "March");
			monthToMonthName.put(4, "April");
			monthToMonthName.put(5, "May");
			monthToMonthName.put(6, "June");
			monthToMonthName.put(7, "July");
			monthToMonthName.put(8, "August");
			monthToMonthName.put(9, "September");
			monthToMonthName.put(10, "October");
			monthToMonthName.put(11, "November");
			monthToMonthName.put(12, "December");

			monthNameToMonth.put("Jan", 1);
			monthNameToMonth.put("Feb", 2);
			monthNameToMonth.put("Mar", 3);
			monthNameToMonth.put("Apr", 4);
			monthNameToMonth.put("May", 5);
			monthNameToMonth.put("Jun", 6);
			monthNameToMonth.put("Jul", 7);
			monthNameToMonth.put("Aug", 8);
			monthNameToMonth.put("Sep", 9);
			monthNameToMonth.put("Oct", 10);
			monthNameToMonth.put("Nov", 11);
			monthNameToMonth.put("Dec", 12);

			daysInMonths.put(1, 31);
			daysInMonths.put(2, 28);
			daysInMonths.put(3, 31);
			daysInMonths.put(4, 30);
			daysInMonths.put(5, 31);
			daysInMonths.put(6, 30);
			daysInMonths.put(7, 31);
			daysInMonths.put(8, 31);
			daysInMonths.put(9, 30);
			daysInMonths.put(10, 31);
			daysInMonths.put(11, 30);
			daysInMonths.put(12, 31);
		}
	}
	
	@Override
	public int getYear() {
		return year;
	}
	
	@Override
	public String getMonthName() {
		return monthToMonthName.get(monthOfYear);
	}

	@Override
	public int getMonthofYear() {
		return monthOfYear;
	}

	@Override
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	
	@Override
	public String getDateMMDDYYYY() {
		String dateString = zerosHelper(monthOfYear);
		dateString += "/";
		dateString += zerosHelper(dayOfMonth);
		dateString += "/";
		dateString += year;		
		return dateString;
	}
	
	@Override
	public String getAsLabelDate() {
		String mon = monthToMonthName.get(monthOfYear).substring(0,3);
		mon += " ";
		mon += zerosHelper(dayOfMonth);
		return mon;
	}
	
	@Override
	public String getDateYYMMDD() {
		return zerosHelper(year % 100) + zerosHelper(monthOfYear) + zerosHelper(dayOfMonth);
	}
	
	@Override
	public String getMMDD() {
		return zerosHelper(monthOfYear) + "/" + zerosHelper(dayOfMonth);
	}
	
	/**
	 * Append a zero to numbers less than 10 and return.
	 * @param number the number to return
	 * @return the number as a String if it is greater than 10, otherwise the number preceded by a 0
	 */
	private String zerosHelper(int number) {
		if (number < 10) {
			return "0" + Integer.toString(number);
		}
		return Integer.toString(number);
	}

	@Override
	public boolean dateEarlierThan(Date d) {
		if (year < d.getYear()) {
			return true;
		}
		if (year > d.getYear()) {
			return false;
		}
		if (monthOfYear > d.getMonthofYear()) {
			return false;
		}
		if (monthOfYear < d.getMonthofYear()) {
			return true;
		}
		return dayOfMonth < d.getDayOfMonth();
	}

	@Override
	public boolean dateLaterThan(Date d) {
		if (year < d.getYear()) {
			return false;
		}
		if (year > d.getYear()) {
			return true;
		}
		if (monthOfYear > d.getMonthofYear()) {
			return true;
		}
		if (monthOfYear < d.getMonthofYear()) {
			return false;
		}
		return dayOfMonth > d.getDayOfMonth();
	}

	@Override
	public boolean dateEquals(Date d) {
		return year == d.getYear() && monthOfYear == d.getMonthofYear() && dayOfMonth == d.getDayOfMonth();
	}

	@Override
	public void addDays(int numDays) {
		dayOfMonth += numDays;
		while (dayOfMonth > daysInMonths.get(monthOfYear)) {
			dayOfMonth -= daysInMonths.get(monthOfYear);
			monthOfYear += 1;
			if (monthOfYear > 12) {
				monthOfYear = 1;
				year += 1;
			}
		}
	}
	
	/**
	 * Parse a date from a given string. Assumes the string is in the format
	 * mm/dd/yyyy, or mm/dd/yy.
	 * @param d the String to parse
	 * @return the Date parse form the string
	 */
	public static Date parseDate(String d) {
		String day = "1";
		String month = "1";
		String year = "2022";
		int slash = d.indexOf("/");
		if (slash > 0) {
			month = d.substring(0, slash > 2? 2 : slash);
		}
		slash += 1;
		int secSlash = d.indexOf("/", slash);
		if (secSlash > 0) {
			day = d.substring(slash, secSlash - slash > 2? slash + 2 : secSlash);
		}
		secSlash += 1;
		if (secSlash > 0 && secSlash < d.length() - 1) {
			year = d.substring(secSlash, secSlash + 4 > d.length()? d.length() : secSlash + 4);
		}
		if (year.equals("22")) {
			year = "2022";
		}
		
		return new DateImp(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
	}
	
	/**
	 * Helper for Dates in Excel cells. Parses a Date from an Excel date cell
	 * @param d the String contents of the cell to parse to a date
	 * @return the parsed date
	 */
	public static Date parseCellDate(String d) {
		int space = d.indexOf(" ") + 1;
		String monthStr = d.substring(space, space + 3);
		space = d.indexOf(" ", space) + 1;
		String dayStr = d.substring(space, space + 2);
		String yrStr = d.substring(d.length() - 4);
		return new DateImp(monthNameToMonth.get(monthStr), Integer.parseInt(dayStr), Integer.parseInt(yrStr));
	}
}
