package com.auto.framework.pom.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.auto.framework.pom.common.pages.BasePage;
import com.auto.framework.pom.logger.LogManager;

public class PageClassFactory  {

	public static BasePage getCurrentPageinstance(Context context) {
		PageObjectMapper mapper = new PageObjectMapper();
		String pageRef =  context.getDriver().getCurrentUrl().split(mapper.MainURL)[1];
		//LogManager.log("MainURL : " +mapper.MainURL+ "\npageRef : "+pageRef);
		Set<String> keys = mapper.objects.keySet();
		for(String key: keys) {
			if(pageRef.startsWith(key)) {
				pageRef = key;
				break;
			}
		}
		if(mapper.isPageExists(context, pageRef)) {
			Class classObj = (Class) mapper.objects.get(pageRef);
			Constructor con;
			try {
				con = classObj.getConstructor(Context.class);
				return (BasePage) con.newInstance(context);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
}
