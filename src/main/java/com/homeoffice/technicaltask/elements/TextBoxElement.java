package com.homeoffice.technicaltask.elements;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class TextBoxElement extends BaseElement {

    public TextBoxElement(FindElementOnPageBy findingBy, String finder) {
        super(FindElementOnPageBy.convert(findingBy, finder));
    }

    public void setText(String text) {
        clearText();
        if (text != null) {
            getElement().sendKeys(text);
        }
    }

    public void clearText() {
        await().atMost(WAIT_TIME_IN_SECONDS, SECONDS).until(this::clearElement);
    }
}