package org.automationsuite.pages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BasePage {

    protected @NonNull PageManager pageIndex(){
        return PageManager.getInstance();
    }

    protected SharedFunctionalityPage shared(){ return pageIndex().getSharedFunctionality(); }
}
