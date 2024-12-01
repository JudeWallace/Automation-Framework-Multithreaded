package org.automationsuite.pages;

import org.automationsuite.ElementInformation.ElementAttributeTypes;
import org.automationsuite.config.WebDriverDefinition;
import org.openqa.selenium.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DriverPage extends BasePage{

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
     * Navigates to an application's environment URL if stored in the solution.
     * This also waits for the page state to return ready.
     */
    /*public void navigateToEnvironmentUrl(){
       driver.get(pageIndex().getEnvironments().getEnvironmentURL());
        pageIndex().getTimers().waitUntilPageIsReady();
        pageIndex().getEnvironments().getEnvironmentVersion();
        pageIndex().getEnvironments().getEnvironmentName();
    }*/

    /**
     * Navigates to a provider url.
     * This also waits for the page state to return ready.
     * @param url The url to navigate to.
     */

    public void navigateToProvidedUrl(String url){
        driver.get(url);
        pageIndex().getTimers().waitUntilPageIsReady();
    }

    /**
     * Returns the current url.
     * @return The current url.
     */

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    /**
     *
     */
    public void createAndSwitchToNewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public ArrayList<String> getCurrentTabs(){
        return new ArrayList<>(driver.getWindowHandles());
    }

    /**
     * Assuming there is more than 1 tab, find and switch to another tab
     */
    public void switchToAnotherTab(int tabNumber){
        ArrayList<String> tabs = getCurrentTabs();
        while(tabs.size() <= 1){
            tabs.clear();
            tabs = getCurrentTabs();
        }
        driver.switchTo().window(tabs.get(tabNumber - 1));
    }

    public void closeCurrentTabAndGoToFirstOpenTab(){
        ArrayList<String> tabs = getCurrentTabs();
        driver.close();
        if(!tabs.isEmpty()) {
            driver.switchTo().window(tabs.getFirst());
        }
    }

    /**
     * Find and return a single element.
     * @param attributeType The attribute type to reference.
     * @param locator The attribute value to reference.
     * @param requireWait Are you expecting the presence of the element? Should be set to true 99% of the time unless you're
     *                    not always expecting the element to be appearing.
     * @return A WebElement reference to an element.
     */
    public WebElement findWaitAndGetSingularElement(ElementAttributeTypes attributeType, String locator, boolean requireWait){
        final String cssLocator;
        switch (attributeType){
            case Id -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfIdElementIsLocated(locator);
                }
                return driver.findElement(By.id(locator));
            }
            case xPath -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfXpathElementIsLocated(locator);
                }
                return driver.findElement(By.xpath(locator));
            }
            case ClassName -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfClassElementIsLocated(locator);
                }
                return driver.findElement(By.className(locator));
            }
            case DataTestId -> {
                cssLocator = "[data-testid='" + locator + "']";
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfCustomAttributeElementIsLocated(cssLocator);
                }
                return driver.findElement(By.cssSelector(cssLocator));
            }
        }
        return null;
    }

    /**
     * Find and return a list of elements.
     * @param attributeType The attribute type to reference.
     * @param locator The attribute value to reference.
     * @param requireWait Are you expecting the presence of the element? Should be set to true 99% of the time unless you're
     *                    not always expecting the element to be appearing.
     * @return A list of WebElement reference for multiple elements.
     */
    public List<WebElement> findWaitAndGetMultipleElements(ElementAttributeTypes attributeType, String locator, boolean requireWait){
        final String cssLocator;
        switch (attributeType) {
            case Id -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfIdElementIsLocated(locator);
                }
                return driver.findElements(By.id(locator));
            }
            case xPath -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfXpathElementIsLocated(locator);
                }
                return driver.findElements(By.xpath(locator));
            }
            case ClassName -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfClassElementIsLocated(locator);
                }
                return driver.findElements(By.className(locator));
            }
            case DataTestId -> {
                cssLocator = "[data-testid='" + locator + "']";
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfCustomAttributeElementIsLocated(cssLocator);
                }
                return driver.findElements(By.cssSelector(cssLocator));
            }
        }
        return null;
    }

    /**
     * Find and returns an element
     * @param parentElement The parent element to search elements from.
     * @param attributeType The attribute type to reference.
     * @param locator The attribute value to reference.
     * @param requireWait Are you expecting the presence of the element? Should be set to true 99% of the time unless you're
     *                    not always expecting the element to be appearing.
     * @return A list of WebElement reference for multiple elements.
     */
    public WebElement findWaitAndGetSingularElementFromParent(WebElement parentElement, ElementAttributeTypes attributeType, String locator, boolean requireWait){
        final String cssLocator;
        pageIndex().getAssertions().notNullAssertion(parentElement);
        switch(attributeType){
            case Id -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedIdElement(parentElement, locator);
                }
                return parentElement.findElement(By.id(locator));
            }
            case xPath -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedXpathElement(parentElement, locator);
                }
                return parentElement.findElement(By.xpath(locator));
            }
            case ClassName -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedClassElement(parentElement, locator);
                }
                return parentElement.findElement(By.className(locator));
            }
            case DataTestId -> {
                cssLocator = "[data-testid='" + locator + "']";
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedCustomAttributeElement(parentElement, cssLocator);
                }
                return parentElement.findElement(By.cssSelector(cssLocator));
            }
        }
        return null;
    }

    /**
     * Find and return a list of elements.
     * @param parentElement The parent element to search elements from.
     * @param attributeType The attribute type to reference.
     * @param locator The attribute value to reference.
     * @param requireWait Are you expecting the presence of the element? Should be set to true 99% of the time unless you're
     *                    not always expecting the element to be appearing.
     * @return A list of WebElement reference for multiple elements.
     */
    public List<WebElement> findWaitAndGetMultipleElementsFromParent(WebElement parentElement, ElementAttributeTypes attributeType, String locator, boolean requireWait){
        final String cssLocator;
        pageIndex().getAssertions().notNullAssertion(parentElement);
        switch(attributeType){
            case Id -> {
                pageIndex().getTimers().waitUntilPresenceOfNestedIdElement(parentElement, locator);
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedIdElement(parentElement, locator);
                }
                return parentElement.findElements(By.id(locator));
            }
            case xPath -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedXpathElement(parentElement, locator);
                }
                return parentElement.findElements(By.xpath(locator));
            }
            case ClassName -> {
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedClassElement(parentElement, locator);
                }
                return parentElement.findElements(By.className(locator));
            }
            case DataTestId -> {
                cssLocator = "[data-testid='" + locator + "']";
                if(requireWait) {
                    pageIndex().getTimers().waitUntilPresenceOfNestedCustomAttributeElement(parentElement, cssLocator);
                }
                return parentElement.findElements(By.cssSelector(cssLocator));
            }
        }
        return null;
    }

    /**
     * @param URL: webpage url to be navigated too
     */
    public void navigateToURL(String URL) {
        driver.get(URL);  // Uses the driver instance for this thread
    }

}
