package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuiteSelectionPage {
    WebDriver driver;
    WebDriverWait wait;

    public SuiteSelectionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    // Locator for the Suite Selection page header
    By suiteSelectionHeader = By.xpath("//h4[contains(text(),'My Suites')]");

    // Method to check if the Suite Selection page is displayed
    public boolean isSuiteSelectionPageDisplayed() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(suiteSelectionHeader));
            return header.getText().contains("My Suites");  // Verify header text
        } catch (Exception e) {
            return false;
        }
    }

    // Locator for Suite radio button list (South, North West, East)
    By suiteRadioButtonList = By.cssSelector(".MuiBox-root.css-1yjvs5a");

    // Method to select a suite based on the test data
    public void selectSuite(String suiteName) {
        try {
            WebElement suiteElement = wait.until(ExpectedConditions.visibilityOfElementLocated(suiteRadioButtonList));
            WebElement suiteRadioButton = suiteElement.findElement(By.xpath("//span[contains(text(),'" + suiteName + "')]"));
            suiteRadioButton.click();
            System.out.println("Selected suite: " + suiteName);
        } catch (Exception e) {
            System.out.println("Error selecting suite: " + suiteName);
            e.printStackTrace();
        }
    }

    // Locator for the Continue button
    By continueButton = By.xpath("//button[contains(text(),'CONTINUE')]");

    // Method to click on the Continue button
    public void clickContinue() {
        try {
            // Log that we are waiting for the Continue button to become clickable
            System.out.println("Waiting for Continue button to be clickable...");

            // Log the XPath being used to locate the Continue button
            String continueButtonXPath = "//button[normalize-space()='Continue']";
            System.out.println("Using XPath: " + continueButtonXPath);

            // Wait for the Continue button to become clickable
//            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jss1630.jss1684.jss1631 button .MuiTouchRipple-root.css-w0pj6f")));
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='jss1630 jss1770 jss1631'] button[type='button']")));

            // Log that the Continue button is found and clickable
            System.out.println("Continue button is clickable, proceeding with click...");

            // Click the Continue button
//            continueBtn.click();



            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", continueBtn);  // Trigger the click event

            // Log that the button was clicked
            System.out.println("Continue button clicked.");
        } catch (Exception e) {
            // Log any exceptions or errors encountered
            System.out.println("Error while trying to click the Continue button.");
            e.printStackTrace();
        }
    }

    // Locator for the popup window text ("Place an order?")
    By popupText = By.cssSelector("h6.MuiTypography-root.MuiTypography-h6.css-1ct4ter");


    // Method to get the popup text
    public String getPopupText() {
        try {
            // Wait for the popup text element to be visible
            WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(popupText));

            // Get the text from the element
            String popupMessage = popupElement.getText();
            System.out.println("Popup message: " + popupMessage);
            return popupMessage;
        } catch (Exception e) {
            System.out.println("Error getting popup text.");
            return "No popup found";
        }
    }

    // Method to verify redirection to Suite Preferences page
    public boolean verifyRedirectionToSuitePreferences() {
        try {
            By suitePreferencesHeader = By.xpath("//h1[contains(text(),'Suite Preferences')]");
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(suitePreferencesHeader));
            if (header.getText().contains("Suite Preferences")) {
                System.out.println("Redirection to Suite Preferences successful.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error verifying redirection to Suite Preferences.");
            e.printStackTrace();
        }
        return false;
    }

    // Locator for the "Update existing order" option
    By updateExistingOrderOption = By.xpath("//span[normalize-space()='Update existing order']");

    // Method to select "Update existing order" option
    public void selectUpdateExistingOrder() {
        try {
            WebElement updateOption = wait.until(ExpectedConditions.visibilityOfElementLocated(updateExistingOrderOption));
            updateOption.click();
            clickContinueForUpdateExistingOrder();
            System.out.println("Selected 'Update existing order' option.");
        } catch (Exception e) {
            System.out.println("Error selecting 'Update existing order'.");
            e.printStackTrace();
        }
    }

    // Method to verify redirection to My Orders page
    public boolean verifyRedirectionToMyOrders() {
        try {
            By myOrdersHeader = By.xpath("//h4[contains(text(),'My Orders')]");
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(myOrdersHeader));
            if (header.getText().contains("My Orders"))
            {
                System.out.println("Redirection to My Orders successful.");
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error verifying redirection to My Orders.");
            e.printStackTrace();
        }
        return false;
    }

    // Method to click on Continue button in "Place an order?" popup
    public void clickContinueForPlaceOrder() {
        try {
//            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'][normalize-space()='Continue'])[2]")));
//            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jss1630.jss1652.jss1631 button")));
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jss1689.MuiBox-root.css-0 button .MuiTouchRipple-root.css-w0pj6f")));
            continueButton.click();
            System.out.println("Continue button for Place an order? clicked.");
        } catch (Exception e) {
            System.out.println("Error clicking the Continue button for Place an order?");
            e.printStackTrace();
        }
    }

    public void clickContinueForUpdateExistingOrder() {
        try {
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'][normalize-space()='Continue'])[2]")));
            continueButton.click();
            System.out.println("Continue button for existing order clicked.");
        } catch (Exception e) {
            System.out.println("Error clicking the Continue button for existing order");
            e.printStackTrace();
        }
    }

    // Check if the popup is inside an iframe (if needed)
    public boolean isPopupInsideIframe() {
        try {
            WebElement iframeElement = driver.findElement(By.tagName("iframe"));
            return iframeElement != null;
        } catch (Exception e) {
            return false;
        }
    }

     //Switch to iframe if the popup is inside it
    public void switchToIframe() {
        try {
            WebElement iframeElement = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframeElement);
            System.out.println("Switched to iframe successfully.");
        } catch (Exception e) {
            System.out.println("Error while switching to iframe: " + e.getMessage());
        }
    }

     //Main method to handle the popup text and act accordingly
    public void handlePopup() {
        try {
            // Check if the popup is inside an iframe
            if (isPopupInsideIframe()) {
                // Switch to the iframe
                switchToIframe();
            }

            // Now check the popup text
            String popupMessage = getPopupText();
            if (popupMessage.contains("Place an order?")) {
                clickContinueForPlaceOrder();  // Click continue for "Place an order?"
            } else if (popupMessage.contains("existing order(s)")) {
                selectUpdateExistingOrder();  // Select "Update existing order"
            } else {
                System.out.println("Unexpected popup message: " + popupMessage);
            }

        } catch (Exception e) {
            System.out.println("Error while handling the popup: " + e.getMessage());
        }
    }
}
