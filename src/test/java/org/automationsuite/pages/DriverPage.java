package org.automationsuite.pages;

import org.automationsuite.config.WebDriverDefinition;
import org.openqa.selenium.*;
import java.time.Duration;

public class DriverPage {

    private WebDriver driver;

    /**
     * Getter for driver instance
     * @return The driver instance
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Shut down the driver and clean up
     */
    public void driverShutdown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Create a Chrome web browser for each scenario to be run on
     */
    public void createWebBrowser() {
        if (driver == null) {
            driver = WebDriverDefinition.WEBKIT.createDriver();
            driver.manage().window().setSize(new Dimension(1920, 1080));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
    }

    /**
     * @param URL: webpage url to be navigated too
     */
    public void navigateToURL(String URL) {
        driver.get(URL);  // Uses the driver instance for this thread
    }
}
