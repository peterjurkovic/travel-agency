package com.peterjurkovic.travelagency.client.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface TwoFaUserDetails extends UserDetails{

    void markTwoFaAsCompleted();
    
    boolean isTwoFaCompleted();
}
