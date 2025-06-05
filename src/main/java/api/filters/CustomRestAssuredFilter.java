package api.filters;

import io.qameta.allure.Allure;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;


public class CustomRestAssuredFilter implements OrderedFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomRestAssuredFilter.class);
    private static final Pattern ANSI_PATTERN = Pattern.compile("\u001B\\[[;\\d]*m");

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        String method = requestSpec.getMethod();
        String uri = requestSpec.getURI();
        String headers = requestSpec.getHeaders().toString();
        String body = requestSpec.getBody() != null ? requestSpec.getBody().toString() : "[no body]";

        String requestLog = colorize("\n==================== REQUEST ====================\n", CYAN_TEXT()) +
                String.format("Method:       %s\n", method) +
                String.format("URI:          %s\n", uri) +
                colorize("Headers:      ", CYAN_TEXT()) + headers + "\n" +
                colorize("Body:         ", CYAN_TEXT()) + body;

        logger.info(requestLog);
        Allure.addAttachment("Request", "text/plain", stripAnsi(requestLog), StandardCharsets.UTF_8.name());

        Response response = ctx.next(requestSpec, responseSpec);

        String rawBody = response.getBody().asString();
        String jsonBody = rawBody.replaceAll("(?s).*<body>(.*)</body>.*", "$1");

        String prettyJson = jsonBody;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(jsonBody, Object.class);
            prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            logger.warn("Failed to pretty print JSON body", e);
        }

        String responseLog = colorize("\n==================== RESPONSE ===================\n", GREEN_TEXT()) +
                String.format("Status:       %d\n", response.statusCode()) +
                String.format("Time:         %d ms\n", response.time()) +
                String.format("Content-Type: %s\n", response.getContentType()) +
                colorize("Body:\n", GREEN_TEXT()) +
                colorize(prettyJson, YELLOW_TEXT()) +
                colorize("\n==================================================", BRIGHT_BLACK_TEXT());

        logger.info(responseLog);
        Allure.addAttachment("Response", "application/json", stripAnsi(prettyJson), ".json");

        return response;
    }

    private String stripAnsi(String input) {
        return ANSI_PATTERN.matcher(input).replaceAll("");
    }


    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}