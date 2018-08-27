package com.auto.framework.pom.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface IDriverManager {
	
	public static final String CHROME_DRIVER = "CHROME_DRIVER";
	public static final String FIREFOX_DRIVER = "FIREFOX_DRIVER";
	
	public WebDriver getDriver(String driverName);
	public WebDriver getDriver(String driverName, DesiredCapabilities capabilities);
	
}
