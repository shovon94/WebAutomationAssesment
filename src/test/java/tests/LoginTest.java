package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String filePath = "src/test/resources/testData/TestData_v2.xlsx";  // Path to Excel file
        ExcelReader excelReader = new ExcelReader(filePath);
        List<List<String>> data = excelReader.readExcelData("LoginData");

        // Count how many tests have RunMode = Yes
        int rowsToRun = 0;
        for (int i = 1; i < data.size(); i++) {
            System.out.println("Row " + i + " Data: " + data.get(i));  // Debugging step
            if (data.get(i).size() > 4 && "Yes".equals(data.get(i).get(4).trim())) {  // Checking if RunMode = Yes (Column E, zero-indexed 4)
                rowsToRun++;
            }
        }

        System.out.println("Rows to run: " + rowsToRun);  // Print number of rows to run

        // Create Object[][] for only tests with RunMode = Yes
        Object[][] loginTestData = new Object[rowsToRun][4];  // Renamed array to avoid conflict
        int j = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).size() > 4 && "Yes".equals(data.get(i).get(4).trim())) {  // Ensuring RunMode is "Yes"
                String testDescription = data.get(i).get(1);  // Test Description (Column B)
                String testData = data.get(i).get(2);  // Test Data (Column C)

                // Parse the TestData (split by comma and equal sign)
                Map<String, String> parsedData = parseTestData(testData);

                // Extract username and password from parsed data
                String username = parsedData.get("username");
                String password = parsedData.get("password");

                loginTestData[j] = new Object[]{
                        testDescription,  // Test Description
                        username,         // Extracted Username
                        password,         // Extracted Password
                        data.get(i).get(3)    // Expected Result (Column D)
                };
                j++;
            }
        }

        return loginTestData;
    }


    // Helper method to parse test data from a string like "username=test,password=1234"
    private Map<String, String> parseTestData(String testData) {
        Map<String, String> dataMap = new HashMap<>();
        String[] keyValuePairs = testData.split(",");  // Split by comma to get key-value pairs

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");  // Split by equals sign to separate key and value
            if (keyValue.length == 2) {
                dataMap.put(keyValue[0].trim(), keyValue[1].trim());  // Add to the map
            }
        }
        return dataMap;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String testDescription, String username, String password, String expectedResult) {

        // Log test description
        logger.info("Starting testLogin with username: {}", username);

        // Create a new LoginPage object
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(username);  // Use dynamic username from Excel
        loginPage.enterPassword(password);  // Use dynamic password from Excel
        loginPage.clickLogin();

        // Store the actual result based on the login outcome
        String actualResult = "Failure"; // Default assumption
        if ("Success".equals(expectedResult)) {
            if (loginPage.isLoginSuccessful()) {
                actualResult = "Success";
                logger.info("Login successful for username: {}", username);
            } else {
                actualResult = "Failure";
                logger.error("Login failed for username: {}", username);
            }
        } else {
            String actualErrorMessage = loginPage.getLoginErrorMessage();
            actualResult = "Failure";
            logger.error("Login failed for username: {}. Error: {}", username, actualErrorMessage);
        }

        // Assert the result matches the expected outcome
        Assert.assertEquals(actualResult, expectedResult, "Test failed for username: " + username);
    }
}
