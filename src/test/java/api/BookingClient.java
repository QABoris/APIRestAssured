package api;

import io.restassured.response.Response;
import config.ApiConfig;
import models.Booking;
import models.BookingResponse;

import static io.restassured.RestAssured.given;

public class BookingClient {

    public static Response getAllBookings() {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .when()
                .get(ApiConfig.BOOKING_ENDPOINT);
    }

    public static Response getBookingById(int bookingId) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .when()
                .get(ApiConfig.BOOKING_ENDPOINT + "/" + bookingId);
    }

    public static Response createBooking(Booking booking) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .body(booking)
                .when()
                .post(ApiConfig.BOOKING_ENDPOINT);
    }

    public static Response updateBooking(int bookingId, Booking booking, String authToken) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .header("Cookie", "token=" + authToken)
                .body(booking)
                .when()
                .put(ApiConfig.BOOKING_ENDPOINT + "/" + bookingId);
    }

    public static Response updateBookingWithBasicAuth(int bookingId, Booking booking, String username, String password) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .auth().basic(username, password)
                .body(booking)
                .when()
                .put(ApiConfig.BOOKING_ENDPOINT + "/" + bookingId);
    }

    public static Response partialUpdateBooking(int bookingId, Object partialBooking, String authToken) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .header("Cookie", "token=" + authToken)
                .body(partialBooking)
                .when()
                .patch(ApiConfig.BOOKING_ENDPOINT + "/" + bookingId);
    }

    public static Response deleteBooking(int bookingId, String authToken) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .header("Cookie", "token=" + authToken)
                .when()
                .delete(ApiConfig.BOOKING_ENDPOINT + "/" + bookingId);
    }

    public static Response deleteBookingWithBasicAuth(int bookingId, String username, String password) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .auth().basic(username, password)
                .when()
                .delete(ApiConfig.BOOKING_ENDPOINT + "/" + bookingId);
    }

    public static Response getBookingsWithFilter(String firstName, String lastName) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
                .when()
                .get(ApiConfig.BOOKING_ENDPOINT);
    }

    public static Response getBookingsWithDateFilter(String checkIn, String checkOut) {
        return given()
                .spec(ApiConfig.getBaseRequestSpec())
                .queryParam("checkin", checkIn)
                .queryParam("checkout", checkOut)
                .when()
                .get(ApiConfig.BOOKING_ENDPOINT);
    }

    public static BookingResponse createBookingAndGetResponse(Booking booking) {
        Response response = createBooking(booking);
        if (response.getStatusCode() == 200) {
            return response.as(BookingResponse.class);
        }
        throw new RuntimeException("Failed to create booking. Status: " + response.getStatusCode());
    }
}