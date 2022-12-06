package TestSuite;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseUI;
import Base.PageBaseClass;
import Pages.CarWashPage;
import Pages.FreeListingPage;
import Pages.GymPage;
import utilities.TestDataProvider;

public class TestSuite extends BaseUI {

	CarWashPage car;
	FreeListingPage freeList;
	GymPage gymPage;

	@Test(dataProvider = "getTestData", groups = "Smoke")
	public void smokeTest(Hashtable<String, String> testData) throws Exception {

		logger = report.createTest("Smoke Test");
		configure();
		invokeBrowser();
		PageBaseClass pageBaseClass = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBaseClass);

		car = pageBaseClass.openURL();
		car.selectLocation();
		car.autoCarMenu();
		car.selectCarWash();
		car.sortByRating();
		car.displayDetails();

		freeList = car.freeList();
		freeList.clickOnFreeList();
		freeList.doSubmit(testData.get("Company Name"), testData.get("Name Title"), testData.get("First Name"),
				testData.get("Last Name"), testData.get("Mobile Number"), testData.get("Landline Number"));
		freeList.verifyBusinessInfoPage();

		gymPage = freeList.gymPage();
		gymPage.fitness();
		gymPage.gym();
		gymPage.gymMenu();
		driver.quit();

	}

	@Test(dataProvider = "getTestData", groups = "Regression", dependsOnMethods = "smokeTest")
	public void regresionTest(Hashtable<String, String> testData) throws Exception {

		logger = report.createTest("Regression Test");

		invokeBrowser();
		PageBaseClass pageBaseClass = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBaseClass);

		car = pageBaseClass.openURL();
		car.selectLocation();
		car.autoCarMenu();
		car.selectCarWash();
		car.carWashTitleCheck();
		car.checkLocation();
		car.sortByRating();
		car.ratingIsDisplayed();
		car.displayDetails();

		freeList = car.freeList();
		freeList.clickOnFreeList();
		freeList.verifyTitle();
		freeList.doSubmit(testData.get("Company Name"), testData.get("Name Title"), testData.get("First Name"),
				testData.get("Last Name"), testData.get("Mobile Number"), testData.get("Landline Number"));
		freeList.verifyBusinessInfoPage();

		gymPage = freeList.gymPage();
		gymPage.fitness();
		gymPage.fitnessMenu();
		gymPage.gym();
		gymPage.gymMenu();
		driver.quit();

	}

	@DataProvider
	public Object[][] getTestData() throws IOException {
		return TestDataProvider.getTestData("TestSheet", "TestOne");
	}
}
