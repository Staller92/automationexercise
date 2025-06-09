package api.requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductsRequests {

    private static final String BASE_PATH = "/api/productsList";

    public static Response getProductsList() {
        return given()
                .when()
                .get(BASE_PATH);
    }
}
