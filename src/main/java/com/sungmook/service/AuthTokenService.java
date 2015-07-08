package com.sungmook.service;

import com.sungmook.domain.AuthToken;
import com.sungmook.domain.Member;
import com.sungmook.exception.InvalidTokenException;
import com.sungmook.repository.AuthTokenRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class AuthTokenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MemberService memberService;

    public void generateAndSendMail(AuthToken.Type type, Member member){
        AuthToken authToken = new AuthToken(member);
        authToken.setType(type);
        authTokenRepository.save(authToken);

        mailService.sendSignupConfirmMail(authToken);
        
    }

    @Transactional
    public AuthToken confirmAndGet(Long memberId, String token) {
        AuthToken authToken = authTokenRepository.findByToken(token);

        // 1. 토큰 없음
        if( authToken == null ){
            throw new InvalidTokenException();
        }

        // 2. 이미 컨펌된 토큰.
        if( authToken.getConfirmDate() != null ){
            throw new InvalidTokenException();
        }

        // 3. 이상한 사용자가 토큰 사용함. 어뷰징.
        if( !authToken.getMember().getId().equals(memberId) ){
            throw new InvalidTokenException();
        }

        // 4. 만료기간 지남
        DateTime expireDate = new DateTime(authToken.getCreatedDate()).plusHours(AuthToken.EXPIRE_HOUR);
        if( expireDate.isBeforeNow() ){
            throw new InvalidTokenException();
        }

        switch (authToken.getType() ){
            case SIGNUP:
                memberService.confirmSignup(authToken.getMember().getId());
                break;
            case FIND_PASSWORD:
                break;
            default:
                throw new InvalidTokenException();
        }

        authToken.setConfirmDate(new Date());
        authTokenRepository.save(authToken);

        return authToken;
    }
}
