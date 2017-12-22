package com.peterjurkovic.travelagency.client.user;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.peterjurkovic.travelagency.common.model.User;


public class UserUtils {

    public Optional<User> getLoggedUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserAdapter){
            return Optional.of( ((UserAdapter) auth.getPrincipal() ).getUser());
        }
        return Optional.empty();
    }
    
    public Optional<TwoFaUserDetails> getLoggedUserDetails(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof TwoFaUserDetails){
            return Optional.of( ((TwoFaUserDetails) auth.getPrincipal() ));
        }
        return Optional.empty();
    }
    
    
    public void loginUser(User user){
        if(user == null || getLoggedUser().isPresent()){
            return;
        }
        
        Authentication auth = new UsernamePasswordAuthenticationToken(new UserAdapter(user), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
