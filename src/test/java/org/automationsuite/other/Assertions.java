package org.automationsuite.other;

import org.automationsuite.pages.BasePage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class Assertions extends BasePage {
    /**
     * An assertion to check if 2 values are equal. The expected and actual values must be of the same type.
     * I.E - 2 WebElements, 2 Strings, etc.
     * This will also send a message to the log output files.
     * @param expectedValue The expected value.
     * @param actualValue The actual value.
     */

    public void equalAssertion(Object expectedValue, Object actualValue){
        try {
            assertEquals(expectedValue, actualValue);
            log.info("""
                    AssertEquals Successful:\s
                    Expected - ({}) {}
                    Actual - ({}) {}""", expectedValue.getClass().getSimpleName(), expectedValue
                    , actualValue.getClass().getSimpleName(), actualValue);
        }
        catch (AssertionError aEx){
            log.error("""
                    AssertEquals Failure:\s
                    Expected - ({}) {}
                    Actual - ({}) {}""", expectedValue.getClass().getSimpleName(), expectedValue
                    , actualValue.getClass().getSimpleName(), actualValue);
            throw new AssertionError();
        }
    }

    /**
     * An assertion to check a boolean equals true.
     * This will also send a message to the log output files.
     * @param actualValue The actual value.
     */
    public void trueBooleanAssertion(boolean actualValue){
        try {
            assertTrue(actualValue);
            log.info("""
                    AssertTrue Successful:\s
                    Actual - ({}) {}""" , "boolean", true);
        }
        catch (AssertionError aEx){
            log.error("""
                    AssertTrue Failure:\s
                    Actual - ({}) {}""" , "boolean", actualValue);
            throw new AssertionError();
        }
    }

    /**
     * An assertion to check a boolean equals false.
     * This will also send a message to the log output files.
     * @param actualValue The actual value.
     */

    public void falseBooleanAssertion(boolean actualValue){
        try {
            assertFalse(actualValue);
            log.info("""
                    AssertFalse Successful:\s
                    Actual - ({}) {}""" , "boolean", false);
        }
        catch (AssertionError aEx){
            log.error("""
                    AssertFalse Failure:\s
                    Actual - ({}) {}""" , "boolean", actualValue);
            throw new AssertionError();
        }
    }

    /**
     * An assertion to check if an expected value returns null.
     * This will also send a message to the log output files.
     * @param expectedNullValue A value we expect to be null.
     */

    public void nullAssertion(Object expectedNullValue){
        try {
            assertNull(expectedNullValue);
            log.info("""
                    AssertNull Successful:\s
                    Actual - {}""" , "null");
        }
        catch (AssertionError aEx){
            assertNotNull(expectedNullValue);
            log.error("""
                    AssertNull Failure:\s
                    Actual - ({}) {}""" , expectedNullValue.getClass().getSimpleName(), expectedNullValue);
            throw new AssertionError();
        }
    }

    /**
     * An assertion to check if an expected value returns not null.
     * This will also send a message to the log output files.
     * @param expectedNotNullValue A value we expect to not be null.
     */

    public void notNullAssertion(Object expectedNotNullValue){
        try {
            assertNotNull(expectedNotNullValue);
            log.info("""
                    AssertNotNull Successful:\s
                    Actual - ({}) {}""" , expectedNotNullValue.getClass().getSimpleName(), expectedNotNullValue);
        }
        catch (AssertionError aEx){
            log.error("""
                    AssertNotNull Failure:\s
                    Actual - {}""" , "null");
            throw new AssertionError();
        }
    }

    /**
     * An assertion to check if 2 values are not equal. The expected and actual values must be of the same type.
     * I.E - 2 WebElements, 2 Strings, etc.
     * This will also send a message to the log output files.
     * @param expectedValue The expected value.
     * @param actualValue The actual value.
     */

    public void notEqualAssertion(Object expectedValue, Object actualValue){
        try {
            assertNotEquals(expectedValue, actualValue);
            log.info("""
                    AssertNotEquals Successful:\s
                    Expected - ({}) {}
                    Actual - ({}) {}""", expectedValue.getClass().getSimpleName(), expectedValue
                    , actualValue.getClass().getSimpleName(), actualValue);
        }
        catch (AssertionError aEx){
            log.error("""
                    AssertNotEquals Failure:\s
                    Expected - ({}) {}
                    Actual - ({}) {}""", expectedValue.getClass().getSimpleName(), expectedValue
                    , actualValue.getClass().getSimpleName(), actualValue);
            throw new AssertionError();
        }
    }

    /**
     * An assertion to check if a body of text contains a string.
     * This will also send a message to the log output files.
     * @param bodyOfTextToCheck The body of text to search in.
     * @param valueToFindWithinText The value to find within that body.
     */

    public void stringContainsValueAssertion(String bodyOfTextToCheck, String valueToFindWithinText){
        try {
            assertTrue(valueToFindWithinText.toLowerCase().contains(bodyOfTextToCheck.toLowerCase()));
            log.info("""
                    AssertTrue Successful:\s
                    ({}) was found within the body of text passed through.""", valueToFindWithinText.toLowerCase());
        }
        catch (AssertionError aEx){
            log.error("""
                    AssertTrue Failure:\s
                    ({}) was not found within the body of text passed through.""", valueToFindWithinText.toLowerCase());
            throw new AssertionError();
        }
    }

    public void listContainsStringAssertion(List<String> listOfExpectedValues, String actualString){
        try {
            assert (listOfExpectedValues.contains(actualString.toLowerCase()));
            log.info("""
                    Assert Successful:\s
                    ({}) was found within the list passed through.""", actualString.toLowerCase());
        } catch (AssertionError aEx) {
            log.error("""
                    Assert Failure:\s
                    ({}) was not found in the list passed through.""", actualString.toLowerCase());
        }

    }
}
