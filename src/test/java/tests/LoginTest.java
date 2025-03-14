package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.EventPage;
import utils.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginTest extends BaseTest
{
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String filePath = "src/test/resources/testData/TestData.xlsx";  // Path to Excel file
        ExcelReader excelReader = new ExcelReader(filePath);
        List<List<String>> data = excelReader.readExcelData("LoginData");

        // Count how many tests have RunMode = Yes
        int rowsToRun = 0;
        for (int i = 1; i < data.size(); i++) {
            if ("Yes".equals(data.get(i).get(5))) {  // Checking if RunMode = Yes
                rowsToRun++;
            }
        }

        // Create Object[][] for only tests with RunMode = Yes
        Object[][] testData = new Object[rowsToRun][3];  // Only "Yes" tests
        int j = 0;
        for (int i = 1; i < data.size(); i++) {
            if ("Yes".equals(data.get(i).get(5))) {
                testData[j] = new Object[]{data.get(i).get(2), data.get(i).get(3), data.get(i).get(4)};  // C = Username, D = Password, E = Expected Result
                j++;
            }
        }
        return testData;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String expectedResult) {
        logger.info("Starting testLogin with username: {}", username);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(username);  // Use dynamic username from Excel
        loginPage.enterPassword(password);  // Use dynamic password from Excel
        loginPage.clickLogin();

        // Verify login success or failure based on the expected result
        if ("Success".equals(expectedResult)) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed with valid credentials!");
            logger.info("Login successful for username: {}", username);

            // No need for separate event page check, it's handled by isLoginSuccessful()
        } else {
            String actualErrorMessage = loginPage.getLoginErrorMessage();
            Assert.assertTrue(actualErrorMessage.contains("Invalid"), "Unexpected error message!");
            logger.error("Login failed for username: {}. Error: {}", username, actualErrorMessage);
        }
    }
}
