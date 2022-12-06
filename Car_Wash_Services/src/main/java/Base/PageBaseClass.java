package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Pages.CarWashPage;

public class PageBaseClass extends BaseUI {

	public ExtentTest logger;

	public PageBaseClass(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	/******************** Justdial.com ********************/
	public CarWashPage openURL() {
		logger.log(Status.INFO, "Opening Website to check Car Wash Page");
		driver.get(prop.getProperty("url"));
		logger.log(Status.PASS, "Successfully Opened Website");
		waitForPageLoad();

		CarWashPage carWashPage = new CarWashPage(driver, logger);
		PageFactory.initElements(driver, carWashPage);
		return carWashPage;
	}

}
