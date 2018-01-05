package com.peterjurkovic.travelagency.admin.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface TwoFaAdminUserDetails extends UserDetails{

    void markTwoFaAsCompleted();
    
    boolean isTwoFaCompleted();
    
}
