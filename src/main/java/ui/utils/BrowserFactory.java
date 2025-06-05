package ui.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.config.ConfigurationManager;

public enum BrowserFactory {

    CHROMIUM {
        @Override
        public WebDriver createInstance() {
            log.info("Initializing Chrome browser instance");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            if (ConfigurationManager.config().headless()) {
                log.debug("Running Chrome in headless mode");
                options.addArguments("--headless=new");
            }

            if (ConfigurationManager.config().incognito()) {
                log.debug("Running Chrome in incognito mode");
                options.addArguments("--incognito");
            }

            return new ChromeDriver(options);
        }
    },

    FIREFOX {
        @Override
        public WebDriver createInstance() {
            log.info("Initializing Firefox browser instance");
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            if (ConfigurationManager.config().headless()) {
                log.debug("Running Firefox in headless mode");
                options.addArguments("--headless");
            }

            if (ConfigurationManager.config().incognito()) {
                log.debug("Running Firefox in private mode");
                options.addArguments("-private");
            }

            return new FirefoxDriver(options);
        }
    };

    private static final Logger log = LoggerFactory.getLogger(BrowserFactory.class);

    public abstract WebDriver createInstance();
}
