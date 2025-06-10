package api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.*;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;

public class JsonUtils {
    public static <T> T extractJsonFromHtmlBody(String htmlBody, Class<T> clazz) {
        String json = htmlBody.replaceFirst("(?s)^.*?<body>", "").replaceFirst("</body>.*$", "");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSchemaFor(Class<?> clazz) {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(clazz);
        String schemaName = clazz.getSimpleName() + ".json";

        try {
            File file = Paths.get("src", "main", "java", "api", "schemas", schemaName).toFile();
            file.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(jsonSchema.toPrettyString());
                }

        } catch (Exception e) {
            throw new RuntimeException("Failed to write schema file", e);
        }
        return schemaName;
    }
}
