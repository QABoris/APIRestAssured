package api;

import io.restassured.response.Response;
import config.ApiConfig;
import models.AuthToken;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public static Response authenticate(String username, String password) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .body(Map.of("username", username, "password", password))
                .when()
                .post(ApiConfig.AUTH_ENDPOINT);
    }

    public static String getAuthToken(String username, String password) {
        Response response = authenticate(username, password);

        if (response.getStatusCode() == 200) {
            AuthToken authToken = response.as(AuthToken.class);
            return authToken.getToken();
        }

        throw new RuntimeException("Failed to authenticate. Status: " + response.getStatusCode());
    }

    public static String getDefaultAuthToken() {
        return getAuthToken(ApiConfig.DEFAULT_USERNAME, ApiConfig.DEFAULT_PASSWORD);
    }
}