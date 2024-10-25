package org.automationsuite.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import org.apache.commons.io.FilenameUtils;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentReportManager {
    private static ExtentReports extentReports = init();
    private static ConcurrentHashMap<String, ExtentTest> featureTests = new ConcurrentHashMap<>();

    private static final ThreadLocal<Scenario> threadLocalScenario = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> threadLocalScenarioTest = new ThreadLocal<>();
    private static final ThreadLocal<Deque<PickleStepTestStep>> threadLocalScenarioSteps = ThreadLocal.withInitial(LinkedList::new);


    private static ExtentReports init(){
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports/AutomationSuiteReport.html");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Results");
        sparkReporter.config().setTheme(Theme.DARK);

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        return extentReports;
    }

    public static void beforeScenario(Scenario scenario) {
        threadLocalScenario.set(scenario);

        String featureFileName = FilenameUtils.getBaseName(threadLocalScenario.get().getUri().toString());
        System.out.println("Thread: " + Thread.currentThread().threadId() + " running scenarios in -> " + featureFileName);

        ExtentTest featureTest = featureTests.computeIfAbsent(featureFileName, name -> extentReports.createTest(name));
        System.out.println(threadLocalScenario.get().getName());
        ExtentTest scenarioNode = featureTest.createNode(threadLocalScenario.get().getName());
        threadLocalScenarioTest.set(scenarioNode);

        threadLocalScenarioSteps.get().addAll(getAllScenarioSteps());
    }

    public static void afterScenarioStep(){
        if(!threadLocalScenarioSteps.get().isEmpty()){
            PickleStepTestStep step = threadLocalScenarioSteps.get().pop();
            logStep(step);
        }
    }

    public static void afterScenarioCleanup(){
        threadLocalScenarioTest.remove();
        threadLocalScenarioSteps.remove();

    }

    private static List<PickleStepTestStep> getAllScenarioSteps() {
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

    private static void logStep(PickleStepTestStep step){
        String stepText = step.getStep().getText();
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

        if(threadLocalScenario.get().isFailed()){
            // todo logic for failed step
        } else {
            stepNode.pass(MarkupHelper.createLabel("Step passed.", ExtentColor.GREEN));
        }
    }

    public static void flushReport(){
        if (extentReports != null){
            extentReports.flush();
        }
    }
}
