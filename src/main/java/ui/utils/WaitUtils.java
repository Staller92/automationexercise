package ui.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static ui.config.ConfigurationManager.config;

public final class WaitUtils {
    private static final Logger log = LoggerFactory.getLogger(WaitUtils.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(config().timeout());

    private WaitUtils() {}

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        log.debug("Waiting for element to be clickable: {}", locator);
        WebElement element = new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
        log.debug("Element is clickable: {}", locator);
        return element;
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        log.debug("Waiting for element to be visible: {}", locator);
        WebElement element = new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        log.debug("Element is visible: {}", locator);
        return element;
    }

    public static boolean waitForInvisible(WebDriver driver, By locator) {
        log.debug("Waiting for element to become invisible: {}", locator);
        boolean result = new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        log.debug("Element is invisible: {}", locator);
        return result;
    }
}
