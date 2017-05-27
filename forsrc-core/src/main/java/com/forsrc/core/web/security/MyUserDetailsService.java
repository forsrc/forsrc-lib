package com.forsrc.core.web.security;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.forsrc.pojo.Role;
import com.forsrc.pojo.UserPrivacy;

public class MyUserDetailsService implements UserDetailsService {

    private final Log logger = LogFactory.getLog(getClass());

    private static final Map<Long, Role> MAP_ROLES = new ConcurrentHashMap<>(10);

    @Autowired
    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPrivacy user = this.getUserByUsername(username);
        logger.info(String.format("--> MyUserDetailsService.loadUserByUsername() --> User is : %s", username));
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
            System.err.println(e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
        if (user == null) {
            logger.info(
                    String.format("--> MyUserDetailsService.loadUserByUsername() --> User is not exist: %s", username));
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
