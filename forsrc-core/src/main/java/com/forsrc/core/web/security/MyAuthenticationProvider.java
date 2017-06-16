package com.forsrc.core.web.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService myUserDetailsService;

    public MyAuthenticationProvider(UserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if (password == null) {
            throw new BadCredentialsException(String.format("Null password for: %s", username));
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        String md5 = getPassword(username.getBytes(), password.getBytes());
        boolean match = new BCryptPasswordEncoder().matches(md5, userDetails.getPassword());
        if (!match) {
            throw new BadCredentialsException(String.format("Invalid password for: %s", username));
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private String getPassword(byte[] username, byte[] password) {
        byte[] split = "/".getBytes();
        byte[] pwd = new byte[username.length + split.length + password.length + 1];
        System.arraycopy(username, 0, pwd, 0, username.length);
        System.arraycopy(split, 0, pwd, username.length, split.length);
        System.arraycopy(password, 0, pwd, username.length + split.length, password.length);
        String pw = DigestUtils.md5Hex(pwd);
        return pw;
    }
}
