package com.sungmook.domain.validation.validator;

import com.sungmook.domain.Member;
import com.sungmook.domain.validation.constrant.NoDuplicatedUsername;
import com.sungmook.repository.MemberRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public class NoDuplicatedUsernameValidator implements ConstraintValidator<NoDuplicatedUsername, CharSequence> {



    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void initialize(NoDuplicatedUsername noDuplicatedUsername) {

    }

    @Override
    public boolean isValid(CharSequence username, ConstraintValidatorContext constraintValidatorContext) {

        if( username == null || username.length() == 0 ){
            return false;
        }

        Member member = memberRepository.findByUsername(username.toString());
        if( member != null ){
            return false;
        }

        return true;

    }
}
