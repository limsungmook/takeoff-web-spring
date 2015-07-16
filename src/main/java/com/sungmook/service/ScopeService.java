package com.sungmook.service;

import com.sungmook.domain.Scope;
import com.sungmook.domain.ScopeUser;
import com.sungmook.domain.User;
import com.sungmook.repository.ScopeRepository;
import com.sungmook.repository.ScopeUserRepository;
import com.sungmook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class ScopeService {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScopeRepository scopeRepository;

    @Autowired
    private ScopeUserRepository scopeUserRepository;

    @Transactional
    public void setupDefaults(User user){
        Scope friendScope = new Scope(Scope.Type.FRIEND);
        friendScope.setName("친구공개");
        friendScope.setUser(user);
        scopeRepository.save(friendScope);
        scopeUserRepository.save(new ScopeUser(friendScope, user));

        Scope meScope = new Scope(Scope.Type.ME);
        meScope.setName("나만보기");
        meScope.setUser(user);
        scopeRepository.save(meScope);
        scopeUserRepository.save(new ScopeUser(meScope, user));
    }

}
