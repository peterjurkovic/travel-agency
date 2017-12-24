package com.peterjurkovic.travelagency.common.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class Booking {

    public enum BookingStatus{PENDING, CONFIRMED, CANCELED}
    
    @Id
    private String id;
    
    @DateTimeFormat(iso = ISO.DATE)
    private Instant tripDate;
    
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Instant bookingDate = Instant.now(); 
    private int days;
    private int persons;
    
    @DBRef 
    private User user;
    @DBRef
    private Trip trip;
    private BookingStatus status = BookingStatus.PENDING;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Instant getTripDate() {
        return tripDate;
    }
    public void setTripDate(Instant tripDate) {
        this.tripDate = tripDate;
    }
    public Instant getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Instant bookingDate) {
        this.bookingDate = bookingDate;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    public BookingStatus getStatus() {
        return status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    
    
    
    
}
