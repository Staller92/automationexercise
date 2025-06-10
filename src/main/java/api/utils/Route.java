package api.utils;

import api.models.BrandResponse;
import api.models.ProductsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum Route {

    BRANDS("/api/brandsList$", BrandResponse.class),
    PRODUCTS("/api/productsList$", ProductsResponse.class);

    private static final Logger logger = LoggerFactory.getLogger(Route.class);

    private final Pattern pattern;
    private final Class<?> dto;

    Route(String regex, Class<?> dto) {
        this.pattern = Pattern.compile(regex);
        this.dto = dto;
    }

    public boolean matches(String path) {
        return pattern.matcher(path).find();
    }

    public Class<?> dto() {
        return dto;
    }

    public static Class<?> detect(String path) {
        Class<?> result = Arrays.stream(values())
                .filter(r -> r.matches(path))
                .peek(r -> logger.debug("Route matched: [{}] → {}", path, r.name()))
                .map(Route::dto)
                .findFirst()
                .orElse(null);

        if (result == null) {
            logger.debug("No matching route for path: [{}]", path);
        }

        return result;
    }
}
