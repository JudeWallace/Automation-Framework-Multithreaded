package org.automationsuite.pages;

import lombok.Getter;
import org.automationsuite.other.Assertions;
import org.automationsuite.other.Timers;
import org.automationsuite.variables.Buttons;
import org.automationsuite.variables.InputFields;

@Getter
public class PageManager {

    private static final ThreadLocal<PageManager> instance = ThreadLocal.withInitial(PageManager::new);

    // Pages
    private final DriverPage driverPage = new DriverPage();
    private final SharedFunctionalityPage sharedFunctionality = new SharedFunctionalityPage();

    // Utils
    private final Timers timers = new Timers();
    private final Assertions assertions = new Assertions();

    // Variables
    private final Buttons buttons = new Buttons();
    private final InputFields inputFields = new InputFields();

    /**
     * Class Constructor
     */
    private PageManager(){}

    /**
     * Get the threads instance
     * @return Instance for running thread
     */
    public static PageManager getInstance(){
        return instance.get();
    }

    /**
     * Clean up of the instance to save memory
     */
    public static void cleanup() {
        instance.remove();
    }
}
