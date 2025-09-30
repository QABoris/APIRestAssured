package utils;

import models.Booking;
import models.BookingDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TestDataGenerator {

    private static final Random random = new Random();
    private static final String[] FIRST_NAMES = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Lisa"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
    private static final String[] ADDITIONAL_NEEDS = {"Breakfast", "Late checkout", "Extra towels", "WiFi", "Parking", ""};

    public static Booking generateRandomBooking() {
        return new Booking(
                getRandomFirstName(),
                getRandomLastName(),
                getRandomPrice(),
                getRandomBoolean(),
                generateRandomBookingDates(),
                getRandomAdditionalNeeds()
        );
    }

    public static BookingDates generateRandomBookingDates() {
        LocalDate checkIn = LocalDate.now().plusDays(random.nextInt(30) + 1);
        LocalDate checkOut = checkIn.plusDays(random.nextInt(10) + 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return new BookingDates(
                checkIn.format(formatter),
                checkOut.format(formatter)
        );
    }

    public static Booking createValidBooking(String firstName, String lastName) {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return new Booking(
                firstName,
                lastName,
                150,
                true,
                new BookingDates(checkIn.format(formatter), checkOut.format(formatter)),
                "Breakfast"
        );
    }

    private static String getRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    private static String getRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private static Integer getRandomPrice() {
        return random.nextInt(500) + 50;
    }

    private static Boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    private static String getRandomAdditionalNeeds() {
        return ADDITIONAL_NEEDS[random.nextInt(ADDITIONAL_NEEDS.length)];
    }
}