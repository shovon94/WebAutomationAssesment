package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver)
    {
        this.driver=driver;
    }

    By emailField= By.xpath("//input[@id='mui-2']");
    By passwordField= By.xpath("//input[@id='mui-3']");
    By loginButton= By.cssSelector("button[type='submit']");

    public void enterEmail()
    {
        driver.findElement(emailField).sendKeys("email");
    }

    public void enterPassword()
    {
        driver.findElement(passwordField).sendKeys("pass");
    }

    public void clickLogin()
    {
        driver.findElement(loginButton).click();
    }


}
