package com.auto.framework.pom.controller;

import java.util.HashMap;
import java.util.Map;

import com.auto.framework.pom.common.ReadProperties;
import com.auto.framework.pom.config.IQAEnvironmentHost;
import com.auto.framework.pom.config.IStage;

public class PageObjectManager {

	public static final String MainURL = ReadProperties.getInstance().getConfiguredEnvironmentAttributeValue(IQAEnvironmentHost.MAIN_URL);
	
	public static final String Home_Page = "/Movie-Ticket-Online-booking/C/Bangalore";
	public static final String User_Login_Page = "/OnlineTheatre/Theatre/UserLogin.aspx";
	public static final String Offers_Page = "/Movie-offers-Tickets-Online-Booking-Show-Timings/latest-offers/Offer_Section_new";
	public static final String App_Page = "/download_application/latest-offers/mobile_application";
	
	public static boolean isPageExists(Context context, String pageRef) {
		String currentURL = context.getDriver().getCurrentUrl();
		if(currentURL.contains(MainURL + pageRef)) {
			return true;
		}
		return false;
	}
	
	static Map<String, Object> objects = new HashMap<String, Object>();
}
