package api.filters;

import api.utils.Route;
import api.utils.Specs;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


public class SchemaValidationFilter implements OrderedFilter {
    private static final Logger logger = LoggerFactory.getLogger(SchemaValidationFilter.class);
    @Override
    public Response filter(FilterableRequestSpecification req,
                           FilterableResponseSpecification res,
                           FilterContext ctx) {

        logger.info("→ {} {}", req.getMethod(), req.getURI());

        Response response = ctx.next(req, res);

        String path = URI.create(req.getURI()).getPath();
        Class<?> dto = Route.detect(path);

        if (dto == null) {
            logger.debug("No schema mapped for [{}] – skip validation", path);
            return response;
        }

        logger.debug("Validate [{}] with schema '{}.json'", path, dto.getSimpleName());

        try {
            Specs.jsonSchema(dto).validate(response);
            logger.info("✔ Schema OK ({})", dto.getSimpleName());
        } catch (AssertionError e) {
            logger.error("✖ Schema validation FAILED for [{}]: {}", path, e.getMessage());
            throw e;
        }

        return response;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}