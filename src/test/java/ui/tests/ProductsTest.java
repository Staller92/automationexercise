package ui.tests;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import ui.pages.CartPage;
import ui.pages.ProductPage;

public class ProductsTest extends BaseUiTest {
//aqa_user@hotmail.com
    //aqa_user12345
    @Test
    public void searchProductsAndAddToCart() {
        SoftAssertions softly = new SoftAssertions();
        ProductPage productPage = getHomePage().getHeader().openProducts();
        String name = productPage.getProductNameById(1);
        String price = productPage.getProductPriceById(1);
        productPage = productPage.searchProductByName(name);

        softly.assertThat(productPage.getProductNameById(1)).isNotEmpty();
        softly.assertThat(productPage.getProductNameById(1)).isEqualTo(name);
        softly.assertThat(productPage.getProductPriceById(1)).isNotEmpty();
        softly.assertThat(productPage.getProductPriceById(1)).isEqualTo(price);

        CartPage cartPage = productPage.addProductById(1).acceptPopup().getHeader().openCart();

        softly.assertThat(cartPage.getProductNameInCartById(1)).isNotEmpty();
        softly.assertThat(cartPage.getProductNameInCartById(1)).isEqualTo(name);
        softly.assertThat(cartPage.getProductPriceInCartById(1)).isNotEmpty();
        softly.assertThat(cartPage.getProductPriceInCartById(1)).isEqualTo(price);
        softly.assertAll();
    }


    @Test
    public void addProductsToCart() {
        SoftAssertions softly = new SoftAssertions();
        ProductPage productPage = getHomePage().getHeader().openProducts();
        String name = productPage.getProductNameById(2);
        String price = productPage.getProductPriceById(2);
        CartPage cartPage = productPage.addProductById(2).acceptPopup().getHeader().openCart();

        softly.assertThat(cartPage.getProductNameInCartById(2)).isNotEmpty();
        softly.assertThat(cartPage.getProductNameInCartById(2)).isEqualTo(name);
        softly.assertThat(cartPage.getProductPriceInCartById(2)).isNotEmpty();
        softly.assertThat(cartPage.getProductPriceInCartById(2)).isEqualTo(price);
        softly.assertAll();
    }


}
