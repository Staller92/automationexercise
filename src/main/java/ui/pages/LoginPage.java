package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.models.User;
import ui.utils.BasePageFactory;

public class LoginPage extends BasePage {
    private Header header;
    public static final String LOGIN_INPUT_EMAIL = "//form[contains(@action, 'login')]/input[@type='email']";
    public static final String LOGIN_INPUT_PASSWORD = "//form[contains(@action, 'login')]/input[@type='password']";
    public static final String LOGIN_SUBMIT = "//form[contains(@action, 'login')]/button[@type='submit']";
    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }
    public void initComponents() {
        header = new Header(webDriver);
    }
    public Header getHeader() {
       return header;
    }

    @Step("Input login email = {email}")
    public LoginPage inputEmail(String email) {
        webDriver.findElement(By.xpath(LOGIN_INPUT_EMAIL)).sendKeys(email);
        return this;
    }

    @Step("Input login password = {password}")
    public LoginPage inputPassword(String password) {
        webDriver.findElement(By.xpath(LOGIN_INPUT_PASSWORD)).sendKeys(password);
        return this;
    }

    @Step("Login as user <user>")
    public HomePage loginAs(User user) {
        inputPassword(user.getPassword());
        inputEmail(user.getEmail());
        return clickSubmit();
    }

    @Step("Click on submit login")
    public HomePage clickSubmit() {
        webDriver.findElement(By.xpath(LOGIN_SUBMIT)).click();
        return BasePageFactory.createInstance(webDriver, HomePage.class);
    }
}
