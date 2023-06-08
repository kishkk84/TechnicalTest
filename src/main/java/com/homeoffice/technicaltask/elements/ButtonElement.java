package com.homeoffice.technicaltask.elements;

public class ButtonElement extends BaseElement {

    public ButtonElement(FindElementOnPageBy findingBy, String finder) {
        super(FindElementOnPageBy.convert(findingBy, finder));
    }

    public void click() {
        clickElement();
    }
}