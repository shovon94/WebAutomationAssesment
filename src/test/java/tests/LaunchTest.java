package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;

public class LaunchTest extends BaseTest {
    @Test
    public void testWebsiteLaunch()
    {
        HomePage homePage= new HomePage(driver);
        homePage.launchWeb();
        System.out.println("Website is launched successfully");
    }
}
