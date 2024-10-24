package org.automationsuite.steps;

import io.cucumber.java.Scenario;
import org.automationsuite.pages.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.automationsuite.pages.PageManager;

public class Hooks extends BasePage {

    @Before
    public void beforeScenarioSetup(Scenario scenario){
        pageIndex().getDriverPage().createWebBrowser();
        // create a web browser
    }

    @After
    public void afterScenario() {
        // Exit driver thread
        pageIndex().getDriverPage().driverShutdown();
        PageManager.cleanup();
    }
}
