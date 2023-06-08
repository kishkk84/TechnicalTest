package com.homeoffice.technicaltask.pages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.homeoffice.technicaltask.setup.WebDriverFactory.navigateTo;

@Slf4j
@Component
public class BasePage {

    @Value("${ds.baseUrl}")
    public String baseUrl;

    public void navigateToCazoo() {
        navigateTo(baseUrl);
    }
}
