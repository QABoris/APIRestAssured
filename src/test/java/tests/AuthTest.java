package tests;

import api.AuthClient;
import config.ApiConfig;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

@Epic("Authentication")
@Feature("Auth Token Management")
public class AuthTest extends BaseTest {

    @Test(description = "Verify successful authentication with valid credentials")
    @Story("Token Generation")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessfulAuthentication() {
        given()
                .spec(ApiConfig.getBaseRequestSpec())
                .body(Map.of("username", ApiConfig.DEFAULT_USERNAME, "password", ApiConfig.DEFAULT_PASSWORD))
        .when()
                .post(ApiConfig.AUTH_ENDPOINT)
        .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", not(emptyString()));
    }

    @Test(description = "Verify authentication fails with invalid credentials")
    public void testFailedAuthentication() {
        given()
                .spec(ApiConfig.getBaseRequestSpec())
                .body(Map.of("username", "invalid", "password", "credentials"))
        .when()
                .post(ApiConfig.AUTH_ENDPOINT)
        .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test(description = "Verify getAuthToken utility method works correctly")
    public void testGetAuthTokenUtility() {
        String token = AuthClient.getDefaultAuthToken();

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }
}