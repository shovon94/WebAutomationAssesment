package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuitePreferencePage {
    WebDriver driver;
    WebDriverWait wait;

    public SuitePreferencePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    // Locator for the Save button
//    By saveButton = By.xpath("//button[normalize-space()='Save']");
    By saveButton = By.cssSelector("button[type='submit']");

    // Method to click the Save button
    public void clickSave() {
        try {
            // Log that we are waiting for the Save button to be clickable
            System.out.println("Waiting for Save button to be clickable...");

            // Wait for the Save button to become clickable
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));

            // Log that the Save button is found and clickable
            System.out.println("Save button is clickable, proceeding with click...");

            // Click the Save button
            saveBtn.click();

            // Log that the button was clicked
            System.out.println("Save button clicked.");
        } catch (Exception e) {
            // Log any exceptions or errors encountered
            System.out.println("Error while trying to click the Save button.");
            e.printStackTrace();
        }
    }
}
