package api.utils;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public final class Specs {

    private Specs() {}
    public static ResponseSpecification jsonSchema(Class<?> dtoClass) {
        JsonUtils.generateSchemaFor(dtoClass);
        return new ResponseSpecBuilder()
                .expectBody(matchesJsonSchema(new File(("src/main/java/api/schemas/" + dtoClass.getSimpleName() + ".json"))))
                .build();
    }
}
