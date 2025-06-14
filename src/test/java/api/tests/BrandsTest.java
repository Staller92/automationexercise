package api.tests;

import api.models.Brand;
import api.models.BrandResponse;
import api.requests.BrandRequests;
import api.utils.JsonUtils;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;


public class BrandsTest extends BaseApiTest {

    @Test
    public void getBrandsList() {
        String rawBrandsResponse = BrandRequests.getBrandsList()
                .then()
                //.log().all()
                .statusCode(200)
                .extract()
                .asString();

        BrandResponse brandsResponse = JsonUtils.extractJsonFromHtmlBody(rawBrandsResponse, BrandResponse.class);

        List<Brand> payouts = brandsResponse.getBrands();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(brandsResponse.getResponseCode()).isEqualTo(200);
        softly.assertThat(payouts).isNotEmpty();
        softly.assertAll();
    }


}
