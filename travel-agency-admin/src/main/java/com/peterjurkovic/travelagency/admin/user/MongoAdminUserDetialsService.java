package com.peterjurkovic.travelagency.admin.user;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.peterjurkovic.travelagency.common.model.AdminUser;
import com.peterjurkovic.travelagency.common.repository.AdminUserRepository;

public class MongoAdminUserDetialsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AdminUserRepository adminUserRepository;

    public MongoAdminUserDetialsService(AdminUserRepository userRepository) {
        this.adminUserRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        if (log.isDebugEnabled())
            log.debug("Searching admin user by: {} ", username);
        Optional<AdminUser> user = adminUserRepository.findByEmail(username.trim());
        if (user.isPresent()) {
            log.info("User found: {}",user.get());
            return new AdminUserAdapter(user.get());
        }
        log.info("User with username {} was not found" + username);
        return null;
    }

}
