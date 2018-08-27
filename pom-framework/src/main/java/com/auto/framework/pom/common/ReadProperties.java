package com.auto.framework.pom.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.auto.framework.pom.config.IEnvironment;
import com.auto.framework.pom.logger.LogManager;

public class ReadProperties extends Properties implements IProperties {
	private static final long serialVersionUID = 1L;
	
	protected static ReadProperties instance = new ReadProperties();
	
	public String getEnvironmentFile() {
		return CONFIG_FOLDER +"/"+ ENVIRONMENT_FILE;
	}

	public String getStageFile() {
		return CONFIG_FOLDER +"/"+ STAGE_FILE;
	}
	
	public String getConfigFile() {
		return CONFIG_FOLDER +"/"+ CONFIG_FILE;
	}

	public String getEnvironmentAttributeValue(String attributeName) {
		return getAttributeValue(getEnvironmentFile(), attributeName);
	}
	
	public String getStageFileAttributeValue(String attributeName) {
		return getAttributeValue(getStageFile(), attributeName);
	}
	
	public String getConfigFileAttributeValue(String attributeName) {
		return getAttributeValue(getConfigFile(), attributeName);
	}

	public String getAttributeValue(String file, String attribute) {
		ReadProperties rProp = new ReadProperties();
		FileInputStream input = null;
		String value = null;
		try {
			input = new FileInputStream(new File(file));
			rProp.load(input);
			value = rProp.getProperty(attribute);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public String getConfiguredEnvironmentAttributeValue(String attributeName) {
		String env_name = getEnvironmentAttributeValue(IEnvironment.ENV_NAME);
		LogManager.log(env_name);
		switch(env_name.trim().toLowerCase()){
		case "stage.properties":
			return getStageFileAttributeValue(attributeName);
		}
		return null;
	}
	
	public static ReadProperties getInstance() {
		return instance;
	}
}
