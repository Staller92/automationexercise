package ui.utils;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExtendedWebElement implements WebElement {
    private static final Logger log = LoggerFactory.getLogger(ExtendedWebElement.class);

    private final WebDriver driver;
    private final WebElement element;
    private final By locator;

    public ExtendedWebElement(WebDriver driver, WebElement element, By locator) {
        this.driver = driver;
        this.element = element;
        this.locator = locator;
        log.debug("Wrapped WebElement: {}", locator);
    }

    @Override
    public WebElement findElement(By by) {
        log.debug("Finding child element by: {}", by);
        WebElement child = element.findElement(by);
        return new ExtendedWebElement(driver, child, by);
    }

    @Override
    public List<WebElement> findElements(By by) {
        log.debug("Finding child elements by: {}", by);
        List<WebElement> children = element.findElements(by);
        List<WebElement> result = new ArrayList<>();
        for (WebElement el : children) {
            result.add(new ExtendedWebElement(driver, el, by));
        }
        return result;
    }

    @Override
    public void click() {
        log.info("Clicking element: {}", locator);
        try {
            scrollIntoView();
            waitUntilClickable().click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Element not clickable via standard click. Using JS click for: {}", locator);
            clickViaJS();
        }
    }

    @Override
    public String getText() {
        try {
            String text = waitUntilVisible().getText();
            log.info("Getting text from element {}: {}", locator, text);
            return text;
        } catch (StaleElementReferenceException e) {
            log.warn("Stale element for locator {}. Retrying getText()", locator);
            return element.getText();
        }
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        log.info("Sending keys to element {}: {}", locator, keysToSend);
        waitUntilVisible().clear();
        element.sendKeys(keysToSend);
    }

    @Override public boolean isDisplayed() {
        boolean displayed = element.isDisplayed();
        log.debug("Element {} displayed: {}", locator, displayed);
        return displayed;
    }

    @Override public boolean isEnabled() {
        boolean enabled = element.isEnabled();
        log.debug("Element {} enabled: {}", locator, enabled);
        return enabled;
    }

    @Override public void clear() {
        log.debug("Clearing element: {}", locator);
        element.clear();
    }

    @Override public String getAttribute(String name) {
        String value = element.getAttribute(name);
        log.debug("Attribute '{}' of element {}: {}", name, locator, value);
        return value;
    }

    @Override public String getTagName() { return element.getTagName(); }
    @Override public String getCssValue(String name) { return element.getCssValue(name); }
    @Override public boolean isSelected() { return element.isSelected(); }
    @Override public Point getLocation() { return element.getLocation(); }
    @Override public Dimension getSize() { return element.getSize(); }
    @Override public Rectangle getRect() { return element.getRect(); }
    @Override public <X> X getScreenshotAs(OutputType<X> outputType) { return element.getScreenshotAs(outputType); }
    @Override public void submit() { element.submit(); }

    private WebElement waitUntilClickable() {
        return WaitUtils.waitForClickable(driver, locator);
    }

    private WebElement waitUntilVisible() {
        return WaitUtils.waitForVisible(driver, locator);
    }

    private void scrollIntoView() {
        log.debug("Scrolling element into view: {}", locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void clickViaJS() {
        log.debug("Clicking element via JS: {}", locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
