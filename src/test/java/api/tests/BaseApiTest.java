package api.tests;

import api.filters.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import ui.config.ConfigurationManager;

public class BaseApiTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiTest.class);
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .addFilter(new CustomRestAssuredFilter())
            .setBaseUri(ConfigurationManager.config().baseUrl())
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

    @BeforeSuite
    public void setup() {
        logger.debug("Setup rest assured specification");
        RestAssured.requestSpecification = requestSpec;
    }

}
