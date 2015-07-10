package com.sungmook.service;

import com.sungmook.domain.Member;
import com.sungmook.domain.SessionUser;
import com.sungmook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username);

        if( member == null ){
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        return wrapAndReturn( new SessionUser(member.getUsername(), member.getEncryptedPassword(), member.getRoles()), member );
    }

    public UserDetails loadUserByUserId( Long userId ) throws UsernameNotFoundException, DataAccessException {
        Member member = memberRepository.findById(userId);

        if( member == null ){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        return wrapAndReturn( new SessionUser(member.getUsername(), member.getEncryptedPassword(), member.getRoles()), member );
    }

    /**
     * 세션에 필요한 데이터들을 넣는다.
     * @param sessionUser
     * @param member
     * @return
     */
    private UserDetails wrapAndReturn(SessionUser sessionUser, Member member){
        sessionUser.setMemberId( member.getId() );
        return sessionUser;
    }
}
