package api.utils;

import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

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
}
