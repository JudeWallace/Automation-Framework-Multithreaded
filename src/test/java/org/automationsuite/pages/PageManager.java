package org.automationsuite.pages;

import lombok.Data;
import lombok.Getter;

@Data
public class PageManager {

    private static final ThreadLocal<PageManager> instance = ThreadLocal.withInitial(PageManager::new);

    // Pages
    @Getter
    private final DriverPage driverPage = new DriverPage();
    private final SharedFunctionalityPage sharedFunctionality = new SharedFunctionalityPage();

    //util

    /**
     * Class Constructor
     */
    private PageManager(){}

    /**
     * Get the threads instance
     * @return: Instance for running thread
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
