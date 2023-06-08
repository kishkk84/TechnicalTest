package com.homeoffice.technicaltask.elements;

import com.homeoffice.technicaltask.setup.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static com.homeoffice.technicaltask.setup.WebDriverFactory.getWebDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Slf4j
public abstract class BaseElement {

    private static final int DEFAULT_POLL_INTERVAL = 10;
    private static final int DEFAULT_POLL_TIMEOUT = 30;
    public static final long WAIT_TIME_IN_SECONDS = 30L;
    private final By finder;

    protected BaseElement(By finder) {
        this.finder = finder;
    }

    protected WebElement getElement() {
        log.debug("getting element by: {}", finder);
        WebDriver driver = getWebDriver();
        WebElement element = findVisibleElement();
        log.debug("found visible element by: {}", finder);

        new Actions(driver).moveToElement(element).build().perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'end', inline: 'start'});", element);
        log.debug("successfully moved to element: {}", finder);
        return element;
    }

    private WebElement findVisibleElement() {
        WebDriver driver = WebDriverFactory.getWebDriver();

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_POLL_TIMEOUT))
                .pollingEvery(Duration.ofMillis(DEFAULT_POLL_INTERVAL))
                .ignoring(WebDriverException.class)
                .until(visibilityOfElementLocated(finder));
    }

    public boolean isDisplayed() {
        WebDriver driver = WebDriverFactory.getWebDriver();
        return !driver.findElements(finder).isEmpty();
    }

    public boolean isDisplayedAfterWait() {
        WebDriver driver = WebDriverFactory.getWebDriver();
        try {
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(3))
                    .ignoring(WebDriverException.class)
                    .until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(finder)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean clickElement() {
        try {
            new FluentWait<>(getWebDriver())
                    .withTimeout(Duration.ofSeconds(DEFAULT_POLL_TIMEOUT))
                    .pollingEvery(Duration.ofMillis(DEFAULT_POLL_INTERVAL))
                    .until(ExpectedConditions.elementToBeClickable(getElement()));

            getElement().click();
        } catch (WebDriverException wde) {
            return false;
        }

        return true;
    }

    public boolean clearElement() {
        try {
            getElement().clear();
        } catch (WebDriverException wde) {
            return false;
        }
        return true;
    }
}