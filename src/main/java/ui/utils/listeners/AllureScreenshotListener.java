package ui.utils.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.utils.DriverFactory;

public class AllureScreenshotListener implements TestLifecycleListener {

    private static final Logger log = LoggerFactory.getLogger(AllureScreenshotListener.class);

    @Override
    public void beforeTestStop(TestResult result) {
        if (DriverFactory.hasDriverStarted()) {
            log.debug("beforeTestStop triggered for: {}", result.getName());
            if (result.getStatus() == Status.BROKEN || result.getStatus() == Status.FAILED) {
                log.info("Test '{}' failed with status: {}. Attempting to capture screenshot.", result.getName(), result.getStatus());

                WebDriver driver = DriverFactory.getDriver();
                if (driver != null) {
                    try {
                        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                        Allure.getLifecycle().addAttachment(
                                "Failure Screenshot",
                                "image/png",
                                ".png",
                                screenshot
                        );
                        log.info("Screenshot successfully captured and attached to Allure report.");
                    } catch (Exception e) {
                        log.error("Failed to capture screenshot for '{}': {}", result.getName(), e.getMessage(), e);
                    }
                } else {
                    log.warn("Driver is null. Cannot capture screenshot for '{}'", result.getName());
                }
            }
        }
    }
}
