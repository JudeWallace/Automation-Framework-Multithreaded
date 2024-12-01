package org.automationsuite.ElementInformation;

import lombok.Getter;

@Getter
public class ElementInfoStruct {

    private String attributeValue;
    private ElementAttributeTypes attributeType;

    /**
     * Sets element information in a struct for functionality in SharedFunctionalityPage.
     * @param attributeType Which attribute is being referenced (class, xpath etc.)
     * @param attributeValue The locator value of the attribute being referenced.
     */

    public void setElementInformation(ElementAttributeTypes attributeType, String attributeValue){
        this.attributeType = attributeType;
        this.attributeValue = attributeValue;
    }
}