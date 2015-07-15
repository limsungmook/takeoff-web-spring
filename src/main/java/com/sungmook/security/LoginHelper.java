package com.sungmook.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class LoginHelper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDetailsService userDetailsService;


    public void login(String email){
        SessionUser sessionUser = (SessionUser)userDetailsService.loadUserByUsername(email);
        SecurityContext securityContext = SecurityContextHolder.getContext();

        logger.debug("Security Context : {}", securityContext);
        Authentication authentication = securityContext.getAuthentication();
        logger.debug("authentication : {}", authentication);

        Object credentials = null;
        if( authentication != null ){
            credentials = authentication.getCredentials();
        }
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(sessionUser, credentials, sessionUser.getAuthorities()));
    }

}
