package tests;

import api.AuthClient;
import api.BookingClient;
import config.ApiConfig;
import io.qameta.allure.*;
import models.Booking;
import models.BookingResponse;
import utils.TestDataGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Booking Management")
@Feature("Booking Operations")
public class BookingTest extends BaseTest {

    private List<Integer> createdBookingIds = new ArrayList<>();

    @Test(description = "Verify getting all bookings returns a list")
    @Story("Get All Bookings")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllBookings() {
        given()
                .spec(ApiConfig.getBaseRequestSpec())
        .when()
                .get(ApiConfig.BOOKING_ENDPOINT)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(description = "Verify creating a new booking")
    @Story("Create Booking")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateBooking() {
        Booking newBooking = TestDataGenerator.generateRandomBooking();

        given()
                .spec(ApiConfig.getBaseRequestSpec())
                .body(newBooking)
        .when()
                .post(ApiConfig.BOOKING_ENDPOINT)
        .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking", notNullValue())
                .body("booking.firstname", equalTo(newBooking.getFirstName()))
                .body("booking.lastname", equalTo(newBooking.getLastName()));
    }

    @Test(description = "Verify getting a specific booking by ID")
    public void testGetBookingById() {
        Booking newBooking = TestDataGenerator.createValidBooking("John", "Doe");
        BookingResponse createdBooking = BookingClient.createBookingAndGetResponse(newBooking);
        createdBookingIds.add(createdBooking.getBookingId());

        given()
                .spec(ApiConfig.getBaseRequestSpec())
        .when()
                .get(ApiConfig.BOOKING_ENDPOINT + "/" + createdBooking.getBookingId())
        .then()
                .statusCode(200)
                .body("firstname", equalTo("John"))
                .body("lastname", equalTo("Doe"));
    }

    @Test(description = "Verify updating an existing booking with token authentication")
    public void testUpdateBookingWithToken() {
        Booking originalBooking = TestDataGenerator.createValidBooking("Jane", "Smith");
        BookingResponse createdBooking = BookingClient.createBookingAndGetResponse(originalBooking);
        createdBookingIds.add(createdBooking.getBookingId());

        Booking updatedBooking = TestDataGenerator.createValidBooking("Jane", "Updated");
        String authToken = AuthClient.getDefaultAuthToken();

        given()
                .spec(ApiConfig.getBaseRequestSpec())
                .header("Cookie", "token=" + authToken)
                .body(updatedBooking)
        .when()
                .put(ApiConfig.BOOKING_ENDPOINT + "/" + createdBooking.getBookingId())
        .then()
                .statusCode(200)
                .body("lastname", equalTo("Updated"));
    }

    @Test(description = "Verify deleting an existing booking")
    public void testDeleteBooking() {
        Booking newBooking = TestDataGenerator.createValidBooking("Delete", "Me");
        BookingResponse createdBooking = BookingClient.createBookingAndGetResponse(newBooking);

        String authToken = AuthClient.getDefaultAuthToken();

        given()
                .spec(ApiConfig.getBaseRequestSpec())
                .header("Cookie", "token=" + authToken)
        .when()
                .delete(ApiConfig.BOOKING_ENDPOINT + "/" + createdBooking.getBookingId())
        .then()
                .statusCode(201);

        given()
                .spec(ApiConfig.getBaseRequestSpec())
        .when()
                .get(ApiConfig.BOOKING_ENDPOINT + "/" + createdBooking.getBookingId())
        .then()
                .statusCode(404);
    }

    @Test(description = "Verify filtering bookings by name")
    public void testFilterBookingsByName() {
        String firstName = "FilterTest";
        String lastName = "User";

        Booking testBooking = TestDataGenerator.createValidBooking(firstName, lastName);
        BookingResponse createdBooking = BookingClient.createBookingAndGetResponse(testBooking);
        createdBookingIds.add(createdBooking.getBookingId());

        given()
                .spec(ApiConfig.getBaseRequestSpec())
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
        .when()
                .get(ApiConfig.BOOKING_ENDPOINT)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(description = "Verify handling of non-existent booking ID")
    public void testGetNonExistentBooking() {
        given()
                .spec(ApiConfig.getBaseRequestSpec())
        .when()
                .get(ApiConfig.BOOKING_ENDPOINT + "/99999")
        .then()
                .statusCode(404);
    }

    @AfterMethod
    public void cleanup() {
        if (!createdBookingIds.isEmpty()) {
            String authToken = AuthClient.getDefaultAuthToken();
            for (Integer bookingId : createdBookingIds) {
                try {
                    BookingClient.deleteBooking(bookingId, authToken);
                } catch (Exception e) {
                    // Ignore cleanup failures
                }
            }
            createdBookingIds.clear();
        }
    }
}