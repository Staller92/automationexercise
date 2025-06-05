package ui.tests;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import ui.models.User;
import ui.pages.HomePage;
import ui.pages.LoginPage;

public class LoginTest extends BaseUiTest {

    @Test
    public void searchProductsAndAddToCart() {
        SoftAssertions softly = new SoftAssertions();
        LoginPage loginPage = getHomePage().getHeader().openLogin();
        User user = new User.Builder().setName("aqa_user").setPassword("aqa_user12345").setEmail("aqa_user@hotmail.com").build();
        HomePage homePage = loginPage.loginAs(new User.Builder().setName("aqa_user").setPassword("aqa_user12345").setEmail("aqa_user@hotmail.com").build());
        softly.assertThat(homePage.getHeader().getLoggedInAs()).isEqualTo(user.getName());
        softly.assertAll();
    }
}
