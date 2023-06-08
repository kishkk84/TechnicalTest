package com.homeoffice.technicaltask.elements;

import org.openqa.selenium.By;

public enum FindElementOnPageBy {
    XPATH,
    CSS,
    ID,
    TEXT,
    TAG;

    public static By convert(FindElementOnPageBy type, String finder) {
        switch (type) {
            case CSS:
                return By.cssSelector(finder);
            case XPATH:
                return By.xpath(finder);
            case TEXT:
                return By.linkText(finder);
            case ID:
                return By.id(finder);
            case TAG:
                return By.tagName(finder);
            default:
                throw new UnsupportedOperationException("type not supported: " + type);
        }
    }
}