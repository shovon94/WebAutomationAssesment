package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EventPage {
    WebDriver driver;
    WebDriverWait wait;

    public EventPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    // Locators for elements on the event page
    By eventPageHeader = By.xpath("//h4[normalize-space()='My Events']"); // Update this XPath if necessary

    // Method to check if the event page is loaded
    public boolean isEventPageDisplayed() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(eventPageHeader));
            return header.getText().equals("My Events");  // Exact match for the header text
        } catch (Exception e) {
            return false; // If element is not found or not visible, return false
        }
    }
}
