package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Base.PageBaseClass;

public class FreeListingPage extends PageBaseClass {

	public FreeListingPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	/**************** Click on freelist **********************/
	@FindBy(xpath = "//a[@id='h_flist']")
	WebElement freeList;

	public void clickOnFreeList() {
		try {
			logger = report.createTest("Free Listing -Click Free Listing");
			freeList.click();
			reportPass("Free Listing functionality selected");
		} catch (Exception e) {
			reportFail("Free Listing not selected " + e.getMessage());
		}
	}

	/********************* Verify the free listing page **************************/
	public void verifyTitle() {
		try {
			logger = report.createTest("Free Listing -Verify Free Listing page title");
			verifyTitle("Free Listing");
		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Free Listing -Verification failed");
		}
	}

	/********************* Input the values ********************/
	@FindBy(id = "fcom")
	WebElement company;

	@FindBy(id = "fname")
	WebElement first;

	@FindBy(id = "lname")
	WebElement last;

	@FindBy(xpath = "//div/div[@class='drop']")
	WebElement title;

	@FindBy(id = "fmb0")
	WebElement mobile;

	@FindBy(id = "fph0")
	WebElement land;

	@FindBy(xpath = "//button[@class='bbtn subbtn']")
	WebElement submit;

	public void doSubmit(String companyName, String nameTitle, String firstName, String lastName, String phone,
			String landline) {
		try {
			logger = report.createTest("Submit the Freelisting data");
			company.sendKeys(companyName);
			Actions action = new Actions(driver);
			action.moveToElement(title).click().perform();
			driver.findElement(By.xpath("//li[@onclick=\"javascript:setSalutaion('" + nameTitle + "')\"]")).click();
			first.sendKeys(firstName);
			last.sendKeys(lastName);
			mobile.sendKeys(phone);
			land.sendKeys(landline);
			reportPass("data submitted succesfully");
			submit.click();
		} catch (Exception e) {
			reportFail("Cant submit the data " + e.getMessage());
		}

	}

	@FindBy(id = "fph0Err")
	WebElement phoneError;
	@FindBy(id = "fmb0Err")
	WebElement mobileError;
	@FindBy(id = "")
	WebElement ferror;
	@FindBy(id = "fnameErr")
	WebElement fnameError;
	@FindBy(id = "lnameErr")
	WebElement lnameError;
	@FindBy(id = "fcoe")
	WebElement companyError;

	public void verifyBusinessInfoPage() {
		System.out.println("===============================================");
		try {
			logger = report.createTest("Error message displayed");
			// Free Listing Business Info
			verifyTitle("Free Listing Business Info");
			if (phoneError.isDisplayed()) {
				System.out.println(phoneError.getText());
				logger.log(Status.FAIL, "Landline Number is Invalid");
			} else if (mobileError.isDisplayed()) {
				System.out.println(mobileError.getText());
				logger.log(Status.FAIL, "Phone Number is Invalid");
			} else if (fnameError.isDisplayed()) {
				System.out.println(fnameError.getText());
				logger.log(Status.FAIL, "First name is Invalid");
			} else if (lnameError.isDisplayed()) {
				System.out.println(lnameError.getText());
				logger.log(Status.FAIL, "Last name is Invalid");
			} else if (companyError.isDisplayed()) {
				System.out.println(companyError.getText());
				logger.log(Status.FAIL, "Comapny name is Invalid");
			}

		} catch (Exception e) {
			reportFail("No error message displayed " + e.getMessage());
		}

	}

	public GymPage gymPage() {
		GymPage gympage = new GymPage(driver, logger);
		PageFactory.initElements(driver, gympage);
		return gympage;
	}

}
