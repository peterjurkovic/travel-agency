package com.peterjurkovic.travelagency.admin.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.security.core.GrantedAuthority;

import com.peterjurkovic.travelagency.common.model.AdminUser;
import com.peterjurkovic.travelagency.common.model.User;

public class AdminUserAdapter implements TwoFaAdminUserDetails{
    
    private static final long serialVersionUID = 6598170339813718068L;
    private final AdminUser user;
    private final AtomicBoolean twoFaCompleted = new AtomicBoolean();
    
    public AdminUserAdapter(AdminUser user){
        this.user = Objects.requireNonNull(user);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AdminUser getUser() {
        return user;
    }

    public void markTwoFaAsCompleted(){
        this.twoFaCompleted.set( true );
    }
    
    public boolean isTwoFaCompleted(){
        return twoFaCompleted.get();
    }
    
    
}
