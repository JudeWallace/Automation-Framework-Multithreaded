package org.automationsuite.pages;

import io.cucumber.datatable.DataTable;
import org.automationsuite.ElementInformation.ElementAttributeTypes;
import org.automationsuite.ElementInformation.ElementInfoStruct;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;


public class SharedFunctionalityPage extends BasePage {

    public void testBBCHeader(){
        WebElement header = webDriver().getWebDriver().findElement(By.xpath("//*[@id=\"header-content\"]/div/div"));
        assertEquals("MADE TO FAIL", header.getText());
    }

    /**
     * Scrolls an element into view.
     * @param element The WebElement reference to an element.
     */
    public void scrollIntoView(WebElement element){
        Actions scrollAction = new Actions(webDriver().getWebDriver());
        scrollAction.moveToElement(element);
        scrollAction.perform();
    }

    /**
     * Verifies an element exists
     * @param element The WebElement reference to an element.
     */
    public void verifyElementExists(WebElement element){
        pageIndex().getTimers().waitUntilElementIsVisible(element);
        pageIndex().getAssertions().notNullAssertion(element);
    }

    /*************** Button functionality *********************************************************************************/

    /**
     * Clicks on an element using javascript
     * @param elementToClick Element to be clicked
     */
    public void jsButtonClick(WebElement elementToClick){
        assertNotNull(elementToClick);
        pageIndex().getTimers().waitUntilElementIsVisible(elementToClick);
        pageIndex().getTimers().waitUntilElementIsClickable(elementToClick);

        JavascriptExecutor js = (JavascriptExecutor) webDriver().getWebDriver();

        scrollIntoView(elementToClick);
        js.executeScript("arguments[0].click();", elementToClick);
    }

    /**
     * Click on an element using the ActionBuilder package
     * @param element Element to be clicked
     */
    public void clickNonButtonElement(WebElement element){
        verifyElementExists(element);
        scrollIntoView(element);
        timers().waitUntilElementIsVisible(element);

        Actions actionBuilder = new Actions(webDriver().getWebDriver());
        Action clickNonButtonElement = actionBuilder
                .moveByOffset(0,5)
                .pause(Duration.ofMillis(500))
                .moveToElement(element)
                .click()
                .build();
        clickNonButtonElement.perform();
    }


    /**
     * Finds button information stored in the solution based on its label name.
     * @param buttonName The user facing label for a button in an application.
     * @return Returns a struct with button information.
     */
    private ElementInfoStruct findButton(String buttonName){
        return pageIndex().getButtons().findAndReturnButtonForApplication(buttonName.toLowerCase());
    }

    /**
     * The overarching method used to find and click a button.
     * @param buttonName The user facing label for a button in an application.
     */
    public void findAndClickButton(String buttonName){
        ElementInfoStruct buttonInformation = findButton(buttonName);
        WebElement button = webDriver().findWaitAndGetSingularElement(
                buttonInformation.getAttributeType(), buttonInformation.getAttributeValue(), true);
        clickObtainedButton(button);
    }

    /**
     * Checks that a button exists, scrolls it into view, waits until the element is clickable if applicable
     * then clicks it.
     * @param button The WebElement reference to a button.
     */
    public void clickObtainedButton(WebElement button){
        verifyElementExists(button);
        scrollIntoView(button);
        pageIndex().getTimers().waitUntilElementIsVisible(button);
        pageIndex().getTimers().waitUntilElementIsClickable(button);

        try{
            button.click();
        } catch (StaleElementReferenceException ex){
            System.out.println("Inside clickObtainedButton stale element");
        }
    }

/*************** Checkboxes functionality *****************************************************************************/
    /**
     * find the checkboxes and click on the one specified in the feature file
     * @param dataTable A datatable containing what options to select for each checkbox
     */
    public void findAndSelectTheSpecifiedCheckboxOption(DataTable dataTable){}

    /**
     * Untick all the checkboxes visible on the page
     */
    public void untickAllCheckboxes(){
        List<WebElement> checkboxes = webDriver().findWaitAndGetMultipleElements(ElementAttributeTypes.xPath,
                "//input[@type='checkbox']", true);

        checkboxes.forEach(box -> {
            if(box.isSelected()){
                shared().jsButtonClick(box);
            }
        });
    }

/*************** Input fields functionality ***************************************************************************/

    /**
     * Finds input field information stored in the solution based on its label name.
     * @param inputFieldName The user facing label for an input field in an application.
     * @return Returns a struct with input field information.
     */
    private ElementInfoStruct findInputField(String inputFieldName){
        return pageIndex().getInputFields().findAndReturnInputFieldForApplication(inputFieldName.toLowerCase());
    }

    /**
     * Entering a value into a field.
     * @param field The WebElement reference to an input field.
     * @param fieldValue The value to send to the field.
     */
    private void enterValueIntoField(WebElement field, String fieldValue) {
        field.sendKeys(fieldValue);
    }

    /**
     * Clearing fields. The WebDriverWait timer waits until the field is clear.
     * @param field The WebElement reference to the input field.
     */
    public void clearField(WebElement field) {
        int fieldValueLength = field.getAttribute("value").length();
        if(fieldValueLength > 0){
            for (int i = 0; i < fieldValueLength + 5; i++){
                field.sendKeys(Keys.BACK_SPACE);
                if(field.getAttribute("value").isEmpty()){
                    break;
                }
            }
        }
    }

    /**
     * The overarching method used to find and populate an input field with a string.
     * @param inputFieldLabelName The user facing label for an input field in an application.
     * @param fieldValue The value to send to the input field.
     */
    public void findInputFieldAndPopulateWithString(String inputFieldLabelName, String fieldValue){
        ElementInfoStruct inputFieldInformation = findInputField(inputFieldLabelName);
        WebElement inputField = webDriver().findWaitAndGetSingularElement
                (inputFieldInformation.getAttributeType(), inputFieldInformation.getAttributeValue(), true);
        clearAndPopulateField(inputField, fieldValue);
    }

    /**
     * This method verifies the input field exists, clears and checks the field is empty then
     * it enters the value into it.
     * @param field The WebElement reference to an input field.
     * @param fieldValue The value to send to the input field.
     */
    public void clearAndPopulateField(WebElement field, String fieldValue) {
        verifyElementExists(field);
        clearField(field);
        enterValueIntoField(field, fieldValue);
    }


/*************** Dropdown functionality *******************************************************************************/

    /**
     * Returns all options for any dropdown
     * @return A list of WebElements of options from the dropdown.
     */
    private List<WebElement> returnDropdownOptions(){ return null;}

    /**
     * Returns the option currently selected in the dropdown box
     * Note: Set elementAttributeTypes to null if you wish to find the dropdown based on the label and not Id,xPath...
     * @param elementAttributeTypes Element attribute type of the dropdown
     * @param dropdown Relative information for the dropdown
     * @return The text of the option selected
     */
    public String getSelectedDropdownOption(ElementAttributeTypes elementAttributeTypes, String dropdown) {
        switch (elementAttributeTypes) {
            case Id -> {
                return new Select(webDriver().findWaitAndGetSingularElement(ElementAttributeTypes.Id, dropdown, true)).getAllSelectedOptions().getFirst().getText();
            }
            case xPath, ClassName, DataTestId -> {
                return null;
            }
        }
        return "";
    }


    /**
     * Returns the dropdown element
     * @param dropdownLabel The label associated with the dropdown box
     * @return The Select web element of the dropdown box
     */
    public WebElement findAndReturnDropdownElement(String dropdownLabel){ return null; }

    /**
     * This method finds the dropdown
     * @param labelValue The label next to the dropdown box we are trying to access
     * @param desiredOption The option from the dropdown box to select
     */
    public void findAndSelectDropdownOption(String labelValue, String desiredOption){
        //todo -> use select to handle this
    }

}
