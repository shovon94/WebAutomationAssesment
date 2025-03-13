package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin()
    {
        LoginPage loginPage= new LoginPage(driver);
        loginPage.enterEmail();;
        loginPage.enterPassword();
        loginPage.clickLogin();

    }

}
