package com.auto.framework.pom.pom_framework.testing;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.auto.framework.pom.TestBase;
import com.auto.framework.pom.logger.LogManager;
import com.auto.framework.pom.pages.AppPage;
import com.auto.framework.pom.pages.HomePage;
import com.auto.framework.pom.pages.OffersPage;
import com.auto.framework.pom.pages.UserLoginPage;

public class PlayWithPagesTest extends TestBase {

	HomePage homePage ;
	
	@BeforeMethod
	public void setUp() {
		context = getDefaultContext();
		homePage = new HomePage(context);
	}
	
	@Test
	public void playWithPagesTest() {
		LogManager.log("Launching the home page");
		homePage.launchHomePage();
		LogManager.log("clicking Login/Sign Up");
		UserLoginPage userLoginPage = (UserLoginPage) homePage.clickLoginSignUp();
		LogManager.log("clicking OFFERS");
		OffersPage offersPage = (OffersPage) userLoginPage.clickOffers();
		LogManager.log("clicking App");
		AppPage app = (AppPage) offersPage.clickApp();
		LogManager.log("clicking again Login/Sign Up");
		userLoginPage = (UserLoginPage) app.clickLoginSignUp();
	}
	
	
	@Test
	public void playWithPagesTest2() {
		LogManager.log("Launching the home page");
		homePage.launchHomePage();
		LogManager.log("clicking Login/Sign Up");
		UserLoginPage userLoginPage = (UserLoginPage) homePage.clickLoginSignUp();
		LogManager.log("clicking OFFERS");
		OffersPage offersPage = (OffersPage) userLoginPage.clickOffers();
		LogManager.log("clicking App");
		AppPage app = (AppPage) offersPage.clickApp();
		LogManager.log("clicking again Login/Sign Up");
		userLoginPage = (UserLoginPage) app.clickLoginSignUp();
	}
	
	@Test
	public void playWithPagesTest3() {
		LogManager.log("Launching the home page");
		homePage.launchHomePage();
		LogManager.log("clicking Login/Sign Up");
		UserLoginPage userLoginPage = (UserLoginPage) homePage.clickLoginSignUp();
		LogManager.log("clicking OFFERS");
		OffersPage offersPage = (OffersPage) userLoginPage.clickOffers();
		LogManager.log("clicking App");
		AppPage app = (AppPage) offersPage.clickApp();
		LogManager.log("clicking again Login/Sign Up");
		userLoginPage = (UserLoginPage) app.clickLoginSignUp();
	}
	
	@Test
	public void playWithPagesTest4() {
		LogManager.log("Launching the home page");
		homePage.launchHomePage();
		LogManager.log("clicking Login/Sign Up");
		UserLoginPage userLoginPage = (UserLoginPage) homePage.clickLoginSignUp();
		LogManager.log("clicking OFFERS");
		OffersPage offersPage = (OffersPage) userLoginPage.clickOffers();
		LogManager.log("clicking App");
		AppPage app = (AppPage) offersPage.clickApp();
		LogManager.log("clicking again Login/Sign Up");
		userLoginPage = (UserLoginPage) app.clickLoginSignUp();
	}
	
	@Test (dependsOnMethods = {"playWithPagesTest4"})
	public void playWithPagesTest5() {
		LogManager.log("Launching the home page");
		homePage.launchHomePage();
		LogManager.log("clicking Login/Sign Up");
		UserLoginPage userLoginPage = (UserLoginPage) homePage.clickLoginSignUp();
		LogManager.log("clicking OFFERS");
		OffersPage offersPage = (OffersPage) userLoginPage.clickOffers();
		LogManager.log("clicking App");
		AppPage app = (AppPage) offersPage.clickApp();
		LogManager.log("clicking again Login/Sign Up");
		userLoginPage = (UserLoginPage) app.clickLoginSignUp();
	}
	
	@AfterMethod
	public void tearDown() {
		quit();
	}
}
