package ui.utils;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomWebDriver implements WebDriver, TakesScreenshot {
    private static final Logger log = LoggerFactory.getLogger(CustomWebDriver.class);
    private final WebDriver driver;

    public CustomWebDriver(WebDriver driver) {
        this.driver = driver;
        log.info("CustomWebDriver initialized with driver: {}", driver.getClass().getSimpleName());
    }

    @Override
    public void get(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        log.debug("Current URL: {}", url);
        return url;
    }

    @Override
    public String getTitle() {
        String title = driver.getTitle();
        log.debug("Page title: {}", title);
        return title;
    }

    @Override
    public String getPageSource() {
        log.debug("Fetching page source");
        return driver.getPageSource();
    }

    @Override
    public void close() {
        log.info("Closing current window");
        driver.close();
    }

    @Override
    public void quit() {
        log.info("Quitting browser");
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        Set<String> handles = driver.getWindowHandles();
        log.debug("Window handles: {}", handles);
        return handles;
    }

    @Override
    public String getWindowHandle() {
        String handle = driver.getWindowHandle();
        log.debug("Current window handle: {}", handle);
        return handle;
    }

    @Override
    public TargetLocator switchTo() {
        log.debug("Switching context");
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        log.debug("Navigating...");
        return driver.navigate();
    }

    @Override
    public Options manage() {
        log.debug("Managing driver options");
        return driver.manage();
    }

    @Override
    public WebElement findElement(By by) {
        log.info("Finding element by: {}", by);
        WebElement rawElement = driver.findElement(by);
        return new ExtendedWebElement(driver, rawElement, by);
    }

    @Override
    public List<WebElement> findElements(By by) {
        log.info("Finding elements by: {}", by);
        List<WebElement> elements = driver.findElements(by);
        List<WebElement> wrappedElements = new ArrayList<>();
        for (WebElement el : elements) {
            wrappedElements.add(new ExtendedWebElement(driver, el, by));
        }
        return wrappedElements;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        log.info("Capturing screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(target);
    }
}
