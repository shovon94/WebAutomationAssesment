package utils;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class ExtentReporter implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        // Initialize the HTML report
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("reports/ExtentReport.html");  // Save to reports folder
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Start a new test in the report
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success for the test
        test.pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Capture screenshot and add to the report on failure
        WebDriver driver = ((BaseTest) result.getInstance()).getDriver(); // Get WebDriver instance
        String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());

        try {
            test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());  // Attach screenshot
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped tests
        test.skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finalize the report
        extent.flush();
    }

    // Method to capture screenshots
    private String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = System.currentTimeMillis() + "";
        String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";  // Path for screenshot

        // Take screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);

        try {
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);  // Save screenshot
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshotPath;  // Return screenshot file path
    }
}
