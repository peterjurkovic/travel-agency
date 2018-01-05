package com.peterjurkovic.travelagency.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.peterjurkovic.travelagency.common.repository.BookingRepository;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.common.repository.TripRepository;
import com.peterjurkovic.travelagency.common.repository.UserRepository;

@Controller
public class DashboardController {

    @Autowired
    private ConversationRepository conversationRepository;
    
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/")
    public String showDashboard(ModelMap model){
        model.put("noConversations", conversationRepository.count());
        model.put("noTrips", tripRepository.count());
        model.put("noBookings", bookingRepository.count());
        model.put("noUsers", userRepository.count());
        return "dashboard";
    }
}
