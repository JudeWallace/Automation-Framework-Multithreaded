package org.automationsuite.testlaunchers;

import io.cucumber.core.feature.FeatureWithLines;
import io.cucumber.core.options.RuntimeOptions;
import io.cucumber.core.options.RuntimeOptionsBuilder;
import io.cucumber.core.runtime.Runtime;
import io.cucumber.tagexpressions.TagExpressionParser;
import lombok.extern.slf4j.Slf4j;
import org.automationsuite.reporting.ExtentReportManager;
import org.junit.Test;

import java.net.URI;

@Slf4j
public class SerialExecutionTestIGNORE {

    private final String scenarioTags = System.getProperty("TAGS") == null ? "@Test" : System.getProperty("TAGS");

    @Test
    public void executeSuite() {
        System.out.println("Hello Automation Suite");

        log.info("Tags being used for the execution: " + scenarioTags);

        RuntimeOptionsBuilder runtimeOptions = new RuntimeOptionsBuilder()
                .addFeature(FeatureWithLines.parse("classpath:Features"))
                .addPluginName("com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
                .addPluginName("org.automationsuite.plugins.CucumberStepListener")
                .addGlue(URI.create("classpath:org.automationsuite.steps"))
                .addDefaultSummaryPrinterIfNotDisabled();


        runtimeOptions.addTagFilter(TagExpressionParser.parse(scenarioTags));


        RuntimeOptions runtimeConfig = runtimeOptions.build();

        Runtime runtime = Runtime.builder()
                .withRuntimeOptions(runtimeConfig)
                .withClassLoader(() -> Thread.currentThread().getContextClassLoader())
                .build();

        try {
            runtime.run();
            ExtentReportManager.flushReport();
        } catch (Exception ex) {
            System.exit(1);
        }
    }
}
