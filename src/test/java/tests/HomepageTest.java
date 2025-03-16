package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomepageTest extends BaseTest {
    @Test
    public void testWebsiteLaunch()
    {
        HomePage homePage= new HomePage(driver);
        String actualTitle=homePage.getTitle();
        String expectedTitle="Cantaloupe";
        Assert.assertEquals(actualTitle,expectedTitle,"WebTitle doesn't Match");
    }
}
