package com.peterjurkovic.travelagency.client.user;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.UserRepository;

public class MongoUserDetialsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public MongoUserDetialsService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        if (log.isDebugEnabled())
            log.debug("Searching user by: {} ", username);
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            log.info("User found: {}" + user.get());
            return new UserAdapter(user.get());
        }
        log.info("User with username {} was not found" + user.get());
        return null;
    }

}
