package com.auto.framework.pom.controller;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.auto.framework.pom.driver.WebDriverManager;
import com.auto.framework.pom.logger.LogManager;

public class Context {
	
	private WebDriver webdriver;
	private LogManager logManager;  
	
	public int objectLoadTime = 5;
	public int pageLoadTime = 90;
    
    public Context(String driverName) {
    	this.webdriver = new WebDriverManager().getDriver(driverName);
    	setWaitAndATimeout(objectLoadTime, pageLoadTime);
    	this.logManager = new LogManager();
	}
	
	public Context(String driverName, DesiredCapabilities capabilities) {
		this.webdriver = new WebDriverManager().getDriver(driverName, capabilities);
		setWaitAndATimeout(objectLoadTime, pageLoadTime);
		logManager = new LogManager();
	}
	
	public WebDriver getDriver() {
		return webdriver;
	}
	
    public LogManager getLogManager() {
		return logManager;
	}
    
	public void setWaitAndATimeout(int waitTime,int pageLoadTime) {
		webdriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}
}

