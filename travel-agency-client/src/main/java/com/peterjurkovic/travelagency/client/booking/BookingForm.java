package com.peterjurkovic.travelagency.client.booking;

import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.peterjurkovic.travelagency.common.model.Booking;
import com.peterjurkovic.travelagency.common.model.Trip;

public class BookingForm {

    @NotNull
    private Instant tripDate;
    
    @Min(value = 5)
    @Max(value = 14)
    private int days;
    
    @Min(value = 1)
    @Max(value = 10)
    private int persons;
    
    public Instant getTripDate() {
        return tripDate;
    }
    public void setTripDate(Instant tripDate) {
        this.tripDate = tripDate;
    }
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    
    public int getPersons() {
        return persons;
    }
    public void setPersons(int persons) {
        this.persons = persons;
    }
    @Override
    public String toString() {
        return "[tripDate=" + tripDate + ", days=" + days + ", persons=" + persons + "]";
    }
    
    public Booking asBooking(Trip trip){
        Booking booking = new Booking();
        booking.setBookingDate(tripDate);
        booking.setDays(days);
        booking.setPersons(persons);
        booking.setTrip(Objects.requireNonNull(trip));
        return booking;
    }
    
}
