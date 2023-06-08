package com.homeoffice.technicaltask.setup;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
public class WebDriverFactory {

    private static Optional<WebDriver> INSTANCE = Optional.empty();
    private static final String HEADLESS = "--headless";
    private static final String WINDOW_SIZE = "--window-size=1920,1080";

    /**
     * @param browserType       eg: Chrome, Firefox,... etc
     * @param pageLoadStrategy  eg: none OR normal OR eager
     * @param shouldRunHeadless eg: true OR false
     */
    public static void createWebDriver(BrowserType browserType, PageLoadStrategy pageLoadStrategy, boolean shouldRunHeadless) {
        INSTANCE = Optional.of(buildWebDriver(browserType, pageLoadStrategy, shouldRunHeadless));
    }

    public static WebDriver getWebDriver() {
        return INSTANCE.orElseThrow(() ->
                new IllegalStateException("driver not created, create an instance of WebDriverFactory first"));
    }

    public static void closeWebDriver() {
        log.info("completed testing...");
        log.info("closing web driver...");
        if (INSTANCE.isPresent()) {
            getWebDriver().quit();
        }
    }

    public static void deleteAllCookies() {
        getWebDriver().manage().deleteAllCookies();
    }

    public static void embedScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                WebDriver webDriver = getWebDriver();
                if (webDriver != null) {
                    byte[] screenshot = Shutterbug.shootPage(webDriver, Capture.FULL_SCROLL).getBytes();
                    scenario.attach(screenshot, "image/png", scenario.getName() + " screenshot");
                } else {
                    log.error(format("******** webDriver is null, can't obtain screenshot for %s, id: %s, line: %s ********",
                            scenario.getName(), scenario.getId(), scenario.getLine()));
                }
            } catch (WebDriverException wde) {
                log.error(wde.getMessage());
            } catch (ClassCastException cce) {
                log.error("The cast error is : " + cce.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param browserType       eg: Chrome, Firefox,... etc
     * @param pageLoadStrategy  eg: none OR normal OR eager
     * @param shouldRunHeadless eg: true OR false
     * @return WebDriver
     */
    private static WebDriver buildWebDriver(BrowserType browserType, PageLoadStrategy pageLoadStrategy, boolean shouldRunHeadless) {

        switch (browserType) {
            case Chrome:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setPageLoadStrategy(pageLoadStrategy);
                if (shouldRunHeadless) {
                    log.info("running driver in headless mode");
                    chromeOptions.addArguments(HEADLESS, WINDOW_SIZE);
                    chromeOptions.setPageLoadStrategy(pageLoadStrategy);
                }
                return WebDriverManager.chromedriver().capabilities(chromeOptions).create();

            case Firefox:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(HEADLESS, WINDOW_SIZE);
                return WebDriverManager.firefoxdriver().capabilities(firefoxOptions).create();

            default:
                throw new WebDriverException("The requested driver not found.");
        }
    }

    public static void navigateTo(String url) {
        log.debug("navigating to url: {}", url);
        getWebDriver().navigate().to(url);
        log.debug("navigated to url: {}", url);
    }
}
