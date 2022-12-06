package Pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;

import Base.PageBaseClass;

public class GymPage extends PageBaseClass {

	public GymPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	@FindBy(xpath = "//span[@id='hotkeys_text_26']")
	WebElement fitness;

	/*************** Go to HomePage and click on GYM Page ********************/
	public void fitness() {
		try {
			logger = report.createTest("Fitness -Click Fitness");
			driver.get(prop.getProperty("url"));
			if (fitness.isDisplayed() && fitness.isEnabled()) {
				reportInfo("Fitness functionality available and clicked");
				fitness.click();
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			reportFail("Fitness function failed" + e.getMessage());
		}
	}

	/******************** Verify fitness functionality ************************/
	public void fitnessMenu() {
		try {
			logger = report.createTest("Fitness -Verify Fitness menu page");
			verifyTitle("Fitness");
		} catch (Exception e) {
			reportFail("Fitness menu not available " + e.getMessage());
		}
	}

	/**************** Click on Gym *******************/
	@FindBy(xpath = "//span[@title='Gym']")
	WebElement gym;

	public void gym() {
		try {
			logger = report.createTest("Fitness -Click Gym");
			if (gym.isEnabled()) {
				reportInfo("Gym button is visible and selected");
				takeScreenshot();
				gym.click();
			} else {
				assertTrue(false);
			}

		} catch (Exception e) {
			reportFail("Gym button not visible " + e.getMessage());
		}
	}

	/*************** Retrieve All SubMenu ***************/
	@FindBy(xpath = "//ul[@class='mm-listview']/li")
	List<WebElement> list;

	public void gymMenu() {
		System.out.println("===============================================");
		try {
			logger = report.createTest("Fitness -Print all the sub-menu available under Gym menu");
			for (WebElement element : list) {
				reportInfo(element.getText());
				System.out.println(element.getText());
			}
		} catch (Exception e) {
			reportFail("Gym sub-menu not displayed " + e.getMessage());
		}
	}

}
