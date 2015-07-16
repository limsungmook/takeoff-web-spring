package com.sungmook.service;

import com.sungmook.domain.AuthToken;
import com.sungmook.domain.User;
import com.sungmook.domain.Role;
import com.sungmook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class UserService {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScopeService scopeService;

    @Transactional
    public void socialInstantSignup(User user){
        user.setUsername("tmp@tmp.com");
        userRepository.save(user);

        scopeService.setupDefaults(user);

        user.setUsername(user.getId() + "@" + "takeoff.social");
    }

    @Transactional
    public void signup(User user){
        userRepository.save(user);
        authTokenService.generateAndSendMail(AuthToken.Type.SIGNUP, user);
    }

    @Transactional
    public void confirmSignup(Long memberId) {
        User user = userRepository.findOne(memberId);
        Set<Role> roles = user.getRoles();
        roles.clear();
        roles.add(Role.buildFromValue(Role.Value.USER));
        scopeService.setupDefaults(user);
    }
}
