package com.peterjurkovic.travelagency.client.verify;

import org.springframework.security.core.userdetails.UserDetails;

public interface TwoFaUserDetails extends UserDetails{

    void markTwoFaAsCompleted();
    
    boolean isTwoFaCompleted();
}
