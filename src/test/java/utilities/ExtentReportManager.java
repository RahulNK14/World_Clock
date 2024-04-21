package utilities;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager extends BaseClass implements ITestListener {

	public ExtentSparkReporter sparkReporter;  //UI of the report
	public ExtentReports extent; //info into the report
	public ExtentTest test; //test results into the report
	
	String repName;
	
	public void onStart(ITestContext context) {
		
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/Test-Reports.html");
		
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("World Clock test report");
		sparkReporter.config().setTheme(Theme.DARK);
		
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
	    
		extent.setSystemInfo("team", "gcc");
	 }
	
	public void onTestStart(ITestResult result) {
		logger.info("********** " + result.getName() + " Test started **********");
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.PASS, result.getName() + " got Successfully executed");
		try {
			String imgPath = BaseClass.captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logger.info(result.getName() + " Sucessfully Executed");
		
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.FAIL,result.getName()+" got failed!!!!!!!!");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		//take screenshot here
		try {
			String imgPath = BaseClass.captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		logger.error(result.getName() + " got failed");
		
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP,result.getName() + " got Skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		logger.info(result.getName() + " got skipped !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
	}

	public void onFinish(ITestContext context) {
		logger.info("Closing the browser");
		extent.flush();
		
		BaseClass.tearDown();
	}
}
