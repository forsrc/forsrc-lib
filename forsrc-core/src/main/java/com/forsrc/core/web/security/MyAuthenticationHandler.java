package com.forsrc.core.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.forsrc.core.web.user.service.UserService;
import com.forsrc.pojo.User;

public class MyAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler
        implements AuthenticationFailureHandler, LogoutHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MyAuthenticationHandler.class);

    RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        SecurityContext sc = SecurityContextHolder.getContext();
        Object principal = sc.getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails) sc.getAuthentication().getPrincipal();
            User user = userService.get(myUserDetails.getUserPrivacy().getUserId());
            session.setAttribute("USER", user);
        }
        LOGGER.info("--> onAuthenticationSuccess(): {}", sc.getAuthentication().getName());

        // clearAuthenticationAttributes(request);

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        String targetUrl = savedRequest.getRedirectUrl();
        if (targetUrl != null && "".equals(targetUrl)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException ae) throws IOException, ServletException {
        SecurityContext sc = SecurityContextHolder.getContext();
        LOGGER.info("--> onAuthenticationFailure(): {}", sc.getAuthentication().getName());
        SecurityContextHolder.clearContext();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc == null) {
            return;
        }
        if (sc.getAuthentication() == null) {
            return;
        }
        LOGGER.info("--> logout(): {}", sc.getAuthentication().getName());
        SecurityContextHolder.clearContext();
        clearAuthenticationAttributes(request);
    }
}
