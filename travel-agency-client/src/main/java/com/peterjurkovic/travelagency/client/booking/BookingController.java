package com.peterjurkovic.travelagency.client.booking;

import java.time.Instant;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.peterjurkovic.travelagency.client.exceptions.PageNotFoundException;
import com.peterjurkovic.travelagency.common.model.Booking;
import com.peterjurkovic.travelagency.common.model.Trip;
import com.peterjurkovic.travelagency.common.repository.TripRepository;
import com.peterjurkovic.travelagency.common.tools.InstantFormatter;

@Controller
public class BookingController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final TripRepository tripRepository;
    private final BookingService bookingService;
    
    
    @Autowired
    public BookingController(TripRepository tripRepository, BookingService bookingService){
        this.tripRepository = tripRepository;
        this.bookingService = bookingService;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addCustomFormatter(new InstantFormatter());
    }
    
    @GetMapping("/booking/{id}")
    public String showBookingPage(@PathVariable String id, ModelMap model){
        log.info("Showing booking form for trip {}", id);
        return populateModel(id, model, new BookingForm());
    }

   
    
    @PostMapping("/booking/{id}")
    public String placeBooking(@PathVariable String id, ModelMap model, @Valid BookingForm form, BindingResult result){
        log.info("placeBooking {}", form);
        
        if(result.hasErrors()){
            log.info("Booking form contains errors {}", result);
            return populateModel(id, model, form);
        }
        
        Booking booking = form.asBooking( getTripOrThrow(id) );
        bookingService.placeBooking(booking);
        model.addAttribute("created", true);
        model.put("booking", booking);
        return populateModel(id, model, form);
    }
    
    
    private String populateModel(String id, ModelMap model, BookingForm form) {
        model.put("trip", getTripOrThrow(id));
        model.addAttribute(form);
        return "booking";
    }
    
    private Trip getTripOrThrow(String id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if( ! trip.isPresent() ){
            log.info("No trip was found with ID {}", id);
            throw new PageNotFoundException();
        }
        
        return trip.get();
    }
    
}
