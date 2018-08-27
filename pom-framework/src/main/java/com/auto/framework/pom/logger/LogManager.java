package com.auto.framework.pom.logger;

import org.testng.Reporter;

import com.auto.framework.pom.common.DateFunctions;

public class LogManager {

	private static String dateFormat = "dd MMM, yyyy HH:mm:ss";
	private static String timeZone = "IST";
	
	private static long alternateLine = 0;
	
	private static String getCallingClassName(){
		final int three = 3;
		String name = Thread.currentThread().getStackTrace()[three].getClassName();
		return name;
	}
	
	private static String getCallingSimpleClassName(){
		final int three = 3;
		String name = Thread.currentThread().getStackTrace()[three].getClassName();
		name = name.split("\\.")[name.split("\\.").length-1];
		return name;
	}
	
	public static void log(String message) {
		String convertedString = DateFunctions.getCurrentSystemTime(dateFormat, timeZone) +"   [INFO]   ["
				+getCallingClassName() +"]  "+message;
		String convertedStringForTestNG = "[INFO]   ["
				+getCallingSimpleClassName() +"]  "+message;
		System.out.println(convertedString);
		if(alternateLine++ %2 == 0) {
			Reporter.log("<font style='background-color:#E7E4E4';>" +convertedStringForTestNG+ "</font>");
		} else {
			Reporter.log(convertedStringForTestNG);
		}
	}
	
	public static void logWarning(String message) {
		String convertedString = DateFunctions.getCurrentSystemTime(dateFormat, timeZone) +"   [WARNING]   ["
				+getCallingClassName() +"]  "+message;
		String convertedStringForTestNG = "[WARNING]   ["
				+getCallingSimpleClassName() +"]  "+message;
		System.out.println(convertedString);
		Reporter.log("<font style='color: yellow;'>" +convertedStringForTestNG+ "</font>");
		alternateLine++;
	}
	
	public static void logError(String message) {
		String convertedString = DateFunctions.getCurrentSystemTime(dateFormat, timeZone) +"   [ERROR]   ["
				+getCallingClassName() +"]  "+message;
		String convertedStringForTestNG = "[ERROR]   ["
				+getCallingSimpleClassName() +"]  "+message;
		System.out.println(convertedString);
		Reporter.log("<font style='color: red;'>" +convertedStringForTestNG+ "</font>");
		alternateLine++;
	}
}
