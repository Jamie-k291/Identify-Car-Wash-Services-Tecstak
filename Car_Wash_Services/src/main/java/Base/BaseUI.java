package Base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.DateUtil;
import utilities.ExtentReportManager;

public class BaseUI {

	public WebDriver driver;
	public static Properties prop;
	public static ExtentHtmlReporter extenthtml;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	public static Boolean flag;
	Scanner sc = new Scanner(System.in);

	/****************** Configure Location **************************/
	public void configure() {
		System.out.println("===============================================");
		System.out.println("Select a location(Enter 1 or 2)\n1.Mumbai\n2.Detect Location");
		String location = sc.next();
		if (location.contains("1")) {
			flag = true;
		} else if (location.equals("2")) {
			flag = false;
		} else {
			System.out.println("Failed to configure location");
			System.exit(0);
		}
		System.out.println("===============================================");

		sc.close();
	}

	/******************** Invoke Browser ********************/
	public void invokeBrowser() throws Exception {
		prop = new Properties();
		FileInputStream file = new FileInputStream("src\\test\\Resources\\config.properties");
		prop.load(file);

		String browser = prop.getProperty("browserName");
		if (browser.equalsIgnoreCase("Chrome")) {

			WebDriverManager.chromedriver().setup();

			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");
			option.addArguments("--disable-blink-features=AutomationControlled");

			driver = new ChromeDriver(option);

		} else if (browser.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();

			FirefoxOptions option = new FirefoxOptions();
			option.setProfile(new FirefoxProfile());
			option.addPreference("dom.webnotifications.enabled", false);
			option.addArguments("--disable-blink-features=AutomationControlled");

			driver = new FirefoxDriver(option);

		} else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		} else {
			reportInfo("Missing Driver name");
			System.exit(0);
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@AfterTest
	public void flushReports() {
		report.flush();
		flag = null;
	}

	/******************** Take Screenshot upon report failure ********************/
	public void takeScreenshot() {
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(
				System.getProperty("user.dir") + "/ScreenShots/" + DateUtil.getTimeStamp() + ".png");

		try {
			FileUtils.copyFile(sourceFile, destinationFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "/ScreenShots/" + DateUtil.getTimeStamp() + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/******************** Report Status ********************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenshot();
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
		takeScreenshot();
	}

	public void reportInfo(String reportInfo) {
		logger.log(Status.INFO, reportInfo);
	}

	/******************** Page Load Wait ********************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**************************** Verify **************************/
	public void verifyTitle(String expected) {
		String title = driver.getTitle();
		if (title.contains(expected)) {
			reportInfo("Title matched for the " + expected + " Page");
			logger.log(Status.PASS, "Page is verified");

		} else {
			reportInfo("Title not matched for the " + expected + " Page");
			logger.log(Status.FAIL, "Page is not verified");

		}
	}

	public Boolean verifyElement(String expected, String actual) {
		if (actual.contains(expected)) {
			return true;
		} else {
			return false;
		}
	}

}
