package app.nopcommerce.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper 
{
	
	public WebDriver ldriver;

	public WaitHelper(WebDriver rdriver) 
	{
		
		ldriver = rdriver;
		PageFactory.initElements(ldriver, this);
	}
	
	public void WaitForElement(WebElement element, long timeOutInSeconds) 
	{
		
		WebDriverWait wait = new WebDriverWait(ldriver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	


}
