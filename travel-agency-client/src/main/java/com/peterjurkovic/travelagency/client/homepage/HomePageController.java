package com.peterjurkovic.travelagency.client.homepage;

import static java.util.Objects.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomePageController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final HomePageHelper helper;
    
    @Autowired
    public HomePageController(HomePageHelper tripRepository){
        this.helper = requireNonNull(tripRepository);
    }
    
    @GetMapping("/")
    public String home(ModelMap model){
        if (log.isDebugEnabled())  log.debug("Showing home page...");
        
        model.addAttribute("recent", helper.findThreeRecentTrips());
        model.addAttribute("popular", helper.findThreeMostPopularTrips());
        
        return "home";
    }
}
