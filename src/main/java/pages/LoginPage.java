
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver)
    {
        this.driver=driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    By emailField= By.xpath("//input[@name='email']");
    By passwordField= By.xpath("//input[@name='password']");
    By loginButton= By.cssSelector("button[type='submit']");
    By errorMessage = By.xpath("//div[@class='MuiAlert-message css-1xsto0d']");



    public void enterEmail(String email)
    {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password)
    {
        WebElement passInput=wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passInput.clear();
        passInput.sendKeys(password);
    }

    public void clickLogin()
    {
        WebElement loginBtn=wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }

    // Method to check if login was successful
    // This method verifies that login redirects to the event page
    public boolean isLoginSuccessful() {
        // Now, we check if the event page loads after login by calling the EventPage's method
        EventPage eventPage = new EventPage(driver);
        return eventPage.isEventPageDisplayed();  // Returns true if the event page is displayed
    }
    // Method to capture login error message
    public String getLoginErrorMessage()
    {
        try
        {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Set explicit wait
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiAlert-message.css-1xsto0d")));  // Using CSS selector
            return errorMsg.getText();  // Return the error message text
        }
        catch (Exception e)
        {
            return "No error message displayed";  // Return this if no error message is found
        }
    }





}
