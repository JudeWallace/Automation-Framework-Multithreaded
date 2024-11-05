package org.automationsuite.steps;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.an.E;
import lombok.extern.slf4j.Slf4j;
import org.automationsuite.pages.BasePage;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.automationsuite.pages.PageManager;
import org.automationsuite.reporting.ExtentReportManager;

@Slf4j
public class Hooks extends BasePage {

    private ExtentReportManager extentReportManager = new ExtentReportManager();

    @Before
    public void beforeScenarioSetup(Scenario scenario){
        log.info("Setting up the webdriver..");
        pageIndex().getDriverPage().createWebBrowser();
        extentReportManager.beforeScenario(scenario);
    }

    @AfterStep
    public void afterScenarioStep(){
        extentReportManager.afterScenarioStep();

    }

    @After
    public void afterScenario() {
        // Exit driver thread
        pageIndex().getDriverPage().driverShutdown();
        PageManager.cleanup();
        extentReportManager.afterScenarioCleanup();
    }
}
