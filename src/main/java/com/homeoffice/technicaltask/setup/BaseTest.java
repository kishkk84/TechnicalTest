package com.homeoffice.technicaltask.setup;

import com.homeoffice.technicaltask.pages.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.homeoffice.technicaltask.setup.WebDriverFactory.closeWebDriver;
import static com.homeoffice.technicaltask.setup.WebDriverFactory.createWebDriver;
import static com.homeoffice.technicaltask.setup.WebDriverFactory.deleteAllCookies;
import static com.homeoffice.technicaltask.setup.WebDriverFactory.embedScreenshot;

@Slf4j
public class BaseTest {

    @Autowired
    private BasePage basePage;

    @Value("${ds.browser.type}")
    private BrowserType browserType;

    @Value("${ds.headless}")
    private boolean shouldRunHeadless;

    @Value("${ds.pageLoadStrategy}")
    private PageLoadStrategy pageLoadStrategy;


    @Before
    public void setUp(Scenario scenario) {
        log.info("Starting..." + scenario.getName() + " Test");
        createWebDriver(browserType, pageLoadStrategy, shouldRunHeadless);
        deleteAllCookies();
        basePage.navigateToCazoo();
    }

    @After
    public void close(Scenario scenario) {
        if (scenario.isFailed()) {
            log.info(String.format("Scenario %s has failed, attaching a screenshot", scenario.getName()));
            embedScreenshot(scenario);
        }
        log.info("Running tear down..." + scenario.getName() + " Test");
        closeWebDriver();
    }
}