package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver webDriver;

    protected BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        initComponents();
    }

    public void initComponents() {
    }

    public void handleConsentPopup() {
        try {
            webDriver.findElement(By.cssSelector("button.fc-cta-consent")).click();
        } catch (TimeoutException ignored) {
        }
    }
}
