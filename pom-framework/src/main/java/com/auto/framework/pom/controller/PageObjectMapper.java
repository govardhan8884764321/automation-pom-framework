package com.auto.framework.pom.controller;

import com.auto.framework.pom.pages.AppPage;
import com.auto.framework.pom.pages.HomePage;
import com.auto.framework.pom.pages.OffersPage;
import com.auto.framework.pom.pages.UserLoginPage;

public class PageObjectMapper extends PageObjectManager {
	
	public PageObjectMapper() {
		objects.put(Home_Page, HomePage.class);
		objects.put(User_Login_Page, UserLoginPage.class);
		objects.put(Offers_Page, OffersPage.class);
		objects.put(App_Page, AppPage.class);
	}
	
}
