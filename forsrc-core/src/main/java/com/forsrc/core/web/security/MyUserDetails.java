package com.forsrc.core.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.forsrc.pojo.Role;
import com.forsrc.pojo.UserPrivacy;
import com.forsrc.pojo.UserRole;

public class MyUserDetails implements UserDetails {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4063306962694515085L;

    @Autowired
    private SecurityService securityService;
    private UserPrivacy userPrivacy;
    private Map<Long, Role> roles;

    public MyUserDetails(SecurityService securityService, UserPrivacy userPrivacy, Map<Long, Role> roles) {
        this.securityService = securityService;
        this.roles = roles;
        this.userPrivacy = userPrivacy;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> auths = new ArrayList<>();
        // auths.add(new SimpleGrantedAuthority("ROLE_TEST"));
        List<Role> roles = securityService.findRolesByUserId(userPrivacy.getUserId());
        for (Role role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
//        Set<UserRole> userRoles = userPrivacy.getUser().getUserRoles();
//        for (UserRole userRole : userRoles) {
//            auths.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
//        }

        return auths;
    }

    @Override
    public String getPassword() {
        return new BCryptPasswordEncoder().encode(userPrivacy.getPassword());
    }

    @Override
    public String getUsername() {
        return userPrivacy.getUsername();
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

    public UserPrivacy getUserPrivacy() {
        return userPrivacy;
    }

    public void setUserPrivacy(UserPrivacy userPrivacy) {
        this.userPrivacy = userPrivacy;
    }
}
