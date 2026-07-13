package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportsManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ThreadLocal <ExtentTest> test = new ThreadLocal<>();
	public String repName;
	
	public void onStart(ITestContext testContext) {
		
		String timestamp = new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new Date());
		repName = "report_" + timestamp + ".html";
		
		sparkReporter= new ExtentSparkReporter(System.getProperty("user.dir")+"//reports//"+ repName);
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Project Name", "Selenium With Jenkins");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub-Module", "Reports");
		extent.setSystemInfo("Reporter Name", System.getProperty("user.name"));
		
		String className = testContext.getClass().getName();
		extent.setSystemInfo("TestCase", className);
		
		String operatingSystem = testContext.getCurrentXmlTest().getParameter("OS");
		extent.setSystemInfo("Operating System", operatingSystem);
		
		String Browser = testContext.getCurrentXmlTest().getParameter("Browser");
		extent.setSystemInfo("Browser", Browser);
		
		List<String> groups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!groups.isEmpty()) {
			extent.setSystemInfo("Groups", groups.toString());
		}	
	}
	
	public void onTestSuccess(ITestResult result) {
		test.set(extent.createTest(result.getName()));
		test.get().assignCategory(result.getMethod().getGroups());
		test.get().log(Status.PASS, result.getName() + " got successfully executed");
	}
	
	public void onTestFailure(ITestResult result) {
		test.set(extent.createTest(result.getName()));
		test.get().assignCategory(result.getMethod().getGroups());
		test.get().log(Status.FAIL, result.getThrowable() + " got failed");
		
		String path;
		try {
			path = BaseClass.captureScreenshot(result.getName());
			test.get().addScreenCaptureFromPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		test.set(extent.createTest(result.getName()));
		test.get().assignCategory(result.getMethod().getGroups());
		test.get().log(Status.SKIP, result.getName() + " got skipped");
		
	}
	
	public void onFinish(ITestContext Context) {
		extent.flush();
		/*String filepath = System.getProperty("user.dir") + "//reports//" + repName; 
		
		File file = new File(filepath);
		
		try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
