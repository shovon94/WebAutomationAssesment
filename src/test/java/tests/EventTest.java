package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.EventPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SuiteSelectionPage;
import utils.ExcelReader;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EventTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(EventTest.class);

    @DataProvider(name = "eventData")
    public Object[][] getEventData() {
        String filePath = "src/test/resources/testData/TestData_v2.xlsx";  // Path to Excel file
        ExcelReader excelReader = new ExcelReader(filePath);
        List<List<String>> data = excelReader.readExcelData("EventData");

        // Count how many tests have RunMode = Yes
        int rowsToRun = 0;
        for (int i = 1; i < data.size(); i++) {
            System.out.println("Row " + i + " Data: " + data.get(i));  // Debugging step
            if (data.get(i).size() > 5 && "Yes".equals(data.get(i).get(5).trim())) {  // Checking if RunMode = Yes (Column F, zero-indexed 5)
                rowsToRun++;
            }
        }

        System.out.println("Rows to run: " + rowsToRun);  // Print number of rows to run

        // Create Object[][] for only tests with RunMode = Yes
        Object[][] eventTestData = new Object[rowsToRun][4];  // Renamed array to avoid conflict
        int j = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).size() > 5 && "Yes".equals(data.get(i).get(5).trim())) {  // Ensuring RunMode is "Yes"
                String testDescription = data.get(i).get(1);  // Test Description (Column B)
                String eventDate = data.get(i).get(2);  // Event Date (Column C)
                String eventName = data.get(i).get(3);  // Event Name (Column D)

                eventTestData[j] = new Object[]{
                        testDescription,  // Test Description
                        eventDate,         // Event Date (from Excel)
                        eventName,         // Event Name (from Excel)
                        data.get(i).get(4)    // Expected Result (Column E)
                };
                j++;
            }
        }

        return eventTestData;
    }

//    @Test(dataProvider = "eventData")
//    public void testEventSelection(String testDescription, String eventDate, String eventName, String expectedResult) {
//
//        // Log test description
//        logger.info("Starting testEventSelection for date: {}", eventDate);
//
//        // Create a new EventPage object
//        EventPage eventPage = new EventPage(driver);
//
//        // Select the date dynamically from the Excel data
//        eventPage.selectDate(eventDate);
//
//        // Store the actual result based on the event availability
//        String actualResult = "No Event Available";
//        if (expectedResult.equals("Success")) {
//            if (eventPage.areEventsAvailable()) {
//                eventPage.selectEvent(eventName);
//                eventPage.clickContinue();
//                actualResult = "Success";
//                logger.info("Event selected: {}", eventName);
//            } else {
//                logger.error("No events available for the date: {}", eventDate);
//            }
//        } else {
//            if (eventPage.isNoEventMessageDisplayed()) {
//                actualResult = "No Event Available";
//                logger.info("No event available message is displayed.");
//            } else {
//                logger.error("Unexpected event availability for date: {}", eventDate);
//            }
//        }
//
//        // Assert the result matches the expected outcome
//        Assert.assertEquals(actualResult, expectedResult, "Test failed for event on date: " + eventDate);
//    }


    @Test(dataProvider = "eventData")
    public void testEventSelection(String testDescription, String eventDate, String eventName, String expectedResult) {

        // Log test description
        logger.info("Starting testEventSelection for date: {}", eventDate);

        // Create a new EventPage object
        EventPage eventPage = new EventPage(driver);

        // Select the date dynamically from the Excel data
        eventPage.selectDate(eventDate);

        // Store the actual result based on the event availability
        String actualResult = "No Event Available";

        if (expectedResult.equals("Success")) {
            if (eventPage.areEventsAvailable()) {
                eventPage.selectEvent(eventName);
                eventPage.clickContinue();

                // After clicking continue, verify if the page redirects to the Suite Selection page
                SuiteSelectionPage suiteSelectionPage = new SuiteSelectionPage(driver);

                // Verify if the Suite Selection page is displayed by checking the page header
                if (suiteSelectionPage.isSuiteSelectionPageDisplayed()) {
                    actualResult = "Success";
                    logger.info("Event selected: {} and redirected to Suite Selection Page", eventName);
                } else {
                    actualResult = "Failure";
                    logger.error("Redirection to Suite Selection Page failed after selecting event: {}", eventName);
                }
            } else {
                actualResult = "Failure";
                logger.error("No events available for the date: {}", eventDate);
            }
        } else {
            // If no events are expected, check for the "No events" message
            if (eventPage.isNoEventMessageDisplayed()) {
                actualResult = "No Event Available";
                logger.info("No event available message is displayed.");
            } else {
                actualResult = "Failure";
                logger.error("Unexpected event availability for date: {}", eventDate);
            }
        }

        // Assert the result matches the expected outcome
        Assert.assertEquals(actualResult, expectedResult, "Test failed for event on date: " + eventDate);
    }





}
