package com.peterjurkovic.travelagency.client.booking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peterjurkovic.travelagency.client.user.UserUtils;
import com.peterjurkovic.travelagency.common.model.Booking;
import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.BookingRepository;

@Service
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final BookingRepository bookingRepository;
    private final UserUtils userUtils;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository, UserUtils userUtils){
        this.bookingRepository = bookingRepository;
        this.userUtils = userUtils;
    }
    
    
    public void placeBooking(Booking booking){
        User user = userUtils.getLoggedUserOrThrow();
        booking.setUser(user);
        
        bookingRepository.save(booking);
        log.info("Booking saved {} ", booking);
    }
    
        
    public List<Booking> getLoggedUserBookings(){
        User user = userUtils.getLoggedUserOrThrow();
        return bookingRepository.findByUser(user);
        
    }
    
}
