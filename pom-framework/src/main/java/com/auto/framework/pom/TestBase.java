package com.auto.framework.pom;

import com.auto.framework.pom.common.ReadProperties;
import com.auto.framework.pom.config.IConfig;
import com.auto.framework.pom.controller.Context;
import com.auto.framework.pom.logger.LogManager;

public class TestBase {

	ReadProperties prop = ReadProperties.getInstance();
	public Context context;
	
	public Context getDefaultContext() {
		context = new Context(prop.getConfigFileAttributeValue(IConfig.DRIVER_TYPE));
		LogManager.log("Default web driver is created successfully!");
		return context;
	}
	
	public void quit() {
		if(context != null) {
			LogManager.log("Quiting the web driver");
			context.getDriver().quit();
		}
	}
}
