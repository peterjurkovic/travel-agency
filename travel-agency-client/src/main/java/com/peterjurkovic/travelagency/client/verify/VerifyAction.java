package com.peterjurkovic.travelagency.client.verify;


import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.peterjurkovic.travelagency.common.model.User;

import lombok.Data;


@Component
@SessionScope
public @Data class VerifyAction {

    private User user;
    private String requestId;
    private String successRedirectUrl;
    
    public void with(User user){
        this.user = Objects.requireNonNull(user);
    }
    
    public void with(User user, String redirectUrl){
        with(user);
        this.successRedirectUrl = Objects.requireNonNull(redirectUrl);
    }
   
    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
    
    public String getSuccessRedirectUrl(){
        if(this.successRedirectUrl == null){
            return "/";
        }
        return successRedirectUrl;
    }
}
