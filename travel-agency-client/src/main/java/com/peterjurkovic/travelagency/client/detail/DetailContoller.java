package com.peterjurkovic.travelagency.client.detail;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.peterjurkovic.travelagency.client.exceptions.PageNotFoundException;
import com.peterjurkovic.travelagency.common.model.Trip;
import com.peterjurkovic.travelagency.common.repository.TripRepository;

@Controller
public class DetailContoller {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final TripRepository tripRepository;
    
    public DetailContoller(TripRepository tripRepository){
        this.tripRepository = tripRepository; 
    }

    @GetMapping("/trip/{id}")
    public String showDetail(Model model, @PathVariable String id){
        
        if ( log.isDebugEnabled() ) log.debug("Showing trip detail [{}]" , id);
        
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isPresent()){
            model.addAttribute("trip", trip.get());
        }else{
            log.info("No trip was found with ID {}" , id);
            throw new PageNotFoundException();
        }
        return "trip-detail";
    }
}
