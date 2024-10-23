package org.automationsuite.pages;

import lombok.Getter;
import org.automationsuite.config.WebDriverDefinition;
import org.openqa.selenium.*;

import java.time.Duration;

public class DriverPage {

    @Getter
    static WebDriver driver;

    public void createWebBrowser(){
        driver = WebDriverDefinition.WEBKIT.createDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}
