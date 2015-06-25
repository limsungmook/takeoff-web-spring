package com.sungmook.service;

import com.sungmook.domain.Member;
import com.sungmook.domain.SessionUser;
import com.sungmook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username);

        if( member == null ){
            throw new RuntimeException();
        }

        Set<GrantedAuthority> roles = null;
        if( member.isAdmin() ){
            roles = new HashSet<GrantedAuthority>();
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new SessionUser(member.getUsername(), member.getPassword(), roles);
    }
}
