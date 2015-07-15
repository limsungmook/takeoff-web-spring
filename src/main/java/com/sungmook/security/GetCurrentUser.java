package com.sungmook.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public class GetCurrentUser {

    private GetCurrentUser() {}

    public static final SessionUser get(){
        if( SecurityContextHolder.getContext().getAuthentication() == null ){
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getClass() == String.class) {
            return null;
        }

        if( !(principal instanceof SessionUser) )
            return null;

        return (SessionUser) principal;
    }

}
