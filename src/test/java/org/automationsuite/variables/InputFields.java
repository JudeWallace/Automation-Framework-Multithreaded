package org.automationsuite.variables;

import org.automationsuite.ElementInformation.ElementAttributeTypes;
import org.automationsuite.ElementInformation.ElementInfoStruct;
import org.automationsuite.pages.BasePage;

public class InputFields extends BasePage {

    /**
     * Calls a method to return input field element information.
     * @param inputFieldName The user facing label name for the input field.
     * @return A struct containing input field information.
     */
    public ElementInfoStruct findAndReturnInputFieldForApplication(String inputFieldName){
        ElementInfoStruct elementInfo = new ElementInfoStruct();

        switch(inputFieldName.toLowerCase()){
            case "username" -> elementInfo.setElementInformation(ElementAttributeTypes.xPath, "test-field-input");
            case "password" -> elementInfo.setElementInformation(ElementAttributeTypes.DataTestId, "test-password-input");
            case "another test input field name" -> elementInfo.setElementInformation(ElementAttributeTypes.DataTestId, "");
            default -> throw new IllegalStateException("Unexpected value: " + inputFieldName.toLowerCase());
        }
        return elementInfo;
    }
}
