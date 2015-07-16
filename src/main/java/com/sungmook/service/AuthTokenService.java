package com.sungmook.service;

import com.sungmook.domain.AuthToken;
import com.sungmook.domain.User;
import com.sungmook.exception.CommonException;
import com.sungmook.exception.InvalidTokenException;
import com.sungmook.repository.AuthTokenRepository;
import com.sungmook.repository.UserRepository;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void generateAndSendMail(AuthToken.Type type, User user){
        AuthToken authToken = new AuthToken(user);
        authToken.setType(type);
        authTokenRepository.save(authToken);

        mailService.sendConfirmMail(authToken);
        
    }

    @Transactional
    public void generateAndSendMail(AuthToken.Type type, String email){

        User user = userRepository.findByUsername(email);
        if( user == null ){
            throw new CommonException("존재하지 않는 사용자입니다.");
        }
        AuthToken authToken = new AuthToken(user);
        authToken.setType(type);
        authTokenRepository.save(authToken);

        mailService.sendConfirmMail(authToken);

    }

    @Transactional
    public AuthToken validAndGet(Long memberId, String token) {
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
        if( !authToken.getUser().getId().equals(memberId) ){
            throw new InvalidTokenException();
        }

        // 4. 만료기간 지남
        DateTime expireDate = new DateTime(authToken.getCreatedDate()).plusHours(AuthToken.EXPIRE_HOUR);
        if( expireDate.isBeforeNow() ){
            throw new InvalidTokenException();
        }

        switch (authToken.getType() ){
            case SIGNUP:
                userService.confirmSignup(authToken.getUser().getId());
                authToken.setConfirmDate(new Date());
                authTokenRepository.save(authToken);
                break;
            case FIND_PASSWORD:
                break;
            default:
                throw new InvalidTokenException();
        }

        return authToken;
    }

    /**
     * 1. 토큰을 검증하고
     * 2. 토큰을 최종적으로 컨펌하고
     * 3. 패스워드를 변경한다.
     * @param authTokenId
     * @param memberId
     * @param password
     */
    @Transactional
    public void validAndConfirmAndChangePassword(Long authTokenId, Long memberId, String password) {
        AuthToken authToken = authTokenRepository.findOne(authTokenId);
        if( !authToken.getUser().getId().equals(memberId) ) {
            throw new CommonException("토큰의 소유자가 다릅니다.");
        }
        User user = userRepository.findById(memberId);
        user.setEncryptedPasswordFromPassword(password);
        userRepository.save(user);
    }
}
