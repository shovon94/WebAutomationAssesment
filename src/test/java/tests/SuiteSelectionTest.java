package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SuiteSelectionPage;

public class SuiteSelectionTest extends BaseTest {

    @Test
    public void testSuiteSelectionAndRedirection() {
        // Create SuiteSelectionPage object
        SuiteSelectionPage suiteSelectionPage = new SuiteSelectionPage(driver);

        // Verify Suite Selection page is displayed
        Assert.assertTrue(suiteSelectionPage.isSuiteSelectionPageDisplayed(), "Suite Selection page not displayed");

        // Select a suite (for example, 'South')
        suiteSelectionPage.selectSuite("South");

        // Click Continue
        suiteSelectionPage.clickContinue();

        // Verifying popup text
        String popupText = suiteSelectionPage.getPopupText();
        if (popupText.contains("Place an order?")) {
            // Click the Continue button for "Place an order?" flow
            suiteSelectionPage.clickContinueForPlaceOrder();

            // Verifying redirection to Suite Preferences page
            Assert.assertTrue(suiteSelectionPage.verifyRedirectionToSuitePreferences(), "Redirection to Suite Preferences page failed");

            // Click the Save button to finalize the action
            suiteSelectionPage.clickContinue();
        } else if (popupText.contains("You have exisiting")) {
            // Select "Update existing order"
            suiteSelectionPage.selectUpdateExistingOrder();

            // Verifying redirection to My Orders page
            Assert.assertTrue(suiteSelectionPage.verifyRedirectionToMyOrders(), "Redirection to My Orders page failed");
        }
    }
}
