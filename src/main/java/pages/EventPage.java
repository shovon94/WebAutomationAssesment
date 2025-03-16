package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EventPage {
    WebDriver driver;
    WebDriverWait wait;

    public EventPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    // Locators for elements on the event page
    By eventPageHeader = By.xpath("//h4[normalize-space()='My Events']");
    By eventContainer = By.cssSelector(".MuiBox-root.css-178yklu");  // Event container where events are displayed
    By eventMessage = By.cssSelector(".MuiTypography-root.MuiTypography-body1"); // Message in case no events available
    By eventList = By.cssSelector(".MuiListItem-root.MuiListItem-gutters"); // Event list items (Automation Horizon, etc.)

    // Calendar date selector (selecting specific date)
    public By calendarDateSelector(String date) {
        return By.xpath("//button[contains(@class, 'MuiPickersDay-root') and text()='" + date + "']");
    }

    // Method to check if the event page is loaded
    public boolean isEventPageDisplayed() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(eventPageHeader));
            return header.getText().equals("My Events");  // Exact match for the header text
        } catch (Exception e) {
            return false;
        }
    }

    // Method to select the date on the calendar
    public void selectDate(String date) {
        By dateSelector = calendarDateSelector(date);
        WebElement dateButton = wait.until(ExpectedConditions.elementToBeClickable(dateSelector));
        dateButton.click();
    }

    // Method to check if events are available (If events list container is visible)
    public boolean areEventsAvailable() {
        try {
            WebElement eventContainerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(eventContainer));
            return eventContainerElement.isDisplayed(); // Check if the events container is displayed
        } catch (Exception e) {
            return false;
        }
    }

    // Method to check if the "No events" message is displayed
    public boolean isNoEventMessageDisplayed() {
        try {
            WebElement noEventMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(eventMessage));
            return noEventMessage.getText().equals("You either don't have any events on this date or the pre-order window is not open.");
        } catch (Exception e) {
            return false;
        }
    }

    // Method to select an event from the list dynamically (by event name)
    // Method to select an event if available
    // Method to select an event if available
    public void selectEvent(String eventName) {
        try {
            // Wait until the event list container is visible
            WebElement eventListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(eventList));

            // Log the available events before selection for verification
            System.out.println("Available events: " + eventListContainer.getText());

            // Build the XPath for the event
            String eventXPath = "//span[contains(text(), '" + eventName + "')]/ancestor::label";

            // Print the XPath being used for selection
            System.out.println("Using XPath to select event: " + eventXPath);

            // Wait until the event element is present
            WebElement eventElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(eventXPath)));

            // Scroll into view if needed
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", eventElement);

            // Wait until the event is clickable
            eventElement = wait.until(ExpectedConditions.elementToBeClickable(eventElement));

            // If the event is found, log and click it
            System.out.println("Selecting event: " + eventName);
            eventElement.click();
        } catch (Exception e) {
            System.out.println("Event not found: " + eventName);
        }
    }



    // Method to click on the Continue button (if available)
    public void clickContinue() {
        try {
            By continueButton = By.xpath("//button[contains(text(),'CONTINUE')]");
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            continueBtn.click();
        } catch (Exception e) {
            System.out.println("Continue button not found!");
        }
    }
}
