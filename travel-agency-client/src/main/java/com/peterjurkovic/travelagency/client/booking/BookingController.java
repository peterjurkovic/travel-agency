package com.peterjurkovic.travelagency.client.booking;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.peterjurkovic.travelagency.client.exceptions.PageNotFoundException;
import com.peterjurkovic.travelagency.common.model.Trip;
import com.peterjurkovic.travelagency.common.repository.TripRepository;

@Controller
public class BookingController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final TripRepository tripRepository;
    
    @Autowired
    public BookingController(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }
    
    @GetMapping("/booking/{id}")
    public String showBookingPage(@PathVariable String id, ModelMap model){
        
        Optional<Trip> trip = tripRepository.findById(id);
        if( ! trip.isPresent() ){
            log.info("No trip was found with ID {}", id);
            throw new PageNotFoundException();
        }
        model.put("trip", trip.get());
        return "booking";
    }
}
