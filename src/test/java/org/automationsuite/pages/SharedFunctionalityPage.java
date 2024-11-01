package org.automationsuite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;


public class SharedFunctionalityPage extends BasePage {

    public void testBBCHeader(){
        WebElement header = pageIndex().getDriverPage().getWebDriver().findElement(By.xpath("//*[@id=\"header-content\"]/div/div"));
        assertEquals("MADE TO FAIL", header.getText());
    }
}
