package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SuitePartnerPage {
    WebDriver driver;
    WebDriverWait wait;

    private String itemName;
    private String itemPrice;

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

    // Method to select items from all categories
    public void selectItemsFromCategories() {
        // Wait until the categories are visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased wait time
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[contains(@class, 'MuiTypography-root')]")));

        // Scroll the page to load elements
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Locate all categories
        List<WebElement> categories = driver.findElements(By.xpath("//h2[contains(@class, 'MuiTypography-root')]"));

        // Print the XPath of categories for inspection
        System.out.println("XPath for categories: //h2[contains(@class, 'MuiTypography-root')]");

        // Iterate over each category
        for (WebElement category : categories) {
            String categoryText = category.getText();
            System.out.println("Selecting items for category: " + categoryText);

            // Print the XPath for category link to check manually
            String categoryLinkXpath = "//h2[contains(text(),'" + categoryText + "')]/following-sibling::div//a";
            System.out.println("XPath for category link: " + categoryLinkXpath);

            // Wait until the category link is clickable
            WebElement categoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(categoryLinkXpath)));

            // Click on the category to show items
            categoryLink.click();

            // Wait for the items to be loaded again after the page reloads
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[contains(text(),'" + categoryText + "')]/following-sibling::div//a")));

            // Re-fetch items under the category after the page has changed or updated
            List<WebElement> items = driver.findElements(By.xpath("//h2[contains(text(),'" + categoryText + "')]/following-sibling::div//a"));

            // Print the XPath of items for inspection
            System.out.println("XPath for items under the category: " + "//h2[contains(text(),'" + categoryText + "')]/following-sibling::div//a");

            // If no items are found, continue to the next category
            if (items.isEmpty()) {
                System.out.println("No items found in category: " + categoryText);
                continue;
            }

            // Click the first item under the category
            WebElement firstItem = items.get(0);
            System.out.println("Selecting item: " + firstItem.getText());

            itemName = firstItem.getText().split("\n")[0];  // Assuming the first line is the item name
            itemPrice = firstItem.getText().split("\n")[1];  // Assuming the second line is the price

            firstItem.click();

            // Wait for the Add to Order button to appear
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='+ Add to order']")));

            // Click the Add to Order button
            WebElement addToOrderButton = driver.findElement(By.xpath("//button[normalize-space()='+ Add to order']"));
            addToOrderButton.click();
            System.out.println("Added one item to order. Going to the next category.");

            // Navigate back to the category list
            //driver.navigate().back();

            // Wait for the page to reload
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[contains(@class, 'MuiTypography-root')]")));

            // Sleep for a moment before selecting the next category
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void selectItemsFromCategoriesv2() {
        // Wait until the categories are visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased wait time
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[contains(@class, 'MuiTypography-root')]")));

        // Locate all categories
        List<WebElement> categories = driver.findElements(By.xpath("//h2[contains(@class, 'MuiTypography-root')]"));

        // Get the number of categories
        int categoryCount = categories.size();
        System.out.println("Total categories found: " + categoryCount);

        // Proceed with selecting only one item from the first category
        if (categoryCount > 0) {
            WebElement category = categories.get(0); // Select the first category
            String categoryText = category.getText();
            System.out.println("Selecting one item from category: " + categoryText);

            // Dynamically create the XPath for the category using its text
            String categoryXpath = "//h2[contains(text(),'" + categoryText + "')]/following-sibling::div//a";
            System.out.println("XPath for category link: " + categoryXpath);

            // Wait until the category link is clickable
            WebElement categoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(categoryXpath)));

            // Click on the category to show items
            categoryLink.click();

            // Wait for the items to be loaded under the category
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(categoryXpath)));

            // Re-fetch the items after the page has changed or updated
            List<WebElement> items = driver.findElements(By.xpath(categoryXpath));

            // If no items are found, skip this category
            if (items.isEmpty()) {
                System.out.println("No items found in category: " + categoryText);
                return; // No items, return from the method
            }

            // Select the first item under the first category
            WebElement firstItem = items.get(0);
            System.out.println("Selecting item: " + firstItem.getText());
            firstItem.click();

            // Wait for the Add to Order button to appear and be clickable
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='+ Add to order']")));

            // Click the Add to Order button
            WebElement addToOrderButton = driver.findElement(By.xpath("//button[normalize-space()='+ Add to order']"));
            addToOrderButton.click();
            System.out.println("Added one item to order.");

            // Proceed with the rest of the test after adding the item
        } else {
            System.out.println("No categories found.");
        }



    }

    public void viewCartAndSelect() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set an explicit wait for 10 seconds
        WebElement viewCartButton = null;
        int attempts = 0;

        while (attempts < 3) {  // Retry up to 3 times
            try {
                // Wait for the element to be clickable before interacting
                viewCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'MuiButton-root')]//span[contains(text(), 'View Cart')]")));
                viewCartButton.click();  // Perform the click action
                break;  // Break if the click was successful
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("StaleElementReferenceException encountered. Retrying... Attempt " + attempts);
                try {
                    Thread.sleep(1000);  // Wait for a second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();  // Handle interrupt
                }
            }
        }

        if (attempts == 3) {
            System.out.println("Failed to click the View Cart button after 3 attempts.");
        }
    }












}
