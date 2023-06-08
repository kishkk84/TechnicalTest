package com.homeoffice.technicaltask.elements;

import java.util.Optional;

public class ReadOnlyTextBoxElement extends BaseElement {

    public ReadOnlyTextBoxElement(FindElementOnPageBy findingBy, String finder) {
        super(FindElementOnPageBy.convert(findingBy, finder));
    }

    public String getText() {
        return Optional.of(getElement().getText())
                .filter(t -> !t.equals(""))
                .orElse(Optional.ofNullable(getElement().getAttribute("value")).orElse(""));
    }
}
