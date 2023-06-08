package com.homeoffice.technicaltask.pages;

import com.homeoffice.technicaltask.elements.ButtonElement;
import com.homeoffice.technicaltask.elements.TextBoxElement;

import static com.homeoffice.technicaltask.elements.FindElementOnPageBy.ID;
import static com.homeoffice.technicaltask.elements.FindElementOnPageBy.XPATH;

public class ValueMyCarPage {

    public static final ButtonElement ACCEPT_ALL = new ButtonElement(XPATH, "//span[text()='Accept All']");
    public static final TextBoxElement ENTER_REG = new TextBoxElement(ID, "vrm");
    public static final ButtonElement START_VALUATION = new ButtonElement(XPATH, "//span[text()='Start valuation']");
}