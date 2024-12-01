package org.automationsuite.other;

import org.automationsuite.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Timers extends BasePage {

    private final int urlTimeLimit = 60;
    private final int elementPresenceTimeLimit = 45;
    private final int elementChecksTimeLimit = 45;

    /**
     * A test timer used to quickly test steps during development. Only ever used as a last resort beyond that.
     *
     * @param timeInSeconds Time in seconds to wait.
     * @throws InterruptedException May throw an InterruptedException.
     */

    public void threadSleepTestTimer(int timeInSeconds) throws InterruptedException {
        System.out.println("Initialising test sleep timer for " + timeInSeconds + " seconds.");
        Thread.sleep(Duration.ofSeconds(timeInSeconds));
        System.out.println("Test sleep timer complete.");
    }

    /**
     * A timer that waits for a page to be ready.
     */

    public void waitUntilPageIsReady() {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(30)).until((
                ExpectedCondition<Boolean>) webdriver -> {
            assert webdriver != null;
            return ((JavascriptExecutor) webdriver).executeScript("return document.readyState").equals("complete");
        });
    }

    /**
     * A timer that waits for the presence of an element via its xPath attribute.
     *
     * @param elementLocator The locator of the element.
     */

    public void waitUntilPresenceOfXpathElementIsLocated(String elementLocator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(elementLocator)));
    }

    /**
     * A timer that waits for the presence of an element via its class attribute.
     *
     * @param elementLocator The locator of the element.
     */

    public void waitUntilPresenceOfClassElementIsLocated(String elementLocator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfElementLocated(By.className(elementLocator)));
    }

    /**
     * A timer that waits for the presence of an element via its id attribute.
     *
     * @param elementLocator The locator of the element.
     */
    public void waitUntilPresenceOfIdElementIsLocated(String elementLocator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfElementLocated(By.id(elementLocator)));
    }

    /**
     * A timer that waits for the presence of an element via its custom css attribute.
     *
     * @param elementLocator The locator of the element.
     */
    public void waitUntilPresenceOfCustomAttributeElementIsLocated(String elementLocator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(elementLocator)));
    }

    /**
     * A timer that waits for an element to be visible.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilElementIsVisible(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.visibilityOf(element));
    }

    /**
     * A timer that waits for an element to be invisible.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilElementIsInvisible(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.invisibilityOf(element));
    }

    /**
     * A timer that waits for an element to be in a state where it is clickable.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilElementIsClickable(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * A timer that waits for the current url to match what the expected one is.
     *
     * @param urlToBe The expected url.
     */

    public void waitUntilUrlEquals(String urlToBe) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(urlTimeLimit)).until(
                ExpectedConditions.urlToBe(urlToBe));
    }

    /**
     * A timer that waits until the url contains a string
     *
     * @param urlToContain The part that should be in the url.
     */

    public void waitUntilUrlContains(String urlToContain) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(urlTimeLimit)).until(
                ExpectedConditions.urlContains(urlToContain));
    }

    // todo javadoc
    public void waitUntilUrlHasChanged(String originalUrl) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(urlTimeLimit)).until(
                driver -> !driver.getCurrentUrl().equals(originalUrl));
    }

    /**
     * A timer that waits until the value in an element matches the expected value.
     *
     * @param element      The WebElement reference to the element in context.
     * @param valueToMatch The expected string.
     */

    public void waitUntilValueInFieldMatches(WebElement element, String valueToMatch) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.textToBePresentInElementValue(element, valueToMatch));
    }

    /**
     * A timer that waits until an element is enabled.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilElementIsEnabled(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until((
                ExpectedCondition<Boolean>) _ -> element.isEnabled());
    }

    /**
     * A timer that waits until an element is disabled.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilElementIsDisabled(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until((
                ExpectedCondition<Boolean>) _ -> !element.isEnabled());
    }

    /**
     * A timer that waits until an elements value is empty.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilFieldValueIsEmpty(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(element, "value")));
    }

    public void waitUntilFieldValueIsNotEmpty(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.attributeToBeNotEmpty(element, "value"));
    }

    public void waitUntilFieldValueIsSet(WebElement element) {
        try {
            new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(1)).until(
                    ExpectedConditions.attributeToBeNotEmpty(element, "value"));
        } catch (TimeoutException ex) {
            //System.out.println("No text");
        }
    }

    /**
     * A timer that waits until an attribute in an element contains a string.
     *
     * @param element       The WebElement reference to the element in context.
     * @param attribute     The attribute to check if the value contains a string.
     * @param textToContain The string that the element's attribute should contain.
     */

    public void waitUntilAttributeContains(WebElement element, String attribute, String textToContain) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.attributeContains(element, attribute, textToContain));
    }

    /**
     * A timer that waits until an attribute in an element does not contain a string.
     *
     * @param element          The WebElement reference to the element in context.
     * @param attribute        The attribute to check if the value does not contain a string.
     * @param textToNotContain The string that the element's attribute should not contain.
     */

    public void waitUntilAttributeDoesNotContain(WebElement element, String attribute, String textToNotContain) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementChecksTimeLimit)).until(
                ExpectedConditions.not(ExpectedConditions.attributeContains(element, attribute, textToNotContain)));
    }

    /**
     * A timer to wait for the presence of a nested element with a custom css attribute.
     *
     * @param parentElement The WebElement reference to the parent element in context.
     * @param locator       The locator of the child element.
     */

    public void waitUntilPresenceOfNestedCustomAttributeElement(WebElement parentElement, String locator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, By.cssSelector(locator)));
    }

    /**
     * A timer to wait for the presence of a nested element with an id attribute.
     *
     * @param parentElement The WebElement reference to the parent element in context.
     * @param locator       The locator of the child element.
     */

    public void waitUntilPresenceOfNestedIdElement(WebElement parentElement, String locator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, By.id(locator)));
    }

    /**
     * A timer to wait for the presence of a nested element with a xpath attribute.
     *
     * @param parentElement The WebElement reference to the parent element in context.
     * @param locator       The locator of the child element.
     */

    public void waitUntilPresenceOfNestedXpathElement(WebElement parentElement, String locator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, By.xpath(locator)));
    }

    /**
     * A timer to wait for the presence of a nested element with a class attribute.
     *
     * @param parentElement The WebElement reference to the parent element in context.
     * @param locator       The locator of the child element.
     */

    public void waitUntilPresenceOfNestedClassElement(WebElement parentElement, String locator) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(elementPresenceTimeLimit)).until(
                ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, By.className(locator)));
    }

    /**
     * A timer to wait until an element is checked.
     *
     * @param element The WebElement reference to the element in context.
     */

    public void waitUntilElementIsChecked(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(5)).until((
                ExpectedCondition<Boolean>) _ -> element.isSelected());
    }

    public void waitUntilElementTextIsNotEmpty(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(8)).until((
                ExpectedCondition<Boolean>) _ -> !element.getText().isEmpty());
    }

    public void waitUntilElementTextEquals(WebElement element, String expectedText) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(8)).until((
                ExpectedCondition<Boolean>) _ -> !element.getText().equalsIgnoreCase(expectedText));
    }

    public void waitUntilElementIsStale(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(element));
    }

    public void waitUntilTableIncrease(WebElement table, int initialSize) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(5)).until((
                ExpectedCondition<Boolean>) _ -> table.findElements(By.xpath(".//tbody/tr")).size() == (initialSize + 1));
    }

    public void waitUntilElementIsRefreshed(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }

    public void waitUntilElementIsRefreshedAndInvisible(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(element)));
    }

    public void waitUntilTableIsDisplayed(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(8)).until((
                ExpectedCondition<Boolean>) _ -> element.isDisplayed());
    }

    public void waitUntilElementHasRefreshed(WebElement element) {
        new WebDriverWait(webDriver().getWebDriver(), Duration.ofSeconds(5)).until(
                ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }
}
