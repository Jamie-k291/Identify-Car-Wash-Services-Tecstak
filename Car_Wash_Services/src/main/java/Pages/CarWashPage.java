package Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Base.PageBaseClass;

public class CarWashPage extends PageBaseClass {

	public CarWashPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	String value;
	JavascriptExecutor executor = (JavascriptExecutor) driver;

	/********************** Change to nearby Location ***********************/
	@FindBy(id = "city")
	WebElement location;
	@FindBy(xpath = "//a[normalize-space()='Detect my Location']")
	WebElement detect;

	public void selectLocation() {
		try {
			logger = report.createTest("Detect Location");
			location.click();
			detect.click();
			reportPass("Location detected Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Detect Location Failed " + e.getMessage());
		}
		value = location.getAttribute("value");
	}

	/********************** Select the Auto car from menu *********************/
	@FindBy(id = "hotkeys_text_7")
	WebElement autoCare;

	public void autoCarMenu() {
		try {
			logger = report.createTest("Car Wash -Click Auto Car");
			autoCare.click();
			reportPass("Auto Car Functionality selected from the left menu of justdial website.");

		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Auto Car functionality not Selected" + e.getMessage());
		}
	}

	/*************** Click on the Car wash from the list ****************/
	@FindBy(xpath = "//span[@title='Car Wash']")
	WebElement carWash;

	public void selectCarWash() {
		try {
			logger = report.createTest("Car Wash -Click Car Wash");
			carWash.click();
			waitForPageLoad();
			reportPass("Car Wash Functionality selected from the 'Auto Care' Menu");

		} catch (Exception e) {

			e.printStackTrace();
			reportFail("Car Wash functionality not Selected");
		}
	}

	/****************** Carwash title Check **********************/
	public void carWashTitleCheck() {
		try {
			logger = report.createTest("Car Wash -Verify title");
			verifyTitle("Car Washing Services");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FindBy(xpath = "//h1[@class='gglsrc lng_commn']")
	WebElement near;

	/**************** Location check *******************/
	public void checkLocation() {

		try {
			logger = report.createTest("Car Wash - Verify Location");

			if (verifyElement(value, near.getText())) {
				reportPass("Shops are displayed for the given location");
			} else {
				reportFail("Shops aren't displayed for the given location");
			}

		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Location check failed");
		}

	}

	/****************** Sort by rating ***************************/
	@FindBy(xpath = "//li[@id = 'rating']")
	WebElement rating;

	public void sortByRating() {
		try {
			logger = report.createTest("Car Wash -Sort by Ratings");
			rating.click();
			executor.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
			reportPass("Sorted by rating");
		} catch (Exception e) {
			reportFail("Sorting failed " + e.getMessage());
		}
	}

	/*************** Verify the rating is displayed ***********************/

	@FindBy(xpath = "//*[contains(@class, 'green-box')]")
	List<WebElement> star;

	public void ratingIsDisplayed() {
		try {
			logger = report.createTest("Car Wash -Ratings Displayed");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String rateStr = star.get(0).getText();
			if (rateStr.isBlank()) {
				reportFail("Ratings not displayed for the shops");
			} else {
				reportPass("Ratings displayed for the shops");
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Rating is not present");
		}
	}

	/**************** Verify the vote is displayed **************/
	@FindBy(xpath = "//p/a/span[@class='rt_count lng_vote']")
	List<WebElement> vote;

	public void voteIsDisplayed() {
		try {
			logger = report.createTest("Car Wash -Votes");
			String voteStr = vote.get(0).getText();
			if (voteStr.isBlank()) {
				reportFail("Vote not displayed for the shops");
			} else {
				reportPass("Vote displayed for the shops");

			}
		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Vote is not present");
		}
	}

	/**************** Display required details ********************/
	@FindBy(xpath = "//p[span[contains(text(),'91')]]/preceding-sibling::h2")
	List<WebElement> shopName;

	@FindBy(xpath = "//span[@class='shownum']")
	List<WebElement> shownum;

	@FindBy(xpath = "//p[@class='contact-info ']/child::span[contains(text(),'91')]")
	List<WebElement> phone;

	public void displayDetails() {

		for (int i = 0; i < shownum.size(); i++) {
			executor.executeScript("arguments[0].click();", shownum.get(i));
		}
		try {
			int count = 0;
			logger = report.createTest("Car Wash -Print Shop names filtered by more then 4 ratings and 20 votes");
			for (int i = 0; i < phone.size(); i++) {
				Float ratings = Float.parseFloat(star.get(i).getText());
				String number = vote.get(i).getText().split(" ")[0];
				int votes = Integer.parseInt(number);
				if (count <= 5) {
					if (ratings >= 4 && votes > 20) {
						reportInfo(i + 1 + ". " + shopName.get(i).getText() + "||" + ratings + "||"
								+ vote.get(i).getText());
						count++;
						System.out.println(i + 1 + ". " + shopName.get(i).getText() + "||" + ratings + "||"
								+ vote.get(i).getText() + "||" + phone.get(i).getText());
					}
				}
			}
			reportPass("Car Wash Service shop name has been displayed successfully");

		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Car Wash Service shop names not displayed");
		}

	}

	public FreeListingPage freeList() {
		FreeListingPage freelist = new FreeListingPage(driver, logger);
		PageFactory.initElements(driver, freelist);
		return freelist;
	}

}
