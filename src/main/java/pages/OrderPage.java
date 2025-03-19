package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor to initialize the WebDriver and WebDriverWait
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Explicit wait
    }

    // Locator for the "My Order" header to verify the order page
    By orderPageHeader = By.xpath("//h1[normalize-space()='My Order']");

    // Method to verify if the Order page is displayed by checking the header text
    public boolean isOrderPageDisplayed() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(orderPageHeader));
            if (header.getText().equals("My Order")) {
                System.out.println("Order page is displayed.");
                return true;  // Return true if header text is correct
            }
        } catch (Exception e) {
            System.out.println("Error verifying Order page.");
            e.printStackTrace();
        }
        return false;  // Return false if not displayed
    }

    // Locator for the "Save Pre-Order" button
    By savePreOrderButton = By.xpath("//button[contains(text(),'Save')]");

    // Method to click the "Save Pre-Order" button
    public void clickSavePreOrder() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(savePreOrderButton));
            saveButton.click();
            System.out.println("Save Pre-Order button clicked.");
            Thread.sleep(15000);
        } catch (Exception e) {
            System.out.println("Error clicking 'Save Pre-Order' button.");
            e.printStackTrace();
        }
    }

    public void givePaymentinfo() {
//         Locator for the iframe by its src URL or other unique identifiers (You can customize this based on your page)
        //By iframeLocator = By.xpath("//iframe[contains(@src, 'pay.google.com')]");

        try {
            //Wait for the iframe to be visible and then switch to it
//            WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(iframeLocator));
//            driver.switchTo().frame(iframeElement); // Switch to the iframe

            // Locator for the credit card option inside the iframe
            By creditCard = By.xpath("//button[@aria-label='Credit Card']");

            // Wait and click on the credit card option
            WebElement creditBtn = wait.until(ExpectedConditions.elementToBeClickable(creditCard));
            creditBtn.click();
            System.out.println("Credit Card option selected");
            Thread.sleep(20000);

            // Optionally, switch back to the default content if needed
            driver.switchTo().defaultContent(); // Go back to the main page from iframe

        } catch (Exception e) {
            System.out.println("Error Clicking credit card option!");
            e.printStackTrace();
        }
    }

    public void paymentVerify() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set an explicit wait for 10 seconds

        try {
            //iframe [@title='Iframe for secured card number']

            By iframeLocatorCard = By.xpath("//iframe [@title='Iframe for secured card number']");
//            WebElement iframeElementCard = wait.until(ExpectedConditions.elementToBeClickable(iframeLocatorCard));
            WebElement iframeElementCard1 = driver.findElement(iframeLocatorCard);


            Thread.sleep(10000);
            ((JavascriptExecutor) driver).executeScript("document.evaluate(\"//input[@id='adyen-checkout-encryptedCardNumber-1742184853033']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.disabled = false;");


            //driver.switchTo().frame(iframeElementCard);

            // Switch to the iframe containing the payment form


            // Card number input - Wait until the element is clickable
//            WebElement cardNumberInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='adyen-checkout-encryptedCardNumber-1742167670135']")));
//            WebElement cardNumberInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".adyen-checkout__input.adyen-checkout__input--large.adyen-checkout__card__cardNumber__input.CardInput-module_adyen-checkout__input__11tlB")));
            WebElement cardNumberInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='adyen-checkout-encryptedCardNumber-1742184853033']")));
//            iframeElementCard.click();
//            iframeElementCard.clear();
//            iframeElementCard.sendKeys("6543278934517895");


            cardNumberInput.click();
            cardNumberInput.clear();
            cardNumberInput.sendKeys("4444444444444444");

            // Expiry date input - Wait until the element is clickable
            WebElement expiryDateInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='adyen-checkout-encryptedExpiryDate-1742167670136']")));
            expiryDateInput.click();
            expiryDateInput.clear();
            expiryDateInput.sendKeys("05/25");

            // CVV input - Wait until the element is clickable
            WebElement cvvInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='adyen-checkout-encryptedSecurityCode-1742167670137']")));
            cvvInput.clear();
            cvvInput.sendKeys("273");

            // Cardholder name input - Wait until the element is clickable
            WebElement cardHolderNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='adyen-checkout-holderName-1742167670138']")));
            cardHolderNameInput.click();
            cardHolderNameInput.clear();
            cardHolderNameInput.sendKeys("name");

            // Verify button click - Wait until the button is clickable
            WebElement verifyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='container-scheme-14d56eba-a76b-4c5e-bd06-d444a7b15286']/div/button")));
            verifyButton.click();

            // Switch back to the main content (parent frame)
            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | InterruptedException e) {
            System.out.println("Error: Element not found or issue with iframe navigation: " + e.getMessage());
        }
    }


    public void paymentV2(){
        driver.findElement(By.xpath("//iframe [@title='Iframe for secured card number']")).click();
        driver.findElement(By.xpath("//iframe [@title='Iframe for secured card number']")).sendKeys("370000000000002");
        System.out.println("Key Send!");
//        driver.findElement(By.xpath("//input[@aria-label='Expiry date']")).click();
//        driver.findElement(By.xpath("//input[@aria-label='Expiry date']")).sendKeys("0330");

    }


}
