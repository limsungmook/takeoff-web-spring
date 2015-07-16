package com.sungmook.domain.validation.validator;

import com.sungmook.domain.User;
import com.sungmook.domain.validation.constrant.NoDuplicatedUsername;
import com.sungmook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Component
public class NoDuplicatedUsernameValidator implements ConstraintValidator<NoDuplicatedUsername, CharSequence> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(NoDuplicatedUsername noDuplicatedUsername) {

    }

    @Override
    public boolean isValid(CharSequence username, ConstraintValidatorContext constraintValidatorContext) {

        if( username == null || username.length() == 0 ){
            return true;
        }

        User user = userRepository.findByUsername(username.toString());
        logger.debug("멤버 존재하는지? {}", user);
        if( user != null ){
            return false;
        }

        return true;

    }
}
