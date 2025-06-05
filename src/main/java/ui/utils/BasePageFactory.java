package ui.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.pages.BasePage;

import java.lang.reflect.Constructor;

public final class BasePageFactory {
    private static final Logger logger = LoggerFactory.getLogger(BasePageFactory.class);

    private BasePageFactory() {}

    public static <T extends BasePage> T createInstance(WebDriver webDriver, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(WebDriver.class);
            T instance = constructor.newInstance(webDriver);
            //instance.initComponents();
            logger.debug("Page instance created: {}", clazz.getSimpleName());
            return instance;
        } catch (Exception e) {
            logger.error("Failed to create instance of {}", clazz.getSimpleName(), e);
            throw new RuntimeException("Failed to create instance of " + clazz.getSimpleName(), e);
        }
    }
}
