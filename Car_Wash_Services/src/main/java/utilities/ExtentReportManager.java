package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	public static ExtentReports report;

	/************** Create ExtentReports Instance ******************/
	public static ExtentReports getReportInstance() {

		if (report == null) {
			String reportName = DateUtil.getTimeStamp() + ".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "/test-output/" + reportName);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);

			report.setSystemInfo("Group", "QEA22CSDIPM002-Team5");
			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("JAMAL K", "2232475");
			report.setSystemInfo("UDAY PRAKASH MORE", "2232684");
			report.setSystemInfo("KOTHAREDDY GARI MAHESH REDDY", "2232716");
			report.setSystemInfo("SUMIT CHAITRAM ZARKHANDE", "2232524");
			report.setSystemInfo("DEVARAKONDA MANASA", "2232628");

			htmlReporter.config().setDocumentTitle("Hackathon Project");
			htmlReporter.config().setReportName("Identify Car Wash Services");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
			htmlReporter.config().setTheme(Theme.DARK);
		}

		return report;
	}

}
