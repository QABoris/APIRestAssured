package config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ApiConfig.class.getClassLoader().getResourceAsStream("data.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find data.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data.properties", e);
        }
    }

    public static final String BASE_URL = properties.getProperty("base.url");
    public static final String BOOKING_ENDPOINT = "/booking";
    public static final String AUTH_ENDPOINT = "/auth";
    public static final String PING_ENDPOINT = "/ping";

    public static final String DEFAULT_USERNAME = properties.getProperty("default.username");
    public static final String DEFAULT_PASSWORD = properties.getProperty("default.password");

    public static RequestSpecification getBaseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType("application/json")
                .setAccept("application/json")
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static void configureRestAssured() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}