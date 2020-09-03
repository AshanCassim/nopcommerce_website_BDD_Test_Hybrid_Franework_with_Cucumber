package app.nopcommerce.stepDefinitions;


import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import app.nopcommerce.pageObjects.AddcustomerPage;
import app.nopcommerce.pageObjects.LoginPage;
import app.nopcommerce.pageObjects.SearchCustomerPage;


public class BaseClass {

	public WebDriver driver;
	public LoginPage lp;
	public AddcustomerPage addCust;
	public SearchCustomerPage searchCust;
	public static Logger logger;
	public Properties configProp;
	
	
	
	//Created for generating random string for Unique email
	public static String randomestring(){
		String generatedString1 = RandomStringUtils.randomAlphabetic(5);
		return (generatedString1);
	}
	
	
	
}

