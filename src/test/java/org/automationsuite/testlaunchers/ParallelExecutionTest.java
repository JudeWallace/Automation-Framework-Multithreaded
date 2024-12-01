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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class ParallelExecutionTest {

        private final String scenarioTags = System.getProperty("TAGS") == null ? "@Test" : System.getProperty("TAGS");

        @Test
        public void executeParallelSuite() {
                log.info("Running tests with tags: " + scenarioTags);

                // Load all feature files
                List<String> featureFiles = loadFeatureFiles();

                // Define a fixed thread pool with a size of 3
                int threadCount = 2;
                ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

                try {
                        // Submit each feature file to the thread pool
                        featureFiles.forEach(featureFile -> executorService.submit(() -> runFeatureFile(featureFile)));
                } finally {
                        // Shutdown the executor service gracefully after all tasks are completed
                        executorService.shutdown();
                        try {
                                // Wait for all threads to complete execution
                                if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                                        executorService.shutdownNow();
                                }
                                ExtentReportManager.flushReport();
                        } catch (InterruptedException e) {
                                log.error("Thread pool interrupted while waiting for termination", e);
                                executorService.shutdownNow();
                        }
                }
        }

        private void runFeatureFile(String featureFile) {
                log.info("Running feature file: " + featureFile);
                RuntimeOptionsBuilder runtimeOptions = new RuntimeOptionsBuilder()
                        .addFeature(FeatureWithLines.parse(featureFile))
                        .addGlue(URI.create("classpath:org.automationsuite.steps"))
                        .addDefaultSummaryPrinterIfNotDisabled()
                        .addPluginName("pretty")
                        .addPluginName("org.automationsuite.plugins.CucumberStepListener"); // Your custom plugin

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

