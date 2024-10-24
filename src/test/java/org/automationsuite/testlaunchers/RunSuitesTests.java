package org.automationsuite.testlaunchers;

import io.cucumber.core.feature.FeatureWithLines;
import io.cucumber.core.options.RuntimeOptions;
import io.cucumber.core.options.RuntimeOptionsBuilder;
import io.cucumber.core.runtime.Runtime;
import io.cucumber.tagexpressions.TagExpressionParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class RunSuitesTests {

    private final String scenarioTags = System.getProperty("TAGS") == null ? "@Test" : System.getProperty("TAGS");

    @Test
    public void executeSuite() {
        System.out.println("Hello Automation Suite");
        log.info("Tags being used for the execution: " + scenarioTags);

        // Load all feature files
        List<String> featureFiles = loadFeatureFiles();

        featureFiles.parallelStream().forEach(this::runFeatureFile);
    }

    private void runFeatureFile(String featureFile) {
        RuntimeOptionsBuilder runtimeOptions = new RuntimeOptionsBuilder()
                .addFeature(FeatureWithLines.parse(featureFile))
                .addPluginName("com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
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
        } catch (Exception ex) {
            log.error("Error running feature file: " + featureFile, ex);
        }
    }

    private List<String> loadFeatureFiles() {
        try {
            return Files.walk(Paths.get("src/test/resources/Features"))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".feature"))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error loading feature files", e);
            return List.of();
        }
    }
}
