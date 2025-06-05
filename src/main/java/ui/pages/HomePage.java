package ui.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private Header header;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void initComponents() {
        header = new Header(webDriver);
    }

    public Header getHeader() {
        return header;
    }

}
