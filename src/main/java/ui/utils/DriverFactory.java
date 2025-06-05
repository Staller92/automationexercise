package ui.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ui.config.ConfigurationManager.config;

public class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = config().browser().toUpperCase();
        log.info("Initializing WebDriver for browser: {}", browser);

        try {
            WebDriver rawDriver = BrowserFactory.valueOf(browser).createInstance();
            CustomWebDriver superDriver = new CustomWebDriver(rawDriver);
            driver.set(superDriver);
            log.info("WebDriver initialized successfully for thread: {}", Thread.currentThread().getId());
        } catch (IllegalArgumentException e) {
            log.error("Invalid browser name '{}' specified in configuration", browser, e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to initialize WebDriver", e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            log.warn("Attempted to get WebDriver before initialization for thread: {}", Thread.currentThread().getId());
        }
        return currentDriver;
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                log.info("Quitting WebDriver for thread: {}", Thread.currentThread().getId());
                currentDriver.quit();
            } catch (Exception e) {
                log.warn("Exception while quitting WebDriver", e);
            } finally {
                driver.remove();
                log.info("WebDriver removed from thread-local storage");
            }
        } else {
            log.warn("No WebDriver to quit for thread: {}", Thread.currentThread().getId());
        }
    }

    public static boolean hasDriverStarted() {
        return driver.get() != null;
    }
}
