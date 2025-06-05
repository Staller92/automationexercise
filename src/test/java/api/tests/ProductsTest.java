package api.tests;

import api.models.Product;
import api.models.ProductsResponse;
import api.utils.JsonUtils;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductsTest extends BaseApiTest {

    @Test
    public void getAllProductsList() {
        String rawProductsResponse = given()
                //.log().all()
                .when()
                .get("/api/productsList")
                .then()
                //.log().all()
                .statusCode(200)
                .extract()
                .asString();

        ProductsResponse productsResponse = JsonUtils.extractJsonFromHtmlBody(rawProductsResponse, ProductsResponse.class);

        List<Product> payouts = productsResponse.getProducts();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(productsResponse.getResponseCode()).isEqualTo(200);
        softly.assertThat(payouts).isNotEmpty();
        softly.assertAll();
    }



}
