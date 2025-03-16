package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuitePartnerPage {
    WebDriver driver;
    WebDriverWait wait;

    public SuitePartnerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    // Locator for the Suite Partner header (to verify the redirection)
    By suitePartnerHeader = By.xpath("//h1[normalize-space()='CHEQ QA Suite Partner']");

    // Method to verify the redirection to the Suite Partner page
    public boolean verifyRedirectionToSuitePartner() {
        try {
            // Wait for the Suite Partner page header to be visible
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(suitePartnerHeader));

            // Check if the header contains the expected text
            if (header.getText().contains("CHEQ QA Suite Partner")) {
                System.out.println("Redirection to Suite Partner successful.");
                return true;  // Redirection successful
            } else {
                System.out.println("Redirection to Suite Partner failed. Header text: " + header.getText());
                return false;  // Failed redirection
            }
        } catch (Exception e) {
            System.out.println("Error verifying redirection to Suite Partner.");
            e.printStackTrace();
        }
        return false;  // Return false in case of any exception
    }
}
