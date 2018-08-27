package com.auto.framework.pom.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverManager implements IDriverManager {

	public WebDriver getDriver(String driverName) {
		WebDriver driver = null;
		switch(driverName) { 
		case CHROME_DRIVER:
			driver = getChromeDriver();
			break;
		case FIREFOX_DRIVER:
			driver = getFirefoxDriver();
			break;
		default:
			driver = getChromeDriver();
		}
		return driver;
	}

	public WebDriver getDriver(String driverName, DesiredCapabilities capabilities) {
		WebDriver driver = null;
		switch(driverName) { 
		case CHROME_DRIVER:
			driver = getChromeDriver(capabilities);
			break;
		case FIREFOX_DRIVER:
			driver = getFirefoxDriver(capabilities);
			break;
		default:
			driver = getChromeDriver(capabilities);
		}
		return driver;
	}

	public WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
		return new ChromeDriver();
	}
	
	public WebDriver getChromeDriver(DesiredCapabilities capabilities) {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
		return new ChromeDriver(capabilities);
	}
	
	public WebDriver getFirefoxDriver() {
		System.setProperty("webdriver.firefox.marionette","Drivers/geckodriver.exe");
		return new FirefoxDriver();
	}
	
	public WebDriver getFirefoxDriver(DesiredCapabilities capabilities) {
		System.setProperty("webdriver.firefox.marionette","Drivers/geckodriver.exe");
		return new FirefoxDriver(capabilities);
	}
}
