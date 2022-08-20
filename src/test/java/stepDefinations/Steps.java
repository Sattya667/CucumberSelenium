package stepDefinations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.apache.commons.lang3.RandomStringUtils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import objectRepo.AddcustomerPage;
import objectRepo.LoginPage;
import objectRepo.SearchCustomerPage;
import utils.Configuration;
import utils.Helper;

public class Steps {

    public WebDriver driver;
    public LoginPage lp;
    public AddcustomerPage addCust;
	public SearchCustomerPage searchCust;
    public static String env;
    public static String browser;
    public static String app;

    @Before
    public void setup() throws IOException
    {


        //String br=configProp.getProperty("browser");
	
	  String br="chrome";
	  
        if (br.equals("firefox")) {
           
            driver = new FirefoxDriver();
        }

        else if (br.equals("chrome")) {
        	WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        else if (br.equals("ie")) {
            
            driver = new InternetExplorerDriver();
        }

    }
    @AfterStep
    public void takeScreenShot(Scenario scenario){
        byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"image/png","aaa");
    }
	
	@After(order=0)
	public void quitBrowser() {
		driver.quit();
	}

    @Given("^user uses following environment details$")
    public void setEnvVar(DataTable values){
        Map<String,String> envValues = values.asMap(String.class,String.class);
        env = envValues.get("Environment");
        browser = envValues.get("Browser");
        app = envValues.get("Application");
    }
		
	
	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {
		lp = new LoginPage(driver);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		driver.get(url);
	    
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String password) {
		lp.setUserName(email);
		lp.setPassword(password);
		

	}

	@When("Click on Login")
	public void click_on_login() throws InterruptedException {
		lp.clickLogin();
		Thread.sleep(5000);
	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String expTitle) {
		
		if (driver.getPageSource().contains("Login was unsuccessful")) {
			driver.close();
			Assert.assertTrue(false);
		}
		else {
			Assert.assertEquals(expTitle, driver.getTitle());
		}
		
	}

	@When("User click on Log out link")
	public void user_click_on_log_out_link() {
		lp.clickLogout();

	}

	@Then("close browser")
	public void close_browser() {
		driver.quit();
	}
	
	@Then("User can view Dashboad")
    public void user_can_view_Dashboad() {

//        logger.info("********* Adding Customer Scenario started  ***************");
        addCust=new AddcustomerPage(driver);
//        logger.info("********* Dashboard Display validation ***************");
        Assert.assertEquals("Dashboard / nopCommerce administration",addCust.getPageTitle());

    }

    @When("User click on customers Menu")
    public void user_click_on_customers_Menu() throws InterruptedException
    {
        Thread.sleep(3000);
        addCust.clickOnCustomersMenu();
    }

    @When("click on customers Menu Item")
    public void click_on_customers_Menu_Item() throws InterruptedException
    {
        Thread.sleep(2000);
        addCust.clickOnCustomersMenuItem();
    }

    @When("click on Add new button")
    public void click_on_Add_new_button() {
        addCust.clickOnAddnew();
    }

    @Then("User can view Add new customer page")
    public void user_can_view_Add_new_customer_page() {
        Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
    }

    @When("User enter customer info")
    public void user_enter_customer_info() throws InterruptedException
    {

        String email = RandomStringUtils.randomAlphabetic(5) + "@gmail.com";
//        String email ="abcd@gmail.com";
        addCust.setEmail(email);
        System.out.println("Password is : ### "+Configuration.getConfig("testData."+env+"."+app+".Password") );
        addCust.setPassword(Configuration.getConfig("testData."+env+"."+app+".Password"));

        Thread.sleep(9000);
        // Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        System.out.println("Role is####### "+Configuration.getConfig("testData."+env+"."+app+".CustomerRoles") );

        addCust.setCustomerRoles(Configuration.getConfig("testData."+env+"."+app+".CustomerRoles"));
        Thread.sleep(3000);

        addCust.setManagerOfVendor(Configuration.getConfig("testData."+env+"."+app+".ManagerOfVendor"));
        addCust.setGender(Configuration.getConfig("testData."+env+"."+app+".Gender"));
        addCust.setFirstName(Configuration.getConfig("testData."+env+"."+app+".FirstName"));
        addCust.setLastName(Configuration.getConfig("testData."+env+"."+app+".LastName"));
        addCust.setDob(Configuration.getConfig("testData."+env+"."+app+".Dob")); // Format: D/MM/YYY
        addCust.setCompanyName(Configuration.getConfig("testData."+env+"."+app+".CompanyName"));
        addCust.setAdminContent("This is for testing.........");
    }

    @When("click on Save button")
    public void click_on_Save_button() {
    addCust.clickOnSave();
    }

    @Then("User can view confirmation message {string}")
    public void user_can_view_confirmation_message(String string) {
//        logger.info("********* Add customer validation ***************");
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains("The new customer has been added successfully"));
    }

    //Searching customers using EMail ID ...................................

    @When("Enter customer EMail")
    public void enter_customer_EMail() {
//        logger.info("********* Search Customer by Email ID Scenario started ***************");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setEmail("victoria_victoria@nopCommerce.com");
    }

    @When("Click on search button")
    public void click_on_search_button() throws InterruptedException
    {
        searchCust.clickSearch();
        Thread.sleep(3000);
    }

    @Then("User should found Email in the Search table")
    public void user_should_found_Email_in_the_Search_table() {
//        logger.info("********* Search customer by email validation ***************");
        boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        Assert.assertEquals(true, status);
    }

    //Searching customers using Name ...................................
    @When("Enter customer FirstName")
    public void enter_customer_FirstName() {
//        logger.info("********* Seqarch custoemr by Name Scenario started ***************");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setFirstName("Victoria");
    }

    @When("Enter customer LastName")
    public void enter_customer_LastName() {
//        logger.info("********* Providing customer name ***************");

        searchCust.setLastName("Terces");
    }

    @Then("User should found Name in the Search table")
    public void user_should_found_Name_in_the_Search_table() {
//        logger.info("********* Search customer by name validation ***************");

        boolean status=searchCust.searchCustomerByName("Victoria Terces");
        Assert.assertEquals(true, status);
    }

}
