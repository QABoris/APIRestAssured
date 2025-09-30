package tests;

import api.HealthCheckClient;
import config.ApiConfig;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

@Epic("System Health")
@Feature("Health Check")
public class HealthCheckTest extends BaseTest {

    @Test(description = "Verify API health check endpoint returns correct response")
    @Story("API Ping")
    @Severity(SeverityLevel.CRITICAL)
    public void testPingEndpoint() {
        given()
                .spec(ApiConfig.getBaseRequestSpec())
        .when()
                .get(ApiConfig.PING_ENDPOINT)
        .then()
                .statusCode(201)
                .body(equalTo("Created"));
    }

    @Test(description = "Verify API health check utility method")
    public void testApiHealthyUtility() {
        boolean isHealthy = HealthCheckClient.isApiHealthy();
        assertTrue(isHealthy, "API should be healthy");
    }
}