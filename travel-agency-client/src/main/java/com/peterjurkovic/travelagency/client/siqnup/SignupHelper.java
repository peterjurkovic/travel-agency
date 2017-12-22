package com.peterjurkovic.travelagency.client.siqnup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.client.user.UserUtils;
import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.UserRepository;

@Component
class SignupHelper {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtils userUtils;
    
    @Autowired
    public SignupHelper(UserRepository userRepository, PasswordEncoder passwordEncoder, UserUtils userUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userUtils = userUtils;
    }
    
    public void signupAndSignInUser(User user){
        user.setPassword( passwordEncoder.encode( user.getPassword()) ); 
        userRepository.save(user);
        log.info("New user created {} ", user);
        userUtils.loginUser(user);
        log.info("User {} has been logged in ", user.getEmail());
    }
}
