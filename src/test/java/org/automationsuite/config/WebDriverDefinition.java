package org.automationsuite.config;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum WebDriverDefinition {

    WEBKIT(() -> {

        if(!Boolean.parseBoolean(System.getenv("PIPELINE_EXECUTION"))) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");//, "--headless");
            return new ChromeDriver(options);
        }
        else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--remote-allow-origins=*");
            try {
                return new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), options);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Chrome driver", e);
            }
        }
    });

    private final Supplier<RemoteWebDriver> driverFactory;

    /**
     * Gets the assigned WebDriver.
     * @return The WebDriver.
     */
    public RemoteWebDriver createDriver(){
        return driverFactory.get();
    }
}