package api;

import io.restassured.response.Response;
import config.ApiConfig;

import static io.restassured.RestAssured.given;

public class HealthCheckClient {

    public static Response ping() {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .when()
                .get(ApiConfig.PING_ENDPOINT);
    }

    public static boolean isApiHealthy() {
        try {
            Response response = ping();
            return response.getStatusCode() == 201 && "Created".equals(response.asString());
        } catch (Exception e) {
            return false;
        }
    }
}