package com.forsrc.core.web.security;

import com.forsrc.pojo.Role;
import com.forsrc.pojo.UserPrivacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPrivacy user = this.getUserByUsername(username);
        System.out.println(String.format("--> MyUserDetailsService.loadUserByUsername() --> User is : %s", username));
        Map<Long, Role> roles = getRoles();
        MyUserDetails myUserDetails = new MyUserDetails(securityService, user, roles);
        return myUserDetails;
    }

    public UserPrivacy getUserByUsername(String username) throws UsernameNotFoundException {
        UserPrivacy user = null;
        try{
            user = securityService.findByUsername(username);
        } catch (RuntimeException e ){
            user = null;
        }
        if (user == null) {
            System.out.println(String.format("--> MyUserDetailsService.loadUserByUsername() --> User is not exist: %s", username));
            throw new UsernameNotFoundException(String.format("User is not exist: %s", username));
            //throw new BadCredentialsException(String.format("User is not exist: %s", username));
        }
        return user;
    }

    private Map<Long, Role> getRoles() {
        Map<Long, Role> map = new HashMap<>();
        List<Role> roles = securityService.getRoles();
        for (Role role : roles) {
            map.put(role.getId(), role);
        }
        return map;
    }
}
