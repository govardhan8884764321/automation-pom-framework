package com.auto.framework.pom.pom_framework.testing;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.auto.framework.pom.TestBase;
import com.auto.framework.pom.logger.LogManager;

public class OpenBrowserAndQuitTest extends TestBase {
	
	@BeforeMethod
	public void setUp() {
		LogManager.log("from setUp");
		context =  getDefaultContext();
	}
	
	@Test
	public void testing() {
		LogManager.log("from testing");
	}
	
	@AfterMethod
	public void tearDown() {
		quit();
	}
}
