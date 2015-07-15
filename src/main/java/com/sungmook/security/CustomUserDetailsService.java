package com.sungmook.security;

import com.sungmook.domain.Member;
import com.sungmook.security.SessionUser;
import com.sungmook.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {

        logger.debug("로그인 시도할 멤버 아이디 : {}", email);
        Member member = memberRepository.findByUsername(email);

        if( member == null ){
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        return wrapAndReturn(member);
    }

    /**
     * 세션에 필요한 데이터들을 넣는다.
     * @param member
     * @return
     */
    private UserDetails wrapAndReturn(Member member) {
        SessionUser sessionUser = new SessionUser(member.getUsername(), member.getEncryptedPassword(), member.getRoles());
        sessionUser.setMemberId(member.getId());
        sessionUser.setName(member.getName());
        sessionUser.setProfilePic(member.getProfilePic());
        sessionUser.setAdmin(member.isAdmin());

        return sessionUser;
    }
}
