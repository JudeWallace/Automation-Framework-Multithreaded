package org.automationsuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;
import org.automationsuite.pages.BasePage;

@Slf4j
public class TestSteps extends BasePage {

    @Given("I navigate to the BBC website")
    public void iNavigateToTheBBCWebsite() throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        System.out.println("Running on thread: "+ threadId);

        log.info("Navigating to the BBC website");
        pageIndex().getDriverPage().navigateToURL("https://www.bbc.com");
        Thread.sleep(2000);
    }

    @And("I log a message {string}")
    public void iLogAMessage(String arg0) {
        log.info("Thread: " + Thread.currentThread().getId() + " logging execution " + arg0);
    }
}
