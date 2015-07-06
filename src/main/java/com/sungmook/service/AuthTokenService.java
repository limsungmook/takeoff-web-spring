package com.sungmook.service;

import com.sungmook.domain.AuthToken;
import com.sungmook.domain.Member;
import com.sungmook.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class AuthTokenService {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private MailService mailService;

    public void generateAndSendMail(AuthToken.Type type, Member member){
        AuthToken authToken = new AuthToken(member);
        authToken.setType(type);
        authTokenRepository.save(authToken);

        mailService.sendSignupConfirmMail(authToken);
        
    }
}
