package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingResponse {

    @JsonProperty("bookingid")
    private Integer bookingId;

    @JsonProperty("booking")
    private Booking booking;

    public BookingResponse() {}

    public BookingResponse(Integer bookingId, Booking booking) {
        this.bookingId = bookingId;
        this.booking = booking;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}