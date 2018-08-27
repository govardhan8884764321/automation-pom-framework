package com.auto.framework.pom.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.auto.framework.pom.logger.LogManager;


public class DateFunctions {

	public static String getCurrentSystemTime(String dateFormat, String timeZone) {
		return getSystemDate(dateFormat, 0, 0, 0, 0, 0, 0, timeZone);
	}
	
	private static Calendar cal = Calendar.getInstance();

	public static Date getSimpleDateFromString(String date) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		return formatter.parse(date);
	}

	public static Date getDateFromString(String date, String format) throws ParseException{
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(date);
	}

	public static Date addMonths(Date date, int noOfMonthToAdd){
		cal.setTime(date);
		cal.add(Calendar.MONTH, noOfMonthToAdd);
		return cal.getTime();
	}

	public static String convertDateToString(Date date, String format){
		DateFormat formatter = new SimpleDateFormat(format);
		String result = formatter.format(date);
		return result;
	}

	/**
	 * converts the date passed to given timeZone and returns it in specified format
	 * @param date
	 * @param format
	 * @param tz
	 * @return
	 */
	public static String convertDateToString(Date date, String format, TimeZone tz){
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(tz);
		String result = formatter.format(date);
		return result;
	}

	/**
	 * returns the current system date
	 * @param dateFormat
	 * @return
	 * 		String
	 */
	public static String getSystemDate(String dateFormat){
		LogManager.log("[getSystemDate] returns the current system date.");
		SimpleDateFormat simpleDateFormate = new SimpleDateFormat(dateFormat);
		return simpleDateFormate.format(new Date());
	}

	/**
	 * returns the system date by adding years, months, days
	 * @param dateFormat
	 * @param addYears
	 * @param addMonths
	 * @param addDays
	 * @return
	 * 		String
	 */
	public static String getSystemDate(String dateFormat, int addYears, int addMonths, int addDays){
		LogManager.log("[getSystemDate] returns the system date by adding years, months, days.");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, addYears);
		calendar.add(Calendar.MONTH, addMonths);
		calendar.add(Calendar.DAY_OF_MONTH, addDays);
		SimpleDateFormat simpleDateFormate = new SimpleDateFormat(dateFormat);
		return simpleDateFormate.format(calendar.getTime());
	}

	/**
	 * returns the system date by adding years, months, days
	 * @param dateFormat
	 * @param addYears
	 * @param addMonths
	 * @param addDays
	 * @return
	 * 		String
	 */
	public static String getSystemDate(String dateFormat, int addYears, int addMonths, int addDays, int addHours, int addMinutes, int addSeconds){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, addYears);
		calendar.add(Calendar.MONTH, addMonths);
		calendar.add(Calendar.DAY_OF_MONTH, addDays);
		calendar.add(Calendar.HOUR_OF_DAY, addHours);
		calendar.add(Calendar.MINUTE, addMinutes);
		calendar.add(Calendar.SECOND, addSeconds);
		SimpleDateFormat simpleDateFormate = new SimpleDateFormat(dateFormat);
		TimeZone timeZone = TimeZone.getTimeZone("PST");
		simpleDateFormate.setTimeZone(timeZone);
		return simpleDateFormate.format(calendar.getTime());
	}
	
	/**
	 * returns the system date by adding years, months, days
	 * @param dateFormat
	 * @param addYears
	 * @param addMonths
	 * @param addDays
	 * @return
	 * 		String
	 */
	public static String getSystemDate(String dateFormat, int addYears, int addMonths, int addDays, 
			int addHours, int addMinutes, int addSeconds, String timeZone){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, addYears);
		calendar.add(Calendar.MONTH, addMonths);
		calendar.add(Calendar.DAY_OF_MONTH, addDays);
		calendar.add(Calendar.HOUR_OF_DAY, addHours);
		calendar.add(Calendar.MINUTE, addMinutes);
		calendar.add(Calendar.SECOND, addSeconds);
		SimpleDateFormat simpleDateFormate = new SimpleDateFormat(dateFormat);
		TimeZone _timeZone = TimeZone.getTimeZone(timeZone);
		simpleDateFormate.setTimeZone(_timeZone);
		return simpleDateFormate.format(calendar.getTime());
	}

	/**
	 * returns number of days in a current month
	 * @return
	 * 		no.of days in a current month
	 */
	public static int getNumberOfDaysInCurrentMonth(){
		Calendar mycal = new GregorianCalendar(Calendar.getInstance().getWeekYear(), Calendar.MONTH+1, 1);
		return mycal.getActualMaximum(Calendar.DAY_OF_MONTH); 
	}

	/**
	 * returns number of days in a current year
	 * @return
	 * 		no.of days in a current year
	 */
	public static int getNumberOfDaysInCurrentYear(){
		Calendar mycal = new GregorianCalendar(Calendar.getInstance().getWeekYear(), Calendar.MONTH+1, 1);
		return mycal.getActualMaximum(Calendar.DAY_OF_YEAR); 
	}

	/**
	 * returns number of days between given two dates
	 * @param dateFormat
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long getDaysBetweenDates(String dateFormat, String startDate, String endDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		Date dateStart = simpleDateFormat.parse(startDate);
		Date dateEnd = simpleDateFormat.parse(endDate);
		return Math.round((dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public static void main(String[] args) {
		LogManager.log("time starting");
		LogManager.log(getCurrentSystemTime("dd MMM, yyyy HH:mm:ss", "IST"));
		LogManager.log(getCurrentSystemTime("dd MMM, yyyy HH:mm:ss", "PST"));
	}
}
