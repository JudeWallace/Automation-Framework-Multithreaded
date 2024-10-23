package org.automationsuite.testlaunchers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RunSuitesTests {

    private final String scenarioTags = System.getProperty("TAGS") == null ? "@FullExecution" : System.getProperty("TAGS");

    @Test
    public void executeSuite() {
        System.out.println("Hello Automation Suite");

        log.info(System.getenv("TAGS"));

    }
}