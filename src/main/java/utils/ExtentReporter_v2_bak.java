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

public class ExtentReporter_v2_bak implements ITestListener {
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
        // Check if the test has parameters
        Object[] parameters = result.getParameters();
        if (parameters.length > 0) {
            // If parameters exist, get test description from the first parameter
            String testDescription = (String) parameters[0];  // Test description passed as first parameter
            test = extent.createTest(result.getMethod().getMethodName(), testDescription);  // Create test with the description

            // Log the test data (test data is the second parameter)
            if (parameters.length > 1) {
                String testData = (String) parameters[1];  // Test data passed from DataProvider
                test.info("Test Data: " + testData);  // Log the full value from TestData column
            }

            // Log the expected result (third parameter)
            if (parameters.length > 3) {
                String expectedResult = (String) parameters[3];
                test.info("Expected Result: " + expectedResult);
            }
        } else {
            // If no parameters, create the test with a default description
            test = extent.createTest(result.getMethod().getMethodName(), "No Test Data Provided");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success for the test
        if (test != null) {
            test.pass("Test passed");

            // Log the actual result (Success)
            String actualResult = "Success";
            test.info("Actual Result: " + actualResult);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (test != null) {
            // Capture screenshot and add to the report on failure
            WebDriver driver = ((BaseTest) result.getInstance()).getDriver(); // Get WebDriver instance
            String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());  // Capture the screenshot

            try {
                // Ensure the screenshot path is relative to the report location
                String fullScreenshotPath = "file:///" + new File(screenshotPath).getAbsolutePath();  // Absolute path for better access
                System.out.println(screenshotPath);  // For debugging, print the screenshot path

                // Attach screenshot to failure report using the full path
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(fullScreenshotPath).build());

                // Log Expected and Actual results correctly
                Object[] parameters = result.getParameters();
                if (parameters.length > 3) {
                    String expectedResult = (String) parameters[3];  // Expected result from Test Data
                    String actualResult = "Failure";  // Failure as actual result

                    // Capture the actual error message
                    String actualErrorMessage = "Unknown Error";
                    if (result.getThrowable() != null) {
                        actualErrorMessage = result.getThrowable().getMessage();
                    }

                    // Log the Expected and Actual results
                    test.info("Expected Result: " + expectedResult);  // Log Expected Result
                    test.info("Actual Result: " + actualResult + " - " + actualErrorMessage);  // Log Actual Result
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped tests
        if (test != null) {
            test.skip("Test skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finalize the report
        extent.flush();  // Save all the results in the report
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
