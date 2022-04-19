package main;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateImp implements Date {
	private static HashMap<Integer, String> monthToMonthName = 
			(HashMap<Integer,String>)
			Stream.of(new Object[][] { 
			     { 1, "January" }, 
			     { 2, "February"}, 
			     { 3, "March"}, 
			     { 4, "April"}, 
			     { 5, "May"}, 
			     { 6, "June"}, 
			     { 7, "July"}, 
			     { 8, "August"}, 
			     { 9, "September"}, 
			     { 10, "October"}, 
			     { 11, "November"}, 
			     { 12, "December"}, 
			 })
			.collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

	private int dayOfMonth;
	private int monthOfYear;
	private int year;
	
	public DateImp(int m, int d, int y) {
		dayOfMonth = d;
		monthOfYear = m;
		year = y;
	}
	
	public DateImp() {
		dayOfMonth = 1;
		monthOfYear = 1;
		year = 1900;
	}
	
	@Override
	public String getDate() {
		String dateString = zerosHelper(monthOfYear);
		dateString += "/";
		dateString += zerosHelper(dayOfMonth);
		dateString += "/";
		dateString += year;		
		return dateString;
	}
	
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
	
}
