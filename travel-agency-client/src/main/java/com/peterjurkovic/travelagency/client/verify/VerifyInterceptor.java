package com.peterjurkovic.travelagency.client.verify;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.peterjurkovic.travelagency.client.user.TwoFaUserDetails;
import com.peterjurkovic.travelagency.client.user.UserUtils;

public class VerifyInterceptor implements HandlerInterceptor{

    private final Logger log  = LoggerFactory.getLogger(getClass());
    
    private final UserUtils userUtils;
  
    public VerifyInterceptor(UserUtils userUtils){
        this.userUtils = userUtils;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if( ! VerifyController.URL.equalsIgnoreCase(request.getRequestURI())){
            Optional<TwoFaUserDetails> loggedUserDetails = userUtils.getLoggedUserDetails();
            if(loggedUserDetails.isPresent() && ! loggedUserDetails.get().isTwoFaCompleted()){
                log.info("2FA required for user {} " , loggedUserDetails.get().getUsername());
                response.sendRedirect( request.getContextPath() + VerifyController.URL);
                return false;
            }
        }
        return true;
    }
}
