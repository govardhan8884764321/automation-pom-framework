package com.auto.framework.pom.common.pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.auto.framework.pom.common.ReadProperties;
import com.auto.framework.pom.config.IQAEnvironmentHost;
import com.auto.framework.pom.controller.Context;
import com.auto.framework.pom.controller.PageClassFactory;
import com.auto.framework.pom.logger.LogManager;
import com.google.common.base.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BasePage {

	public static int DEFAULT_WAIT_TIME = 5;
	public static int DEFAULT_PAGE_TIMEOUT = 90;

	protected Context context;
	protected WebDriver driver;
	public String pageUrl;
	public String pageTitle;

	public static final String MEMBER_URL_BASE = "members/";
	public static final String MANAGER_URL_BASE = "managers/";
	public static final String ADMIN_URL_BASE = "admins/";
	public static final String USERS_URL_BASE = "users/";
	public static final String PAGESET_URL_BASE = "tr/pageset/";


	public BasePage(Context context) {
		this(context, DEFAULT_WAIT_TIME);
	}

	public BasePage(Context context,int waitTime) {
		this.context = context;
		this.driver = context.getDriver();
		this.context.setWaitAndATimeout(waitTime, DEFAULT_PAGE_TIMEOUT);
	}

	protected WebElement findElement(By locator) {
		LogManager.log("Trying To Find Element From By Locator " + locator.toString());
		WebElement element = driver.findElement(locator);
		highlightWebElement(element);
		return element;
	}

	protected List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * Select an element if it is not already selected
	 * @param by By Locator
	 * @return false if already selected otherwise true
	 */
	protected boolean selectIfNotChecked(By by) {
		WebElement element = findElement(by);
		if ( !element.isSelected() ) {
			element.click();
			return true;
		}
		return false;
	}
	
	/**
	 * un check an element if it is already selected/checked
	 * @param by By Locator
	 * @return false if already selected otherwise true
	 */
	protected boolean deselectIfChecked(By by) {
		WebElement element = findElement(by);
		if ( element.isSelected() ) {
			element.click();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param locator
	 * @param doYouWantToCheck
	 * @return
	 * 		boolean true/false
	 */
	public boolean checkBoxItem(By locator, boolean doYouWantToCheck){
		if(doYouWantToCheck){
			return selectIfNotChecked(locator);
		} else {
			return deselectIfChecked(locator);
		}
	}

	/**
	 * Return true if BY element is present in DOM
	 * @param by By Locator
	 * @return true if present otherwise false
	 */
	public boolean isPresent(By by) {
		if(by != null)
			return context.getDriver().findElements(by).size() > 0; 
			else {
				logMessage("The locator "+by+" is invalid.");
				return false;
			}
	}

	/**
	 * Return true if BY element is visible in DOM
	 * @param by By Locator
	 * @return true if element visible otherwise false
	 */
	public boolean isVisible(By by) {
		try{
			return context.getDriver().findElement(by).isDisplayed();
		} catch(NoSuchElementException nse){
			return false;
		}
	}

	/**
	 * 
	 * @return Current Page context
	 */
	public Context getContext() {
		return this.context;
	}

	public String getBaseUrl() {
		return ReadProperties.getInstance().getConfiguredEnvironmentAttributeValue(IQAEnvironmentHost.MAIN_URL);
	}
	
	/**
	 * GoToUrl 
	 * @param Url String
	 */
	public void goToUrl(String Url) {
		driver.get(Url);
	}

	/**
	 * Click on Element 
	 * @param locator By Locator
	 */
	public void click(By locator) {
		findElement(locator).click();
	}

	/**
	 * Selects the  Option according to visible text by iterating option by option
	 * @param locator By Locator
	 * @param text Visible text
	 */
	public void selectByVisibleText(By locator,String text, boolean isElementCaseSensitive) {
		if(isElementCaseSensitive){
			LogManager.log("[selectByVisibleText] starting to set "+text);
			try{
				Select dropdown = new Select(findElement(locator));
				List<WebElement> options = dropdown.getOptions();
				for(WebElement option: options){
					String optionText = option.getText();
					if(optionText.trim().equalsIgnoreCase(text)){
						dropdown.selectByVisibleText(optionText);
						break;
					}
				}
				LogManager.log("selecting the visible text "+text+" from "+findElement(locator));
			} catch(NoSuchElementException e){
				captureImage();
				e.printStackTrace();
			}
		} else {
			selectByVisibleText(locator, text);
		}
	}

	/**
	 * Selects the  Option according to visible text
	 * @param locator
	 * @param text
	 */
	public void selectByVisibleText(By locator,String text) {
		LogManager.log("[selectByVisibleText] starting to set "+text);
		Select dropdown = new Select(findElement(locator));
		dropdown.selectByVisibleText(text);
	}
	
	/**
	 * Selects the option based on partial text in the option
	 * @param locator
	 * @param text
	 */
	public void selectByPartialText(By locator, String text) {
		LogManager.log("[selectByPartialText] starting to set " + text);
		Select dropdown = new Select(findElement(locator));
		List<WebElement> options = dropdown.getOptions();
		for (WebElement option : options) {
			if (option.getText().contains(text)) {
				option.click();
				break;
			}
		}
	}

	/**
	 * Selects the Option according to option value
	 * @param locator By Locator
	 * @param text Option value
	 */
	protected void selectByValue(By locator,String text) {
		LogManager.log("[selectByValue] starting to set "+text);
		Select dropdown = new Select(findElement(locator));
		dropdown.selectByValue(text);
	}

	/**
	 * Selects the Radio button according to option value
	 * @param locator By Locator
	 * @param value Option value
	 */
	public void selectRadioByValue(By locator,String value) {
		selectRadioByAttribute(locator,"value",value);
	}

	/**
	 * Selects the Radio button according to any attribute(value,name)
	 * @param locator By Locator
	 * @param value value of attribute
	 */
	public void selectRadioByAttribute(By locator,String attribute,String value) {
		LogManager.log("[selectRadioByAttribute] starting to set "+value);
		final List<WebElement> radios = findElements(locator);
		for (WebElement radio : radios) {
			if (radio.getAttribute(attribute).equals(value)) {
				radio.click();
				logMessage("radio button is clicked");
				break;
			}
		}
	}

	/**
	 * returns the text of first selected option
	 * @param locator
	 * @return
	 */
	public String getDropDownText(By locator) {
		LogManager.log("[getDropDownText] starting to get frist selected dropdown item from select item");
		Select dropdown = new Select(findElement(locator));
		LogManager.log("[getDropDownText] ending...");
		return dropdown.getFirstSelectedOption().getText();
	}

	public void submit(By locator) {
		findElement(locator).submit();
	}

	/**
	 * 
	 * @return Current WebDriver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}

	/**
	 * 
	 * @param locator By locator
	 * @return return text string if present
	 */
	public String getText(By locator) {
		if(isPresent(locator))
			return  findElement(locator).getText();
		else return "";
	}

	/**
	 * 
	 * @param locator By locator
	 * @return return text string if present
	 */
	public String getText(WebElement webElement) {
		if(isPresent(webElement))
			return  webElement.getText();
		else return "";
	}

	/**
	 * Click on element if it is present
	 * @param locator
	 */
	public void clickIfPresent(By locator) {
		if(isPresent(locator))
			click(locator);
	}

	public String getAttribute(By locator,String attribute) {
		return findElement(locator).getAttribute(attribute);
	}

	/**
	 * Run javaScript on page
	 * @param script scriptString
	 * @return
	 */
	public Object runScript(String script) {
		return ((JavascriptExecutor) driver).executeScript(script);
	}

	/**
	 * Executes a script on an element
	 * @note Really should only be used when the web driver is sucking at exposing
	 * functionality natively
	 * @param script The script to execute
	 * @param element The target of the script, referenced as arguments[0]
	 */
	public void runScript(String script, WebElement element) {
		((JavascriptExecutor)driver).executeScript(script, element);
	}

	public void Type(By locator, String text) { 
		WebElement element = findElement(locator);
		element.sendKeys(text);
	}
	/** 
	 * Type something into an input field. WebDriver doesn't normally clear these
	 * before typing, so this method does that first. It also sends a return key
	 * to move the focus out of the element.
	 */
	public void clearAndType(By locator, String text) {
		WebElement element = findElement(locator);
		clearAndType(element,text);
	}

	/**
	 * Clear textBox and enter String 
	 * @param element
	 * @param text
	 */
	public void clearAndType(WebElement element,String text) {
        element.clear();
        element.sendKeys(text);
        LogManager.log("The text "+text+" is set to "+element);
    }
	
	/**
	 * 
	 * @return currentUrl String
	 */
	public String getCurrentUrl() {
		LogManager.log("[getCurrentUrl] starting...");
		return driver.getCurrentUrl();
	}
	

	/**
	 * 
	 * @param locator By Locator
	 * @return WebElement corresponding to By Locator with 1 second polling upTo second 
	 */
	public WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return findElement(locator);
			}
		});
		return  foo;
	};

	/**
	 * returns the boolean value true if the text is found in page source else returns false
	 * @param text
	 * @return
	 */
	public boolean VerifyText(String... text_values) {
		String pageSource = driver.getPageSource();
		boolean isExists = true;
		for(String text: text_values){
			if(!pageSource.contains(text)){
				LogManager.log("The text '"+text+"' is not found in DOM.");
				isExists = false;
				break;
			}
		}
		return isExists;
	}	

	/**
	 * returns the page source of the page
	 * @return
	 */
	public String getPageSource(){
		return driver.getPageSource();
	}

	/**
	 * returns the parent window handle
	 * @return
	 */
	public String getParentWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * switch to new window
	 * @param parentWindow
	 */
	public void switchToNewWindow(String parentWindow ) {
		Set<String> handles =  driver.getWindowHandles();
		for(String windowHandle  : handles) {
			if(!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
			}
		}

	}

	/**
	 * switches to parent window
	 */
	public void switchToParentWindow(){
		Set<String> handles =  driver.getWindowHandles();
		int counterFlag = 0;
		for(String windowHandle  : handles) {
			if(++counterFlag == 1){
				wait_(1000);
				driver.switchTo().window(windowHandle);
				break;
			}
		}
	}

	/**
	 * closes the child window
	 * @param parentWindow
	 */
	public void closeChildWindow(String parentWindow){
		driver.close(); 
		driver.switchTo().window(parentWindow); 
	}

	public boolean isRadioButtonSelected( By locator ){
		return findElement(locator).isSelected();
	}

	public BasePage acceptAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 8);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			LogManager.log(""+e.getMessage());
		}
		return PageClassFactory.getCurrentPageinstance(context);
	}
	
	public void dismissAlert(){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 8);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			LogManager.log(""+e.getMessage());
		}
	}

	public void wait_(long timeoutinMili){
		try {
			Thread.sleep(timeoutinMili);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * confirms the VAT by clicking Continue button
	 * @param doYouWantToAcceptVAT
	 */
	public void confirmVAT(boolean doYouWantToAcceptVAT){
		logMessage("[confirmVAT] starting...");
		if(isVisible(By.id("vatInputForm"))){
			if(doYouWantToAcceptVAT){

			} else {
				clickSubmitButton("Continue", By.id("submit"));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			logMessage("VAT page is not found.");
		}
	} 

	/**
	 * clicks on the element whose property is WebElement
	 * @param eleName
	 * @param element
	 */
	public void clickElement(String eleName, WebElement element){
		if(element == null){
			logFail("element '"+eleName+"' is not valid.");
		} else {
			if(isPresent(element)){
				if(isEnabled(element)){
					element.click();
					wait_(1000);
				} else {
					logFail("Given element '"+eleName+"' is disabled.");
				}
			} else {
				logFail("Given element "+eleName+" is not present in DOM structure.");
			}
		}
	} 

	/**
	 * clicks on the element whose property is locator
	 * @param eleName
	 * @param locator
	 */
	public void clickElement(String eleName, By locator){
		if(locator == null){
			logFail("element '"+eleName+"' is invalid.");
		} else {
			if(isPresent(locator)){
				if(isEnabled(locator)){
					findElement(locator).click();
				} else {
					logFail("Given element '"+eleName+"' is disabled.");
				}
			} else {
				logFail("Given element '"+eleName+"' is not present in DOM structure.");
			}
		}
	} 



	/**
	 * clicks the link whose property is WebElement
	 * @param linkName
	 * @param linkText
	 */
	public void clickLink(String linkName, WebElement linkText){
		if(linkText == null){
			logFail("The link '"+linkName+"' is not valid.");
		} else {
			if(isPresent(linkText)){
				if(isEnabled(linkText)){
					linkText.click();
					logPass("The link '"+linkName+"' is found and clicked.");
				} else {
					logFail("Expected link '"+linkText+"' is disabled.");
				}
			} else {
				logFail("Given link '"+linkText+"' is not present in DOM structure.");
			}
		}
	} 

	/**
	 * clicks the link whose property is locator
	 * @param linkName
	 * @param locator
	 */
	public void clickLink(String linkName, By locator){
		if(locator == null){
			logFail("The link '"+linkName+"' is not valid.");
		} else {
			clickLink(linkName, findElement(locator));
		}
	} 

	/**
	 * clicks the submit button whose property is WebElement
	 * @param buttonName
	 * @param element
	 */
	public void clickSubmitButton(String buttonName, WebElement element){
		if(element == null){
			logFail("The button '"+buttonName+"' is invalid.");
		} else {
			if(isPresent(element)){
				if(isEnabled(element)){
					element.submit();
					logPass("The submit button '"+buttonName+"' is found and clicked.");
				} else {
					logFail("Expected submit button '"+buttonName+"' is disabled.");
				}
			} else {
				logFail("Expected submit button '"+buttonName+"' is not present in DOM structure.");
			}
		}
	}

	/**
	 * clicks on the submit button whose property is locator
	 * @param buttonName
	 * @param locator
	 */
	public void clickSubmitButton(String buttonName, By locator){
		if(locator == null){
			logFail("The button '"+buttonName+"' is invalid.");
		} else {
			clickSubmitButton(buttonName, findElement(locator));
		}
	}

	/**
	 * set the value in given WebElement
	 * @param element
	 * @param value
	 */
	public void setTextBox(WebElement element, String value){
		if(element == null){
			logFail("Given element '"+element+"' is invalid.");
		} else {
			if(isPresent(element)){
				if(isEnabled(element)){
					element.clear();
					element.sendKeys(value);
					logPass("The value "+value+" is set to '"+element+"'");
				} else {
					logFail("Given element '"+element+"' is disabled.");
				}
			} else {
				logFail("Given element '"+element+"' is not present in the DOM structure.");
			}
		}
	} 

	/**
	 * set the value in given WebElement
	 * @param fieldName
	 * @param element
	 * @param value
	 */
	public void setTextBox(String fieldName, WebElement element, String value){
		if(element == null){
			logFail("Given element '"+fieldName+"' is invalid.");
		} else {
			if(isPresent(element)){
				if(isEnabled(element)){
					element.clear();
					element.sendKeys(value);
					logPass("The value "+value+" is set to '"+fieldName+"'");
				} else {
					logFail("Given element '"+fieldName+"' is disabled.");
				}
			} else {
				logFail("Given element '"+fieldName+"' is not present in the DOM structure.");
			}
		}
	} 

	/**
	 * sets the value in given locator
	 * @param locator
	 * @param value
	 */
	public void setTextBox(By locator, String value){
		if(locator == null){
			logFail("Given locator '"+locator+"' is invalid.");
		}else {
			setTextBox(findElement(locator), value);
		}
	} 

	/**
	 * sets the value in given locator
	 * @param fieldName
	 * @param locator
	 * @param value
	 */
	public void setTextBox(String fieldName, By locator, String value){
		if(locator == null){
			logFail("Given element '"+fieldName+"' is invalid.");
		} else {
			setTextBox(fieldName, findElement(locator), value);
		}
	} 

	/**
	 * returns the boolean value true if text found against locator otherwise returns false
	 * @param textFieldName
	 * @param locator
	 * @param expectedText
	 * @return
	 * 		boolean
	 */
	public boolean verifyTextBoxValue(String textFieldName, By locator, String expectedText){
		LogManager.log("[verifyTextBoxValue] returns the boolean value true if text found against locator otherwise returns false.");
		String actualText = context.getDriver().findElement(locator).getAttribute("value");
		if(actualText.equals(expectedText)){
			logPass("Both actual '"+actualText+"' and expected '"+expectedText+"' text are matching.");
			return true;
		} else {
			logFail("Both actual '"+actualText+"' and expected '"+expectedText+"' text are not matching.");
			return false;
		}
	}

	/**
	 * switches to another frame with reference to given locator
	 * @param locator
	 */
	public void switchToFrame(By locator){
		driver.switchTo().frame(driver.findElement(locator));
	} 

	/**
	 * sets the implicit wait in seconds
	 * @param inSeconds
	 */
	public void implicitWait(int inSeconds){
		driver.manage().timeouts().implicitlyWait(inSeconds, TimeUnit.SECONDS);
	} 

	/**
	 * waits the execution control until desired locator is found
	 * @param locator
	 * @param seconds
	 */
	public void waitUntilElementPresent(By locator, int seconds){
		WebDriverWait implicitWait = (new WebDriverWait(driver, seconds));
		WebElement element = implicitWait.until(ExpectedConditions.presenceOfElementLocated(locator));
		logMessage(""+element);
		if(element == null){
			logMessage("Given locator "+locator+" is not found.");
		}
	} 

	/**
	 * captures the image
	 */
	public void captureImage() {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("c:\\temp\\screenshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns boolean value based on the WebElement availability in the DOM structure
	 * @param element
	 * @return
	 */
	public boolean isPresent(WebElement element){
		try {
			return element.isDisplayed(); 
		} catch(NoSuchElementException e){
			return false;
		}
	} 

	/**
	 * @Description
	 * 		This method reveals that whether given locator is editable or not
	 * @param element
	 * @return
	 * 		boolean
	 */
	public boolean isEnabled(WebElement element){
		try {
			if(element.isEnabled())
				return true;
			return false;
		} catch(NoSuchElementException e){
			return false;
		}
	} 

	/**
	 * @Description
	 * 		This method reveals that whether given locator is editable or not
	 * @param locator
	 * @return
	 * 		boolean
	 */
	public boolean isEnabled(By locator){
		if(locator != null){
			return isEnabled(findElement(locator));
		} 
		return false;
	} 

	/**
	 * @Description
	 * 		This method logs the fail/error message
	 * @param failMessage
	 * @return
	 * 		nothing
	 */
	public void logFail(String failMessage){
		captureImage();
		LogManager.log("Fail: "+failMessage);
	} 

	/**
	 * @Description
	 * 		This method logs the success message
	 * @param passMessage
	 * @return
	 * 		nothing
	 */
	public void logPass(String passMessage){
		LogManager.log("Pass: "+passMessage);

	}

	/**
	 * @Description
	 * 		This method logs the casual message
	 * @param casualMessage
	 * @return
	 * 		Nothing
	 */
	public void logMessage(String casualMessage){
		LogManager.log(casualMessage);
	} 

	/**
	 * get the parent element of an element with given locator
	 * @param loactor
	 * @return
	 */
	public WebElement getParentElement(By loactor){
		WebElement element = findElement(loactor);
		return getParentElement(element);
	}

	/**
	 * get the Parent element of an element
	 * @param element
	 * @return
	 */
	public WebElement getParentElement(WebElement element){
		WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].parentNode;", element);
		return parent;
	}

	/**
	 * Wait for an alert with timeout
	 * @param timeout
	 * @return
	 */
	protected Alert waitforAlert(int timeout){
		WebDriverWait wait = new WebDriverWait(driver,timeout);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		return alert;
	}

	/**
	 * returns whether given text is present in the page or not
	 * @param text
	 * @return
	 */
	public boolean isTextPresent(String text){
		String xpath = "//*[contains(text(),'"+text+"')]";
		List<WebElement> list = driver.findElements(By.xpath(xpath));
		if(list.size()>0){
			return true;
		}
		else{
			return false;
		}

	}

	/**
	 * Returns the options present in Select
	 * @param locator By Locator
	 * @return List<WebElement>: list of options from Select
	 */
	protected List<WebElement> getOptionsFromSelect(By locator) {
		LogManager.log("[getOptions] starting to get all the options from Select");
		Select dropdown = new Select(findElement(locator));
		return dropdown.getOptions();
	}

	/**
	 * returns all the option values present in select as a list
	 * @param locator
	 * @return
	 */
	protected List<String> getVisibleValuesFromSelect(By locator) {
		Select dropdown = new Select(findElement(locator));
		ArrayList<String> list = new ArrayList<String>();
		for(WebElement element: dropdown.getOptions()){
			list.add(element.getText());
		}
		return list;
	}
	
	/**
	 * get title of the page
	 * @return
	 */
	public String getPageTitle(){
		return driver.getTitle();
	}

	public WebElement findElement(By locator, String attribute, String attributeValue){
		List<WebElement> webElements = driver.findElements(locator);
		for(int i= 0; i<webElements.size(); i++){
			if(webElements.get(i).getAttribute(attribute).equals(attributeValue)){
				return webElements.get(i);
			}
		}
		return null;
	}
	

	/**
	 * maximizes the window
	 */
	public void maximizeWindow(){
		context.getDriver().manage().window().maximize();
		wait_(1000);
	}

	/**
	 * get text contained by the given element at its level only
	 * Does not works if text in one of the child element is substring of absolute text
	 * @param locator
	 * @return
	 */
	public String getAbsoluteText(By locator){
		WebElement element = findElement(locator);
		String fullText = element.getText();

		List<WebElement> childList = element.findElements(By.xpath("*"));
		for(int i = 0; i<childList.size(); i++ ){
			String text = childList.get(i).getText();
			fullText = fullText.replace(text, "");
		}
		return fullText;
	}

	/**
	 * executes the java script
	 * @param string
	 * @param arguments
	 * @return
	 * 		Object
	 */
	public Object executeScript(String string, Object... arguments) {
		Object obj = null;
		try {
			obj = ((JavascriptExecutor) context.getDriver()).executeScript(string, arguments);
		} catch(StaleElementReferenceException staleException){
			
		}
		return obj;
	}

	/**
	 * Set an attribute in the HTML of a page.
	 * @param element
	 *          The WebElement to modify
	 * @param attributeName
	 *          The attribute to modify
	 * @param value
	 *          The value to set
	 */
	public void setAttribute(WebElement element, String attributeName, String value) {
		executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, value);
	}

	/**
	 * Highlight an element in the UI.
	 * @param element
	 *          The WebElement to highlight
	 */
	private void highlightWebElement(WebElement element) {
		setAttribute(element, "style", "border: 2px solid blue; border-radius: 10px;");
	}
	
	protected String getBodyText(){
		WebElement body = context.getDriver().findElement(By.tagName("body"));
		return body.getText();
	}
	
	/**
	 * verify if the pattern is present in the page
	 * @param pattern
	 * @return
	 */
	public boolean verifyRegex(String pattern){
		String pageSource = driver.getPageSource();
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(pageSource);
		if(m.find())
			return true;
		return false;
	}
	
	/**
	 * Verify a regex, applying the Pattern flags when compiling the pattern
	 * @param pattern
	 * @param flags
	 * @return
	 */
	public boolean verifyRegex(String pattern, int flags){
		String pageSource = driver.getPageSource();
		Pattern r = Pattern.compile(pattern,flags);
		Matcher m = r.matcher(pageSource);
		if(m.find())
			return true;
		return false;
	}
	
	/**
	 * right click on element
	 * @param element
	 */
	public void rightClickOnElement(By element){
		Actions action = new Actions(context.getDriver()). contextClick(findElement(element));
		action.build().perform();
	}
	
	/**
	 * right click on element
	 * @param element
	 */
	public void rightClickOnElement(WebElement element){
		Actions action = new Actions(context.getDriver()). contextClick(element);
		action.build().perform();
	}
}
