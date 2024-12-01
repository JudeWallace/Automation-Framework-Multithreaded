package org.automationsuite.variables;

import org.automationsuite.ElementInformation.ElementAttributeTypes;
import org.automationsuite.ElementInformation.ElementInfoStruct;
import org.automationsuite.pages.BasePage;

public class Buttons extends BasePage {

    /**
     * Calls a method to return button element information.
     * @param buttonName The user facing label name for the button.
     * @return A struct containing button information.
     */

    public ElementInfoStruct findAndReturnButtonForApplication(String buttonName){
        ElementInfoStruct buttonInfo = new ElementInfoStruct();

        switch(buttonName.toLowerCase()){
            case "example 1" -> buttonInfo.setElementInformation(ElementAttributeTypes.DataTestId, "test-button-1");
            case "example 2" -> buttonInfo.setElementInformation(ElementAttributeTypes.DataTestId, "test-button-2" );
            default -> throw new IllegalStateException("Unexpected value: " + buttonName.toLowerCase());
        }
        return buttonInfo;
    }
}