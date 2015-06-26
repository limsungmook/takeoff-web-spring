package com.sungmook.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

/**
 * 기타 세션 정보들을 저장한다.
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public class SessionUser extends User{

    public SessionUser(String username, String password, Set<GrantedAuthority> roles) {
        super(username, password, roles);
    }

}
