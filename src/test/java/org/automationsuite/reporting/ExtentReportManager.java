package org.automationsuite.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.automationsuite.pages.BasePage;
import org.automationsuite.plugins.CucumberStepListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ExtentReportManager extends BasePage {

    private static final ExtentReports extentReports = init();
    private final ConcurrentHashMap<String, ExtentTest> featureTests = new ConcurrentHashMap<>();

    private final ThreadLocal<Scenario> threadLocalScenario = new ThreadLocal<>();
    private final ThreadLocal<ExtentTest> threadLocalScenarioTest = new ThreadLocal<>();
    private final ThreadLocal<Deque<PickleStepTestStep>> threadLocalScenarioSteps = ThreadLocal.withInitial(LinkedList::new);


    private static ExtentReports init(){
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports/AutomationSuiteReport.html");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().thumbnailForBase64(true);
        sparkReporter.viewConfigurer().viewOrder().as(new ViewName[]{ViewName.DASHBOARD, ViewName.TEST, ViewName.LOG, ViewName.CATEGORY}).apply();

        sparkReporter.config().setCss(
                ".container { max-width: 100%; overflow-x: hidden; } " + // Ensure main container fits
                        ".col, .card-panel { max-width: 100%; overflow-x: hidden; } " + // Fit columns and panels
                        "img { max-width: 100%; height: auto; display: block; margin: auto; } " + // Resize images
                        ".log-text { white-space: pre-wrap; word-wrap: break-word; overflow-wrap: break-word; }" +
                        ".card-title .node span { color: white; font-weight: bold; }"// Wrap text logs
        );


        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        return extentReports;
    }

    public void beforeScenario(Scenario scenario) {
        threadLocalScenario.set(scenario);

        String featureFileName = FilenameUtils.getBaseName(threadLocalScenario.get().getUri().toString());

        ExtentTest featureTest = featureTests.computeIfAbsent(featureFileName, name -> extentReports.createTest(name));
        ExtentTest scenarioNode = featureTest.createNode(threadLocalScenario.get().getName());
        threadLocalScenarioTest.set(scenarioNode);

        threadLocalScenarioSteps.get().addAll(getAllScenarioSteps());
    }

    public void afterScenarioStep(){
        if(!threadLocalScenarioSteps.get().isEmpty()){
            PickleStepTestStep step = threadLocalScenarioSteps.get().pop();
            logStep(step, false);
        }
    }

    public void afterScenarioCleanup(){
        if(!threadLocalScenarioSteps.get().isEmpty()) {
            while (!threadLocalScenarioSteps.get().isEmpty()){
                PickleStepTestStep step = threadLocalScenarioSteps.get().pop();
                logStep(step, true);
            }
        }

        threadLocalScenarioTest.remove();
        threadLocalScenarioSteps.remove();

    }

    private List<PickleStepTestStep> getAllScenarioSteps() {
        Field d;
        TestCaseState tcs;
        Field tc;
        TestCase testCase;

        try{
            d = threadLocalScenario.get().getClass().getDeclaredField("delegate");
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        d.setAccessible(true);

        try{
            tcs = (TestCaseState) d.get(threadLocalScenario.get());
        }
        catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }

        try{
            tc = tcs.getClass().getDeclaredField("testCase");
        }
        catch (NoSuchFieldException e){
            throw new RuntimeException(e);
        }
        tc.setAccessible(true);

        try{
            testCase = (TestCase) tc.get(tcs);
        }
        catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }

        List<PickleStepTestStep> scenarioSteps = testCase.getTestSteps()
                .stream()
                .filter(x -> x instanceof PickleStepTestStep)
                .map(x -> (PickleStepTestStep) x).toList();

        scenarioSteps.forEach(step -> System.out.println("Thread: " + Thread.currentThread().threadId() + " " + step.getStep().getText()));
        return scenarioSteps;
    }

    private void logStep(PickleStepTestStep step, Boolean testPreviouslyFailed){
        String stepText = "<span style='color: white; font-weight: bold;'>" + step.getStep().getText() + "</span>";
        String keyword = step.getStep().getKeyword().toLowerCase().trim();
        ExtentTest stepNode;

        switch (keyword){
            case "given" -> stepNode = threadLocalScenarioTest.get().createNode(Given.class, stepText);
            case "when" -> stepNode = threadLocalScenarioTest.get().createNode(When.class, stepText);
            case "and" -> stepNode = threadLocalScenarioTest.get().createNode(And.class, stepText);
            case "then" -> stepNode = threadLocalScenarioTest.get().createNode(Then.class, stepText);
            case "*" -> stepNode = threadLocalScenarioTest.get().createNode(Asterisk.class, stepText);
            default -> throw new IllegalStateException("Unexpected value: " + keyword);
        }

        if(threadLocalScenario.get().isFailed() && testPreviouslyFailed){
            stepNode.skip(MarkupHelper.createLabel("This step has been skipped.", ExtentColor.ORANGE));
        } else if(threadLocalScenario.get().isFailed()){
            stepNode.fail(MarkupHelper.createLabel("Step has failed.", ExtentColor.RED));
            // todo - fix this jank around error logging
            String errStack = CucumberStepListener.getFailedStepError();
            log.error("Error occurred in the \"{}\" step resulting in the error: {}", step.getStep().getText(), errStack);
            stepNode.log(Status.INFO, "<div class='log-text'>" + errStack + "</div>");

            stepNode.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64," + takeScreenshot()).build());
        } else {
            stepNode.pass(MarkupHelper.createLabel("Step passed.", ExtentColor.GREEN));
        }
    }

    private String takeScreenshot(){
        return ((TakesScreenshot) pageIndex().getDriverPage().getWebDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static void flushReport(){
        extentReports.flush();
    }
}
