package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private Header header;
    public static final String ADD_PRODUCT_TO_CART = "//div[@class='single-products']//div[contains(@class,'productinfo')]//a[@data-product-id='%s' and contains(@class, 'add-to-cart')]/i";
    public static final String PRODUCT_NAME = "//div[@class='single-products' and .//a[@data-product-id='%s']]//div[contains(@class,'productinfo')]/p";
    public static final String PRODUCT_PRICE = "//div[@class='single-products' and .//a[@data-product-id='%s']]//div[contains(@class,'productinfo')]/h2";
    public static final String SEARCH_PRODUCT_INPUT_FIELD = "//input[@id='search_product']";
    public static final String SEARCH_PRODUCT_SUBMIT_BUTTON = "//button[@id='submit_search']";
    public static final String MODAL_POP_UP_ACCEPT = "//*[@class='modal-footer']//button";
    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void initComponents() {
        header = new Header(webDriver);
    }
    public Header getHeader() {
        return header;
    }
    public ProductPage addProductById(int id) {
        webDriver.findElement(By.xpath(String.format(ADD_PRODUCT_TO_CART, id))).click();
        return this;
    }

    @Step("Get product name by id = {id}")
    public String getProductNameById(int id) {
        return webDriver.findElement(By.xpath(String.format(PRODUCT_NAME, id))).getText();
    }

    @Step("Get product price in cart by id = {id}")
    public String getProductPriceById(int id) {
        return webDriver.findElement(By.xpath(String.format(PRODUCT_PRICE, id))).getText();
    }

    @Step("Search product by name = {name}")
    public ProductPage searchProductByName(String name) {
        webDriver.findElement(By.xpath(SEARCH_PRODUCT_INPUT_FIELD)).sendKeys(name);
        webDriver.findElement(By.xpath(SEARCH_PRODUCT_SUBMIT_BUTTON)).click();
        return this;
    }

    @Step("Click on continue shopping")
    public ProductPage acceptPopup() {
        webDriver.findElement(By.xpath(MODAL_POP_UP_ACCEPT)).click();
        return this;
    }


}
