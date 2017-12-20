package com.peterjurkovic.travelagency;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.user.User;
import com.peterjurkovic.travelagency.user.UserRepository;

/**
 * Populates mongo db database with tests users
 * @author peter
 */
@Component
public class UserPopulator implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
       
        for(int i = 0; i < 20; i++)
            userRepository.save( generateUser() );
        
    }
    
    private static User generateUser(){
        User user = new User();
        user.setEmail(new  Random().toString() + "@test.com");
        user.setFistName("User name");
        user.setLastName("Last name");
        return user;
    }
}
