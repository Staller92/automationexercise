package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private Header header;
    public static final String PRODUCT_CART_DESCRIPTION = "//tr[@id='product-%s']//td[@class='cart_description']//a";
    public static final String PRODUCT_CART_PRICE = "//tr[@id='product-%s']//td[@class='cart_price']//p";
    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }
    public void initComponents() {
        header = new Header(webDriver);
    }
    public Header getHeader() {
       return header;
    }

    @Step("Get product name in cart by id = {id}")
    public String getProductNameInCartById(int id) {
        return webDriver.findElement(By.xpath(String.format(PRODUCT_CART_DESCRIPTION, id))).getText();
    }

    @Step("Get product price in cart by id = {id}")
    public String getProductPriceInCartById(int id) {
        return webDriver.findElement(By.xpath(String.format(PRODUCT_CART_PRICE, id))).getText();
    }
}
