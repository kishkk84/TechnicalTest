package com.homeoffice.technicaltask.pages;

import com.homeoffice.technicaltask.elements.ReadOnlyTextBoxElement;

import static com.homeoffice.technicaltask.elements.FindElementOnPageBy.XPATH;

public class AboutYourCarPage {

    public static final ReadOnlyTextBoxElement REGISTRATION_NUMBER = new ReadOnlyTextBoxElement(XPATH, "//div[@data-test-id='your-registration-number-summary']//p[1]");
    public static final ReadOnlyTextBoxElement CAR_SUMMARY = new ReadOnlyTextBoxElement(XPATH, "//div[@data-test-id='your-registration-number-summary']//p[2]");
}