package com.peterjurkovic.travelagency.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.Country;
import com.peterjurkovic.travelagency.common.model.Trip;
import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.TripRepository;
import com.peterjurkovic.travelagency.common.repository.UserRepository;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

/**
 * Populates mongo db database with tests users
 * @author peter
 */
@Component
public class DataPopulator implements CommandLineRunner{

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        Lorem lorem = LoremIpsum.getInstance();
        populateUsers(lorem);
        populateTrips(lorem);
        
    }
    
    private void populateTrips(Lorem lorem) {
        log.info("Populating tips...");
        tripRepository.deleteAll();
        for(int i = 0; i < 20; i++){
            Trip trip = new Trip(
                    lorem.getTitle(5, 50) , Country.CZ, 
                    lorem.getParagraphs(150, 500),
                    lorem.getParagraphs(250, 5000)
                    );
            
            tripRepository.save(trip);
        }
        
        
    }
    
    private void populateUsers(Lorem lorem) {
        log.info("Populating users...");
        userRepository.deleteAll();
        String pass = passwordEncoder.encode("123456");
        
        User peter = new User("email@peterjurkovic.sk", pass  , "Peter" , "Jurkovic");
        userRepository.save(peter);
        
        for(int i = 0; i < 20; i++)
            userRepository.save( generateUser(lorem) );
    }
    
    private User generateUser(Lorem lorem){
        User user = new User();
        user.setEmail(lorem.getEmail());
        user.setFistName( lorem.getFirstName() );
        user.setLastName( lorem.getLastName());
        return user;
    }
}
