package app.nopcommerce.stepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import app.nopcommerce.pageObjects.AddcustomerPage;
import app.nopcommerce.pageObjects.LoginPage;
import app.nopcommerce.pageObjects.SearchCustomerPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;


public class Steps extends BaseClass{
	
	@Before
	public void setup() throws IOException, InterruptedException {
		
		logger = LogManager.getLogger(this.getClass());    // Log4j2
		
		//Reading Prop File
		configProp = new Properties();
		FileInputStream configProFIle = new FileInputStream("config.properties"); 
		configProp.load(configProFIle);
		
		String br = configProp.getProperty("browser");
		
		if(br.equals("chrome"))
		{
			logger.info("********* starting TC on chrome*************");
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
				
		}
		else if(br.equals("firefox"))
		{
			logger.info("********* starting TC on firefox*************");
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		else if(br.equals("ie"))
		{
			logger.info("********* starting TC on ie*************");
			//WebDriverManager.iedriver().setup();//Not working
			System.setProperty("webdriver.ie.driver",configProp.getProperty("iepath"));
			driver=new InternetExplorerDriver();
			Thread.sleep(3000);
		}
		else if(br.equals("edge"))
		{
			logger.info("********* starting TC on edge*************");
			//WebDriverManager.edgedriver().setup(); //Not working
			System.setProperty("webdriver.edge.driver",configProp.getProperty("edgepath"));
			driver = new EdgeDriver();
		}
			
			
	}
	
	
	//Login steps....................
	
	
	@Given("User Launch Chrome browser")
	public void user_Launch_Chrome_browser() {
		
		logger.info("************* Launching Browser *****************");
		
		lp=new LoginPage(driver);
	}

	@When("User opens URL {string}")
	public void user_opens_URL(String url) {
		
		logger.info("************* Opening URL  *****************");
		driver.get(url);
		driver.manage().window().maximize();
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_Email_as_and_Password_as(String email, String password) {
		
		logger.info("************* Providing user and password *****************");
		lp.setUserName(email);
		lp.setPassword(password);
	}

	@When("Click on Login")
	public void click_on_Login() {
		
		logger.info("************* click on login *****************");
		lp.clickLogin();
	}

	@Then("Page Title should be {string}")
	public void page_Title_should_be(String exptitle) throws InterruptedException {
	    
		if(driver.getPageSource().contains("Login was unsuccessful"))
		{
			logger.info("************* Login failed *****************");
			driver.close();
			Assert.assertTrue(false);
		}
		else
		{
			logger.info("************* Login Passed *****************");
			Assert.assertEquals(exptitle, driver.getTitle());
		}
		Thread.sleep(3000);
		
	}

	@When("User click on Log out link")
	public void user_click_on_Log_out_link() throws InterruptedException {
		
		logger.info("************* clicking on logout *****************");
		lp.clickLogout();
	    Thread.sleep(3000);
	}

	@Then("close browser")
	public void close_browser() {
		logger.info("************* closing browser *****************");
		driver.quit();
	}
	
	//Customer feature step definitions..........................................
	
	@Then("User can view Dashboad")
	public void user_can_view_Dashboad() {
	  
		addCust=new AddcustomerPage(driver);
		logger.info("********* Verifying Dash board page title after login successful **************");
		Assert.assertEquals("Dashboard / nopCommerce administration",addCust.getPageTitle());
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_Menu() throws InterruptedException {
		Thread.sleep(3000);
		logger.info("********* Clicking on customer main menu **************");
		addCust.clickOnCustomersMenu();
	}

	@When("click on customers Menu Item")
	public void click_on_customers_Menu_Item() throws InterruptedException {
		Thread.sleep(2000);
		logger.info("********* Clicking on customer sub menu **************");
		addCust.clickOnCustomersMenuItem();
	}

	@When("click on Add new button")
	public void click_on_Add_new_button() throws InterruptedException {
		  addCust.clickOnAddnew();
		  Thread.sleep(3000);
	}

	@Then("User can view Add new customer page")
	public void user_can_view_Add_new_customer_page() {
		 Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
	}

	@When("User enter customer info")
	public void user_enter_customer_info() throws InterruptedException {
		
		String email = randomestring() + "@gmail.com";
		addCust.setEmail(email);
		
		addCust.setPassword("test123");
		addCust.setFirstName("Ashan");
		addCust.setLastName("Shan");
		addCust.setGender("Male");
		addCust.setDob("8/08/1994"); // Format: D/MM/YYY
		addCust.setCompanyName("LTRL");
		
		// Registered - default
		// The customer cannot be in both 'Guests' and 'Registered' customer roles
		// Add the customer to 'Guests' or 'Registered' customer role
		addCust.setCustomerRoles("Guest");
		Thread.sleep(3000);

		addCust.setManagerOfVendor("Vendor 2");
		addCust.setAdminComment("This is for testing hee.........");
	}

	@When("click on Save button")
	public void click_on_Save_button() throws InterruptedException {
		
		logger.info("********* Saving customer details **************");   
		addCust.clickOnSave();
		Thread.sleep(2000);
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String string) {
		Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
				.contains("The new customer has been added successfully"));
	}

	
	
	//Searching customer by email ID.............................

	@When("Enter customer EMail")
	public void enter_customer_EMail() {
		
		searchCust=new SearchCustomerPage(driver);
		logger.info("********* Searching customer details by Email **************");
		searchCust.setEmail("james_pan@nopCommerce.com");
	}

	@When("Click on search button")
	public void click_on_search_button() throws InterruptedException {
		
		searchCust.clickSearch();
		Thread.sleep(3000);
	}

	@Then("User should found Email in the Search table")
	public void user_should_found_Email_in_the_Search_table() {
		
		boolean status=searchCust.searchCustomerByEmail("james_pan@nopCommerce.com");
		Assert.assertEquals(true, status);
	}
	
	
	
	//steps for searching a customer by Name................
	
	
	@When("Enter customer FirstName")
	public void enter_customer_FirstName() {
		logger.info("********* Searching customer details by Name **************");
		searchCust=new SearchCustomerPage(driver);
		searchCust.setFirstName("Victoria");
	}

	@When("Enter customer LastName")
	public void enter_customer_LastName() {
		searchCust.setLastName("Terces");
	}

	@Then("User should found Name in the Search table")
	public void user_should_found_Name_in_the_Search_table() {
		
		boolean status=searchCust.searchCustomerByName("Victoria Terces");
		
		if(status == false)
		{
			logger.info("************* Login failed *****************");
			driver.close();
			Assert.assertTrue(false);
		}
		else
		{
			logger.info("************* Login Passed *****************");
			Assert.assertEquals(true, status);
		}
		
		
		
	}
	
}
