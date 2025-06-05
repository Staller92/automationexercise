package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.utils.BasePageFactory;


public class Header {
    public static final String PRODUCTS_BUTTON = "//*[@id='header']//a[contains(@href, '/products')]";
    public static final String CART_BUTTON = "//*[@id='header']//a[contains(@href, '/view_cart')]";
    public static final String LOGIN_BUTTON = "//*[@id='header']//a[contains(@href, '/login')]";
    public static final String LOGGED_IN_AS = "//*[@id='header']//i[contains(@class, 'user')]/../b";
    protected WebDriver webDriver;
    public Header(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public ProductPage openProducts() {
        webDriver.findElement(By.xpath(PRODUCTS_BUTTON)).click();
        return BasePageFactory.createInstance(webDriver, ProductPage.class);
    }

    public CartPage openCart() {
        webDriver.findElement(By.xpath(CART_BUTTON)).click();
        return BasePageFactory.createInstance(webDriver, CartPage.class);
    }

    public LoginPage openLogin() {
        webDriver.findElement(By.xpath(LOGIN_BUTTON)).click();
        return BasePageFactory.createInstance(webDriver, LoginPage.class);
    }

    public String getLoggedInAs() {
        return webDriver.findElement(By.xpath(LOGGED_IN_AS)).getText();
    }
}
