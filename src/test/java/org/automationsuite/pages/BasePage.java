package org.automationsuite.pages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.automationsuite.other.Timers;

@Slf4j
public abstract class BasePage {

    protected @NonNull PageManager pageIndex(){
        return PageManager.getInstance();
    }

    protected SharedFunctionalityPage shared() { return pageIndex().getSharedFunctionality(); }

    protected DriverPage webDriver() { return pageIndex().getDriverPage();}

    protected Timers timers() { return pageIndex().getTimers();}
}
