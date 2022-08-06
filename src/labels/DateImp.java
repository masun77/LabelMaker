package labels;

import java.util.HashMap;

/**
 * A simple date implementation with getters 
 * for month, day, and year. 
 *
 */
public class DateImp implements Date {
	private static HashMap<Integer, String> monthToMonthName = 
			new HashMap<Integer,String>();

	private int dayOfMonth;
	private int monthOfYear;
	private int year;
	
	public DateImp(int m, int d, int y) {
		dayOfMonth = d;
		monthOfYear = m;
		year = y;
		initMonths();
	}
	
	public DateImp() {
		dayOfMonth = 1;
		monthOfYear = 1;
		year = 1900;
		initMonths();
	}
	
	private void initMonths() {
		if (monthToMonthName.get(1) == null) {
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
		}
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
	public String getMonthName() {
		return monthToMonthName.get(monthOfYear);
	}

	@Override
	public int getMonthofYear() {
		return monthOfYear;
	}

	@Override
	public int getYear() {
		return year;
	}

	@Override
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	
	@Override
	public String getAsPackDate() {
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
	
	public static Date parseDate(String d) {
		Date date = new DateImp();
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
}
