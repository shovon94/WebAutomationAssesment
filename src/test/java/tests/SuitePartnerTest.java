package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SuitePartnerPage;

public class SuitePartnerTest extends BaseTest {

    @Test
    public void testCategoryAndItemSelection() throws InterruptedException {
        SuitePartnerPage suitePartnerPage = new SuitePartnerPage(driver);

        // Select categories and click on items
        suitePartnerPage.selectItemsFromCategoriesv2();
        suitePartnerPage.viewCartAndSelect();

        // Verify if redirection to SuitePartnerPage was successful
        Assert.assertTrue(suitePartnerPage.verifyRedirectionToSuitePartner(), "Redirection to Suite Partner failed.");
    }
}
