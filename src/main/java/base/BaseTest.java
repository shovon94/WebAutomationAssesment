package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class BaseTest
{
    protected static WebDriver driver;
    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void setup()
    {
        if (driver == null)
        {  // Initialize driver only if it's null
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(ConfigReader.getProperty("baseURL")); // Open website only once
            logger.info("WebDriver initialized, browser maximized and website is launched!");
        }
    }

    @AfterSuite
    public void teardown() throws InterruptedException {

        if (driver != null)
        {
            driver.quit();
            logger.info("Browser closed and WebDriver session ended.");
            driver = null;
        }

    }

    public WebDriver getDriver() {
        return driver;  // Return the WebDriver instance
    }
}
