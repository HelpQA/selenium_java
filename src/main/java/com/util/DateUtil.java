package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * Gets the current time stamp.
	 *
	 * @return the current time stamp
	 */
	public static String getCurrentTimeStamp() {
		String currentTime = new SimpleDateFormat("yyyy-MMM-dd hhmmss").format(new Date());
		currentTime = currentTime.replace(" ", "_").replace("-", "_");
		return currentTime;
	}
	
	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public static String getCurrentDate() {
		String currentDate = null;
		try {
			currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			currentDate = currentDate.replace(" ", "_").replace("-", "_");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return currentDate;
	}
	
	public static String getDate(int days) {
		String currentDate = null;
		try {
			Date date = new Date();
			currentDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			date = cal.getTime();
			currentDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
			currentDate = currentDate.replace(" ", "_").replace("-", "_");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return currentDate;
	}

	public static String addDays(String strDate, int days) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date;
		Calendar cal = Calendar.getInstance();;
		try {
			date = dateFormat.parse(strDate);
			cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, days); // minus number would decrement the days
		} catch (Exception e) {
			throw new RuntimeException("Getting exception while adding days to the month",e);
		}
		return new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
	}

}
