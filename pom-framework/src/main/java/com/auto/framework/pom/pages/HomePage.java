package com.auto.framework.pom.pages;

import org.openqa.selenium.By;

import com.auto.framework.pom.common.pages.BasePage;
import com.auto.framework.pom.controller.Context;
import com.auto.framework.pom.controller.PageClassFactory;
import com.auto.framework.pom.controller.PageObjectManager;
import com.auto.framework.pom.logger.LogManager;

public class HomePage extends BasePage {

	private By loginSignUP = By.xpath("//span[text()='Log In/Sign Up']");
	private By offers = By.xpath("//span[text()='OFFERS']");
	private By app = By.xpath("//span[text()='APP']");
	
	public HomePage(Context context) {
		super(context);
	}

	public void launchHomePage() {
		String mainURL = getBaseUrl();
		LogManager.log(mainURL + PageObjectManager.Home_Page);
		goToUrl(mainURL + PageObjectManager.Home_Page);
	}
	
	public BasePage clickLoginSignUp() {
		clickElement("Login/Sign Up", loginSignUP);
		return PageClassFactory.getCurrentPageinstance(context);
	}
	
	public BasePage clickOffers() {
		clickElement("OFFERS", offers);
		return PageClassFactory.getCurrentPageinstance(context);
	}
	
	public BasePage clickApp() {
		clickElement("App", app);
		return PageClassFactory.getCurrentPageinstance(context);
	}
}
