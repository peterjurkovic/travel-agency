package com.peterjurkovic.travelagency.admin.user;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.AdminUser;

@Component
public class AdminUserUtils {

    public Optional<AdminUser> getLoggedUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AdminUserAdapter){
            return Optional.of( ((AdminUserAdapter) auth.getPrincipal() ).getUser());
        }
        return Optional.empty();
    }
    
    public AdminUser getLoggedUserOrThrow(){
        Optional<AdminUser> user = getLoggedUser();
        
        if(user.isPresent()){
            return user.get();
        }
        
        throw new AccessDeniedException("No logged user");
    }
    
    public Optional<TwoFaAdminUserDetails> getLoggedUserDetails(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof TwoFaAdminUserDetails){
            return Optional.of( ((TwoFaAdminUserDetails) auth.getPrincipal() ));
        }
        return Optional.empty();
    }
    
    
    public void loginUser(AdminUser user){
        if(user == null || getLoggedUser().isPresent()){
            return;
        }
        
        Authentication auth = new UsernamePasswordAuthenticationToken(new AdminUserAdapter(user), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
