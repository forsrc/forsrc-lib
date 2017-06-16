package com.forsrc.core.web.security;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.forsrc.pojo.Role;
import com.forsrc.pojo.UserPrivacy;

public class MyUserDetailsService implements UserDetailsService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    private static final Map<Long, Role> MAP_ROLES = new ConcurrentHashMap<>(10);

    @Autowired
    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserPrivacy user = this.getUserByUsername(username);
        LOGGER.info("--> MyUserDetailsService.loadUserByUsername() --> User is : {} ({})", username, user);
        Map<Long, Role> roles = getRoles();
        MyUserDetails myUserDetails = new MyUserDetails(securityService, user, roles);
        return myUserDetails;
    }

    public UserPrivacy getUserByUsername(String username) throws UsernameNotFoundException {
        UserPrivacy user = null;
        try {
            user = securityService.findByUsername(username);
        } catch (RuntimeException e) {
            user = null;
            LOGGER.error(e.getMessage(), e);
            throw new UsernameNotFoundException(e.getMessage());
        }
        if (user == null) {
            LOGGER.warn("--> MyUserDetailsService.getUserByUsername() --> User is not exist: {}", username);
            throw new UsernameNotFoundException(String.format("User is not exist: %s", username));
            // throw new BadCredentialsException(String.format("User is not
            // exist: %s", username));
        }
        return user;
    }

    private Map<Long, Role> getRoles() {
        if (MAP_ROLES.isEmpty()) {
            synchronized (MAP_ROLES) {
                if (MAP_ROLES.isEmpty()) {
                    List<Role> roles = securityService.getRoles();
                    for (Role role : roles) {
                        MAP_ROLES.put(role.getId(), role);
                    }
                }
            }
        }
        return MAP_ROLES;
    }
}
