package pages;

import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver)
    {
        this.driver=driver;
    }

    public String getTitle()
    {
        return driver.getTitle();
    }
    public void launchWeb()
    {
        driver.get(ConfigReader.getProperty("baseURL"));
    }
}


