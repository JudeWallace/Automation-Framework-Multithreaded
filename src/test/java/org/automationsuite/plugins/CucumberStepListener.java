package org.automationsuite.plugins;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;

public class CucumberStepListener implements ConcurrentEventListener {

    // ThreadLocal to store error messages independently for each thread
    private static final ThreadLocal<String> failedStepError = new ThreadLocal<>();

    /**
     * Retrieve the failed step error for the current thread.
     */
    public static String getFailedStepError() {
        String err = failedStepError.get();
        System.out.println(err);
        failedStepError.remove();
        return err;
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestStepFinished.class, new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished testStepFinished) {
                if (testStepFinished.getTestStep() instanceof PickleStepTestStep) {
                    if (!testStepFinished.getResult().getStatus().isOk()) {
                        // Capture the full stack trace of the error
                        Throwable error = testStepFinished.getResult().getError();
                        failedStepError.set(formatErrorStack(error));
                    }
                }
            }
        });
    }

    /**
     * Formats the error stack trace to a readable string, removing unnecessary characters.
     */
    private String formatErrorStack(Throwable error) {
        StringBuilder formattedError = new StringBuilder();
        formattedError.append(error.toString()).append("\n");

        for (StackTraceElement element : error.getStackTrace()) {
            formattedError.append("\t at ").append(element.toString()).append("\n");
        }

        // Remove special characters if needed
        return formattedError.toString().replaceAll("[<>\\[\\]]", "");
    }
}