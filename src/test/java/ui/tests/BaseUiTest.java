package ui.tests;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ui.pages.HomePage;
import ui.utils.BasePageFactory;
import ui.utils.DriverFactory;

import static ui.config.ConfigurationManager.config;

public abstract class BaseUiTest {

    private static final Logger log = LoggerFactory.getLogger(BaseUiTest.class);
    private static final ThreadLocal<HomePage> homePage = new ThreadLocal<>();

    protected HomePage getHomePage() {
        return homePage.get();
    }

    @BeforeMethod
    public void beforeMethod() {
        log.info("===== Starting test setup for thread: {} =====", Thread.currentThread().getId());
        DriverFactory.initDriver();
        WebDriver webDriver = DriverFactory.getDriver();

        log.info("Maximizing browser window and navigating to base URL: {}", config().baseUrl());
        webDriver.manage().window().maximize();
        webDriver.get(config().baseUrl());

        HomePage page = BasePageFactory.createInstance(webDriver, HomePage.class);
        page.handleConsentPopup();
        homePage.set(page);
        log.info("Test setup completed successfully");
    }

    @AfterMethod
    public void afterMethod() {
        log.info("===== Tearing down test for thread: {} =====", Thread.currentThread().getId());
        DriverFactory.quitDriver();
    }
}
