package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.LoginTest;

public class TestListener implements ITestListener {

    ExtentReports extent = ExtentReportManager.getExtentReports();
    ExtentTest test;

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((LoginTest) testClass).driver;

        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
        test = extent.createTest(result.getName());
        test.log(Status.FAIL, "Test failed");
        test.addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.log(Status.PASS, "Test passed");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }
}
