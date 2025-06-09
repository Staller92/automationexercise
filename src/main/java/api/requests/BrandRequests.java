package api.requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BrandRequests {

    private static final String BASE_PATH = "/api/brandsList";

    public static Response getBrandsList() {
        return given()
                .when()
                .get(BASE_PATH);
    }
}
