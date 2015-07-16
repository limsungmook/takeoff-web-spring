package com.sungmook.security;

import com.sungmook.domain.User;
import com.sungmook.repository.UserRepository;
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
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {

        logger.debug("로그인 시도할 멤버 아이디 : {}", email);
        User user = userRepository.findByUsername(email);

        if( user == null ){
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        return wrapAndReturn(user);
    }

    /**
     * 세션에 필요한 데이터들을 넣는다.
     * @param user
     * @return
     */
    private UserDetails wrapAndReturn(User user) {
        SessionUser sessionUser = new SessionUser(user.getUsername(), user.getEncryptedPassword(), user.getRoles());
        sessionUser.setUserId(user.getId());
        sessionUser.setName(user.getName());
        sessionUser.setProfilePic(user.getProfilePic());
        sessionUser.setAdmin(user.isAdmin());

        return sessionUser;
    }
}
