package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OrderPage;

public class OrderPageTest extends BaseTest {

    @Test(priority = 1)
    public void testClickSavePreOrder() {
        // Create an instance of the OrderPage class
        OrderPage orderPage = new OrderPage(driver);

        // Verify the Order page is displayed
        boolean isOrderPageDisplayed = orderPage.isOrderPageDisplayed();
        Assert.assertTrue(isOrderPageDisplayed, "Order page is not displayed.");

        // Click the 'Save Pre-Order' button if the Order page is displayed
        if (isOrderPageDisplayed) {
            orderPage.clickSavePreOrder();
        }
    }

    @Test(priority = 2)
    public void selectPaymentoption()
    {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.givePaymentinfo();
    }

    @Test(priority = 3)
    public void paymentVerification() throws InterruptedException {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.paymentV2();
    }
}
