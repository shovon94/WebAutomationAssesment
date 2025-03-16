package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SuitePreferencePage;
import pages.SuitePartnerPage;

public class SuitePreferenceSaveTest extends BaseTest {

    @Test
    public void testSaveAndRedirectionToSuitePartner() {
        // Create the page objects
        SuitePreferencePage suitePreferencePage = new SuitePreferencePage(driver);
        SuitePartnerPage suitePartnerPage = new SuitePartnerPage(driver);

        // Click on the 'Save' button in SuitePreferencePage
        suitePreferencePage.clickSave();

        // Verify redirection to SuitePartnerPage
        boolean isRedirected = suitePartnerPage.verifyRedirectionToSuitePartner();

        // Assert that the redirection was successful
        Assert.assertTrue(isRedirected, "Redirection to SuitePartnerPage failed");
    }
}
