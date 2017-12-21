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
        int img = 1;
        for(int i = 0; i < 9; i++){
            Trip trip = new Trip();
            trip.setCreatedAt(trip.getCreatedAt().minusSeconds( img * 3337 ));
            trip.setTitle( lorem.getTitle(2, 5));
            trip.setCountryCode((i % 2 == 0 ? Country.CZ : Country.US));
            trip.setDescription(lorem.getTitle(25, 40));
            trip.setContent(lorem.getParagraphs(3, 10));     
            trip.setAvatarUrl("http://upload.peterjurkovic.com/img/ta/" + img + ".jpg");
            trip.setPurchases( i * img );         
            if(img++ >= 6) img = 1;
            tripRepository.save(trip);
        }
        
        
    }
    
    private void populateUsers(Lorem lorem) {
        log.info("Populating users...");
        userRepository.deleteAll();
        String pass = passwordEncoder.encode("123456");
        
        User peter = user("email"+ " @ " +"peterjurkovic.sk", pass  , "Peter" , "Jurkovic");
        userRepository.save(peter);
        
        User nicola = user("nicola.giacchetta"+ " @ " +"nexmo.com", pass  , "Peter" , "Jurkovic");
        userRepository.save(nicola);
        
        for(int i = 0; i < 20; i++)
            userRepository.save( generateUser(lorem) );
    }
    
    private User user(String email, String pass, String fistName, String lastName){
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass);
        user.setFirstName(fistName);
        user.setLastName(lastName);
        return user;
    }
    
    private User generateUser(Lorem lorem){
        User user = new User();
        user.setEmail(lorem.getEmail());
        user.setFirstName( lorem.getFirstName() );
        user.setLastName( lorem.getLastName());
        return user;
    }
}
